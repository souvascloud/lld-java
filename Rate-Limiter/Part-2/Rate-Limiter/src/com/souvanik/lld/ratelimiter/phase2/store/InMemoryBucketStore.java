package com.souvanik.lld.ratelimiter.phase2.store;

import com.souvanik.lld.ratelimiter.phase2.model.RateLimitRule;
import com.souvanik.lld.ratelimiter.phase2.model.TokenBucket;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class InMemoryBucketStore implements BucketStore {

    private final ConcurrentMap<String, TokenBucket> buckets =
            new ConcurrentHashMap<>();

    @Override
    public TokenBucket getBucket(String key, RateLimitRule rule) {
        return buckets.computeIfAbsent(
                key,
                k -> new TokenBucket(rule)
        );
    }

    public ConcurrentMap<String, TokenBucket> getBuckets() {
        return buckets;
    }
}