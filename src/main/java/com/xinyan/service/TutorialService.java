package com.xinyan.service;

import java.util.ArrayList;
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
    public List<Tutorial> findAllTutorials(String title) {

        List<Tutorial> tutorials = new ArrayList<Tutorial>();

        if (title == null) 
            tutorials = (List<Tutorial>) tutorialRepository.findAll();
        else 
            tutorials =  (List<Tutorial>) tutorialRepository.findByTitleContaining(title);
        return tutorials;
    }


    @Override
    public Tutorial findTutorial(long id) {

        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
        if (tutorialData.isPresent()) {
            return tutorialData.get();
        }
        else {
            return null;
        }
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
    public List<Tutorial> findPublishedTutorials(boolean published) {
        List<Tutorial> tutorials = tutorialRepository.findByPublished(published);
        return tutorials;
    }


    @Override
    public Tutorial updateTutorial(long id, Tutorial tutorial) {

        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
        if (tutorialData.isPresent()) {
			Tutorial _tutorial = tutorialData.get();
			_tutorial.setTitle(tutorial.getTitle());
			_tutorial.setDescription(tutorial.getDescription());
			_tutorial.setPublished(tutorial.isPublished());
            Tutorial new_tutorial = tutorialRepository.save(_tutorial);
            return new_tutorial;
            }
        else {
            return null;
        }

        }

    
    
}
