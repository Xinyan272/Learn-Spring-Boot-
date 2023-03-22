package com.xinyan.service;
import java.util.List;
import com.xinyan.model.Tutorial;

public interface ITutorialService {
    List<Tutorial> findAllTutorials(String title);
    Tutorial findTutorial(long id);
    Tutorial saveTutorial(Tutorial tutorial);
    Tutorial updateTutorial(long id, Tutorial tutorial);

    void deleteById(long id);
    void deleteAll();

    List<Tutorial> findPublishedTutorials(boolean published);
}
