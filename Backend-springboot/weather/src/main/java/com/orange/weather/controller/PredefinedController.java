package com.orange.weather.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orange.weather.model.PredefinedNotes;
import com.orange.weather.model.admin.PredefinedNotesModel;
import com.orange.weather.model.login.ResponseMessage;
import com.orange.weather.repository.PredefinedNoteRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/predefined")
public class PredefinedController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	PredefinedNoteRepository preNoteRepo;
	
	/**
	 * to set predefined message "automatic messages".
	 * @param	object of predefined model contains the values  
	 * @return resource of custom message contains message of success and http ok status.
	 * @author Ahmed Basuny 
	 */
	@PostMapping("/notes")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> setPredefinedNotes(@RequestBody PredefinedNotesModel predefinedModel) {

		logger.info(" setPredefinedNotes function is called ..");
		logger.debug(" setPredefinedNotes function parameters are .." + predefinedModel.toString());
		
		preNoteRepo.deleteAll();
		
		PredefinedNotes preNote1 = new PredefinedNotes("value1", predefinedModel.getValue1());
		PredefinedNotes preNote2 = new PredefinedNotes("value2", predefinedModel.getValue2());
		PredefinedNotes preNote3 = new PredefinedNotes("value3", predefinedModel.getValue3());
		PredefinedNotes preNote4 = new PredefinedNotes("value4", predefinedModel.getValue4());
		PredefinedNotes preNote5 = new PredefinedNotes("value5", predefinedModel.getValue5());
		
		preNoteRepo.saveAndFlush(preNote1);
		preNoteRepo.saveAndFlush(preNote2);
		preNoteRepo.saveAndFlush(preNote3);
		preNoteRepo.saveAndFlush(preNote4);
		preNoteRepo.saveAndFlush(preNote5);
		
		
		return new ResponseEntity<>(new ResponseMessage("Predefined notes Saved successfully."), HttpStatus.CREATED);
	}

	/**
	 * to get predefined message "automatic messages" from DB.
	 * @return response of predefined model.
	 * @author Ahmed Basuny 
	 */
	@GetMapping("/notes")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<PredefinedNotesModel> getPreNotes() {

		logger.info(" getPreNotes function is called ..");

		
		Optional<PredefinedNotes> preNote1 = preNoteRepo.findByValue("value1");
		Optional<PredefinedNotes> preNote2 = preNoteRepo.findByValue("value2");
		Optional<PredefinedNotes> preNote3 = preNoteRepo.findByValue("value3");
		Optional<PredefinedNotes> preNote4 = preNoteRepo.findByValue("value4");
		Optional<PredefinedNotes> preNote5 = preNoteRepo.findByValue("value5");
		
		PredefinedNotesModel preResponse = new PredefinedNotesModel();
		preResponse.setValue1(preNote1.get().getNote());
		preResponse.setValue2(preNote2.get().getNote());
		preResponse.setValue3(preNote3.get().getNote());
		preResponse.setValue4(preNote4.get().getNote());
		preResponse.setValue5(preNote5.get().getNote());
		
		return new ResponseEntity<PredefinedNotesModel>(preResponse, HttpStatus.OK);
	}

}
