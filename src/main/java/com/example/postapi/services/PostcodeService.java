package com.example.postapi.services;

import com.example.postapi.dtos.NewSuburbWithPostcode;
import com.example.postapi.dtos.SavedSuburbWithPostcode;
import com.example.postapi.entities.Postcode;
import com.example.postapi.entities.Suburb;
import com.example.postapi.repositories.PostcodeRepo;
import com.example.postapi.repositories.SuburbRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostcodeService {
    @Autowired
    private PostcodeRepo postcodeRepo;
    @Autowired
    private SuburbRepo suburbRepo;

    // Looks up a postcode and returns the names of associated suburbs
    public List<String> getSuburbsByPostcode (Integer postcodeNumber) {
        Optional<Postcode> fetchedPostcode = postcodeRepo.findByNumber(postcodeNumber);
        if (fetchedPostcode.isPresent()) {
            Postcode postcode = fetchedPostcode.get();
            List<Suburb> suburbs = postcode.getSuburbs();
            return suburbs.stream().map(Suburb::getName).toList();
        } else {
            // Postcode not found, will bubble up to 404
            return null;
        }
    }

    // Creates a new suburb-postcode link
    public SavedSuburbWithPostcode create (NewSuburbWithPostcode data) {
        // Check if postcode already exists in DB
        Optional<Postcode> fetchedPostcode = postcodeRepo.findByNumber(data.getPostcode());
        Postcode postcode;
        if (fetchedPostcode.isPresent()) {
            postcode = fetchedPostcode.get();
        } else {
            // If postcode doesn't exist, add to DB
            postcode = new Postcode(data.getPostcode());
            postcodeRepo.save(postcode);
        }

        // Check if suburb already exists in DB
        Optional<Suburb> fetchedSuburb = suburbRepo.findByName(data.getSuburb());
        Suburb suburb;
        if (fetchedSuburb.isPresent()) {
            suburb = fetchedSuburb.get();
            if (suburb.getPostcode() != postcode) {
                // Set the suburb's postcode to the one specified in the call
                suburb.setPostcode(postcode);
            }
        } else {
            // If suburb doesn't exist link to postcode and add to DB
            suburb = new Suburb(data.getSuburb(), postcode);
        }
        suburbRepo.save(suburb);

        // Return an object representing the suburb and its postcode
        return new SavedSuburbWithPostcode(suburb.getName(), suburb.getPostcode().getNumber());
    }
}
