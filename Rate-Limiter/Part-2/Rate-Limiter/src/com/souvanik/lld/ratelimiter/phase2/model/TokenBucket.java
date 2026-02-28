package com.souvanik.lld.ratelimiter.phase2.model;

import java.util.concurrent.atomic.AtomicLong;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class TokenBucket {

    private final RateLimitRule rule;
    private final AtomicLong currentTokens;
    private volatile long lastRefillTimeNanos;
    private volatile long lastAccessTimeNanos;

    public TokenBucket(RateLimitRule rule) {
        this.rule = rule;
        this.currentTokens = new AtomicLong(rule.getCapacity());
        long now = System.nanoTime();
        this.lastRefillTimeNanos = now;
        this.lastAccessTimeNanos = now;
    }

    public boolean tryConsume() {
        refill();

        while (true) {
            long existing = currentTokens.get();

            if (existing <= 0) {
                updateLastAccess();
                return false;
            }

            if (currentTokens.compareAndSet(existing, existing - 1)) {
                updateLastAccess();
                return true;
            }
        }
    }

    private void refill() {
        long now = System.nanoTime();
        long lastRefill = lastRefillTimeNanos;

        long nanosPerToken =
                1_000_000_000L / rule.getRefillTokensPerSecond();

        long elapsed = now - lastRefill;

        if (elapsed < nanosPerToken) {
            return;
        }

        synchronized (this) {
            now = System.nanoTime();
            elapsed = now - lastRefillTimeNanos;

            long tokensToAdd = elapsed / nanosPerToken;

            if (tokensToAdd > 0) {
                long updated = Math.min(
                        rule.getCapacity(),
                        currentTokens.get() + tokensToAdd
                );

                currentTokens.set(updated);

                lastRefillTimeNanos =
                        lastRefillTimeNanos + tokensToAdd * nanosPerToken;
            }
        }
    }

    private void updateLastAccess() {
        lastAccessTimeNanos = System.nanoTime();
    }

    public long getLastAccessTimeNanos() {
        return lastAccessTimeNanos;
    }
}
