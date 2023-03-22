package com.xinyan;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;

import java.util.Optional;

import com.xinyan.model.Tutorial;
import com.xinyan.repository.TutorialRepository;
import com.xinyan.service.TutorialService;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock
    private TutorialRepository repository;

    @InjectMocks
    private TutorialService service;


    @Test
    public void should_create_tutorial(){
        Tutorial tutorial = new Tutorial((long)1, "Springboot", "Description", true);
        when(repository.save(any(Tutorial.class))).thenReturn(tutorial);
        Tutorial _tutorial = service.saveTutorial(tutorial);
        assertThat(_tutorial).hasFieldOrPropertyWithValue("title", "Springboot");
        assertThat(_tutorial).hasFieldOrPropertyWithValue("description", "Description");
        assertThat(_tutorial).hasFieldOrPropertyWithValue("published", true);
    }

    @Test
    public void should_update_tutorial(){

        Tutorial tut2 = new Tutorial((long)2,"Tut#2", "Desc#2", false);
        Tutorial updatedTut = new Tutorial((long)2, "updated Tut#2", "updated Desc#2", true);
        when(repository.findById((long) 2)).thenReturn(Optional.of(tut2));
        when(repository.save(any(Tutorial.class))).thenReturn(updatedTut);
        
        Tutorial _tutorial = service.updateTutorial((long)2, updatedTut);

        assertThat(_tutorial.getTitle()).isEqualTo(updatedTut.getTitle());
        assertThat(_tutorial.getDescription()).isEqualTo(updatedTut.getDescription());
        assertThat(_tutorial.isPublished()).isEqualTo(updatedTut.isPublished());

    }
}
