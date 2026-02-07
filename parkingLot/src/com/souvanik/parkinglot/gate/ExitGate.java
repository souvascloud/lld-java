package com.souvanik.parkinglot.gate;

import com.souvanik.parkinglot.model.Ticket;
import com.souvanik.parkinglot.service.ParkingManager;

import java.math.BigDecimal;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class ExitGate {
    private final String gateId;
    private final ParkingManager parkingManager;

    public ExitGate(String gateId, ParkingManager parkingManager) {
        this.gateId = gateId;
        this.parkingManager = parkingManager;
    }

    public BigDecimal exit(Ticket ticket) {
        return parkingManager.exit(ticket);
    }

    public String getGateId() {
        return gateId;
    }
}
