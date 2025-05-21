package com.tourism.customizationplatform.model;

import java.util.UUID;

public class Booking {
    private UUID id;
    private UUID userId;
    private UUID packageId;
    private String travelDate;
    private int travelers;
    private String bookingType;

    public Booking(UUID id, UUID userId, UUID packageId, String travelDate, int travelers, String bookingType) {
        this.id = id;
        this.userId = userId;
        this.packageId = packageId;
        this.travelDate = travelDate;
        this.travelers = travelers;
        this.bookingType = bookingType;
    }

    public Booking() {}

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public UUID getPackageId() { return packageId; }
    public void setPackageId(UUID packageId) { this.packageId = packageId; }
    public String getTravelDate() { return travelDate; }
    public void setTravelDate(String travelDate) { this.travelDate = travelDate; }
    public int getTravelers() { return travelers; }
    public void setTravelers(int travelers) { this.travelers = travelers; }
    public String getBookingType() { return bookingType; }
    public void setBookingType(String bookingType) { this.bookingType = bookingType; }

    public String cancel() {
        return "GROUP".equals(bookingType)
                ? "Group booking cancelled with 50% refund"
                : "Individual booking cancelled with full refund";
    }

    public String toFileFormat() {
        String escapedTravelDate = travelDate.replace(",", "\\,");
        return String.join(",", id.toString(), userId.toString(), packageId.toString(),
                escapedTravelDate, String.valueOf(travelers), bookingType);
    }
}
