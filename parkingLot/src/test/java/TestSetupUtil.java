package test.java;

import com.souvanik.parkinglot.enums.SlotType;
import com.souvanik.parkinglot.model.Floor;
import com.souvanik.parkinglot.model.ParkingLot;
import com.souvanik.parkinglot.model.ParkingSlot;
import com.souvanik.parkinglot.service.ParkingManager;
import com.souvanik.parkinglot.service.impl.DefaultFeeCalculator;

import java.util.ArrayList;
import java.util.List;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class TestSetupUtil {

    public static ParkingManager setupParkingManager() {

        Floor floor1 = new Floor(1, new ArrayList<>());
        Floor floor2 = new Floor(2, new ArrayList<>());

        floor1.addSlot(new ParkingSlot("F1-S1", SlotType.BIKE, floor1));
        floor1.addSlot(new ParkingSlot("F1-S2", SlotType.CAR, floor1));
        floor1.addSlot(new ParkingSlot("F1-S3", SlotType.TRUCK, floor1));

        floor2.addSlot(new ParkingSlot("F2-S1", SlotType.BIKE, floor2));
        floor2.addSlot(new ParkingSlot("F2-S2", SlotType.CAR, floor2));
        floor2.addSlot(new ParkingSlot("F2-S3", SlotType.TRUCK, floor2));

        ParkingLot lot = new ParkingLot(
                "Test-Lot",
                List.of(floor1, floor2)
        );

        return new ParkingManager(lot, new DefaultFeeCalculator());
    }
}
