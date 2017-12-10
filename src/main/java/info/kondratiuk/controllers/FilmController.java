/*
 * License header
 */
package info.kondratiuk.controllers;

import info.kondratiuk.provider.filmprovider.IFilmProvider;
import info.kondratiuk.provider.filmprovider.OmdbapiFilmProvider;
import info.kondratiuk.model.Bonus;
import info.kondratiuk.model.Order;
import info.kondratiuk.model.form.ConfirmForm;
import info.kondratiuk.model.form.DetailsForm;
import info.kondratiuk.pricing.*;
import info.kondratiuk.repository.BonusRepository;
import info.kondratiuk.repository.OrderRepository;
import info.kondratiuk.utils.Util;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author Oleksandr Kondratiuk
 */
@Controller
public class FilmController extends AbstractRepoController {

    private @Value("${premium.bonus}")
    int premiumBonus;

    private @Value("${basic.bonus}")
    int basicBonus;

    protected FilmController(OrderRepository orderRepository, BonusRepository bonusRepository) {
        super(orderRepository, bonusRepository);
    }

    enum FilmRentStatus {
        IN_PROGRESS,
        RENT_ENDS,
        NEVER_RENTED;
    }

    @GetMapping("/film")
    public String handleFilm(@Valid DetailsForm detailsForm,
                              @RequestParam(value = "imdbid") String imdbid,
                              BindingResult bindingResult,
                              Map<String, Object> model) {
        if (bindingResult.hasErrors()) {
            return "/film";
        }
        IFilmProvider omdbapiFilmProvider = new OmdbapiFilmProvider();
        List<Pair> filmDetails = omdbapiFilmProvider.getSearchResultByImdbId(apikey, imdbid);
        String posterUrl = omdbapiFilmProvider.getPosterByImdbId(apikey, imdbid);
        if (filmDetails == null) {
            model.put("noFilmDetails", "Unfortunately, detailed information for the Film is not available at the moment.");
        } else {
            FilmRentStatus rentStatus = getRentStatus(imdbid);
            switch (rentStatus) {
                case RENT_ENDS:
                    model.put("viewed", "This film has been viewed by you already but you can rent it again!");
                    break;
                case IN_PROGRESS:
                    return "locked";
                case NEVER_RENTED:
                    model.put("viewed", "This film has NOT been viewed by you yet!");
                    break;
                default:
                    try{
                        throw new IllegalStateException();
                    }catch(IllegalStateException e)
                    {
                        e.printStackTrace();
                    }
            }
            int numRentedDays = detailsForm.getRentDays();
            if (numRentedDays > 0) {
                double filmPrice = getPrice(imdbid, numRentedDays);
                model.put("filmPrice", "Your price: " + filmPrice + " SEK");
            }
            model.put("filmDetails", filmDetails);
            model.put("basicPrice", basicPrice);
            model.put("premiumPrice", premiumPrice);
            model.put("posterUrl", posterUrl);
        }
        return "/film";
    }

    @PostMapping("/film/rent")
    public String handleRenting(@ModelAttribute ConfirmForm confirmForm) {
        String imdbID = confirmForm.getCurrentImdb();
        String userName = Util.getCurrentUserName();
        LocalDateTime startDate = LocalDateTime.now();
        int initPeriod = Integer.valueOf(confirmForm.getCurrentPeriod());
        double initPrice = getPrice(imdbID, Integer.valueOf(initPeriod));

        Order order = new Order();
        order.setImdbID(imdbID);
        order.setUserName(userName);
        order.setStartDate(startDate);
        order.setInitPeriod(initPeriod);
        order.setInitPrice(initPrice);
        orderRepository.save(order);

        updateBonusForCurrentUser(imdbID, userName);

        return "confirmation";
    }

    private void updateBonusForCurrentUser(String imdbID, String userName) {
        BonusPolicyCalc bonusPolicyCalc = new BonusPolicyCalc(apikey, premiumBonus, basicBonus);
        int bonusCount = bonusPolicyCalc.getBonusCount(imdbID);
        int currentBonusAmount = getBonusPoints();
        int sum = currentBonusAmount + bonusCount;

        Bonus bonus = new Bonus();
        bonus.setUserName(userName);
        bonus.setBonusAmount(sum);
        bonusRepository.save(bonus);
    }

    private FilmRentStatus getRentStatus(String imdbid) {
        List<Order> orders = orderRepository.findAll();
        String userName = Util.getCurrentUserName();
        for (Order order : orders) {
            if (order.getImdbID().equalsIgnoreCase(imdbid) && order.getUserName().equalsIgnoreCase(userName)) {
                if (order.getEndDate() != null) {
                    return FilmRentStatus.RENT_ENDS;
                } else {
                    return FilmRentStatus.IN_PROGRESS;
                }
            }
        }
        return FilmRentStatus.NEVER_RENTED;
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