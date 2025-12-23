package com.souvanik.parkinglot.model;

import com.souvanik.parkinglot.enums.VehicleType;


/*
 * Copyright (c) 2025 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public interface Vehicle {
    String getNumber();
    VehicleType getType();
}
