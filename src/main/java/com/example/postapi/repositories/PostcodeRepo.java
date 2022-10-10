package com.example.postapi.repositories;

import com.example.postapi.entities.Postcode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostcodeRepo extends JpaRepository<Postcode, Integer> {
    public Optional<Postcode> findByNumber(Integer number);
}
