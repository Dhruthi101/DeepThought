package com.example.optimization.model;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class YardSlot {
    private int x;
    private int y;
    private String sizeCap;
    private boolean hasColdUnit;
    private boolean occupied;

    // Adds the `hasColdUnit()` method if you want it directly
    public boolean hasColdUnit() {
        return this.hasColdUnit;
    }

    // Converts a List<Map<String, Object>> to a List of YardSlot objects
    public static List<YardSlot> listFrom(List<Map<String, Object>> list) {
        return list.stream()
            .map(map -> {
                YardSlot slot = new YardSlot();
                slot.setX(map.containsKey("x") ? (Integer) map.get("x") : 0);
                slot.setY(map.containsKey("y") ? (Integer) map.get("y") : 0);
                slot.setSizeCap((String) map.get("sizeCap"));
                slot.setHasColdUnit(map.containsKey("hasColdUnit") ? (Boolean) map.get("hasColdUnit") : false);
                slot.setOccupied(map.containsKey("occupied") ? (Boolean) map.get("occupied") : false);
                return slot;
            })
            .collect(Collectors.toList());
    }
}
