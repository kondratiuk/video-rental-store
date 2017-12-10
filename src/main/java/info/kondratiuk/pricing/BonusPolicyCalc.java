/*
 * License header
 */
package info.kondratiuk.pricing;

/**
 * @author Oleksandr Kondratiuk
 */
public class BonusPolicyCalc {

    private String apikey;
    private int premiumBonus;
    private int basicBonus;

    public BonusPolicyCalc(String apikey, int premiumBonus, int basicBonus) {
        this.apikey = apikey;
        this.premiumBonus = premiumBonus;
        this.basicBonus = basicBonus;
    }

    public int getBonusCount(String imdbID) {
        int bonusCount = 0;
        PricePolicyCalc pricePolicyCalc = new PricePolicyCalc(apikey, imdbID);
        int year = pricePolicyCalc.getYear();
        PriceType filmType = PriceType.get(year);
        switch (filmType) {
            case NEW_RELEASES:
                bonusCount = premiumBonus;
                break;
            case REGULAR:
            case OLD:
                bonusCount = basicBonus;
                break;
            default:
                try{
                    throw new IllegalStateException();
                }catch(IllegalStateException e)
                {
                    e.printStackTrace();
                }
        }
        return bonusCount;
    }

}
