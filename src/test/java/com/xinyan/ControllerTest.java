package com.xinyan;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xinyan.controller.MyController;
import com.xinyan.model.Tutorial;
import com.xinyan.service.ITutorialService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@WebMvcTest(MyController.class)
public class ControllerTest {
    

    @MockBean
    private ITutorialService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateTutorial() throws Exception {
      Tutorial tutorial = new Tutorial(1, "Spring Boot", "Description", true);
  
      mockMvc.perform(post("/api/tutorials").contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(tutorial)))
          .andExpect(status().isCreated())
          .andDo(print());
    }
    
    @Test
    void shouldReturnTutorial() throws Exception {
      long id = 1L;
      Tutorial tutorial = new Tutorial(id, "Spring Boot", "Description", true);
  
      when(service.findTutorial(id)).thenReturn(tutorial);
      mockMvc.perform(get("/api/tutorials/{id}", id)).andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(id))
          .andExpect(jsonPath("$.title").value(tutorial.getTitle()))
          .andExpect(jsonPath("$.description").value(tutorial.getDescription()))
          .andExpect(jsonPath("$.published").value(tutorial.isPublished()))
          .andDo(print());
    }

    @Test
    void shouldReturnNotFoundTutorial() throws Exception {
      long id = 1L;
  
      when(service.findTutorial(id)).thenReturn(null);
      mockMvc.perform(get("/api/tutorials/{id}", id))
           .andExpect(status().isNotFound())
           .andDo(print());
    }

    @Test
    void shouldReturnListOfTutorials() throws Exception {
          List<Tutorial> tutorials = new ArrayList<>(
          Arrays.asList(new Tutorial(1, "Spring Boot @WebMvcTest 1", "Description 1", true),
                        new Tutorial(2, "Spring Boot @WebMvcTest 2", "Description 2", true),
                        new Tutorial(3, "Spring Boot @WebMvcTest 3", "Description 3", true)));
  
      when(service.findAllTutorials(null)).thenReturn(tutorials);
      mockMvc.perform(get("/api/tutorials"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.size()").value(tutorials.size()))
          .andDo(print());
    }

    @Test
    void shouldReturnListOfTutorialsWithFilter() throws Exception {
      List<Tutorial> tutorials = new ArrayList<>(
      Arrays.asList(new Tutorial(1, "Spring Boot @WebMvcTest", "Description 1", true),
                    new Tutorial(3, "Spring Boot Web MVC", "Description 3", true)));
  
      String title = "Boot";
      MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
      paramsMap.add("title", title);
  
      when(service.findAllTutorials(title)).thenReturn(tutorials);
      mockMvc.perform(get("/api/tutorials").params(paramsMap))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.size()").value(tutorials.size()))
          .andDo(print());
    }

    @Test
    void shouldReturnNoContentWhenFilter() throws Exception {
      String title = "abc";
      MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
      paramsMap.add("title", title);
      
      List<Tutorial> empty_tutorials = Collections.emptyList();
  
      when(service.findAllTutorials(title)).thenReturn(empty_tutorials);
      mockMvc.perform(get("/api/tutorials").params(paramsMap))
          .andExpect(status().isNoContent())
          .andDo(print());
    }

    @Test
    void shouldUpdateTutorial() throws Exception {
      long id = 1L;
      Tutorial updatedtutorial = new Tutorial(id, "springUpdated", "Updated", true);
  
      when(service.updateTutorial(any(long.class), any(Tutorial.class))).thenReturn(updatedtutorial);
  
      mockMvc.perform(put("/api/tutorials/{id}", id).contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(updatedtutorial)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.title").value(updatedtutorial.getTitle()))
          .andExpect(jsonPath("$.description").value(updatedtutorial.getDescription()))
          .andExpect(jsonPath("$.published").value(updatedtutorial.isPublished()))
          .andDo(print());
    }

    @Test
    void shouldReturnNotFoundUpdateTutorial() throws Exception {
      long id = 1L;
  
      Tutorial updatedtutorial = new Tutorial(id, "Updated", "Updated", true);
  
      when(service.updateTutorial(any(long.class), any(Tutorial.class))).thenReturn(null);
  
      mockMvc.perform(put("/api/tutorials/{id}", id).contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(updatedtutorial)))
          .andExpect(status().isNotFound())
          .andDo(print());
    }

    @Test
    void shouldDeleteTutorial() throws Exception {
      long id = 1L;
  
      doNothing().when(service).deleteById(id);
      mockMvc.perform(delete("/api/tutorials/{id}", id))
           .andExpect(status().isNoContent())
           .andDo(print());
    } 
    
    @Test
    void shouldDeleteAllTutorials() throws Exception {
      doNothing().when(service).deleteAll();
      mockMvc.perform(delete("/api/tutorials"))
           .andExpect(status().isNoContent())
           .andDo(print());
    }
}
