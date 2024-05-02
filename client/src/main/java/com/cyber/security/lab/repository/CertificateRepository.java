package com.cyber.security.lab.repository;

import com.cyber.security.lab.entity.CertificateEntity;

public class CertificateRepository {
    private String serverCertificate;
    private String serverPublicKey;

    //TODO: Изменить на реальные данные
    public CertificateEntity getClientCertificate() {
        return new CertificateEntity(
                "certificate",
                "publicKey",
                "privateKey"
        );
    }

    public CertificateEntity getServerCertificate() {
        return new CertificateEntity(
                serverCertificate,
                serverPublicKey,
                ""
        );
    }

    public void setServerCertificate(String serverCertificate) {
        this.serverCertificate = serverCertificate;
    }

    public void setServerPublicKey(String serverPublicKey) {
        this.serverPublicKey = serverPublicKey;
    }
}
