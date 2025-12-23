package com.souvanik.parkinglot.strategy;

/*
 * Copyright (c) 2025 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class TruckPricing implements PricingStrategy {

    @Override
    public double pricePerHour() {
        return 30.0;
    }
}
