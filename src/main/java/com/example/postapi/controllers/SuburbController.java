package com.example.postapi.controllers;

import com.example.postapi.dtos.NewSuburbWithPostcode;
import com.example.postapi.dtos.SavedSuburbWithPostcode;
import com.example.postapi.services.PostcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "/suburbs")
public class SuburbController {
    @Autowired
    PostcodeService postcodeService;

    // GET /suburbs?postcode={postcode}
    @GetMapping
    public ResponseEntity<?> getSuburbs (@NotNull @RequestParam Integer postcode) {
        List<String> suburbs = postcodeService.getSuburbsByPostcode(postcode);
        if (suburbs != null) {
            return new ResponseEntity<>(suburbs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The specified postcode could not be found.", HttpStatus.NOT_FOUND);
        }
    }

    // POST /suburbs
    // Expects a JSON request body with shape defined by NewSuburbWithPostcode
    @PostMapping
    public ResponseEntity<SavedSuburbWithPostcode> addSuburb (@Valid @RequestBody NewSuburbWithPostcode data) {
        SavedSuburbWithPostcode newLink = postcodeService.create(data);
        return new ResponseEntity<>(newLink, HttpStatus.CREATED);
    }
}
