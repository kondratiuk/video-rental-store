/*
 * License header
 */
package info.kondratiuk.model.form;

/**
 * @author Oleksandr Kondratiuk
 */
public class StopRentForm {

    private String stopImdb;

    public String getStopImdb() {
        return stopImdb;
    }

    public void setStopImdb(String stopImdb) {
        this.stopImdb = stopImdb;
    }

    @Override
    public String toString() {
        return "StopRentForm{" +
                "stopImdb='" + stopImdb + '\'' +
                '}';
    }
}
