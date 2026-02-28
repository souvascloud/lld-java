package com.souvanik.lld.ratelimiter.phase1;

import java.util.HashMap;
import java.util.Map;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class InMemoryRateLimiter {
    private final RateLimitRule defaultRule;
    private final Map<String, TokenBucket> bucketMap = new HashMap<>();

    public InMemoryRateLimiter(RateLimitRule defaultRule) {
        this.defaultRule = defaultRule;
    }

    public boolean allow(String key) {
        TokenBucket bucket = bucketMap.computeIfAbsent(
                key,
                k -> new TokenBucket(defaultRule)
        );

        return bucket.tryConsume();
    }

}
