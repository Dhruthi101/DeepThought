package com.example.optimization.service;

import com.example.optimization.model.Container;
import com.example.optimization.model.YardSlot;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlotPickerService {
  
    private static final int INVALID_PENALTY = 10_000;

    public YardSlot pickBestSlot(Container container, List<YardSlot> yardMap) {
  
        if (yardMap == null || yardMap.isEmpty()) {
            throw new IllegalArgumentException("Yard map is empty or null, cannot select a slot.");
        }

        int minScore = Integer.MAX_VALUE;
        YardSlot bestSlot = null;

        for (YardSlot slot : yardMap) {
        
            int distance = Math.abs(container.getX() - slot.getX()) + Math.abs(container.getY() - slot.getY());

         
            int sizePenalty = (slot.getSizeCap() == null || !slot.getSizeCap().equals(container.getSize())) 
                                && !slot.getSizeCap().equals("big") ? INVALID_PENALTY : 0;

            int coldPenalty = container.isNeedsCold() && !slot.hasColdUnit() ? INVALID_PENALTY : 0;
            int occupiedPenalty = slot.isOccupied() ? INVALID_PENALTY : 0;

         
            int score = distance + sizePenalty + coldPenalty + occupiedPenalty;

          
            if (score < minScore) {
                minScore = score;
                bestSlot = slot;
            }
        }

      
        return (minScore >= INVALID_PENALTY) ? null : bestSlot;
    }
}
