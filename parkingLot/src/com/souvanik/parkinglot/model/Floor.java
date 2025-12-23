package com.souvanik.parkinglot.model;

import java.util.List;

/*
 * Copyright (c) 2025 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class Floor {
    private final int number;
    private final List<ParkingSlot>  parkingSlots;

    public Floor(int number , List<ParkingSlot> parkingSlots) {
        this.number = number;
        this.parkingSlots = parkingSlots;
    }


    public int getNumber() {
        return number;
    }

    public List<ParkingSlot> getSlots() {
        return parkingSlots;
    }

    /**
     * Find a free slot on this floor that can fit the vehicle.
     */
    public ParkingSlot findAvailableSlot(Vehicle vehicle) {

       return parkingSlots
                .stream()
                .filter(parkingSlot -> parkingSlot.canFit(vehicle))
                .findFirst()
                .orElse(null);

    }
}
