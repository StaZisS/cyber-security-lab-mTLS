package com.cyber.security.lab.config;

import com.cyber.security.lab.AuthenticationService;
import com.cyber.security.lab.AuthenticationServiceImpl;
import com.cyber.security.lab.network.Network;
import com.cyber.security.lab.network.NetworkImpl;
import com.cyber.security.lab.repository.CertificateRepository;
import com.cyber.security.lab.user_interface.CliUserInterface;
import com.cyber.security.lab.user_interface.UserInterface;
import com.google.inject.AbstractModule;

public class BillingModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserInterface.class).to(CliUserInterface.class);
        bind(Network.class).to(NetworkImpl.class);
        bind(AuthenticationService.class).to(AuthenticationServiceImpl.class);

        bind(CertificateRepository.class).asEagerSingleton();
    }
}
