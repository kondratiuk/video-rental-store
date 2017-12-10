/*
 * License header
 */
package info.kondratiuk.model.form;

import org.hibernate.validator.constraints.Range;

/**
 * @author Oleksandr Kondratiuk
 */
public class DetailsForm {

    @Range(min = 1, max = 365)
    private int rentDays = 1;

    public int getRentDays() {
        return rentDays;
    }

    public void setRentDays(int rentDays) {
        this.rentDays = rentDays;
    }

    @Override
    public String toString() {
        return "DetailsForm{" +
                "rentDays=" + rentDays +
                '}';
    }
}
