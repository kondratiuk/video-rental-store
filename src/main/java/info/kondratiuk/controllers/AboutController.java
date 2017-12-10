/*
 * License header
 */
package info.kondratiuk.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Oleksandr Kondratiuk
 */
@Controller
public class AboutController {

    @GetMapping("/about")
    public String handleAbout() {
        return "about";
    }
}
