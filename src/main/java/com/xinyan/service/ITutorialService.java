package com.xinyan.service;
import java.util.List;
import java.util.Optional;

import com.xinyan.model.Tutorial;

public interface ITutorialService {
    List<Tutorial> findAll();
    List<Tutorial> findByTitleContaining(String title);
    Optional<Tutorial> findById(long id);
    Tutorial saveTutorial(Tutorial tutorial);
    void deleteById(long id);
    void deleteAll();

    List<Tutorial> findByPublished(boolean published);
}
