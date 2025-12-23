package test.java;

import com.souvanik.parkinglot.enums.SlotType;
import com.souvanik.parkinglot.model.Floor;
import com.souvanik.parkinglot.model.ParkingLot;
import com.souvanik.parkinglot.model.ParkingSlot;
import com.souvanik.parkinglot.model.Ticket;
import com.souvanik.parkinglot.model.vehicle.Bike;
import com.souvanik.parkinglot.model.vehicle.Car;
import com.souvanik.parkinglot.model.vehicle.Truck;
import com.souvanik.parkinglot.service.ParkingManager;
import com.souvanik.parkinglot.service.impl.DefaultFeeCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Copyright (c) 2025 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class ParkingManagerTest {
    private ParkingManager manager;

    @BeforeEach
    void setUp() {
        manager = setupParkingManager();
    }

    private ParkingManager setupParkingManager() {
        Floor f1 = new Floor(1, Arrays.asList(
                new ParkingSlot("F1-S1", SlotType.BIKE),
                new ParkingSlot("F1-S2", SlotType.CAR),
                new ParkingSlot("F1-S3", SlotType.TRUCK)
        ));
        Floor f2 = new Floor(2, Arrays.asList(
                new ParkingSlot("F2-S1", SlotType.BIKE),
                new ParkingSlot("F2-S2", SlotType.CAR),
                new ParkingSlot("F2-S3", SlotType.TRUCK)
        ));
        ParkingLot lot = new ParkingLot("TestLot", Arrays.asList(f1, f2));
        return new ParkingManager(lot, new DefaultFeeCalculator());
    }

    @Test
    void shouldParkDifferentVehicles() {
        Ticket b = manager.park(new Bike("B1"));
        Ticket c = manager.park(new Car("C1"));
        Ticket t = manager.park(new Truck("T1"));

        assertEquals(SlotType.BIKE, b.getSlot().getSlotType());
        assertEquals(SlotType.CAR, c.getSlot().getSlotType());
        assertEquals(SlotType.TRUCK, t.getSlot().getSlotType());
    }

    @Test
    void shouldCalculateFeeOnExit() {
        Ticket ticket = manager.park(new Car("C2"));
        double fee = manager.exit(ticket);
        assertTrue(fee > 0);
    }

    @Test
    void shouldReuseSlotAfterExit() {
        Ticket t1 = manager.park(new Car("C3"));
        String slotId = t1.getSlot().getSlotId();
        manager.exit(t1);

        Ticket t2 = manager.park(new Car("C4"));
        assertEquals(slotId, t2.getSlot().getSlotId());
    }

    @Test
    void shouldThrowWhenLotIsFull() {
        manager.park(new Bike("B1"));
        manager.park(new Bike("B2"));

        assertThrows(IllegalStateException.class,
                () -> manager.park(new Bike("B3")));
    }

    @Test
    void shouldFreeSlotAfterExit() {
        Ticket t = manager.park(new Truck("T9"));
        ParkingSlot slot = t.getSlot();

        assertTrue(slot.isOccupied());
        manager.exit(t);
        assertFalse(slot.isOccupied());
        assertNull(slot.getVehicle());
    }

    @Test
    void shouldNotAllowNullVehicle() {
        assertThrows(IllegalArgumentException.class,
                () -> manager.park(null));
    }

    @Test
    void shouldNotAllowNullTicketOnExit() {
        assertThrows(IllegalArgumentException.class,
                () -> manager.exit(null));
    }

    @Test
    void shouldNotAllowDoubleExit() {
        Ticket t = manager.park(new Car("C9"));
        manager.exit(t);

        assertThrows(IllegalStateException.class,
                () -> manager.exit(t));
    }

}
