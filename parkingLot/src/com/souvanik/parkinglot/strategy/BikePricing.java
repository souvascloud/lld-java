package com.souvanik.parkinglot.strategy;

import java.math.BigDecimal;

/*
 * Copyright (c) 2025 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class BikePricing implements PricingStrategy{
    @Override
    public BigDecimal pricePerHour() {
        return BigDecimal.valueOf(10);
    }
}
