package com.souvanik.lld.ratelimiter.phase2.cleanup;

import com.souvanik.lld.ratelimiter.phase2.store.InMemoryBucketStore;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class BucketCleanupScheduler {
    private final ScheduledExecutorService scheduler =  Executors.newSingleThreadScheduledExecutor();

    private final InMemoryBucketStore store;
    private final long ttlNanos;

    public BucketCleanupScheduler(InMemoryBucketStore store,
                                  long ttlSeconds) {
        this.store = store;
        this.ttlNanos = ttlSeconds * 1_000_000_000L;
    }

    public void start() {
        scheduler.scheduleAtFixedRate(
                this::cleanup,
                ttlNanos,
                ttlNanos,
                TimeUnit.NANOSECONDS
        );
    }

    private void cleanup() {
        long now = System.nanoTime();

        store.getBuckets().entrySet().removeIf(entry ->
                (now - entry.getValue().getLastAccessTimeNanos()) > ttlNanos
        );
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}
