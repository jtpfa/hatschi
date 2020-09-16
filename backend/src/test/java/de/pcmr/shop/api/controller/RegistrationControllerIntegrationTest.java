package de.pcmr.shop.api.controller;

import de.pcmr.shop.AbstractIntegrationTest;
import org.apache.commons.io.FileUtils;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * End-to-End integration test for the registration api.
 *
 * @author Fynn Lohse
 */

@AutoConfigureMockMvc
public class RegistrationControllerIntegrationTest extends AbstractIntegrationTest {

    private final static String APPLICATION_JSON = ContentType.APPLICATION_JSON.toString();
    private final static String UTF_8 = "UTF-8";
    private final static String PATH_TO_JSON = "src/test/resources/json/registration";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegistrationSuccess() throws Exception {
        String json = loadJsonFromFile("success.json");
        mockMvc.perform(post("/api/registration").contentType(APPLICATION_JSON).content(json)).andExpect(status().isOk());
    }

    @Test
    public void testRegistrationFailedPassword() throws Exception {
        String json = loadJsonFromFile("invalidPassword.json");
        mockMvc.perform(post("/api/registration").contentType(APPLICATION_JSON).content(json)).andExpect(status().is4xxClientError());
    }

    @Test
    public void testRegistrationFailedName() throws Exception {
        String json = loadJsonFromFile("invalidName.json");
        mockMvc.perform(post("/api/registration").contentType(APPLICATION_JSON).content(json)).andExpect(status().is4xxClientError());
    }

    @Test
    public void testRegistrationFailedEmail() throws Exception {
        String json = loadJsonFromFile("invalidEmail.json");
        mockMvc.perform(post("/api/registration").contentType(APPLICATION_JSON).content(json)).andExpect(status().is4xxClientError());
    }

    @Test
    public void testRegistrationFailedAlreadyExists() throws Exception {
        String json = loadJsonFromFile("success.json");
        mockMvc.perform(post("/api/registration").contentType(APPLICATION_JSON).content(json)).andExpect(status().isOk());
        mockMvc.perform(post("/api/registration").contentType(APPLICATION_JSON).content(json)).andExpect(status().is4xxClientError());
    }

    private String loadJsonFromFile(String file) throws IOException {
        return FileUtils.readFileToString(new File(PATH_TO_JSON + "/" + file), UTF_8);
    }
}
