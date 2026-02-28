package com.souvanik.lld.ratelimiter.phase1;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class TokenBucket {

    private final RateLimitRule rule;
    private long currentTokens;
    private long lastRefillTimeNanos;

    public TokenBucket(RateLimitRule rule) {
        this.rule = rule;
        this.currentTokens = rule.getCapacity();
        this.lastRefillTimeNanos = System.nanoTime();
    }

    public boolean tryConsume() {
        refill();

        if (currentTokens > 0) {
            currentTokens--;
            return true;
        }
        return false;
    }

    private void refill() {
        long now = System.nanoTime();
        long elapsedNanos = now - lastRefillTimeNanos;

        long tokensToAdd =
                (elapsedNanos / 1_000_000_000L) * rule.getRefillTokensPerSecond();

        if (tokensToAdd > 0) {
            currentTokens = Math.min(
                    rule.getCapacity(),
                    currentTokens + tokensToAdd
            );
            lastRefillTimeNanos = now;
        }
    }
}
