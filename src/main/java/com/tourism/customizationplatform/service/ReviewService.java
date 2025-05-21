package com.tourism.customizationplatform.service;

import com.tourism.customizationplatform.model.*;
import com.tourism.customizationplatform.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {
    @Value("${file.paths.reviews}")
    private String reviewsFilePath;

    // Create
    public void createReview(Review review) throws IOException {
        FileUtil.writeToFile(reviewsFilePath, review.toFileFormat(), true);
    }

    // Read
    public Review findReviewById(String id) throws IOException {
        List<String> lines = FileUtil.readFromFile(reviewsFilePath);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts[0].equals(id)) {
                return createReviewFromData(parts);
            }
        }
        return null;
    }

    public List<Review> getAllReviews() throws IOException {
        List<Review> reviews = new ArrayList<>();
        List<String> lines = FileUtil.readFromFile(reviewsFilePath);
        for (String line : lines) {
            String[] parts = line.split(",");
            reviews.add(createReviewFromData(parts));
        }
        return reviews;
    }

    public List<Review> getReviewsByPackage(String packageId) throws IOException {
        List<Review> reviews = new ArrayList<>();
        List<String> lines = FileUtil.readFromFile(reviewsFilePath);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts[2].equals(packageId)) {
                reviews.add(createReviewFromData(parts));
            }
        }
        return reviews;
    }

    // Update
    public void updateReview(Review updatedReview) throws IOException {
        List<String> lines = FileUtil.readFromFile(reviewsFilePath);
        List<String> updatedLines = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts[0].equals(updatedReview.getId().toString())) {
                updatedLines.add(updatedReview.toFileFormat());
            } else {
                updatedLines.add(line);
            }
        }
        FileUtil.overwriteFile(reviewsFilePath, updatedLines);
    }

    // Delete
    public void deleteReview(String id) throws IOException {
        List<String> lines = FileUtil.readFromFile(reviewsFilePath);
        List<String> updatedLines = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            if (!parts[0].equals(id)) {
                updatedLines.add(line);
            }
        }
        FileUtil.overwriteFile(reviewsFilePath, updatedLines);
    }



    private Review createReviewFromData(String[] parts) {
        UUID id = UUID.fromString(parts[0]);
        UUID userId = UUID.fromString(parts[1]);
        UUID packageId = UUID.fromString(parts[2]);
        String comment = parts[3].replace("\\,", ","); // Unescape commas
        int rating = Integer.parseInt(parts[4]);
        String reviewType = parts[5];
        return "ANONYMOUS".equals(reviewType)
                ? new AnonymousReview(id, userId, packageId, comment, rating)
                : new VerifiedReview(id, userId, packageId, comment, rating);
    }
}