package com.cyber.security.lab.handler.auth;

import com.cyber.security.lab.AuthenticationService;
import com.cyber.security.lab.JsonUtils;
import com.cyber.security.lab.ResponseTypeEnum;
import com.cyber.security.lab.ResponseUserUtils;
import com.cyber.security.lab.handler.CommandHandler;
import com.cyber.security.lab.handler.CommandType;
import com.cyber.security.lab.ClientCheckRequestDto;
import com.cyber.security.lab.ClientCheckResponseDto;
import com.cyber.security.lab.repository.CertificateRepository;
import com.cyber.security.lab.service.SessionService;
import com.google.inject.Inject;
import io.netty.channel.ChannelHandlerContext;

@CommandType(ResponseTypeEnum.CLIENT_CHECK)
public class ClientCheckHandler implements CommandHandler {
    private final AuthenticationService authService;
    private final SessionService sessionService;
    private final CertificateRepository certificateRepository;

    @Inject
    public ClientCheckHandler(AuthenticationService authenticationService, SessionService sessionService, CertificateRepository certificateRepository) {
        this.authService = authenticationService;
        this.sessionService = sessionService;
        this.certificateRepository = certificateRepository;
    }

    @Override
    public void handle(Object body, ChannelHandlerContext ctx) {
        try {
            ClientCheckRequestDto dto = JsonUtils.fromJson(body, ClientCheckRequestDto.class);
            var sessionId = SessionService.getSessionId(ctx);
            var testMessage = sessionService.getTestMessage(sessionId);

            if (!testMessage.equals(dto.decryptedServerMessage())) {
                ResponseUserUtils.sendError(ctx, "Invalid decrypted message.");
            } else {
                var certificate = certificateRepository.getCertificate();
                var decryptedTestMessage = authService.decryptMessage(certificate.privateKeyPath(), dto.encryptedClientMessage());

                sessionService.setSessionAuthenticated(sessionId, true);

                var response = new ClientCheckResponseDto(decryptedTestMessage);
                ResponseUserUtils.sendOk(ctx, response);
            }

        } catch (Exception e) {
            ResponseUserUtils.sendError(ctx, e.getMessage());
        }
    }
}
