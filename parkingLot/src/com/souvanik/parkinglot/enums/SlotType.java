package com.souvanik.parkinglot.enums;

/*
 * Copyright (c) 2025 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public enum SlotType {
    BIKE(SlotSize.SMALL),
    CAR(SlotSize.MEDIUM),
    TRUCK(SlotSize.LARGE);

    private final SlotSize size;

    SlotType(SlotSize size) {
        this.size = size;
    }

    public SlotSize getSize() {
        return size;
    }
}
