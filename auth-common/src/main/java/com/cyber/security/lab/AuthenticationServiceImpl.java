package com.cyber.security.lab;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Date;

public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public boolean isCorrectCertificate(String certificate) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");

            var resourceUrl = getClass().getResource("/cert/CA-self-signed-certificate.pem");
            FileInputStream caInput = new FileInputStream(resourceUrl.getFile());
            X509Certificate caCert = (X509Certificate) certificateFactory.generateCertificate(caInput);
            caInput.close();

            X509Certificate clientCertificate = (X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(certificate.getBytes()));

            if (new Date().getTime() > clientCertificate.getNotAfter().getTime()) {
                return false;
            }

            clientCertificate.verify(caCert.getPublicKey());

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String encryptMessage(String certificate, String message) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            X509Certificate clientCertificate = (X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(certificate.getBytes()));

            PublicKey key = clientCertificate.getPublicKey();
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(message.getBytes());

            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public String decryptMessage(String keystorePrivateKeyPath, String message) {
        var password = "password";
        try {
            var resourceUrl = getClass().getResource(keystorePrivateKeyPath);
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(new FileInputStream(resourceUrl.getFile()), password.toCharArray());

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, keyStore.getKey("1", password.toCharArray()));

            byte[] encryptedMessageBytes = Base64.getDecoder().decode(message);
            byte[] decryptedMessageBytes = cipher.doFinal(encryptedMessageBytes);

            return new String(decryptedMessageBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
