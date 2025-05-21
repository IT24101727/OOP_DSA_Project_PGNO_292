package com.tourism.customizationplatform.controller;

import com.tourism.customizationplatform.model.Review;
import com.tourism.customizationplatform.service.BSTService;
import com.tourism.customizationplatform.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private BSTService bstService;

    @PostMapping
    public ResponseEntity<String> createReview(@RequestBody Review review) throws IOException {
        if (bstService.findPackageById(review.getPackageId().toString()) == null) {
            return ResponseEntity.badRequest().body("Invalid packageId: Package does not exist");
        }
        review.setId(UUID.randomUUID());
        reviewService.createReview(review);
        return ResponseEntity.ok("Review submitted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReview(@PathVariable String id) throws IOException {
        Review review = reviewService.findReviewById(id);
        return review != null ? ResponseEntity.ok(review) : ResponseEntity.notFound().build();
    }

    @GetMapping("/package/{packageId}")
    public ResponseEntity<List<Review>> getReviewsByPackage(@PathVariable String packageId) throws IOException {
        return ResponseEntity.ok(reviewService.getReviewsByPackage(packageId));
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() throws IOException {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateReview(@PathVariable String id, @RequestBody Review review) throws IOException {
        if (bstService.findPackageById(review.getPackageId().toString()) == null) {
            return ResponseEntity.badRequest().body("Invalid packageId: Package does not exist");
        }
        review.setId(UUID.fromString(id));
        reviewService.updateReview(review);
        return ResponseEntity.ok("Review updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable String id) throws IOException {
        reviewService.deleteReview(id);
        return ResponseEntity.ok("Review deleted successfully");
    }
}