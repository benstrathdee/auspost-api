package com.example.postapi.repositories;

import com.example.postapi.entities.Suburb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SuburbRepo extends JpaRepository<Suburb, Integer> {
    public Optional<Suburb> findByName (String name);
}
