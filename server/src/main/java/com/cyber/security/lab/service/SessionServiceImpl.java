package com.cyber.security.lab.service;

import com.cyber.security.lab.entity.SessionEntity;
import com.cyber.security.lab.public_interface.CreateSessionDto;
import com.cyber.security.lab.repository.SessionRepository;
import com.google.inject.Inject;

public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;

    @Inject
    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public void createSession(CreateSessionDto dto) {
        var entity = new SessionEntity(
                dto.sessionId(),
                false,
                dto.certificate(),
                dto.publicKey(),
                ""
        );
        sessionRepository.createSession(entity);
    }

    @Override
    public void deleteSession(String sessionId) {
        sessionRepository.deleteSession(sessionId);
    }

    @Override
    public void setSessionAuthenticated(String sessionId, boolean isAuthorized) {
        var session = sessionRepository.getSession(sessionId);
        var newSession = new SessionEntity(
                session.sessionId(),
                isAuthorized,
                session.certificate(),
                session.publicKey(),
                session.testMessage()
        );
        sessionRepository.updateSession(newSession);
    }

    @Override
    public boolean isSessionAuthenticated(String sessionId) {
        return sessionRepository.getSession(sessionId).isAuthorized();
    }

    @Override
    public void setCertificateAndPublicKey(String sessionId, String certificate, String publicKey) {
        var session = sessionRepository.getSession(sessionId);
        var newSession = new SessionEntity(
                session.sessionId(),
                session.isAuthorized(),
                certificate,
                publicKey,
                session.testMessage()
        );
        sessionRepository.updateSession(newSession);
    }

    @Override
    public void setTestMessage(String sessionId, String testMessage) {
        var session = sessionRepository.getSession(sessionId);
        var newSession = new SessionEntity(
                session.sessionId(),
                session.isAuthorized(),
                session.certificate(),
                session.publicKey(),
                testMessage
        );
        sessionRepository.updateSession(newSession);
    }

    @Override
    public String getTestMessage(String sessionId) {
        return sessionRepository.getSession(sessionId).testMessage();
    }
}
