package com.souvanik.lld.ratelimiter.phase2.store;

import com.souvanik.lld.ratelimiter.phase2.model.RateLimitRule;
import com.souvanik.lld.ratelimiter.phase2.model.TokenBucket;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public interface BucketStore {
    TokenBucket getBucket(String key, RateLimitRule rule);
}
