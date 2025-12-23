package com.souvanik.parkinglot.model;

import com.souvanik.parkinglot.enums.SlotType;

/*
 * Copyright (c) 2025 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class ParkingSlot {

    private final String slotId;
    private final SlotType slotType;
    private boolean occupied;
    private Vehicle vehicle;

    public ParkingSlot(String slotId, SlotType slotType) {
        this.slotId = slotId;
        this.slotType = slotType;
        this.occupied = false;
    }

    public String getSlotId() {
        return slotId;
    }

    public SlotType getSlotType() {
        return slotType;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }


    /**
     * Check if this slot can fit the given vehicle.
     */

    public boolean canFit(Vehicle vehicle) {
        if(occupied) return false;
        return this.slotType.name().equals(vehicle.getType().name());
    }

    /**
     * Park the vehicle in this slot.
     */

    public void park(Vehicle vehicle) {
       if(!canFit(vehicle)){
           throw new IllegalArgumentException("Slot "+this.slotId+" can not fit vehicle type "+vehicle.getType());
       }
       this.vehicle = vehicle;
       this.occupied = true;
    }

    /**
     * Free this slot.
     */
    public void unpark() {
        this.vehicle = null;
        this.occupied = false;
    }

}
