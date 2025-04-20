package com.example.optimization.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Map;

@Getter
@Setter
public class Container {
    @NotNull(message = "Container ID cannot be null")
    private String id;

    @Min(value = 0, message = "X coordinate must be positive")
    private int x;

    @Min(value = 0, message = "Y coordinate must be positive")
    private int y;

    @NotNull(message = "Size cannot be null")
    private String size;

    private boolean needsCold;

    // fromMap method stays the same
    public static Container fromMap(Map<String, Object> map) {
        Container container = new Container();
        container.id = (String) map.get("id");
        container.x = map.containsKey("x") ? (Integer) map.get("x") : 0;
        container.y = map.containsKey("y") ? (Integer) map.get("y") : 0;
        container.size = (String) map.get("size");
        container.needsCold = map.containsKey("needsCold") ? (Boolean) map.get("needsCold") : false;
        return container;
    }
}
