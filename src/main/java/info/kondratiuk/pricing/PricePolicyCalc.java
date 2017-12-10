/*
 * License header
 */
package info.kondratiuk.pricing;

import info.kondratiuk.provider.filmprovider.IFilmProvider;
import info.kondratiuk.provider.filmprovider.OmdbapiFilmProvider;
import info.kondratiuk.model.DiscountPeriod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * @author Oleksandr Kondratiuk
 */
public class PricePolicyCalc {
    public static final int YEAR_LONG = 4;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String apikey;
    private String imdbid;
    private int numRentedDays;
    private DiscountPeriod discountPeriod;

    public PricePolicyCalc(String apikey, String imdbid) {
        this.apikey = apikey;
        this.imdbid = imdbid;
    }

    public PricePolicyCalc(String apikey, String imdbid, int numRentedDays, DiscountPeriod discountPeriod) {
        this.apikey = apikey;
        this.imdbid = imdbid;
        this.numRentedDays = numRentedDays;
        this.discountPeriod = discountPeriod;
    }

    public double getPrice(double premiumPrice, double basicPrice) {
        BigDecimal precisePrice = null;
        int year = getYear();
        PriceType filmType = PriceType.get(year);
        IPricePolicy pricePolicy;
        switch (filmType) {
            case NEW_RELEASES:
                pricePolicy = new NewReleasesPricePolicy();
                precisePrice = pricePolicy.calculate(numRentedDays, premiumPrice, discountPeriod);
                break;
            case REGULAR:
                pricePolicy = new RegularPricePolicy();
                precisePrice = pricePolicy.calculate(numRentedDays, basicPrice, discountPeriod);
                break;
            case OLD:
                pricePolicy = new OldPricePolicy();
                precisePrice = pricePolicy.calculate(numRentedDays, basicPrice, discountPeriod);
                break;
            default:
                try{
                    throw new IllegalStateException();
                }catch(IllegalStateException e)
                {
                    e.printStackTrace();
                }
        }
        return precisePrice.doubleValue();
    }

    public int getYear() {
        IFilmProvider omdbapiFilmProvider = new OmdbapiFilmProvider();
        String yearRaw = omdbapiFilmProvider.getYearOfFilm(apikey, imdbid);
        int year = 0;
        if (yearRaw != null || yearRaw.length() < YEAR_LONG) {
            if (yearRaw.length() > YEAR_LONG) {
                yearRaw = yearRaw.substring(0, YEAR_LONG);
            }
            year = Integer.valueOf(yearRaw);
        } else {
            logger.error("Price can't be calculated for imdbid: " + imdbid);
            return 0;
        }
        return year;
    }
}
