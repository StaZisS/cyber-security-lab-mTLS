package com.cyber.security.lab.repository;

import com.cyber.security.lab.entity.CertificateEntity;

public class CertificateRepository {
    public CertificateEntity getCertificate() {
        return new CertificateEntity(
                """
                        -----BEGIN CERTIFICATE-----
                        MIIEuzCCAqMCFGCJyS0FYB8WiopBKFxuwPBhkbi+MA0GCSqGSIb3DQEBCwUAMCAx
                        HjAcBgNVBAMMFUNlcnRpZmljYXRlIGF1dGhvcml0eTAeFw0yNDA1MDIxNjI3NTVa
                        Fw0yNDA1MDkxNjI3NTVaMBQxEjAQBgNVBAMMCWxvY2FsaG9zdDCCAiIwDQYJKoZI
                        hvcNAQEBBQADggIPADCCAgoCggIBAJRt5fcLrIeEXNXVTfogakrrVgy9K8ejpcdz
                        0qyJMmQNnOayD+ADAp4muJ6+qmzD2alixhNjEmBF//PuCvSzNFP9laQtLIm5K97A
                        AiW1lEXtwQtmL/gzI9ptvB2iD4EQPxCev2dIL5DVywC/fwJyRdNmuSsmCwN21xDB
                        GPZ9B12H1kSmKefF5osz5DIFHz+KmOwDK7hMIAvAaXfReXBCYI/mDbnRPkdUQkF/
                        QgZFP5NJ2cCzxJcy12HM0F7NHfO/YmHmx1IQPKdDHwSBxFLrZARF+EHKxD9Ja9ls
                        prokmXe2wGmidqA1MrUW00ioiAcrVnvI3yoVZg1sZMXQSPtTfq/dMCkUcPCcM+1M
                        fAj9N7/RAa2N46rEW38tM1+V5DJjpLA6OVwG+1MiJpVIbcCwyl64DHR69yZOJ3wV
                        Y+ZiecooBwhIDsDa3Wr/B4a/Ge+bySvJMu1OJbRYCsxgY5mA6MQbejSBsat8U8N/
                        jLCGku/36xHzo+qq32iPstucXSl30qIgk5fiZszjStcx7sg/mqCkyKP+YPhtExY5
                        0k4ROjqIqBqbmk4utahlELfc8l3d/ZhVzSM0tOysbdFBrxu4c/hf8a/CIrJJSQnL
                        FYdWeguK9WmMC4/gQ1gEonvLLLrg6JmcygHrPSHUl4qS7DspqsDdXrOZMpECFplB
                        3dENdZ3FAgMBAAEwDQYJKoZIhvcNAQELBQADggIBANfz0TD4GvYA2U6yxpZt6/bm
                        /V6Gg4wO1IqMM8Sp2fuV2h6J48UbUdJV+dQ+n8ARmsGz62QwytI/OWf2o8Emycv5
                        qRyKYfgasnikPI8eq/iGSMSlFu/oEoqa0yo3tQVI2qzJBWquDZqjwPwxBLALoYUb
                        c8pZ+1qNYKGwD3FOFk2/KzS0L3hwjsRPBEGb97oQJj7vRZSuxNeNtLDhEIwqY+Ci
                        uE72eEG2D47zqQerqBO0g3yEDk4xG9MqCv7mm26e+ryNKAyTx9kTDtvBzbYuQxjv
                        svCXdjhZnGlY8BvnPheeGkWhTxu1mMuCI9G3HAjWpUZsEf04/afnhgoImCVkqXvT
                        9jlMGGC84pmQRYig87T8A7AMNOa3C/GnYUvKYSStxqAxS2SpcdVDinUHDMT8kkkn
                        74/Daas0QAnYNC5ZnHg0W4r5kINxcT9pafi71xWOs5ouJV3AdBI4af2gh9hwGQHZ
                        XKjhGhHP6bFsMkhR3gAcejEYi9DHJ+g4Soz+tLHyHEK1wtptpsDRq/U+gcKF3ZEG
                        jiLGtSvqpuIGot7n4e8f2/Xg+eH9tedeFE/iggDS7L/cQgbaPQGcpYcihPw8+Zci
                        uIVS3JLrcQvOBPVG4luYRaCRV752qynQh4zIjsuR1HUplbcbofkVmMTREyaPNRTW
                        p2T28sGqDmBp7fJYpWSI
                        -----END CERTIFICATE-----
                        """,
                "publicKey",
                "/cert/Server-keystore.p12"
        );
    }
}
