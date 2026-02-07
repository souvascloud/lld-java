package com.souvanik.parkinglot.service;

import com.souvanik.parkinglot.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 * Copyright (c) 2025 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class ParkingManager {

    private final Map<String, Ticket> activeTickets = new ConcurrentHashMap<>();

    private final ParkingLot parkingLot;
    private final FeeCalculator feeCalculator;

    // Single lock for slot allocation
    private final Object slotAllocationLock = new Object();

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

        synchronized (slotAllocationLock) {
            for (Floor floor : parkingLot.getFloors()) {
                ParkingSlot slot = floor.findAvailableSlot(vehicle);

                if (slot != null) {
                    slot.park(vehicle); //  inside synchronized block
                    Ticket ticket = new Ticket(slot, vehicle);
                    activeTickets.put(ticket.getId(), ticket);
                    return ticket;
                }
            }
        }

        throw new IllegalStateException(
                "No available slot for vehicle: " + vehicle.getType()
        );
    }


    /**
     * Exit vehicle using ticket and return parking fee.
     */
    public BigDecimal exit(Ticket ticket) {

        Ticket active = activeTickets.remove(ticket.getId());
        if (active == null) {
            throw new IllegalStateException("Invalid ticket");
        }

        BigDecimal fee = feeCalculator.calculateFee(ticket, LocalDateTime.now());

        ParkingSlot slot = ticket.getSlot();
        slot.unpark();

        //Return slot to floor index
        slot.getFloor().releaseSlot(slot);

        return fee;
    }


}
