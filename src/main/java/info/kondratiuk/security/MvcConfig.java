/*
 * License header
 */
package info.kondratiuk.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Oleksandr Kondratiuk
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/film").setViewName("film");
        registry.addViewController("/order").setViewName("order");
        registry.addViewController("/locked").setViewName("locked");
        registry.addViewController("/confirmation").setViewName("confirmation");
        registry.addViewController("/about").setViewName("about");
    }

}