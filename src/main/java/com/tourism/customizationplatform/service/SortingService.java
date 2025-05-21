package com.tourism.customizationplatform.service;

import com.tourism.customizationplatform.model.TravelPackage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SortingService {
    public List<TravelPackage> sortByPrice(List<TravelPackage> packages) {
        quickSort(packages, 0, packages.size() - 1);
        return packages;
    }

    private void quickSort(List<TravelPackage> packages, int low, int high) {
        if (low < high) {
            int pi = partition(packages, low, high);
            quickSort(packages, low, pi - 1);
            quickSort(packages, pi + 1, high);
        }
    }

    private int partition(List<TravelPackage> packages, int low, int high) {
        double pivot = packages.get(high).getPrice();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (packages.get(j).getPrice() <= pivot) {
                i++;
                TravelPackage temp = packages.get(i);
                packages.set(i, packages.get(j));
                packages.set(j, temp);
            }
        }
        TravelPackage temp = packages.get(i + 1);
        packages.set(i + 1, packages.get(high));
        packages.set(high, temp);
        return i + 1;
    }
}