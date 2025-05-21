package com.tourism.customizationplatform.model;

import java.util.UUID;

public class InternationalPackage extends TravelPackage {
    public InternationalPackage(UUID id, String name, double price, String destination, int duration) {
        super(id, name, price, destination, duration);
    }

    @Override
    public String getPackageType() {
        return "INTERNATIONAL";
    }
}
