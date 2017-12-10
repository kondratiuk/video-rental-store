/*
 * License header
 */
package info.kondratiuk.pricing;

/**
 * @author Oleksandr Kondratiuk
 */
public enum PriceType {
    NEW_RELEASES("New Releases"),
    REGULAR("Regular film"),
    OLD("Old film");

    public static final int NEWRELEASES_LOW_THRESHOLD = 2016;
    public static final int REGULAR_LOW_THRESHOLD = 2000;
    private final String type;

    PriceType(String type) {
        this.type = type;
    }

    public static PriceType get(int year) {
        if (year >= NEWRELEASES_LOW_THRESHOLD) {
            return NEW_RELEASES;
        }
        if (year >= REGULAR_LOW_THRESHOLD && year < NEWRELEASES_LOW_THRESHOLD) {
            return REGULAR;
        }
        return OLD;
    }

    @Override
    public String toString() {
        return type;
    }
}
