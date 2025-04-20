package com.example.optimization.service;

import com.example.optimization.model.Container;
import com.example.optimization.model.YardSlot;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlotPickerService {
    // Penalty for invalid conditions
    private static final int INVALID_PENALTY = 10_000;

    // Method to pick the best slot based on scoring rules
    public YardSlot pickBestSlot(Container container, List<YardSlot> yardMap) {
        // If the yardMap is empty, throw an exception
        if (yardMap == null || yardMap.isEmpty()) {
            throw new IllegalArgumentException("Yard map is empty or null, cannot select a slot.");
        }

        int minScore = Integer.MAX_VALUE;
        YardSlot bestSlot = null;

        // Loop through each yard slot and calculate the score
        for (YardSlot slot : yardMap) {
            // Calculate the distance between the container and the slot
            int distance = Math.abs(container.getX() - slot.getX()) + Math.abs(container.getY() - slot.getY());

            // Apply penalties based on conditions
            int sizePenalty = (slot.getSizeCap() == null || !slot.getSizeCap().equals(container.getSize())) 
                                && !slot.getSizeCap().equals("big") ? INVALID_PENALTY : 0;

            int coldPenalty = container.isNeedsCold() && !slot.hasColdUnit() ? INVALID_PENALTY : 0;
            int occupiedPenalty = slot.isOccupied() ? INVALID_PENALTY : 0;

            // Calculate the total score for the slot
            int score = distance + sizePenalty + coldPenalty + occupiedPenalty;

            // If this slot has a better score, update the bestSlot
            if (score < minScore) {
                minScore = score;
                bestSlot = slot;
            }
        }

        // Return the best slot found, or null if no valid slot was found
        return (minScore >= INVALID_PENALTY) ? null : bestSlot;
    }
}
