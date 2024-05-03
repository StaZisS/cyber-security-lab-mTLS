package com.cyber.security.lab.handler.auth;

import com.cyber.security.lab.AuthenticationService;
import com.cyber.security.lab.JsonUtils;
import com.cyber.security.lab.ResponseTypeEnum;
import com.cyber.security.lab.ResponseUserUtils;
import com.cyber.security.lab.handler.CommandHandler;
import com.cyber.security.lab.handler.CommandType;
import com.cyber.security.lab.ServerCheckRequestDto;
import com.cyber.security.lab.ServerCheckResponseDto;
import com.cyber.security.lab.repository.CertificateRepository;
import com.cyber.security.lab.service.SessionService;
import com.google.inject.Inject;
import io.netty.channel.ChannelHandlerContext;

import java.util.Random;

@CommandType(ResponseTypeEnum.SERVER_CHECK)
public class ServerCheckHandler implements CommandHandler {
    private static final Random RANDOM = new Random();
    private final AuthenticationService authService;
    private final SessionService sessionService;
    private final CertificateRepository certificateRepository;

    @Inject
    public ServerCheckHandler(AuthenticationService authenticationService, SessionService sessionService, CertificateRepository certificateRepository) {
        this.authService = authenticationService;
        this.sessionService = sessionService;
        this.certificateRepository = certificateRepository;
    }

    @Override
    public void handle(Object body, ChannelHandlerContext ctx) {
        try {
            ServerCheckRequestDto dto = JsonUtils.fromJson(body, ServerCheckRequestDto.class);
            var sessionId = SessionService.getSessionId(ctx);

            var isCorrectCertificate = authService.isCorrectCertificate(dto.certificate());
            if (isCorrectCertificate) {
                sessionService.setCertificateAndPublicKey(sessionId, dto.certificate(), dto.publicKey());

                var randomString = generateRandomString();
                sessionService.setTestMessage(sessionId, randomString);
                var encryptedTestMessage = authService.encryptMessage(dto.certificate(), randomString);

                var serverCertificate = certificateRepository.getCertificate();
                var response = new ServerCheckResponseDto(
                        encryptedTestMessage,
                        serverCertificate.certificate(),
                        serverCertificate.publicKey()
                );
                ResponseUserUtils.sendOk(ctx, response);
            } else {
                ResponseUserUtils.sendError(ctx, "Invalid certificate.");
            }
        } catch (Exception e) {
            ResponseUserUtils.sendError(ctx, e.getMessage());
        }
    }

    private String generateRandomString() {
        return RANDOM.ints(48, 122)
                .filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97))
                .mapToObj(i -> (char) i)
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
