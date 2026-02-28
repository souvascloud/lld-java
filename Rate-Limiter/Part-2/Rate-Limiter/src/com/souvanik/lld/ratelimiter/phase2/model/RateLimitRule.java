package com.souvanik.lld.ratelimiter.phase2.model;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class RateLimitRule {
    private final long capacity;
    private final long refillTokensPerSecond;

    public RateLimitRule(long capacity, long refillTokensPerSecond) {
        if (capacity <= 0 || refillTokensPerSecond <= 0) {
            throw new IllegalArgumentException("Capacity and refill rate must be positive");
        }
        this.capacity = capacity;
        this.refillTokensPerSecond = refillTokensPerSecond;
    }

    public long getCapacity() {
        return capacity;
    }

    public long getRefillTokensPerSecond() {
        return refillTokensPerSecond;
    }
}
