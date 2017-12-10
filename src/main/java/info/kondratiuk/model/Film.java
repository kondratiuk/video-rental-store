/*
 * License header
 */
package info.kondratiuk.model;

/**
 * @author Oleksandr Kondratiuk
 */
public class Film {

    public String title;
    public String year;
    public String imdbID;

    public Film(String title, String year, String imdbID) {
        this.title = title;
        this.year = year;
        this.imdbID = imdbID;
    }

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", imdbID='" + imdbID + '\'' +
                '}';
    }
}
