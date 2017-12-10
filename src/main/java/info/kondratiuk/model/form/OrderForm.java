/*
 * License header
 */
package info.kondratiuk.model.form;

/**
 * @author Oleksandr Kondratiuk
 */
public class OrderForm {

    private String count;
    private String imdbID;
    private String year;
    private String title;
    private String startDate;
    private String initPeriod;
    private String initPrice;
    private String endDate;
    private String chargePrice;
    private String totalPrice;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getInitPeriod() {
        return initPeriod;
    }

    public void setInitPeriod(String initPeriod) {
        this.initPeriod = initPeriod;
    }

    public String getInitPrice() {
        return initPrice;
    }

    public void setInitPrice(String initPrice) {
        this.initPrice = initPrice;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getChargePrice() {
        return chargePrice;
    }

    public void setChargePrice(String chargePrice) {
        this.chargePrice = chargePrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderForm{" +
                "count=" + count +
                ", imdbID='" + imdbID + '\'' +
                ", year='" + year + '\'' +
                ", title='" + title + '\'' +
                ", startDate='" + startDate + '\'' +
                ", initPeriod=" + initPeriod +
                ", initPrice='" + initPrice + '\'' +
                ", endDate='" + endDate + '\'' +
                ", chargePrice='" + chargePrice + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                '}';
    }
}
