/*
 * License header
 */
package info.kondratiuk.controllers;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Oleksandr Kondratiuk
 */
public abstract class AbstractController {

    protected @Value("${omdbapi.apikey}")
    String apikey;

}
