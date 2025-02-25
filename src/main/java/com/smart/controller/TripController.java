package com.smart.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.TripOfferRepository;
import com.smart.entities.Offer;

import org.springframework.ui.Model;



public class TripController {

	    @Autowired
	    private TripOfferRepository tripOfferRepository;

	 // Handle GET request for the form
	    @GetMapping("/submit-trip")
	    public String showSubmitTripForm(Model model) {
	        // Add any data you want to prepopulate the form with
	        model.addAttribute("tripOffer", new Offer());
	        return "submit-trip";  // This will render submit-trip.html from /templates
	    }

	    // Handle POST request to save the trip offer
	    @PostMapping("/submit-trip")
	    public String submitTrip(@RequestParam("location") String location,
	                             @RequestParam("price") Double price,
	                             @RequestParam("image") MultipartFile imageFile) throws IOException {
	        Offer tripOffer = new Offer();
	        tripOffer.setLocation(location);
	        tripOffer.setPrice(price);
	        tripOffer.setImage(imageFile.getBytes());

	        tripOfferRepository.save(tripOffer);

	        return "redirect:/success";  // Redirect to a success page after submission
	    }
	    @GetMapping("/trips")
	    public String getTrips(Model model) {
	        List<Offer> trips = tripOfferRepository.findAll();
	        model.addAttribute("trips", trips);
	        return "trips";  // Thymeleaf template to display trips
	    }
	}


