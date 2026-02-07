package com.souvanik.parkinglot.strategy;

import java.math.BigDecimal;

/*
 * Copyright (c) 2025 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public interface PricingStrategy {
    BigDecimal pricePerHour();
}
