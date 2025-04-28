package dev.cassiano.encurtador_de_url.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;



import dev.cassiano.encurtador_de_url.domain.url.controller.URLController;
import dev.cassiano.encurtador_de_url.domain.url.entity.URL;
import dev.cassiano.encurtador_de_url.domain.url.service.URLService;

@WebMvcTest(URLController.class)
@AutoConfigureMockMvc
public class URLControllerTests {
    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    private URLService service;

    private String url = "http://localhost:8080/";

    @Test
    @DisplayName("Deve conseguir criar um novo url")
    public void createNewUrlCase1() throws Exception {
        String testURL = "https://instagram.com";
        URL test = new URL();
        test.setUrl(testURL);
        
        when(this.service.createNewUrl(anyString())).thenReturn(test);
        this.mockMvc.perform(post(url+"shorten").param("url", testURL))
                        .andDo(print())
                        .andExpect(status().isCreated());
        verify(this.service, times(1)).createNewUrl(testURL);
    }

    @Test
    @DisplayName("Deve ser interceptado na requisicao caso o url seja nulo")
    public void createNewUrlCase2() throws Exception {
        String testURL = null;
        
        this.mockMvc.perform(post(url+"shorten").param("url", testURL))
                        .andDo(print())
                        .andExpect(status().isBadRequest());                        
        verify(this.service, times(0)).createNewUrl(testURL);
    }

}
