package com.souvanik.lld.ratelimiter.phase2.core;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public interface RateLimiter {
    boolean allow(String key);
}
