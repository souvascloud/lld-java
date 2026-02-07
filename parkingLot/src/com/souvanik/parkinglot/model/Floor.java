package com.souvanik.parkinglot.model;

import com.souvanik.parkinglot.enums.SlotSize;
import com.souvanik.parkinglot.enums.VehicleSize;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/*
 * Copyright (c) 2025 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class Floor {

    private final int number;
    private final List<ParkingSlot> parkingSlots;

    private final Map<SlotSize, Queue<ParkingSlot>> freeSlotsBySize =
            new EnumMap<>(SlotSize.class);

    public Floor(int number, List<ParkingSlot> parkingSlots) {
        this.number = number;
        this.parkingSlots = parkingSlots;

        for (SlotSize size : SlotSize.values()) {
            freeSlotsBySize.put(size, new ConcurrentLinkedQueue<>());
        }

        for (ParkingSlot slot : parkingSlots) {
            freeSlotsBySize
                    .get(slot.getSlotType().getSize())
                    .offer(slot);
        }
    }

    public int getNumber() {
        return number;
    }

    /**
     * O(1) slot lookup using size-based index
     */
    public ParkingSlot findAvailableSlot(Vehicle vehicle) {

        VehicleSize vehicleSize = vehicle.getType().getSize();

        for (SlotSize slotSize : SlotSize.values()) {
            if (slotSize.ordinal() >= vehicleSize.ordinal()) {
                Queue<ParkingSlot> queue = freeSlotsBySize.get(slotSize);
                ParkingSlot slot = queue.poll();
                if (slot != null) {
                    return slot;
                }
            }
        }
        return null;
    }

    /**
     * Return slot back to free pool
     */
    public void releaseSlot(ParkingSlot slot) {
        if (!slot.isOccupied()) {
            freeSlotsBySize
                    .get(slot.getSlotType().getSize())
                    .offer(slot);
        }
    }

    public void addSlot(ParkingSlot slot) {
        parkingSlots.add(slot);
        freeSlotsBySize
                .get(slot.getSlotType().getSize())
                .offer(slot);
    }
}

