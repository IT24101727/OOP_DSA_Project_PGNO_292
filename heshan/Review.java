package com.tourism.customizationplatform.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "reviewType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AnonymousReview.class, name = "ANONYMOUS"),
        @JsonSubTypes.Type(value = VerifiedReview.class, name = "VERIFIED")
})
public abstract class Review {
    private UUID id;
    private UUID userId;
    private UUID packageId;
    private String comment;
    private int rating;

    protected Review() {} // Default constructor for Jackson

    protected Review(UUID id, UUID userId, UUID packageId, String comment, int rating) {
        this.id = id;
        this.userId = userId;
        this.packageId = packageId;
        this.comment = comment;
        this.rating = rating;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public UUID getPackageId() { return packageId; }
    public void setPackageId(UUID packageId) { this.packageId = packageId; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public abstract String getReviewType();

    public String toFileFormat() {
        String escapedComment = comment.replace(",", "\\,");
        return String.join(",", id.toString(), userId.toString(), packageId.toString(), escapedComment, String.valueOf(rating), getReviewType());
    }
}