package com.souvanik.parkinglot.gate;

import com.souvanik.parkinglot.model.Ticket;
import com.souvanik.parkinglot.model.Vehicle;
import com.souvanik.parkinglot.service.ParkingManager;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class EntryGate {


        private final String gateId;
        private final ParkingManager parkingManager;

        public EntryGate(String gateId, ParkingManager parkingManager) {
            this.gateId = gateId;
            this.parkingManager = parkingManager;
        }

        public Ticket enter(Vehicle vehicle) {
            return parkingManager.park(vehicle);
        }

        public String getGateId() {
            return gateId;
        }


}
