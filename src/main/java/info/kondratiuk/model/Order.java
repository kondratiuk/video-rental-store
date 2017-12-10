package info.kondratiuk.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Order")
public class Order {

    @Id
    public String imdbID;

    public String userName;

    public LocalDateTime startDate;

    public LocalDateTime endDate;

    public int initPeriod;

    public double initPrice;

    public double chargePrice;

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public int getInitPeriod() {
        return initPeriod;
    }

    public void setInitPeriod(int initPeriod) {
        this.initPeriod = initPeriod;
    }

    public double getInitPrice() {
        return initPrice;
    }

    public void setInitPrice(double initPrice) {
        this.initPrice = initPrice;
    }

    public double getChargePrice() {
        return chargePrice;
    }

    public void setChargePrice(double chargePrice) {
        this.chargePrice = chargePrice;
    }



    @Override
    public String toString() {
        return "Order{" +
                "imdbID='" + imdbID + '\'' +
                ", userName='" + userName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", initPeriod=" + initPeriod +
                ", initPrice=" + initPrice +
                ", chargePrice=" + chargePrice +
                '}';
    }
}