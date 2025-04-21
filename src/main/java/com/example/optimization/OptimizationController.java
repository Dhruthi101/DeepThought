package com.example.optimization;

import com.example.optimization.model.Container;
import com.example.optimization.model.YardSlot;
import com.example.optimization.model.PickSpotRequest;
import com.example.optimization.service.SlotPickerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/optimization")
public class OptimizationController {

    private static final Logger logger = LoggerFactory.getLogger(OptimizationController.class);

    private final SlotPickerService slotPickerService;


    public OptimizationController(SlotPickerService slotPickerService) {
        this.slotPickerService = slotPickerService;
    }

    
    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        logger.info("Received request for API status");
        return ResponseEntity.ok("Optimization API is running!");
    }

 
    @PostMapping("/pickSpot")
    public ResponseEntity<String> pickSpot(@RequestBody PickSpotRequest request) {
        logger.info("Received request for picking optimal yard spot");

      
        Container container = Container.fromMap(request.getContainer());
        List<YardSlot> yardSlots = YardSlot.listFrom(request.getYardMap());

 
        if (container == null || yardSlots.isEmpty()) {
            logger.error("Invalid container or yard map data.");
            return ResponseEntity.badRequest().body("Invalid container or yard map data.");
        }

   
        YardSlot bestSlot = slotPickerService.pickBestSlot(container, yardSlots);

        if (bestSlot != null) {
            logger.info("Optimal Yard Slot found at coordinates: ({}, {})", bestSlot.getX(), bestSlot.getY());
            return ResponseEntity.ok("Optimal Yard Slot: (" + bestSlot.getX() + ", " + bestSlot.getY() + ")");
        } else {
            logger.info("No suitable yard slot found.");
            return ResponseEntity.ok("No suitable yard slot found.");
        }
    }
}
