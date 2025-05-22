package com.tourism.customizationplatform.model;

import java.util.UUID;

public class VerifiedReview extends Review {
    public VerifiedReview(UUID id, UUID userId, UUID packageId, String comment, int rating) {
        super(id, userId, packageId, comment, rating);
    }

    @Override
    public String getReviewType() {
        return "VERIFIED";
    }
}