package com.orange.weather.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orange.weather.model.admin.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
	
	Optional<Note> findByDate(String Date);
}
