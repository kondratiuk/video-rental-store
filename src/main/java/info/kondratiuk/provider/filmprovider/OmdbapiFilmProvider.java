/*
 * License header
 */
package info.kondratiuk.provider.filmprovider;

import info.kondratiuk.model.Film;
import info.kondratiuk.provider.JsonReader;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Oleksandr Kondratiuk
 */
public class OmdbapiFilmProvider implements IFilmProvider {

    private static final String FILM_PROVIDER_BASEURL = "http://www.omdbapi.com/?apikey=";
    private static final String KEYWORDS_CONNECTOR = "%20";
    private static final String PATTERN = "\\s+";
    private static final String SEARCH_BY_KEYWORD = "&s=";
    private static final String SEARCH_BY_IMDB = "&i=";

    private @Value("${omdbapi.apikey}")
    String apikey;

    @Override
    public Map<String, List<Film>> getSearchResultByTitle(String apikey, String searchkey) {
        List<Film> results = new ArrayList(10);

        // Handle search requests with many key words
        String searchNoSpaces = searchkey.trim();
        String search = searchNoSpaces.replaceAll(PATTERN, KEYWORDS_CONNECTOR);

        String path = FILM_PROVIDER_BASEURL + apikey + SEARCH_BY_KEYWORD + search;
        JSONArray jsonArray = JsonReader.readJsonArrFromUrl(path);
        JSONObject jsonResponce = null;
        String totalFilms = null;
        try {
            jsonResponce = jsonArray.getJSONObject(0);

            String response = (String) jsonResponce.get("Response");
            if (response.equalsIgnoreCase("false")) {
                return Collections.emptyMap();
            }
            totalFilms = (String) jsonResponce.get("totalResults");

            JSONArray searchResults = (JSONArray) jsonResponce.get("Search");
            for (int i = 0; i < searchResults.length(); i++) {
                String title = (String) ((JSONObject) searchResults.get(i)).get("Title");
                String year = (String) ((JSONObject) searchResults.get(i)).get("Year");
                String imdbID = (String) ((JSONObject) searchResults.get(i)).get("imdbID");
                results.add(new Film(title, year, imdbID));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Collections.singletonMap(totalFilms, results);
    }

    @Override
    public List<Pair> getSearchResultByImdbId(String apikey, String imdbid) {
        JSONObject jsonObject = getJsonObject(apikey, imdbid);
        if (jsonObject == null) return null;

        List<Pair> list = new ArrayList<>();
        try {
            if (jsonObject.get("Title") != null) {
                list.add(new Pair("Title", (String) jsonObject.get("Title")));
            }

            if (jsonObject.get("Year") != null) {
                list.add(new Pair("Year", (String) jsonObject.get("Year")));
            }
            if (jsonObject.get("imdbID") != null) {
                list.add(new Pair("IMDB ID ", (String) jsonObject.get("imdbID")));
            }
            if (jsonObject.get("imdbRating") != null) {
                list.add(new Pair("IMDB Rating", (String) jsonObject.get("imdbRating")));
            }
            if (jsonObject.get("Runtime") != null) {
                list.add(new Pair("Runtime", (String) jsonObject.get("Runtime")));
            }
            if (jsonObject.get("Language") != null) {
                list.add(new Pair("Language", (String) jsonObject.get("Language")));
            }
            if (jsonObject.get("Genre") != null) {
                list.add(new Pair("Genre", (String) jsonObject.get("Genre")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public String getPosterByImdbId(String apikey, String imdbid) {
        JSONObject jsonObject = getJsonObject(apikey, imdbid);
        if (jsonObject == null) return null;
        try {
            if (jsonObject.get("Poster") != null) {
                return (String) jsonObject.get("Poster");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getYearOfFilm(String apikey, String imdbid) {
        JSONObject jsonObject = getJsonObject(apikey, imdbid);
        if (jsonObject == null) return null;
        try {
            if (jsonObject.get("Year") != null) {
                return (String) jsonObject.get("Year");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getTitleOfFilm(String apikey, String imdbid) {
        JSONObject jsonObject = getJsonObject(apikey, imdbid);
        if (jsonObject == null) return null;
        try {
            if (jsonObject.get("Title") != null) {
                return (String) jsonObject.get("Title");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject getJsonObject(String apikey, String imdbid) {
        JSONObject jsonObject = null;
        String path = FILM_PROVIDER_BASEURL + apikey + SEARCH_BY_IMDB + imdbid;
        jsonObject = JsonReader.readJsonFromUrl(path);
        String response = null;
        try {
            response = (String) jsonObject.get("Response");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (response.equalsIgnoreCase("false")) {
            return null;
        }
        return jsonObject;
    }

}
