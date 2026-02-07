package test.java;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
import com.souvanik.parkinglot.enums.SlotType;
import com.souvanik.parkinglot.enums.VehicleSize;
import com.souvanik.parkinglot.enums.VehicleType;
import com.souvanik.parkinglot.gate.EntryGate;
import com.souvanik.parkinglot.gate.ExitGate;
import com.souvanik.parkinglot.model.ParkingSlot;
import com.souvanik.parkinglot.model.Ticket;
import com.souvanik.parkinglot.model.vehicle.Bike;
import com.souvanik.parkinglot.model.vehicle.Car;
import com.souvanik.parkinglot.model.vehicle.Truck;
import com.souvanik.parkinglot.service.ParkingManager;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class ParkingManagerTest {

    private ParkingManager parkingManager;
    private EntryGate entryGate;
    private ExitGate exitGate;

    @BeforeEach
    void setUp() {
        parkingManager = TestSetupUtil.setupParkingManager();
        entryGate = new EntryGate("ENTRY-1", parkingManager);
        exitGate = new ExitGate("EXIT-1", parkingManager);
    }

    // ------------------------------------------------------
    // Normal Parking Test
    // ------------------------------------------------------
    @Test
    void shouldParkVehiclesSuccessfully() {
        Ticket bikeTicket = entryGate.enter(new Bike("BIKE-1"));
        Ticket carTicket = entryGate.enter(new Car("CAR-1"));
        Ticket truckTicket = entryGate.enter(new Truck("TRUCK-1"));

        assertNotNull(bikeTicket);
        assertNotNull(carTicket);
        assertNotNull(truckTicket);

        assertEquals(VehicleType.BIKE, bikeTicket.getVehicle().getType());
        assertEquals(VehicleType.CAR, carTicket.getVehicle().getType());
        assertEquals(VehicleType.TRUCK, truckTicket.getVehicle().getType());
    }

    // ------------------------------------------------------
    //  Size-Based Slot Compatibility
    // ------------------------------------------------------
    @Test
    void smallVehicleCanParkInLargerSlot() {
        // Fill bike slot first
        entryGate.enter(new Bike("BIKE-1"));

        // Next bike should take larger slot
        Ticket ticket = entryGate.enter(new Bike("BIKE-2"));

        assertNotNull(ticket);
        assertTrue(
                ticket.getSlot().getSlotType().getSize().ordinal()
                        >= VehicleSize.SMALL.ordinal()
        );
    }

    // ------------------------------------------------------
    //  Slot Reuse After Exit
    // ------------------------------------------------------
    @Test
    void slotShouldBeReusedAfterExit() {
        Ticket carTicket = entryGate.enter(new Car("CAR-1"));
        String slotId = carTicket.getSlot().getSlotId();

        exitGate.exit(carTicket);

        Ticket newCarTicket = entryGate.enter(new Car("CAR-2"));

        assertEquals(slotId, newCarTicket.getSlot().getSlotId());
    }

    // ------------------------------------------------------
    // Parking Lot Full
    // ------------------------------------------------------
    @Test
    void shouldThrowExceptionWhenParkingLotIsFull() {
        entryGate.enter(new Bike("BIKE-1"));
        entryGate.enter(new Bike("BIKE-2"));
        entryGate.enter(new Bike("BIKE-3"));
        entryGate.enter(new Car("CAR-1"));
        entryGate.enter(new Truck("TRUCK-1"));
        entryGate.enter(new Truck("TRUCK-2"));

        assertThrows(
                IllegalStateException.class,
                () -> entryGate.enter(new Bike("BIKE-OVERFLOW"))
        );
    }

    // ------------------------------------------------------
    // 5Fee Calculation Accuracy
    // ------------------------------------------------------
    @Test
    void shouldCalculateCorrectFee() throws InterruptedException {
        Ticket bikeTicket = entryGate.enter(new Bike("BIKE-1"));

        Thread.sleep(1000); // simulate time

        BigDecimal fee = exitGate.exit(bikeTicket);

        assertEquals(BigDecimal.valueOf(10), fee);
    }

    // ------------------------------------------------------
    //  Invalid Ticket Exit
    // ------------------------------------------------------
    @Test
    void shouldFailForInvalidTicket() {
        Ticket fakeTicket = new Ticket(
                new ParkingSlot("FAKE", SlotType.BIKE, null),
                new Bike("FAKE")
        );

        assertThrows(
                IllegalStateException.class,
                () -> exitGate.exit(fakeTicket)
        );
    }

    // ------------------------------------------------------
    //  Double Exit Protection
    // ------------------------------------------------------
    @Test
    void shouldNotAllowDoubleExit() {
        Ticket ticket = entryGate.enter(new Car("CAR-1"));
        exitGate.exit(ticket);

        assertThrows(
                IllegalStateException.class,
                () -> exitGate.exit(ticket)
        );
    }

    // ------------------------------------------------------
    //  Concurrent Parking (Thread Safety)
    // ------------------------------------------------------
    @Test
    void shouldHandleConcurrentParkingSafely() throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        Callable<Ticket> task1 = () -> entryGate.enter(new Car("CAR-1"));
        Callable<Ticket> task2 = () -> entryGate.enter(new Bike("BIKE-1"));
        Callable<Ticket> task3 = () -> entryGate.enter(new Truck("TRUCK-1"));

        List<Future<Ticket>> futures =
                executor.invokeAll(List.of(task1, task2, task3));

        Set<String> slotIds = new HashSet<>();

        for (Future<Ticket> f : futures) {
            Ticket t = f.get();
            assertNotNull(t);
            assertTrue(slotIds.add(t.getSlot().getSlotId())); // no duplicates
        }

        executor.shutdown();
    }
}
