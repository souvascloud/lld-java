import com.souvanik.lld.ratelimiter.phase1.InMemoryRateLimiter;
import com.souvanik.lld.ratelimiter.phase1.RateLimitRule;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {

        RateLimitRule rule = new RateLimitRule(5, 1);
        InMemoryRateLimiter limiter = new InMemoryRateLimiter(rule);

        System.out.println("Sending 7 immediate requests:");

        for (int i = 1; i <= 7; i++) {
            System.out.println("Request " + i + " allowed: "
                    + limiter.allow("user:123"));
        }

        System.out.println("\nWaiting 2 seconds...");
        Thread.sleep(2000);

        System.out.println("Sending 3 more requests:");

        for (int i = 1; i <= 3; i++) {
            System.out.println("Request after wait " + i + " allowed: "
                    + limiter.allow("user:123"));
        }
    }
}