/*
 * License header
 */
package info.kondratiuk.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

/**
 * @author Oleksandr Kondratiuk
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserCredentialsTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testStatusAndContentType_UserA_negative() throws Exception {
        RequestBuilder request = post("/login")
                .param("username", "Adam")
                .param("password", "4");

        mockMvc
                .perform(request)
                .andExpect(redirectedUrl("/login?error"));
    }

    @Test
    public void testStatusAndContentType_UserB_negative() throws Exception {
        RequestBuilder request = post("/login")
                .param("username", "Eva")
                .param("password", "4");

        mockMvc
                .perform(request)
                .andExpect(redirectedUrl("/login?error"));
    }

    @Test
    public void testStatusAndContentType_UserA_positive() throws Exception {
        RequestBuilder request = post("/login")
                .param("username", "Adam")
                .param("password", "1");

        mockMvc
                .perform(request)
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testStatusAndContentType_UserB_positive() throws Exception {
        RequestBuilder request = post("/login")
                .param("username", "Eva")
                .param("password", "2");

        mockMvc
                .perform(request)
                .andExpect(redirectedUrl("/"));
    }


}
