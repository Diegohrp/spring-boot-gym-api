package com.diegohrp.gymapi.service;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {
    private static final int MAX_ATTEMPTS = 3;
    private static final int BLOCK_DURATION_MINUTES = 5;
    private final ConcurrentHashMap<String, AttemptInfo> attemptsCache;
    private final ScheduledExecutorService scheduler;

    private static class AttemptInfo {
        int attempts;
        long blockedUntil;

        AttemptInfo() {
            this.attempts = 0;
            this.blockedUntil = 0;
        }
    }

    public LoginAttemptService() {
        this.attemptsCache = new ConcurrentHashMap<>();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();

        // Cleanup task runs every 5 minutes
        scheduler.scheduleAtFixedRate(this::cleanupCache, 5, 5, TimeUnit.MINUTES);
    }

    public void loginSucceeded(String key) {
        attemptsCache.remove(key);
    }

    public void loginFailed(String key) {
        AttemptInfo info = attemptsCache.computeIfAbsent(key, k -> new AttemptInfo());
        info.attempts++;

        if (info.attempts >= MAX_ATTEMPTS) {
            info.blockedUntil = System.currentTimeMillis() + (BLOCK_DURATION_MINUTES * 60 * 1000);
        }
    }

    public boolean isBlocked(String key) {
        AttemptInfo info = attemptsCache.get(key);
        if (info == null) {
            return false;
        }

        if (System.currentTimeMillis() > info.blockedUntil) {
            attemptsCache.remove(key);
            return false;
        }

        return info.attempts >= MAX_ATTEMPTS;
    }

    private void cleanupCache() {
        long now = System.currentTimeMillis();
        attemptsCache.entrySet().removeIf(entry ->
                entry.getValue().blockedUntil < now || entry.getValue().attempts < MAX_ATTEMPTS
        );
    }

    @PreDestroy
    public void destroy() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(1, TimeUnit.MINUTES)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }
}
