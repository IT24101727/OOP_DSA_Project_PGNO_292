package com.tourism.customizationplatform.controller;

import com.tourism.customizationplatform.model.TravelPackage;
import com.tourism.customizationplatform.service.BSTService;
import com.tourism.customizationplatform.service.SortingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/packages")
public class PackageController {
    @Autowired
    private BSTService bstService;
    @Autowired
    private SortingService sortingService;

    @PostMapping
    public ResponseEntity<String> addPackage(@RequestBody TravelPackage pkg) {
        try {
            if (pkg.getName() == null || pkg.getDestination() == null || pkg.getPackageType() == null ||
                    pkg.getPrice() <= 0 || pkg.getDuration() <= 0) {
                return ResponseEntity.badRequest().body("Invalid package data");
            }
            pkg.setId(UUID.randomUUID());
            bstService.addPackage(pkg);
            return ResponseEntity.ok("Package added successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to add package: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Invalid package data: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePackage(@PathVariable String id, @RequestBody TravelPackage pkg) {
        try {
            if (pkg.getName() == null || pkg.getDestination() == null || pkg.getPackageType() == null ||
                    pkg.getPrice() <= 0 || pkg.getDuration() <= 0) {
                return ResponseEntity.badRequest().body("Invalid package data");
            }
            pkg.setId(UUID.fromString(id));
            bstService.updatePackage(pkg);
            return ResponseEntity.ok("Package updated successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to update package: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Invalid package data: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<TravelPackage>> getAllPackages() {
        return ResponseEntity.ok(bstService.getAllPackages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TravelPackage> getPackageById(@PathVariable String id) {
        TravelPackage pkg = bstService.findPackageById(id);
        return pkg != null ? ResponseEntity.ok(pkg) : ResponseEntity.notFound().build();
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<TravelPackage>> getSortedPackages() {
        List<TravelPackage> packages = bstService.getAllPackages();
        return ResponseEntity.ok(sortingService.sortByPrice(packages));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePackage(@PathVariable String id) throws IOException {
        bstService.deletePackage(id);
        return ResponseEntity.ok("Package deleted successfully");
    }
}