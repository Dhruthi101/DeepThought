package com.example.optimization.controller;

import com.example.optimization.model.Container;
import com.example.optimization.model.YardSlot;
import com.example.optimization.service.SlotPickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/optimization")
public class SlotPickerController {

    private static final Logger logger = LoggerFactory.getLogger(SlotPickerController.class);

    @Autowired
    private SlotPickerService service;

    @PostMapping("/pickSpot")
    public ResponseEntity<Map<String, Object>> pickSpot(@RequestBody Map<String, Object> request) {
        logger.info("Received request: {}", request);

        Map<String, Object> response = new HashMap<>();

        try {
          
            Container container = Container.fromMap((Map<String, Object>) request.get("container"));
            
          
            List<YardSlot> yardMap = YardSlot.listFrom((List<Map<String, Object>>) request.get("yardMap"));

       
            if (yardMap.isEmpty()) {
                response.put("error", "Yard map is empty");
                logger.warn("Yard map is empty for container: {}", container.getId());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

        
            YardSlot bestSlot = service.pickBestSlot(container, yardMap);

            if (bestSlot != null) {
                response.put("containerId", container.getId());
                response.put("targetX", bestSlot.getX());
                response.put("targetY", bestSlot.getY());
                logger.info("Best slot found for container {}: ({}, {})", container.getId(), bestSlot.getX(), bestSlot.getY());
                return ResponseEntity.ok(response); // Return status 200 OK
            } else {
                response.put("error", "No suitable slot found");
                logger.warn("No suitable slot found for container: {}", container.getId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // Return status 404 Not Found
            }
        } catch (IllegalArgumentException e) {
            logger.error("Invalid request data: {}", e.getMessage());
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            logger.error("Error processing request", e);
            response.put("error", "Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
