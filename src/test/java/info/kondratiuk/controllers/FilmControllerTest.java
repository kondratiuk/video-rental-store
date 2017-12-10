/*
 * License header
 */
package info.kondratiuk.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oleksandr Kondratiuk
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerTest {

    @Autowired
    private FilmController controller;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testControllerInitializedCorrectly() {
        assertThat(controller).isNotNull();
    }


}