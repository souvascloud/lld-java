package com.souvanik.lld.ratelimiter.phase2;

import com.souvanik.lld.ratelimiter.phase2.algorithm.RateLimitAlgorithm;
import com.souvanik.lld.ratelimiter.phase2.algorithm.TokenBucketAlgorithm;
import com.souvanik.lld.ratelimiter.phase2.cleanup.BucketCleanupScheduler;
import com.souvanik.lld.ratelimiter.phase2.core.DefaultRateLimiter;
import com.souvanik.lld.ratelimiter.phase2.core.RateLimiter;
import com.souvanik.lld.ratelimiter.phase2.model.RateLimitRule;
import com.souvanik.lld.ratelimiter.phase2.rule.InMemoryRuleProvider;
import com.souvanik.lld.ratelimiter.phase2.rule.RuleProvider;
import com.souvanik.lld.ratelimiter.phase2.store.InMemoryBucketStore;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {

        RateLimitRule rule = new RateLimitRule(5, 1);


        InMemoryBucketStore bucketStore = new InMemoryBucketStore();

        RateLimitAlgorithm algorithm = new TokenBucketAlgorithm(bucketStore);


        RuleProvider ruleProvider = new InMemoryRuleProvider(rule);


        RateLimiter rateLimiter =
                new DefaultRateLimiter(algorithm, ruleProvider);

        // start cleanup scheduler (TTL = 10 seconds)
        BucketCleanupScheduler cleanupScheduler =
                new BucketCleanupScheduler(bucketStore, 10);
        cleanupScheduler.start();

        System.out.println("Sending 7 immediate requests:");

        for (int i = 1; i <= 7; i++) {
            System.out.println("Request " + i + " allowed: "
                    + rateLimiter.allow("user:123"));
        }

        System.out.println("\nWaiting 2 seconds...");
        Thread.sleep(2000);

        System.out.println("Sending 3 more requests:");

        for (int i = 1; i <= 3; i++) {
            System.out.println("Request after wait " + i + " allowed: "
                    + rateLimiter.allow("user:123"));
        }

        cleanupScheduler.shutdown();
    }
}
