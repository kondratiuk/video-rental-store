/*
 * License header
 */
package info.kondratiuk.pricing;

import info.kondratiuk.model.DiscountPeriod;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * @author Oleksandr Kondratiuk
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PriceCalculationsTest {

    private @Value("${premium.price}")
    double premiumPrice;

    private @Value("${basic.price}")
    double basicPrice;

    private @Value("${newreleases.period}")
    int newreleasesPeriod;

    private @Value("${regularfilms.period}")
    int regularfilmsPeriod;

    private @Value("${oldfilms.period}")
    int oldfilmsPeriod;

    private int rentDays = 6;

    @Test
    public void testPriceCalculation() {
        BigDecimal precisePrice = null;
        IPricePolicy pricePolicy;

        DiscountPeriod discountPeriod = new DiscountPeriod();
        discountPeriod.setNewreleasesPeriod(newreleasesPeriod);
        discountPeriod.setRegularfilmsPeriod(regularfilmsPeriod);
        discountPeriod.setOldfilmsPeriod(oldfilmsPeriod);

        for (PriceType priceType : PriceType.values()) {
            switch (priceType) {
                case NEW_RELEASES:
                    pricePolicy = new NewReleasesPricePolicy();
                    precisePrice = pricePolicy.calculate(rentDays, premiumPrice, discountPeriod);
                    assertEquals(240, precisePrice.doubleValue(), 0);
                    break;
                case REGULAR:
                    pricePolicy = new RegularPricePolicy();
                    precisePrice = pricePolicy.calculate(rentDays, basicPrice, discountPeriod);
                    assertEquals(120, precisePrice.doubleValue(), 0);
                    break;
                case OLD:
                    pricePolicy = new OldPricePolicy();
                    precisePrice = pricePolicy.calculate(rentDays, basicPrice, discountPeriod);
                    assertEquals(60, precisePrice.doubleValue(), 0);
                    break;
                default:
                    try {
                        throw new IllegalStateException();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
            }
        }
    }
}
