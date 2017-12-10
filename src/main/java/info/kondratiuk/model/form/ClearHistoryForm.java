/*
 * License header
 */
package info.kondratiuk.model.form;

/**
 * @author Oleksandr Kondratiuk
 */
public class ClearHistoryForm {

    private String clearHistory;

    public String getClearHistory() {
        return clearHistory;
    }

    public void setClearHistory(String clearHistory) {
        this.clearHistory = clearHistory;
    }

    @Override
    public String toString() {
        return "ClearHistoryForm{" +
                "clearHistory='" + clearHistory + '\'' +
                '}';
    }
}
