package com.cyber.security.lab.handler.business_logic;

import com.cyber.security.lab.AuthenticationService;
import com.cyber.security.lab.JsonUtils;
import com.cyber.security.lab.MessageRequestDto;
import com.cyber.security.lab.MessageResponseDto;
import com.cyber.security.lab.ResponseTypeEnum;
import com.cyber.security.lab.ResponseUserUtils;
import com.cyber.security.lab.handler.CommandHandler;
import com.cyber.security.lab.handler.CommandType;
import com.cyber.security.lab.repository.CertificateRepository;
import com.cyber.security.lab.service.SessionService;
import com.google.inject.Inject;
import io.netty.channel.ChannelHandlerContext;

@CommandType(ResponseTypeEnum.MESSAGE)
public class MessageHandler implements CommandHandler {
    private final AuthenticationService authService;
    private final SessionService sessionService;
    private final CertificateRepository certificateRepository;

    @Inject
    public MessageHandler(AuthenticationService authenticationService, SessionService sessionService, CertificateRepository certificateRepository) {
        this.authService = authenticationService;
        this.sessionService = sessionService;
        this.certificateRepository = certificateRepository;
    }

    @Override
    public void handle(Object body, ChannelHandlerContext ctx) {
        var sessionId = SessionService.getSessionId(ctx);
        var clientCertificate = sessionService.getCertificate(sessionId);
        var serverCertificate = certificateRepository.getCertificate();
        try {
            MessageRequestDto dto = JsonUtils.fromJson(body, MessageRequestDto.class);
            var decryptedMessage = authService.decryptMessage(serverCertificate.privateKeyPath(), dto.encryptedClientMessage());

            var processedMessage = handleMessage(decryptedMessage);
            var encryptMessage = authService.encryptMessage(clientCertificate, processedMessage);

            var response = new MessageResponseDto(encryptMessage);
            ResponseUserUtils.sendOk(ctx, response);
        } catch (Exception e) {
            ResponseUserUtils.sendError(ctx, e.getMessage());
        }
    }

    private String handleMessage(String message) {
        return message.toLowerCase();
    }
}
