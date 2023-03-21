package com.xinyan.service;

import java.util.List;
import java.util.Optional;

import com.xinyan.model.Tutorial;
import com.xinyan.repository.TutorialRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorialService implements ITutorialService {

    @Autowired
	private TutorialRepository tutorialRepository;

    @Override
    public List<Tutorial> findAll() {

        List<Tutorial> tutorials = (List<Tutorial>) tutorialRepository.findAll();

        return tutorials;
    }

    @Override
    public List<Tutorial> findByTitleContaining(String title) {

        List<Tutorial> tutorials =  (List<Tutorial>) tutorialRepository.findByTitleContaining(title);

        return tutorials;
    }

    @Override
    public Optional<Tutorial> findById(long id) {

        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
        
        return tutorialData;
    }

    @Override
    public Tutorial saveTutorial(Tutorial tutorial) {
        Tutorial _tutorial = tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished()));
        return _tutorial;
    }

    @Override
    public void deleteById(long id) {
        tutorialRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        tutorialRepository.deleteAll();
        
    }

    @Override
    public List<Tutorial> findByPublished(boolean published) {
        List<Tutorial> tutorials = tutorialRepository.findByPublished(published);
        return tutorials;
    }

    
    
}
