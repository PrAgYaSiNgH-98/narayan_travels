package com.smart.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.smart.dao.BookTripRepository;
import com.smart.dao.GalleryRepository;
import com.smart.dao.LocationRepository;
import com.smart.dao.TripOfferRepository;
import com.smart.entities.BookTrip;
import com.smart.entities.Gallery;
import com.smart.entities.Location;
import com.smart.entities.Offer;
import com.smart.service.GalleryService;



@Controller
public class HomeController {

    @Autowired
    private TripOfferRepository tripOfferRepository;
    @Autowired
    private BookTripRepository bookTripRepository;

    @Autowired
    private LocationRepository locationRepository;

    
    
    private final GalleryService galleryService;

    @Autowired  // Ensure Spring injects the service automatically
    public HomeController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }
  
    @Autowired
    private GalleryRepository galleryRepository;

    @GetMapping("/")
    public String homePage(Model model) {
        // Fetch gallery images
        List<Gallery> galleries = galleryRepository.findAll();
        galleries.forEach(gallery -> {
            if (gallery.getImage() != null) {
                gallery.setBase64Image(Base64.getEncoder().encodeToString(gallery.getImage()));
            }
        });

        // Fetch trip offers
        List<Offer> tripOffers = tripOfferRepository.findAll();
        tripOffers.forEach(offer -> {
            if (offer.getImage() != null) {
                offer.setBase64Image(Base64.getEncoder().encodeToString(offer.getImage()));
            }
        });

        // Debugging: Print trip offers count
        System.out.println("Trip Offers Count: " + tripOffers.size());

        // Add attributes to the model
        model.addAttribute("galleries", galleries);
        model.addAttribute("tripOffers", tripOffers);
        model.addAttribute("activePage", "home");

        return "home"; // Ensure "home.html" exists
    }


    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        byte[] imageData = galleryService.getImageById(id);
        if (imageData == null) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // Change based on image type (JPEG, PNG, etc.)
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }
    
    @GetMapping("/upload")
    public String showGalleryPage(Model model) {
        List<Gallery> galleryList = galleryService.getAllGalleryItems();
        model.addAttribute("galleryList", galleryList);
        model.addAttribute("activePage", "upload");

        return "upload_gallery";  // Thymeleaf template name
    }
    


    @PostMapping("/uploadGallery")
    public String uploadGallery(@RequestParam("title") String title,
                                @RequestParam("description") String description,
                                @RequestParam("image") MultipartFile imageFile,
                                RedirectAttributes redirectAttributes) {
        try {
            if (imageFile.isEmpty()) {
                throw new RuntimeException("Image file is missing!");
            }
            Gallery gallery = new Gallery();
            gallery.setTitle(title);
            gallery.setDescription(description);
            gallery.setImage(imageFile.getBytes()); // Convert file to byte[]
            redirectAttributes.addFlashAttribute("successMessage", "Trip submitted successfully!");


            galleryRepository.save(gallery);
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving trip offer: " + e.getMessage());

        }
        return "upload_gallery"; // Redirect to home page after successful upload
    }
    
    @GetMapping("/uploadGallery/edit/{id}")
    public String editGallery(@PathVariable Long id, Model model) {
        Gallery gallery = galleryService.getGalleryById(id);
        model.addAttribute("gallery", gallery);
        return "edit-gallery"; // Ensure you have this Thymeleaf template
    }

    @PostMapping("/uploadGallery/update/{id}")
    public String updateGallery(
            @PathVariable Long id,
            @ModelAttribute Gallery gallery,
            @RequestParam(value = "image", required = false) MultipartFile file,
            RedirectAttributes redirectAttributes) {

        try {
            // Fetch the existing gallery item
            Gallery existingGallery = galleryService.getGalleryById(id);

            if (existingGallery == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Gallery item not found.");
                return "redirect:/uploadGallery";
            }

            // Update title and description
            existingGallery.setTitle(gallery.getTitle());
            existingGallery.setDescription(gallery.getDescription());

            // If a new image is uploaded, update it
            if (file != null && !file.isEmpty()) {
                existingGallery.setImage(file.getBytes()); // Convert MultipartFile to byte[]
            }

            galleryService.saveGallery(existingGallery);
            redirectAttributes.addFlashAttribute("successMessage", "Gallery updated successfully.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating gallery: " + e.getMessage());
        }
        
        return "redirect:/uploadGallery";
    }



    // Delete Gallery Item
    @PostMapping("uploadGallery/delete/{id}")
    public String deleteGallery(@PathVariable Long id) {
        galleryService.deleteGalleryItem(id);
        return "upload_gallery";
    }

	
	@GetMapping("/about")
	public String about(Model model) {
	    model.addAttribute("about", "this is about controller");
        model.addAttribute("activePage", "about");
	    return "about";
	}
	
	@GetMapping("/contact")
	public String contact(Model model) {
	    model.addAttribute("contact", "this is contact controller");
        model.addAttribute("activePage", "contact");
	    return "contact";
	}
	
	@GetMapping("/submit-trip")
	public String trips(Model model) {
	    model.addAttribute("Trips", "this is Trips controller");
        model.addAttribute("activePage", "submit-trip");
	    return "submit-trip";
	}
	
	
	@GetMapping("/trip/details/{id}")
	public String viewTripDetails(@PathVariable Long id, Model model) {
	    Optional<Offer> trip = tripOfferRepository.findById(id);

	    if (trip.isPresent()) {
	        Offer dto = new Offer();
	        dto.setId(trip.get().getId());
	        dto.setLocation(trip.get().getLocation());
	        dto.setPrice(trip.get().getPrice());

	        if (trip.get().getImage() != null) {
	            String base64Image = Base64.getEncoder().encodeToString(trip.get().getImage());
	            dto.setBase64Image(base64Image);
	        }

	        model.addAttribute("trip", dto);
	        return "trip-details";
	    } else {
	        return "error"; // Redirect to an error page if trip not found
	    }
	}

	
	 @GetMapping("/offers")
	    public String showOffers(Model model) {
	        List<Offer> tripOffers = tripOfferRepository.findAll(); // Fetch data
	        for (Offer offer : tripOffers) {
	            if (offer.getImage() != null) {
	                String base64Image = Base64.getEncoder().encodeToString(offer.getImage());
	                offer.setBase64Image(base64Image);
	            }
	        }
	        model.addAttribute("tripOffers", tripOffers);  // Pass data to Thymeleaf
	        model.addAttribute("activePage", "offers");

	        return "offer";  // Must match `offers.html`
	    }


	 @PostMapping("/submit-trip")
	 public String saveTrip(
	         @RequestParam("location") String location,
	         @RequestParam("price") Double price,
	         @RequestParam("description") String description,
	         @RequestParam("image") MultipartFile imageFile,
	         RedirectAttributes redirectAttributes) {

	     try {
	         Offer offer = new Offer();
	         offer.setLocation(location);
	         offer.setPrice(price);
	         offer.setDescription(description);
	         offer.setImage(imageFile.getBytes()); // Store image as byte[]

	         tripOfferRepository.save(offer);

	         // Success message
	         redirectAttributes.addFlashAttribute("successMessage", "Trip submitted successfully!");
	     } catch (IOException e) {
	         // Error message
	         redirectAttributes.addFlashAttribute("errorMessage", "Error saving trip offer: " + e.getMessage());
	     }

	     return "redirect:/trips"; // Redirect to trips page
	 }


	 @GetMapping("/trips")
	 public String showTrips(Model model) {
	     List<Offer> trips = tripOfferRepository.findAll();

	     // Convert image bytes to Base64 for displaying in HTML
	     for (Offer trip : trips) {
	         if (trip.getImage() != null) {
	             String base64Image = Base64.getEncoder().encodeToString(trip.getImage());
	             trip.setBase64Image(base64Image);
	         }
	     }

	     model.addAttribute("trips", trips);
	     model.addAttribute("activePage", "trips");
	     return "trips"; // Load trips.html
	 }


    @PostMapping("/delete/{id}")
    public String deleteTrip(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (tripOfferRepository.existsById(id)) {
            tripOfferRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Trip deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Trip not found!");
        }
        return "redirect:/trips"; // Redirect back to the trips list
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Offer> optionalTrip = tripOfferRepository.findById(id);
        if (optionalTrip.isPresent()) {
            model.addAttribute("trip", optionalTrip.get());
            return "edit-trip";  // Loads edit-trip.html
        } else {
            return "redirect:/trips";  // Redirect if trip not found
        }
    }

    // 2️⃣ Handle form submission and update trip in database
    @PostMapping("/update")
    public String updateTrip(@RequestParam Long id,
                             @RequestParam String location,
                             @RequestParam Double price,
                             @RequestParam String description,
                             @RequestParam(required = false) MultipartFile image,
                             RedirectAttributes redirectAttributes) {
        
        Optional<Offer> optionalTrip = tripOfferRepository.findById(id);
        
        if (optionalTrip.isPresent()) {
            Offer trip = optionalTrip.get();
            trip.setLocation(location);
            trip.setPrice(price);
            trip.setDescription(description);

            // If a new image is uploaded, update it
            if (image != null && !image.isEmpty()) {
                try {
                    byte[] imageBytes = image.getBytes();
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    trip.setBase64Image(base64Image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            tripOfferRepository.save(trip);
            redirectAttributes.addFlashAttribute("successMessage", "Trip updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Trip not found!");
        }

        return "redirect:/trips";  // Redirect back to trip list
    }
    
    @GetMapping("/bookTrip")
    public String showBookTripForm(Model model) {
        model.addAttribute("bookTrip", new BookTrip());
        model.addAttribute("activePage", "bookTrip");
        return "bookTrip"; // Ensure this matches the actual file name
    }

    @PostMapping("/bookTrip")
    public String saveBookTrip(@ModelAttribute BookTrip bookTrip, RedirectAttributes redirectAttributes) {
        try {
            // Save trip details to the database
            bookTripRepository.save(bookTrip);
            
            // Add success message
            redirectAttributes.addFlashAttribute("successMessage", "Trip booked successfully!");
        } catch (Exception e) {
            // Add error message in case of failure
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to book the trip. Please try again.");
        }

        return "redirect:/bookTrip"; // Redirect to avoid duplicate form submissions
    }
    
    @GetMapping("/bookings")
    public String showBookings(Model model) {
        try {
            List<BookTrip> bookings = bookTripRepository.findAll();
            model.addAttribute("bookings", bookings); // Add bookings data to the model
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to fetch bookings. Please try again.");
        }
        model.addAttribute("activePage", "bookings");
        return "bookings"; // Ensure `bookings.html` exists in `src/main/resources/templates`
    }



}


