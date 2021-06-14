package com.backbase.game.kalah.controller;

import com.backbase.game.kalah.model.Game;
import com.backbase.game.kalah.service.GameService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
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
public class GameControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private GameService service;

    private MockMvc mockMvc;

    @PostConstruct
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    /**
     *
     * @throws Exception
     */
    @Test
    @DirtiesContext
    public void test_New_Game_Created() throws Exception {
        final MockHttpServletRequestBuilder playGameRequest = MockMvcRequestBuilders.post("/games");
        this.mockMvc.perform(playGameRequest).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.uri").isNotEmpty()).andReturn();
    }

    /**
     *
     * @throws Exception
     */
    @Test
    @DirtiesContext
    public void test_Make_A_Move_Stones() throws Exception {
        final Game game = this.service.createGame();
        final MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.put("/games/" + game.getId() + "/pits/1");
        this.mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(game.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.uri").value(Matchers.endsWith(game.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.1").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.2").value("7"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.3").value("7"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.4").value("7"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.5").value("7"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.6").value("7"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.7").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.8").value("6"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.9").value("6"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.10").value("6"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.11").value("6"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.12").value("6"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.13").value("6"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.14").value("0")).andReturn();
    }
}
