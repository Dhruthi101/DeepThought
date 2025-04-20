package com.example.optimization.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PickSpotRequest {
    private List<Map<String, Object>> yardMap;
    private Map<String, Object> container;
}
