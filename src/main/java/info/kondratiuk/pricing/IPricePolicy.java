/*
 * License header
 */
package info.kondratiuk.pricing;

import info.kondratiuk.model.DiscountPeriod;

import java.math.BigDecimal;

/**
 * @author Oleksandr Kondratiuk
 */
public interface IPricePolicy {

    BigDecimal calculate(int numRentedDays, double price, DiscountPeriod discountPeriod);

}
