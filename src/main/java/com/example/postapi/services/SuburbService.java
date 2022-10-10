package com.example.postapi.services;

import com.example.postapi.entities.Postcode;
import com.example.postapi.entities.Suburb;
import com.example.postapi.repositories.PostcodeRepo;
import com.example.postapi.repositories.SuburbRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class SuburbService {
    @Autowired
    private SuburbRepo suburbRepo;

    // Looks up a suburb by name and returns the postcode associated
    public Integer getPostcodeBySuburb (String suburbName) {
        Optional<Suburb> fetchedSuburb = suburbRepo.findByName(suburbName);
        if (fetchedSuburb.isPresent()) {
            Suburb suburb = fetchedSuburb.get();
            Postcode postcode = suburb.getPostcode();
            return postcode.getNumber();
        } else {
            // Suburb not found, will bubble up to 404
            return null;
        }
    }
}
