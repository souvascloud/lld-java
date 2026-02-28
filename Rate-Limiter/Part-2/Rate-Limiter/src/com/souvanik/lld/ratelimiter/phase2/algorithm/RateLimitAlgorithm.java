package com.souvanik.lld.ratelimiter.phase2.algorithm;

import com.souvanik.lld.ratelimiter.phase2.model.RateLimitRule;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public interface RateLimitAlgorithm {
    boolean allow(String key, RateLimitRule rule);
}
