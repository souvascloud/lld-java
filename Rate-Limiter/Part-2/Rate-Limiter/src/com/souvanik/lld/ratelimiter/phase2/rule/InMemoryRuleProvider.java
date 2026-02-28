package com.souvanik.lld.ratelimiter.phase2.rule;

import com.souvanik.lld.ratelimiter.phase2.model.RateLimitRule;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class InMemoryRuleProvider implements RuleProvider {

    private final RateLimitRule defaultRule;

    public InMemoryRuleProvider(RateLimitRule defaultRule) {
        this.defaultRule = defaultRule;
    }

    @Override
    public RateLimitRule getRule(String key) {
        return defaultRule;
    }
}