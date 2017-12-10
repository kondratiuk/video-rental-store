/*
 * License header
 */
package info.kondratiuk.model.form;

/**
 * @author Oleksandr Kondratiuk
 */
public class ConfirmForm {

    private String currentImdb;

    private String currentPeriod;

    public String getCurrentImdb() {
        return currentImdb;
    }

    public void setCurrentImdb(String currentImdb) {
        this.currentImdb = currentImdb;
    }

    public String getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(String currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    @Override
    public String toString() {
        return "RentConfirm{" +
                "currentImdb='" + currentImdb + '\'' +
                ", currentPeriod='" + currentPeriod + '\'' +
                '}';
    }
}
