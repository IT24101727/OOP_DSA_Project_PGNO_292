package com.tourism.customizationplatform.controller;

import com.tourism.customizationplatform.model.Booking;
import com.tourism.customizationplatform.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping
    public ResponseEntity<List<Booking>> getBookingsByUser(@RequestParam String userId) {
        try {
            List<Booking> bookings = bookingService.getBookingsByUser(userId);
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            System.err.println("Error fetching bookings: " + e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }
    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody Booking booking) {
        try {
            if (booking.getPackageId() == null || booking.getUserId() == null ||
                    booking.getTravelDate() == null || booking.getTravelers() <= 0 || booking.getBookingType() == null) {
                return ResponseEntity.badRequest().body("Invalid booking data");
            }

            booking.setId(UUID.randomUUID());
            bookingService.saveBooking(booking);
            return ResponseEntity.ok("Booking created successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to create booking: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBooking(@PathVariable String id, @RequestBody Booking booking) {
        try {
            if (booking.getTravelDate() == null || booking.getTravelers() <= 0 ||
                    booking.getBookingType() == null || booking.getPackageId() == null ||
                    booking.getUserId() == null) {
                return ResponseEntity.badRequest().body("Invalid booking data");
            }
            booking.setId(UUID.fromString(id));
            bookingService.updateBooking(booking);
            return ResponseEntity.ok("Booking updated successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to update booking: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid booking ID format");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable String id) {
        try {
            bookingService.deleteBooking(UUID.fromString(id));
            return ResponseEntity.ok("Booking deleted successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to delete booking: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid booking ID format");
        }
    }
}




