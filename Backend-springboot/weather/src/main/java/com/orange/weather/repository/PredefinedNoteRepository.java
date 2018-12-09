package com.orange.weather.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orange.weather.model.PredefinedNotes;

public interface PredefinedNoteRepository extends JpaRepository<PredefinedNotes, Integer> {
	
	Optional<PredefinedNotes> findByValue(String value);
}
