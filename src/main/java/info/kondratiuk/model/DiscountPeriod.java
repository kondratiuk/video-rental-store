/*
 * License header
 */
package info.kondratiuk.model;

/**
 * @author Oleksandr Kondratiuk
 */
public class DiscountPeriod {

    private int newreleasesPeriod;
    private int regularfilmsPeriod;
    private int oldfilmsPeriod;

    public int getNewreleasesPeriod() {
        return newreleasesPeriod;
    }

    public void setNewreleasesPeriod(int newreleasesPeriod) {
        this.newreleasesPeriod = newreleasesPeriod;
    }

    public int getRegularfilmsPeriod() {
        return regularfilmsPeriod;
    }

    public void setRegularfilmsPeriod(int regularfilmsPeriod) {
        this.regularfilmsPeriod = regularfilmsPeriod;
    }

    public int getOldfilmsPeriod() {
        return oldfilmsPeriod;
    }

    public void setOldfilmsPeriod(int oldfilmsPeriod) {
        this.oldfilmsPeriod = oldfilmsPeriod;
    }

    @Override
    public String toString() {
        return "DiscountPeriod{" +
                "newreleasesPeriod=" + newreleasesPeriod +
                ", regularfilmsPeriod=" + regularfilmsPeriod +
                ", oldfilmsPeriod=" + oldfilmsPeriod +
                '}';
    }
}
