package com.orange.weather.controller;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.orange.weather.model.PredefinedNotes;
import com.orange.weather.model.admin.PredefinedNotesModel;
import com.orange.weather.repository.PredefinedNoteRepository;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Matchers.anyString;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class PredefinedControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PredefinedNoteRepository preNoteRepo;

	@InjectMocks
	private PredefinedController preNoteController;

	@Test
	public void setPredefinedNotes() throws Exception {

		JSONObject notesObject = new JSONObject();
		notesObject.put("value1", "dgree more than 1");
		notesObject.put("value2", "degree more than 10");
		notesObject.put("value3", "degree more than 15");
		notesObject.put("value4", "degree more than 20");
		notesObject.put("value5", "degree less than 1");
		
		this.mockMvc
				.perform(post("/api/predefined/notes")
						.with(user("admin@waether.com").password("password").roles("USER", "ADMIN")).with(csrf())
						.contentType(MediaType.APPLICATION_JSON).content(notesObject.toString()))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.message", is("Predefined notes Saved successfully.")));
	}
	
	@Test
	public void getPreNotes() throws Exception {
		PredefinedNotesModel preResponse = new PredefinedNotesModel();
		preResponse.setValue1("dgree more than 1");
		preResponse.setValue2("degree more than 10");
		preResponse.setValue3("degree more than 15");
		preResponse.setValue4("degree more than 20");
		preResponse.setValue5("degree less than 1");
		
		PredefinedNotes preNoteModel = new PredefinedNotes("value", "weather is great"); 

		Mockito.when(preNoteRepo.findByValue(anyString())).thenReturn(Optional.of(preNoteModel));
	
		this.mockMvc.perform(
				get("/api/predefined/notes").with(user("admin@waether.com").password("password").roles("USER", "ADMIN"))
						.with(csrf()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.value1", is("weather is great")))
				.andExpect(jsonPath("$.value2", is("weather is great")))
				.andExpect(jsonPath("$.value3", is("weather is great")))
				.andExpect(jsonPath("$.value4", is("weather is great")))
				.andExpect(jsonPath("$.value5", is("weather is great")));
	}
}
