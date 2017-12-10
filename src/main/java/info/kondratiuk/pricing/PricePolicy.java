/*
 * License header
 */
package info.kondratiuk.pricing;

import info.kondratiuk.model.DiscountPeriod;

import java.math.BigDecimal;

/**
 * @author Oleksandr Kondratiuk
 */
public abstract class PricePolicy implements IPricePolicy {

    public abstract BigDecimal calculate(int numRentedDays, double price, DiscountPeriod discountPeriod);

    protected static final int SET_PRECISION = 2;

    protected BigDecimal getBasicCalculation(int numRentedDays, double price, int period) {
        BigDecimal days = new BigDecimal(numRentedDays);
        BigDecimal pPrice = new BigDecimal(price);
        BigDecimal firstPeriod = new BigDecimal(period);
        BigDecimal result = null;

        if (numRentedDays > period) {
            result = pPrice.add(pPrice.multiply(days.subtract(firstPeriod)));
        } else {
            result = pPrice;
        }
        return result;
    }

}
