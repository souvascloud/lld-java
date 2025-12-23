package com.souvanik.parkinglot.service;

import com.souvanik.parkinglot.model.Ticket;

import java.time.LocalDateTime;

/*
 * Copyright (c) 2025 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public interface FeeCalculator {
    double calculateFee(Ticket ticket, LocalDateTime exitTime);
}
