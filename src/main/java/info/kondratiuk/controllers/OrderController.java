/*
 * License header
 */
package info.kondratiuk.controllers;

import info.kondratiuk.model.Bonus;
import info.kondratiuk.model.Order;
import info.kondratiuk.model.form.ClearHistoryForm;
import info.kondratiuk.model.form.OrderForm;
import info.kondratiuk.model.form.StopRentForm;
import info.kondratiuk.provider.filmprovider.IFilmProvider;
import info.kondratiuk.provider.filmprovider.OmdbapiFilmProvider;
import info.kondratiuk.repository.BonusRepository;
import info.kondratiuk.repository.OrderRepository;
import info.kondratiuk.utils.Util;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Oleksandr Kondratiuk
 */
@Controller
public class OrderController extends AbstractRepoController {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final NumberFormat PRICE_FORMATTER = new DecimalFormat("#0.00");

    protected OrderController(OrderRepository orderRepository, BonusRepository bonusRepository) {
        super(orderRepository, bonusRepository);
    }

    @GetMapping(value = "/order")
    public String handleOrders(Map<String, Object> model) {
        model.put("userName", "User name: " + Util.getCurrentUserName());
        model.put("bonusPoint", "You collected bonus points: " + getBonusPoints());
        model.put("orders", getOrders());

        return "order";
    }

    @PostMapping("/order/clear")
    public String clearOrders(@ModelAttribute ClearHistoryForm сlearHistoryForm,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/order";
        }
        String isClear = сlearHistoryForm.getClearHistory();
        if (isClear.equalsIgnoreCase("clear")) {
            String userName = Util.getCurrentUserName();
            List<Order> orders = orderRepository.findAll();
            for (Order order : orders) {
                if (order.getUserName().equalsIgnoreCase(userName)) {
                    orderRepository.delete(order.getImdbID());
                }
            }
            List<Bonus> bonuses = bonusRepository.findAll();
            for (Bonus bonus : bonuses) {
                if (bonus.getUserName().equalsIgnoreCase(userName)) {
                    bonusRepository.delete(bonus.getUserName());
                }
            }
        }
        return "redirect:/order";
    }

    @PostMapping("/order/imdb")
    public String showForm12(@ModelAttribute StopRentForm stopRentForm,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/order";
        }
        String imdbID = stopRentForm.getStopImdb();
        String userName = Util.getCurrentUserName();
        List<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
            if (order.getImdbID().equalsIgnoreCase(imdbID) && order.getUserName().equalsIgnoreCase(userName)
                    && order.getEndDate() == null) {
                Order endedOder = new Order();
                endedOder.setImdbID(order.getImdbID());
                endedOder.setUserName(order.getUserName());
                endedOder.setStartDate(order.getStartDate());
                endedOder.setInitPeriod(order.getInitPeriod());
                endedOder.setInitPrice(order.getInitPrice());

                LocalDateTime fromDateTime = order.getStartDate();
                LocalDateTime toDateTime = LocalDateTime.now();
                LocalDateTime tempDateTime = LocalDateTime.from(fromDateTime);
                long days = tempDateTime.until(toDateTime, ChronoUnit.DAYS);
                // If film rented within one day it should be paid as for full day since default time unit is a day
                if (days == 0) {
                    days = 1;
                }
                // This block means that we do not decrease price if User returns film earlier than he/she booked it initially
                double chargePrice = 0;
                if (days > order.getInitPeriod()) {
                    double fullPrice = getPrice(imdbID, (int) days);
                    chargePrice = fullPrice - order.getInitPrice();
                }

                endedOder.setEndDate(toDateTime);
                endedOder.setChargePrice(chargePrice);
                orderRepository.save(endedOder);
            }
        }
        return "redirect:/order";
    }

    private List<OrderForm> getOrders() {
        List<Order> orders = orderRepository.findAll();
        String userName = Util.getCurrentUserName();
        int count = 0;
        double initPriceSum = 0;
        double chargePriceSum = 0;
        double totalPriceSum = 0;
        List<OrderForm> result = new ArrayList<>(orders.size());
        for (Order order : orders) {
            if (order.getUserName().equalsIgnoreCase(userName)) {
                OrderForm orderForm = new OrderForm();
                int actualCount = ++count;
                orderForm.setCount(Integer.toString(actualCount));

                String imdbId = order.getImdbID();
                orderForm.setImdbID(imdbId);

                IFilmProvider omdbapiFilmProvider = new OmdbapiFilmProvider();
                String year = omdbapiFilmProvider.getYearOfFilm(apikey, imdbId);
                orderForm.setYear(year);

                String title = omdbapiFilmProvider.getTitleOfFilm(apikey, order.getImdbID());
                orderForm.setTitle(title);

                LocalDateTime startDate = order.getStartDate();
                String formattedStartDate = startDate.format(DATE_TIME_FORMATTER);
                orderForm.setStartDate(formattedStartDate);

                int initPeriod = order.getInitPeriod();
                orderForm.setInitPeriod(Integer.toString(initPeriod));

                double initPrice = order.getInitPrice();
                String initPriceFormatted = PRICE_FORMATTER.format(initPrice);
                orderForm.setInitPrice(initPriceFormatted);
                initPriceSum += initPrice;

                if (order.getEndDate() != null) {
                    LocalDateTime endDate = order.getEndDate();
                    String formattedEndDate = endDate.format(DATE_TIME_FORMATTER);
                    orderForm.setEndDate(formattedEndDate);
                }

                if (order.getEndDate() != null) {
                    double chargePrice = order.getChargePrice();
                    String chargePriceFormatted = PRICE_FORMATTER.format(chargePrice);
                    orderForm.setChargePrice(chargePriceFormatted);
                    chargePriceSum += chargePrice;

                    double totalPrice = order.getInitPrice() + order.getChargePrice();
                    String totalPriceFormatted = PRICE_FORMATTER.format(totalPrice);
                    orderForm.setTotalPrice(totalPriceFormatted);
                    totalPriceSum += totalPrice;
                }
                result.add(orderForm);
            }
        }
        addSumToForm(initPriceSum, chargePriceSum, totalPriceSum, result);

        return result;
    }

    private void addSumToForm(double initPriceSum, double chargePriceSum, double totalPriceSum, List<OrderForm> result) {
        OrderForm orderSumForm = new OrderForm();
        String initPriceSumFormatted = PRICE_FORMATTER.format(initPriceSum);
        orderSumForm.setInitPrice(initPriceSumFormatted);
        String chargePriceSumFormatted = PRICE_FORMATTER.format(chargePriceSum);
        orderSumForm.setChargePrice(chargePriceSumFormatted);
        String totalPriceSumFormatted = PRICE_FORMATTER.format(totalPriceSum);
        orderSumForm.setTotalPrice(totalPriceSumFormatted);
        result.add(orderSumForm);
    }

    private int getBonusPoints() {
        List<Bonus> bonuses = bonusRepository.findAll();
        String userName = Util.getCurrentUserName();
        for (Bonus bonus : bonuses) {
            if (bonus.getUserName().equalsIgnoreCase(userName)) {
                return bonus.getBonusAmount();
            }
        }
        return 0;
    }
}