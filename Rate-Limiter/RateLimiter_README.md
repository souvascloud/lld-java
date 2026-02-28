# Rate Limiter Design in Java (Phase 1 & Phase 2)

This module demonstrates the step by step design and evolution of a Rate
Limiter using the Token Bucket algorithm in Java.

The implementation is intentionally structured in two phases to reflect
the  journey from a basic implementation to a clean & extensible architecture.

------------------------------------------------------------------------

## Overview

Rate limiting is a critical system protection mechanism used to:

-   Prevent abuse and brute-force attacks
-   Ensure fairness among clients
-   Protect expensive downstream resources
-   Maintain service stability under high load

This project explores how to design a rate limiter that is:

-   Correct under concurrency
-   Precise in time calculations
-   Memory-aware
-   Architecturally clean and extensible

------------------------------------------------------------------------

# Phase 1 -- Basic Token Bucket Implementation

Phase 1 focuses on understanding and implementing the core Token Bucket
algorithm.

## Objectives

-   Implement basic token bucket logic
-   Support burst behavior
-   Handle time-based refill
-   Keep the implementation simple and easy to reason about

## Key Characteristics

-   In-memory storage
-   Lazy refill strategy (no background refill thread)
-   Not fully optimized for concurrency
-   Suitable for learning and conceptual clarity

## Package Structure

com.souvanik.lld.ratelimiter.phase1

    RateLimitRule.java
    TokenBucket.java
    InMemoryRateLimiter.java
    Main.java

## What Phase 1 Demonstrates

-   How tokens are consumed
-   How refill works over time
-   How burst handling behaves
-   Why naive implementations fail under concurrency

Phase 1 establishes the foundation but intentionally exposes limitations
that are addressed in Phase 2.

------------------------------------------------------------------------

# Phase 2 -- Thread-Safe and Clean Architecture Design

Phase 2 refactors and improves the implementation to make it safer and
more extensible.

## Objectives

-   Ensure thread-safe token consumption
-   Remove time drift in refill logic
-   Reduce unnecessary synchronization
-   Introduce memory cleanup for inactive keys
-   Separate responsibilities using clean architecture principles

## Key Improvements

### 1. Concurrency Safety

-   Token consumption uses atomic compare-and-set (CAS)
-   Refill logic minimizes lock scope
-   Critical sections are carefully controlled

### 2. Precision in Refill

-   No time drift
-   Partial elapsed time is preserved
-   Refill timestamp advances only by actual consumed duration

### 3. Memory Management

-   Each bucket tracks last access time
-   TTL-based cleanup removes inactive buckets
-   Prevents unbounded memory growth

### 4. Clean Architecture Separation

Responsibilities are separated into:

-   RateLimiter (entry point)
-   RateLimitAlgorithm (strategy layer)
-   BucketStore (state management)
-   RuleProvider (rule resolution)
-   Cleanup scheduler (lifecycle management)

## Package Structure

com.souvanik.lld.ratelimiter.phase2

    core/
        RateLimiter.java
        DefaultRateLimiter.java

    algorithm/
        RateLimitAlgorithm.java
        TokenBucketAlgorithm.java

    model/
        RateLimitRule.java
        TokenBucket.java

    store/
        BucketStore.java
        InMemoryBucketStore.java

    rule/
        RuleProvider.java
        InMemoryRuleProvider.java

    cleanup/
        BucketCleanupScheduler.java

## Architectural Flow

DefaultRateLimiter → RuleProvider → RateLimitAlgorithm → BucketStore →
TokenBucket

This separation allows:

-   Swapping algorithms without modifying core logic
-   Replacing in-memory storage with Redis or other distributed stores
-   Extending rule resolution strategies
-   Improving scalability without redesigning the system

------------------------------------------------------------------------

# Running the Demo

Use the Main class in each phase to observe:

-   Burst handling
-   Token refill behavior
-   Request acceptance and rejection
-   Memory cleanup behavior (Phase 2)

Example configuration:

-   Capacity: 5 tokens
-   Refill rate: 1 token per second

------------------------------------------------------------------------

## LLD Diagrams

- Class Diagram:

  <img width="979" height="925" alt="rate-limiter-domain-model" src="https://github.com/user-attachments/assets/76ff39a6-9fbe-43aa-8142-48c2cadefe99" />

 
  
- Sequence Diagram:

<img width="701" height="514" alt="sequence-rate-limiter" src="https://github.com/user-attachments/assets/9f0be793-004e-4ae4-969a-638a8182f5ee" />

  

- ------------------------------------------------------------------------

# Design Considerations

This implementation was built with the following principles:

-   Start simple and evolve
-   Prefer clarity before optimization
-   Optimize only after correctness
-   Separate responsibilities as complexity grows
-   Design for extension without modification

------------------------------------------------------------------------

# Future Evolution (Part 3 and Beyond)

Potential next steps:

-   Distributed rate limiting using Redis
-   Lua-based atomic refill and consume operations
-   Multi-key limiting (e.g., per-user and per-IP)
-   Retry-after header calculation
-   Metrics integration

------------------------------------------------------------------------

# Conclusion

This project demonstrates how a simple algorithm can evolve into a
robust and extensible system when engineering considerations such as
concurrency, precision, memory lifecycle, and architectural separation
are applied thoughtfully.

The goal is not just to implement rate limiting, but to understand how
design evolves as real world constraints are introduced.
