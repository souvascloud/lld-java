package com.souvanik.lld.ratelimiter.phase2.core;

import com.souvanik.lld.ratelimiter.phase2.algorithm.RateLimitAlgorithm;
import com.souvanik.lld.ratelimiter.phase2.model.RateLimitRule;
import com.souvanik.lld.ratelimiter.phase2.rule.RuleProvider;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class DefaultRateLimiter implements RateLimiter {

    private final RateLimitAlgorithm algorithm;
    private final RuleProvider ruleProvider;

    public DefaultRateLimiter(RateLimitAlgorithm algorithm,
                              RuleProvider ruleProvider) {
        this.algorithm = algorithm;
        this.ruleProvider = ruleProvider;
    }

    @Override
    public boolean allow(String key) {
        RateLimitRule rule = ruleProvider.getRule(key);
        return algorithm.allow(key, rule);
    }
}
