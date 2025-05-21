package com.tourism.customizationplatform.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.security.PublicKey;
import java.util.UUID;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "packageType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DomesticPackage.class, name = "DOMESTIC"),
        @JsonSubTypes.Type(value = InternationalPackage.class, name = "INTERNATIONAL")
})

public abstract class TravelPackage implements Comparable<TravelPackage> {
    private UUID id;
    private String name;
    private double price;
    private String destination;
    private int duration;

    protected TravelPackage(UUID id, String name, double price, String destination, int duration) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.destination = destination;
        this.duration = duration;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    @Override
    public int compareTo(TravelPackage packid ) {
        return this.id.compareTo(packid.id);
    }

    public abstract String getPackageType();

    public String toFileFormat() {
        return String.join(",", id.toString(), name, String.valueOf(price), destination, String.valueOf(duration), getPackageType());
    }
}