/*
 * License header
 */
package info.kondratiuk.controllers;

import info.kondratiuk.model.DiscountPeriod;
import info.kondratiuk.pricing.PricePolicyCalc;
import info.kondratiuk.repository.BonusRepository;
import info.kondratiuk.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Oleksandr Kondratiuk
 */
public abstract class AbstractRepoController extends AbstractController{

    protected @Value("${premium.price}")
    double premiumPrice;

    protected @Value("${basic.price}")
    double basicPrice;

    protected @Value("${newreleases.period}")
    int newreleasesPeriod;

    protected @Value("${regularfilms.period}")
    int regularfilmsPeriod;

    protected @Value("${oldfilms.period}")
    int oldfilmsPeriod;

    protected final OrderRepository orderRepository;
    protected final BonusRepository bonusRepository;

    protected AbstractRepoController(OrderRepository orderRepository, BonusRepository bonusRepository) {
        this.orderRepository = orderRepository;
        this.bonusRepository = bonusRepository;
    }

    protected double getPrice(String imdbid, int numRentedDays) {
        DiscountPeriod discountPeriod = new DiscountPeriod();
        discountPeriod.setNewreleasesPeriod(newreleasesPeriod);
        discountPeriod.setRegularfilmsPeriod(regularfilmsPeriod);
        discountPeriod.setOldfilmsPeriod(oldfilmsPeriod);

        PricePolicyCalc pricePolicyCalc = new PricePolicyCalc(apikey, imdbid, numRentedDays, discountPeriod);
        return pricePolicyCalc.getPrice(premiumPrice, basicPrice);
    }

}
