package com.cyber.security.lab.config;

import com.cyber.security.lab.handler.CommandHandler;
import com.cyber.security.lab.ServerInitializer;
import com.cyber.security.lab.handler.auth.AuthenticationHandler;
import com.cyber.security.lab.handler.business_logic.MessageHandler;
import com.cyber.security.lab.repository.SessionRepository;
import com.cyber.security.lab.repository.UserRepository;
import com.cyber.security.lab.service.AuthenticationService;
import com.cyber.security.lab.service.AuthenticationServiceImpl;
import com.cyber.security.lab.service.SessionManager;
import com.cyber.security.lab.service.SessionManagerImpl;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public class BillingModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ServerInitializer.class).asEagerSingleton();

        bind(SessionRepository.class).asEagerSingleton();
        bind(UserRepository.class).asEagerSingleton();
        bind(AuthenticationService.class).to(AuthenticationServiceImpl.class);
        bind(SessionManager.class).to(SessionManagerImpl.class);

        Multibinder<CommandHandler> multibinder = Multibinder.newSetBinder(binder(), CommandHandler.class);
        multibinder.addBinding().to(AuthenticationHandler.class);
        multibinder.addBinding().to(MessageHandler.class);
    }

}

