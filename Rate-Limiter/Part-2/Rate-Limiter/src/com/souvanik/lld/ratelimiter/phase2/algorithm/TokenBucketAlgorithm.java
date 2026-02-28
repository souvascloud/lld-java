package com.souvanik.lld.ratelimiter.phase2.algorithm;

import com.souvanik.lld.ratelimiter.phase2.model.RateLimitRule;
import com.souvanik.lld.ratelimiter.phase2.model.TokenBucket;
import com.souvanik.lld.ratelimiter.phase2.store.BucketStore;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class TokenBucketAlgorithm implements RateLimitAlgorithm {

    private final BucketStore bucketStore;

    public TokenBucketAlgorithm(BucketStore bucketStore) {
        this.bucketStore = bucketStore;
    }

    @Override
    public boolean allow(String key, RateLimitRule rule) {
        TokenBucket bucket = bucketStore.getBucket(key, rule);
        return bucket.tryConsume();
    }
}