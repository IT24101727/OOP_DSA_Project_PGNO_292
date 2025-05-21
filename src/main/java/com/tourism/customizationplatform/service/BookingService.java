package com.tourism.customizationplatform.service;

import com.tourism.customizationplatform.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final String bookingsFilePath;

    @Autowired
    public BookingService(@Value("${file.paths.bookings}") String bookingsFilePath) {
        this.bookingsFilePath = bookingsFilePath;
    }

    public List<Booking> getAllBookings() {
        return readBookingsFromFile();
    }

    private List<Booking> readBookingsFromFile() {
        List<Booking> bookings = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(bookingsFilePath));
            for (String line : lines) {
                try {
                    String[] parts = line.split(",", -1);
                    if (parts.length == 6) {
                        Booking booking = new Booking();
                        booking.setId(UUID.fromString(parts[0]));
                        booking.setUserId(UUID.fromString(parts[1]));
                        booking.setPackageId(UUID.fromString(parts[2]));
                        booking.setTravelDate(parts[3].replace("\\,", ","));
                        booking.setTravelers(Integer.parseInt(parts[4]));
                        booking.setBookingType(parts[5]);
                        bookings.add(booking);
                    } else {
                        System.err.println("Invalid booking line format: " + line);
                    }
                } catch (IllegalArgumentException  e) {
                    System.err.println("Error parsing booking line: " + line + ", Error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading bookings: " + e.getMessage());
        }
        return bookings;
    }

    public List<Booking> getBookingsByUser(String userId) {
        List<Booking> bookings = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(bookingsFilePath));
            for (String line : lines) {
                try {
                    String[] parts = line.split(",", -1);
                    if (parts.length == 6 && parts[1].equals(userId)) { // userId is in parts[1]
                        Booking booking = new Booking();
                        booking.setId(UUID.fromString(parts[0]));
                        booking.setUserId(UUID.fromString(parts[1]));
                        booking.setPackageId(UUID.fromString(parts[2]));
                        booking.setTravelDate(parts[3]);
                        booking.setTravelers(Integer.parseInt(parts[4]));
                        booking.setBookingType(parts[5]);
                        bookings.add(booking);
                    }
                } catch (IllegalArgumentException  e) {
                    System.err.println("Error parsing booking line: " + line + ", Error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading bookings: " + e.getMessage());
        }
        return bookings;
    }

    public void updateBooking(Booking booking) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(bookingsFilePath));
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;
        for (String line : lines) {
            String[] parts = line.split(",", -1);
            if (parts.length == 6 && parts[0].equals(booking.getId().toString())) {
                updatedLines.add(booking.toFileFormat());
                found = true;
            } else {
                updatedLines.add(line);
            }
        }
        if (!found) {
            throw new IOException("Booking not found for ID: " + booking.getId());
        }
        Files.write(Paths.get(bookingsFilePath), updatedLines);
    }

    public void deleteBooking(UUID bookingId) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(bookingsFilePath));
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;
        for (String line : lines) {
            String[] parts = line.split(",", -1);
            if (parts.length == 6 && !parts[0].equals(bookingId.toString())) {
                updatedLines.add(line);
            } else {
                found = true;
            }
        }
        if (!found) {
            throw new IOException("Booking not found for ID: " + bookingId);
        }
        Files.write(Paths.get(bookingsFilePath), updatedLines);
    }

    public void saveBooking(Booking booking) throws IOException {
        String line = booking.toFileFormat();
        System.out.println("Saving booking: " + line); // Log for debugging
        Files.write(Paths.get(bookingsFilePath), List.of(line), java.nio.file.StandardOpenOption.APPEND, java.nio.file.StandardOpenOption.CREATE);
    }
}