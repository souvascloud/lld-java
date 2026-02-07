package com.souvanik.parkinglot.service;

import com.souvanik.parkinglot.model.Ticket;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
 * Copyright (c) 2025 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public interface FeeCalculator {
    BigDecimal calculateFee(Ticket ticket, LocalDateTime exitTime);
}
