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
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    Booking booking = new Booking();
                    booking.setId(UUID.fromString(parts[0]));
                    booking.setPackageId(UUID.fromString(parts[1]));
                    booking.setUserId(UUID.fromString(parts[2]));
                    booking.setTravelDate(parts[3]);
                    booking.setTravelers(Integer.parseInt(parts[4]));
                    booking.setBookingType(parts[5]);
                    bookings.add(booking);
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
                String[] parts = line.split(",");
                if (parts.length == 6 && parts[2].equals(userId)) {
                    Booking booking = new Booking();
                    booking.setId(UUID.fromString(parts[0]));
                    booking.setPackageId(UUID.fromString(parts[1]));
                    booking.setUserId(UUID.fromString(parts[2]));
                    booking.setTravelDate(parts[3]);
                    booking.setTravelers(Integer.parseInt(parts[4]));
                    booking.setBookingType(parts[5]);
                    bookings.add(booking);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading bookings: " + e.getMessage());
        }
        return bookings;
    }

    public List<Booking> getBookingsByUserId(String userId) {
        List<Booking> allBookings = readBookingsFromFile(); // You should already have this method
        return allBookings.stream()
                .filter(b -> b.getUserId().equals(userId))
                .collect(Collectors.toList());
    }


    public void updateBooking(Booking booking) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(bookingsFilePath));
        List<String> updatedLines = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts[0].equals(booking.getId().toString())) {
                // Preserve packageId and userId from original
                booking.setPackageId(UUID.fromString(parts[1]));
                booking.setUserId(UUID.fromString(parts[2]));
                updatedLines.add(booking.toFileFormat());
            } else {
                updatedLines.add(line);
            }
        }
        Files.write(Paths.get(bookingsFilePath), updatedLines);
    }


    public void deleteBooking(UUID bookingId) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(bookingsFilePath));
        List<String> updatedLines = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            if (!parts[0].equals(bookingId.toString())) {
                updatedLines.add(line);
            }
        }
        Files.write(Paths.get(bookingsFilePath), updatedLines);
    }

    public void saveBooking(Booking booking) throws IOException {
        String line = booking.toFileFormat();
        Files.write(Paths.get(bookingsFilePath), List.of(line), java.nio.file.StandardOpenOption.APPEND, java.nio.file.StandardOpenOption.CREATE);
    }

}
