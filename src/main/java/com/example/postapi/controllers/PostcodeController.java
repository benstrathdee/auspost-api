package com.example.postapi.controllers;

import com.example.postapi.services.SuburbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping(path = "/postcodes")
public class PostcodeController {
    @Autowired
    SuburbService suburbService;

    // GET /postcodes?suburb={suburbName}
    @GetMapping
    public ResponseEntity<Object> getPostcode (@NotBlank @RequestParam String suburb) {
        Integer postcode = suburbService.getPostcodeBySuburb(suburb);
        if (postcode != null) {
            return new ResponseEntity<>(postcode, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The specified suburb could not be found.", HttpStatus.NOT_FOUND);
        }
    }
}
