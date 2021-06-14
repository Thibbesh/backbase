package com.backbase.game.kalah.exception;

import com.backbase.game.kalah.model.Game;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class GameExceptionHandlerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @PostConstruct
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testGameNotFoundException() throws Exception {
        final MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.put("/games/9c021f9c-99e7-4668-aa15-2a9638523287/pits/5");
        this.mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Game not found for id 9c021f9c-99e7-4668-aa15-2a9638523287")).andReturn();
    }

    @Test
    public void testIllegalMoveException() throws Exception {
        final MockHttpServletRequestBuilder initGameRequest = MockMvcRequestBuilders.post("/games");
        final String responseString =
                this.mockMvc.perform(initGameRequest).andReturn().getResponse().getContentAsString();
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        final Game game = objectMapper.readValue(responseString, Game.class);

        final MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.put("/games/" + game.getId() + "/pits/14");
        this.mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Illegal move: It's house/kalah, Can not start from house/kalah"))
                .andReturn();
    }
}
