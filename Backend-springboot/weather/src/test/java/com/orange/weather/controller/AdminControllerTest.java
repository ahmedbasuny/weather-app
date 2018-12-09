package com.orange.weather.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orange.weather.model.admin.MessageModel;
import com.orange.weather.model.admin.Note;
import com.orange.weather.repository.NoteRepository;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Matchers.anyString;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AdminControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private NoteRepository noteRepo;

	@InjectMocks
	private AdminController adminController;

	@Test
	public void setAdminNote() throws Exception {

		MessageModel message = new MessageModel();
		message.setMessage("Degree to is very low. Take care and wear heavy.");

		this.mockMvc
				.perform(post("/api/admin/note")
						.with(user("admin@waether.com").password("password").roles("USER", "ADMIN")).with(csrf())
						.contentType(MediaType.APPLICATION_JSON).content(asJsonString(message)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.message", is("Admin note saved successfully.")));
	}

	@Test
	public void getAdminNote() throws Exception {
		LocalDate date = LocalDate.now();
		Note note = new Note("Degree to is very low. Take care and wear heavy.", date.toString());
		Optional<Note> optionalNote = Optional.of(note);

		Mockito.when(noteRepo.findByDate(anyString())).thenReturn(optionalNote);
		this.mockMvc
				.perform(get("/api/admin/note")
						.with(user("admin@waether.com").password("password").roles("USER", "ADMIN")).with(csrf())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is("Degree to is very low. Take care and wear heavy.")));
	}

	@Test
	public void getOldNote() throws Exception {
		List<Note> notes = Arrays.asList(new Note("Degree to is very low. Take care and wear heavy.", "2018-12-08"),
				new Note("weather to is hot today.", "2018-12-07"));

		Mockito.when(noteRepo.findAll()).thenReturn(notes);
		this.mockMvc.perform(
				get("/api/admin/notes").with(user("admin@waether.com").password("password").roles("USER", "ADMIN"))
						.with(csrf()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
