package com.souvanik.parkinglot.model;

import java.util.List;

/*
 * Copyright (c) 2025 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class ParkingLot {

    private final String id;
    private final List<Floor> floors;

    public ParkingLot(String id, List<Floor> floors) {
        this.id = id;
        this.floors = floors;
    }

    public String getId() {
        return id;
    }

    public List<Floor> getFloors() {
        return floors;
    }
}
