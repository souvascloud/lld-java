package com.souvanik.parkinglot.service;

import com.souvanik.parkinglot.model.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/*
 * Copyright (c) 2025 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class ParkingManager {

    private final Map<String, Ticket> activeTickets = new HashMap<>();

    private final ParkingLot parkingLot;
    private final FeeCalculator feeCalculator;

    public ParkingManager(ParkingLot parkingLot, FeeCalculator feeCalculator) {
        this.parkingLot = parkingLot;
        this.feeCalculator = feeCalculator;
    }

    /**
     * Park a vehicle and return a ticket.
     */
    public Ticket park(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        for (Floor floor : parkingLot.getFloors()) {
            ParkingSlot slot = floor.findAvailableSlot(vehicle);
            if (slot != null) {
                slot.park(vehicle);
                Ticket ticket = new Ticket(slot, vehicle);
                activeTickets.put(ticket.getId(), ticket);
                return ticket;
            }
        }
        throw new IllegalStateException("No available slot for vehicle: " + vehicle.getType());
    }

    /**
     * Exit vehicle using ticket and return parking fee.
     */
    public double exit(Ticket ticket ) {
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket cannot be null");
        }
        Ticket active = activeTickets.remove(ticket.getId());

        if (active == null) {
            throw new IllegalStateException("Invalid or already used ticket: " + ticket.getId());
        }
        LocalDateTime exitTime = LocalDateTime.now();
        double fee = feeCalculator.calculateFee(ticket, exitTime);

        ParkingSlot slot = ticket.getSlot();
        slot.unpark();

        return fee;
    }

}
