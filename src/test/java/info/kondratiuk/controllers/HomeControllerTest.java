/*
 * License header
 */
package info.kondratiuk.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Oleksandr Kondratiuk
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

    @Autowired
    private HomeController controller;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testControllerInitializedCorrectly() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void testStatusAndContentType_Root() throws Exception {
        mockMvc.perform(get( "/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(new MediaType("text", "html", Charset.forName("UTF-8"))))
                .andExpect(content().encoding("UTF-8"));
    }
}
