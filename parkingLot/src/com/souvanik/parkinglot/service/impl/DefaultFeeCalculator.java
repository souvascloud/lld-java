package com.souvanik.parkinglot.service.impl;

import com.souvanik.parkinglot.enums.VehicleType;
import com.souvanik.parkinglot.model.Ticket;
import com.souvanik.parkinglot.service.FeeCalculator;
import com.souvanik.parkinglot.strategy.BikePricing;
import com.souvanik.parkinglot.strategy.CarPricing;
import com.souvanik.parkinglot.strategy.PricingStrategy;
import com.souvanik.parkinglot.strategy.TruckPricing;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

/*
 * Copyright (c) 2025 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class DefaultFeeCalculator implements FeeCalculator {

    private final Map<VehicleType, PricingStrategy> pricingMap = new EnumMap<>(VehicleType.class);

    public DefaultFeeCalculator() {
        pricingMap.put(VehicleType.BIKE, new BikePricing());
        pricingMap.put(VehicleType.CAR, new CarPricing());
        pricingMap.put(VehicleType.TRUCK, new TruckPricing());
    }

    @Override
    public double calculateFee(Ticket ticket, LocalDateTime exitTime) {
        long minutes = Duration.between(ticket.getEntryTime(), exitTime).toMinutes();
        long hours = (minutes / 60) + ((minutes % 60 == 0) ? 0 : 1);
        if (hours == 0) hours = 1;

        PricingStrategy strategy =
                pricingMap.get(ticket.getVehicle().getType());

        if (strategy == null) {
            throw new IllegalArgumentException("No pricing strategy for type: "
                    + ticket.getVehicle().getType());
        }

        return hours * strategy.pricePerHour();
    }

}
