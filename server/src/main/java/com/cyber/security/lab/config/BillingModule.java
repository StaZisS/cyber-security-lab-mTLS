package com.cyber.security.lab.config;

import com.cyber.security.lab.AuthenticationService;
import com.cyber.security.lab.AuthenticationServiceImpl;
import com.cyber.security.lab.handler.CommandHandler;
import com.cyber.security.lab.ServerInitializer;
import com.cyber.security.lab.handler.auth.ClientCheckHandler;
import com.cyber.security.lab.handler.auth.ServerCheckHandler;
import com.cyber.security.lab.handler.business_logic.MessageHandler;
import com.cyber.security.lab.repository.CertificateRepository;
import com.cyber.security.lab.repository.SessionRepository;
import com.cyber.security.lab.service.SessionService;
import com.cyber.security.lab.service.SessionServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public class BillingModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ServerInitializer.class).asEagerSingleton();

        bind(SessionRepository.class).asEagerSingleton();
        bind(CertificateRepository.class).asEagerSingleton();
        bind(AuthenticationService.class).to(AuthenticationServiceImpl.class);
        bind(SessionService.class).to(SessionServiceImpl.class);

        Multibinder<CommandHandler> multibinder = Multibinder.newSetBinder(binder(), CommandHandler.class);
        multibinder.addBinding().to(ServerCheckHandler.class);
        multibinder.addBinding().to(ClientCheckHandler.class);
        multibinder.addBinding().to(MessageHandler.class);
    }

}

