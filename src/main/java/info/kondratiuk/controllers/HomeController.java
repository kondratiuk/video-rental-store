/*
 * License header
 */
package info.kondratiuk.controllers;

import info.kondratiuk.provider.filmprovider.IFilmProvider;
import info.kondratiuk.provider.filmprovider.OmdbapiFilmProvider;
import info.kondratiuk.model.Film;
import info.kondratiuk.model.form.SearchForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author Oleksandr Kondratiuk
 */
@Controller
public class HomeController extends AbstractController {

    @GetMapping("/")
    public String handleHello() {
        return "hello";
    }

    @RequestMapping(value = "/home")
    public String handleHome(@Valid SearchForm searchForm,
                           BindingResult bindingResult,
                           Map<String, Object> model) {
        if (bindingResult.hasErrors()) {
            return "/home";
        }
        if (searchForm.getTitle() != null) {
            IFilmProvider omdbapiFilmProvider = new OmdbapiFilmProvider();
            Map<String, List<Film>> searchResult = omdbapiFilmProvider.getSearchResultByTitle(apikey, searchForm.getTitle());
            if (!searchResult.isEmpty()) {
                String numberSearchResult = searchResult.keySet().iterator().next();
                model.put("searchResult", "Found: " + numberSearchResult + " films but you can see only first 10 in Demo mode.");
                model.put("rentFilm", "If you like to rent a film, please, click on its title. " +
                        "If you like to get more information about a film, please, click on its IMDB ID.");
                model.put("films", searchResult.get(numberSearchResult));
            } else {
                model.put("searchResult", "Found: 0");
            }
        }
        return "home";
    }
}

