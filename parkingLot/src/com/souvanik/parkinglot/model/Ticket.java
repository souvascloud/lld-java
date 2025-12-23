package com.souvanik.parkinglot.model;

import java.time.LocalDateTime;
import java.util.UUID;

/*
 * Copyright (c) 2025 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class Ticket {

    private final String ticketId;
    private final LocalDateTime entryTime;
    private final ParkingSlot parkingSlot;
    private final Vehicle vehicle;

    public Ticket(ParkingSlot parkingSlot, Vehicle vehicle) {
        this.ticketId = UUID.randomUUID().toString();
        this.entryTime = LocalDateTime.now();
        this.parkingSlot = parkingSlot;
        this.vehicle = vehicle;
    }

    public String getId() {
        return ticketId;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSlot getSlot() {
        return parkingSlot;
    }

}
