/*
 * License header
 */
package info.kondratiuk.provider.filmprovider;

import info.kondratiuk.model.Film;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

/**
 * @author Oleksandr Kondratiuk
 */
public interface IFilmProvider {
    Map<String, List<Film>> getSearchResultByTitle(String apikey, String searchkey);
    List<Pair> getSearchResultByImdbId(String apikey, String imdbid);
    String getPosterByImdbId(String apikey, String imdbid);
    String getYearOfFilm(String apikey, String imdbid);
    String getTitleOfFilm(String apikey, String imdbid);
}
