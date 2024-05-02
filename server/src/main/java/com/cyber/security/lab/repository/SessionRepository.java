package com.cyber.security.lab.repository;

import com.cyber.security.lab.entity.SessionEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionRepository {
    private final Map<String, SessionEntity> sessions = new ConcurrentHashMap<>();

    public void createSession(SessionEntity session) {
        sessions.put(session.sessionId(), session);
    }

    public SessionEntity getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public void deleteSession(String sessionId) {
        sessions.remove(sessionId);
    }

    public void updateSession(SessionEntity session) {
        sessions.put(session.sessionId(), session);
    }
}
