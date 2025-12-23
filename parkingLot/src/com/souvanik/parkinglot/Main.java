package com.souvanik.parkinglot;

import com.souvanik.parkinglot.enums.SlotType;
import com.souvanik.parkinglot.model.*;
import com.souvanik.parkinglot.model.vehicle.Bike;
import com.souvanik.parkinglot.model.vehicle.Car;
import com.souvanik.parkinglot.model.vehicle.Truck;
import com.souvanik.parkinglot.service.FeeCalculator;
import com.souvanik.parkinglot.service.ParkingManager;
import com.souvanik.parkinglot.service.impl.DefaultFeeCalculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Copyright (c) 2025 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {

        ParkingManager manager = setupParkingManager();

        // -------- Vehicles --------
        Vehicle bike = new Bike("BIKE-101");
        Vehicle car = new Car("CAR-202");
        Vehicle truck = new Truck("TRUCK-303");

        // -------- Scenario 1: Normal Parking --------
        System.out.println("=== Scenario 1: Normal Parking ===");
        Ticket bikeTicket = manager.park(bike);
        System.out.println("Bike parked at: " + bikeTicket.getSlot().getSlotId());

        Ticket carTicket = manager.park(car);
        System.out.println("Car parked at: " + carTicket.getSlot().getSlotId());

        Ticket truckTicket = manager.park(truck);
        System.out.println("Truck parked at: " + truckTicket.getSlot().getSlotId());

        Thread.sleep(2000);

        // -------- Scenario 2: Exit & Fee Calculation --------
        System.out.println("\n=== Scenario 2: Exit & Fee ===");
        System.out.println("Bike fee: " + manager.exit(bikeTicket));
        System.out.println("Car fee: " + manager.exit(carTicket));
        System.out.println("Truck fee: " + manager.exit(truckTicket));

        // -------- Scenario 3: Reuse Freed Slots --------
        System.out.println("\n=== Scenario 3: Reuse Freed Slots ===");
        Vehicle car2 = new Car("CAR-404");
        Ticket car2Ticket = manager.park(car2);
        System.out.println("Car2 parked at: " + car2Ticket.getSlot().getSlotId());
        manager.exit(car2Ticket);


        // -------- Scenario 4: Parking Lot Full --------
        System.out.println("\n=== Scenario 4: Parking Lot Full ===");
        List<Ticket> tickets = new ArrayList<>();
        try {
            tickets.add(manager.park(new Bike("BIKE-1")));
            tickets.add(manager.park(new Bike("BIKE-2")));
            tickets.add(manager.park(new Bike("BIKE-3"))); // should fail
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
        } finally {
            for (Ticket t : tickets) {
                manager.exit(t);
            }
        }


    }

    private static ParkingManager setupParkingManager() {

        List<ParkingSlot> floor1Slots = Arrays.asList(
                new ParkingSlot("F1-S1", SlotType.BIKE),
                new ParkingSlot("F1-S2", SlotType.CAR),
                new ParkingSlot("F1-S3", SlotType.TRUCK)
        );

        List<ParkingSlot> floor2Slots = Arrays.asList(
                new ParkingSlot("F2-S1", SlotType.BIKE),
                new ParkingSlot("F2-S2", SlotType.CAR),
                new ParkingSlot("F2-S3", SlotType.TRUCK)
        );

        Floor floor1 = new Floor(1, floor1Slots);
        Floor floor2 = new Floor(2, floor2Slots);

        ParkingLot lot = new ParkingLot(
                "Mall-Parking",
                Arrays.asList(floor1, floor2)
        );

        FeeCalculator feeCalculator = new DefaultFeeCalculator();

        return new ParkingManager(lot, feeCalculator);
    }
}