package com.souvanik.parkinglot.enums;

/*
 * Copyright (c) 2025 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public enum VehicleType {
    BIKE(VehicleSize.SMALL),
    CAR(VehicleSize.MEDIUM),
    TRUCK(VehicleSize.LARGE);

    private final VehicleSize size;

    VehicleType(VehicleSize size) {
        this.size = size;
    }

    public VehicleSize getSize() {
        return size;
    }
}
