package com.cyber.security.lab;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

public class AuthenticationServiceImpl implements AuthenticationService {

    //https://czetsuya.medium.com/implementing-x509-certificate-validation-in-java-a-step-by-step-guide-90082d597a31
    //https://habr.com/ru/articles/593507/
    @Override
    public boolean isCorrectCertificate(String certificate, String publicKey) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");

            FileInputStream caInput = new FileInputStream("/cert/CA-self-signed-certificate.pem");
            X509Certificate caCert = (X509Certificate) certificateFactory.generateCertificate(caInput);
            caInput.close();

            X509Certificate clientCertificate = (X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(certificate.getBytes()));

            if (new Date().getTime() > clientCertificate.getNotAfter().getTime()) {
                return false;
            }

            var match = Arrays.equals(clientCertificate.getPublicKey().getEncoded(), caCert.getPublicKey().getEncoded());
            if (!match) {
                return false;
            }

            clientCertificate.verify(caCert.getPublicKey());

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //TODO: зашифровать сообщение
    @Override
    public String encryptMessage(String publicKey, String message) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            X509Certificate clientCertificate = (X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(publicKey.getBytes()));

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

    //TODO: расшифровать сообщение
    @Override
    public String decryptMessage(String privateKeyFile, String message) {
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream(privateKeyFile), "password".toCharArray());
            PrivateKey privateKey = (PrivateKey) keyStore.getKey("selfsigned", "password".toCharArray());

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] encryptedMessageBytes = Base64.getDecoder().decode(message);
            byte[] decryptedMessageBytes = cipher.doFinal(encryptedMessageBytes);

            return new String(decryptedMessageBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
