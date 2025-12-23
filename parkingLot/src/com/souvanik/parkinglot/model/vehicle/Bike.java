package com.souvanik.parkinglot.model.vehicle;

import com.souvanik.parkinglot.enums.VehicleType;
import com.souvanik.parkinglot.model.Vehicle;

/*
 * Copyright (c) 2025 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class Bike implements Vehicle {

    private final String number;

    public Bike(String number) {
        this.number = number;
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public VehicleType getType() {
        return VehicleType.BIKE;
    }
}
