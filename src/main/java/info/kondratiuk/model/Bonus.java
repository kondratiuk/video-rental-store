/*
 * License header
 */
package info.kondratiuk.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Oleksandr Kondratiuk
 */
@Document(collection = "Bonus")
public class Bonus {

    @Id
    public String userName;

    public int bonusAmount;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(int bonusAmount) {
        this.bonusAmount = bonusAmount;
    }

    @Override
    public String toString() {
        return "Bonus{" +
                "userName='" + userName + '\'' +
                ", bonusAmount=" + bonusAmount +
                '}';
    }
}