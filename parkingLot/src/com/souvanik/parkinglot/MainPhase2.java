package com.souvanik.parkinglot;

import com.souvanik.parkinglot.gate.EntryGate;
import com.souvanik.parkinglot.gate.ExitGate;
import com.souvanik.parkinglot.model.Ticket;
import com.souvanik.parkinglot.model.Vehicle;
import com.souvanik.parkinglot.model.vehicle.Bike;
import com.souvanik.parkinglot.model.vehicle.Car;
import com.souvanik.parkinglot.model.vehicle.Truck;
import com.souvanik.parkinglot.service.ParkingManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static com.souvanik.parkinglot.Main.setupParkingManager;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class MainPhase2 {
    public static void main(String[] args) throws InterruptedException {

        ParkingManager manager = setupParkingManager();

        EntryGate entryGate1 = new EntryGate("ENTRY-1", manager);
        EntryGate entryGate2 = new EntryGate("ENTRY-2", manager);

        ExitGate exitGate1 = new ExitGate("EXIT-1", manager);
        ExitGate exitGate2 = new ExitGate("EXIT-2", manager);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        System.out.println("===== PHASE 2 DEMO START =====");

        List<Ticket> tickets =
                runConcurrentParking(entryGate1, entryGate2, executorService);

        simulateParkingDuration();

        processExits(tickets, exitGate1, exitGate2);

        demonstrateSizeBasedReuse(entryGate1, exitGate1);

        demonstrateParkingLotFull(entryGate1, exitGate1);

        shutdownExecutor(executorService);

        System.out.println("===== PHASE 2 DEMO END =====");
    }

    // ------------------------------------------------------
    // Scenario 1: Concurrent Parking
    // ------------------------------------------------------
    private static List<Ticket> runConcurrentParking(
            EntryGate entryGate1,
            EntryGate entryGate2,
            ExecutorService executorService) {

        System.out.println("\n=== Scenario 1: Concurrent Parking ===");

        List<Callable<Ticket>> tasks = List.of(
                () -> parkVehicle(entryGate1, new Car("CAR-101")),
                () -> parkVehicle(entryGate2, new Bike("BIKE-201")),
                () -> parkVehicle(entryGate1, new Truck("TRUCK-301"))
        );

        List<Ticket> tickets = new ArrayList<>();

        try {
            List<Future<Ticket>> futures = executorService.invokeAll(tasks);
            for (Future<Ticket> future : futures) {
                tickets.add(future.get());
            }
        } catch (Exception e) {
            System.out.println("Concurrent parking failed: " + e.getMessage());
        }

        return tickets;
    }

    private static Ticket parkVehicle(EntryGate gate, Vehicle vehicle) {
        Ticket ticket = gate.enter(vehicle);
        System.out.println(
                vehicle.getType() + " parked at: " + ticket.getSlot().getSlotId()
        );
        return ticket;
    }

    // ------------------------------------------------------
    // Scenario 2: Exit & Fee Calculation
    // ------------------------------------------------------
    private static void processExits(
            List<Ticket> tickets,
            ExitGate exitGate1,
            ExitGate exitGate2) {

        System.out.println("\n=== Scenario 2: Exit & Fee Calculation ===");

        for (int i = 0; i < tickets.size(); i++) {
            ExitGate gate = (i % 2 == 0) ? exitGate1 : exitGate2;
            Ticket ticket = tickets.get(i);

            System.out.println(
                    ticket.getVehicle().getType()
                            + " fee: ₹" + gate.exit(ticket)
            );
        }
    }

    // ------------------------------------------------------
    // Scenario 3: Size-Based Slot Reuse
    // ------------------------------------------------------
    private static void demonstrateSizeBasedReuse(
            EntryGate entryGate,
            ExitGate exitGate) {

        System.out.println("\n=== Scenario 3: Size-Based Slot Reuse ===");

        Vehicle bike = new Bike("BIKE-999");
        Ticket ticket = entryGate.enter(bike);

        System.out.println(
                "Bike parked in slot: " + ticket.getSlot().getSlotId()
        );

        exitGate.exit(ticket);
    }

    // ------------------------------------------------------
    // Scenario 4: Parking Lot Full
    // ------------------------------------------------------
    private static void demonstrateParkingLotFull(
            EntryGate entryGate,
            ExitGate exitGate) {

        System.out.println("\n=== Scenario 4: Parking Lot Full ===");

        List<Ticket> tickets = new ArrayList<>();

        try {
            tickets.add(entryGate.enter(new Bike("BIKE-1")));
            tickets.add(entryGate.enter(new Bike("BIKE-2")));
            tickets.add(entryGate.enter(new Bike("BIKE-3"))); // expected failure
        } catch (Exception e) {
            System.out.println("Expected failure: " + e.getMessage());
        } finally {
            tickets.forEach(exitGate::exit);
        }
    }

    // ------------------------------------------------------
    // Utility Methods
    // ------------------------------------------------------
    private static void simulateParkingDuration() throws InterruptedException {
        Thread.sleep(2000);
    }

    private static void shutdownExecutor(ExecutorService executorService)
            throws InterruptedException {

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
    }
}
