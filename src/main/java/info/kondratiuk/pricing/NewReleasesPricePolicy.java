/*
 * License header
 */
package info.kondratiuk.pricing;

import info.kondratiuk.model.DiscountPeriod;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * @author Oleksandr Kondratiuk
 */
public class NewReleasesPricePolicy extends PricePolicy {

    @Override
    public BigDecimal calculate(int numRentedDays, double price, DiscountPeriod discountPeriod) {
        int period = discountPeriod.getNewreleasesPeriod();

        BigDecimal result = getBasicCalculation(numRentedDays, price, period);
        return result.round(new MathContext(SET_PRECISION, RoundingMode.UP));
    }

}
