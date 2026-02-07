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
    private final Floor floor;

    public ParkingSlot(String slotId, SlotType slotType , Floor floor) {
        this.slotId = slotId;
        this.slotType = slotType;
        this.floor = floor;
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

    public Floor getFloor() {
        return floor;
    }

    /**
     * Slot can hold exactly ONE vehicle.
     * Size only defines compatibility.
     */
    public boolean canFit(Vehicle vehicle) {
        if (occupied) return false;

        return vehicle.getType().getSize().ordinal() <= slotType.getSize().ordinal();
    }

    /**
     * Park the vehicle in this slot.
     */

    public void park(Vehicle vehicle) {
        if (occupied) {
            throw new IllegalStateException("Slot already occupied: " + slotId);
        }
        if (!canFit(vehicle)) {
            throw new IllegalArgumentException(
                    "Slot " + slotId + " cannot fit vehicle type " + vehicle.getType()
            );
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
