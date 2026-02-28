package com.souvanik.lld.ratelimiter.phase2.rule;

import com.souvanik.lld.ratelimiter.phase2.model.RateLimitRule;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public interface RuleProvider {
    RateLimitRule getRule(String key);
}
