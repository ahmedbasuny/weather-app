package com.orange.weather.controller;

import java.time.LocalDate;
import java.util.List;
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

import com.orange.weather.model.admin.MessageModel;
import com.orange.weather.model.admin.Note;
import com.orange.weather.model.login.ResponseMessage;
import com.orange.weather.repository.NoteRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	NoteRepository noteRepo;
	
	/**
	 * to set admin notes and save it in DB.
	 * @param	object of message to be saved contain the admin message.  
	 * @return resource of custom message contains message of success and http ok status.
	 * @author Ahmed Basuny 
	 */
	@PostMapping("/note")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> setNote(@RequestBody MessageModel messageModel) {

		logger.info(" setNote function is called ..");
		logger.debug(" setNote function parameters are .." + messageModel.getMessage());

		LocalDate date = LocalDate.now();

		Optional<Note> note = noteRepo.findByDate(date.toString());
		if (note.isPresent()) {
			note.get().setMessage(messageModel.getMessage());
			noteRepo.saveAndFlush(note.get());
			return new ResponseEntity<>(new ResponseMessage("Admin note updated successfully."), HttpStatus.CREATED);
		} else {
			Note newNote = new Note(messageModel.getMessage(), date.toString());
			noteRepo.saveAndFlush(newNote);
			return new ResponseEntity<>(new ResponseMessage("Admin note saved successfully."), HttpStatus.CREATED);
		}
	}
	
	/**
	 * to get admin notes.
	 * @return resource of the admin message and http ok status.
	 * @author Ahmed Basuny 
	 */
	@GetMapping("/note")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> getAdminNote() {

		logger.info(" getAdminNote function is called ..");

		LocalDate date = LocalDate.now();

		Optional<Note> note = noteRepo.findByDate(date.toString());
		if (note.isPresent()) {
			return new ResponseEntity<>(new ResponseMessage(note.get().getMessage()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ResponseMessage("NOT_FOUND."), HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * to get all previous admin notes. 
	 * @return resource of list of notes messages and http ok status.
	 * @author Ahmed Basuny 
	 */
	@GetMapping("/notes")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<Note>> getAllAdminNotes() {

		logger.info(" getAllAdminNotes function is called ..");

		List<Note> notes = noteRepo.findAll();
			return new ResponseEntity<>(notes, HttpStatus.OK);
	}

}
