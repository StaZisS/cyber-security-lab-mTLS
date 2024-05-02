package com.cyber.security.lab.repository;

import com.cyber.security.lab.entity.CertificateEntity;

public class CertificateRepository {

    public CertificateEntity getCertificate() {
        return new CertificateEntity(
                "certificate",
                "publicKey",
                "privateKey"
        );
    }
}
