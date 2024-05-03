import com.cyber.security.lab.AuthenticationService;
import com.cyber.security.lab.AuthenticationServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthenticationServiceTest {
    private final AuthenticationService authenticationService = new AuthenticationServiceImpl();

    @Test
    public void testIsCorrectCertificate() {
        var certificate = """
                -----BEGIN CERTIFICATE-----
                MIIEuDCCAqACFFYnhy2U/EapQ2JXlOIIEJ5IQaCGMA0GCSqGSIb3DQEBCwUAMCAx
                HjAcBgNVBAMMFUNlcnRpZmljYXRlIGF1dGhvcml0eTAeFw0yNDA1MDIxNjI4MDJa
                Fw0yNDA1MDkxNjI4MDJaMBExDzANBgNVBAMMBkNsaWVudDCCAiIwDQYJKoZIhvcN
                AQEBBQADggIPADCCAgoCggIBAMdiuHZfc8m9HDUW3dHKVIBMyi596QHzFXvmpGT7
                GVhm07usxHzcjIXC1uBY53LD3jKuaabwH+iYwXZ4l2GywKuORAFEF2NMgNzvyNQQ
                7ETZxGQIkPWD+Yb0OarZbf/ImF2+Qc20wbmHPp0gqCreYbhLbSIUZNzsPjA1gS0b
                mr4wJu8gxNcsm4KHbv8UZwyRmRN4mb0t3Knz9qL6wWAK8B1T7Kvss53oD952KZJ3
                iGr88FojoZ86SoUF7wvlh1SL8oLqNiENtrxkaWBvak6+CBVJ4efyuY+yplrOZg5b
                /rsmlpDIuAlrZZUfHjOwWUam95UlhJzDGpgcZq4OyfpGTWtga0Y6ZR2Pbdq5vWt4
                /GPaMBMopl+q+0/GdoyvDjHG8iIGkwaya/aGuOwnKigi3c+SkkuMRCaGhl1Ktr+5
                EWJbaJ4exVFC7LDsyJcWfoEb62mAzRh+3ofZ7jkseTc8VS4ET45L8GNnAryH7Zj9
                Thai2tSLof2ByZs0hyCqsTfAMKn9C+kyjB2RyaNQrg9Po9DckfGGi7xWxq+wa1mc
                e/qSPQRbGT3ejUpqiYtnydFyNeRfQiw4Cnq2OU5ZlHWwP154KEiduYSuLyAnVlfg
                wiCQyk4sm1orLXovPqxZ0cnyNx8Dno4wU6856jeo5ZwArxNT4haXGvGXPvCSYMT5
                elx1AgMBAAEwDQYJKoZIhvcNAQELBQADggIBAGnevjl7HP9/TG+tO4uaz8U2IuKo
                sl6RiDtoXWi9s9t1z3F8jWIS95v+Oxc9unT5FI/N0jSRavSQm+uRR8KL1dBohUkP
                X3jaMK5I1utQlCaIZq0sPbJUThsK9jaFu2aMM21kqo/G7+Slmgg0xSPDZOKW8DLX
                5RlWgzaC1VDCstaScAejDjWAGftMeiSOuVCwAC/JrF8krVAoWvma31ockj2Oimm+
                f3o01x4u9+u+IkXccgui6EYLigr+lxc1c8yGhO18h3s4WrXg0G3eNu+l5cT4zolo
                q8anxJ6t9PYSbDxK7/iFtY5kOHi0oLjFFH7OZntrbEfFVCuuTkbkgHao3Fhgadc+
                1/kAtGhmB3Wt1FPn0AYP/r/tW6P9qXk7TUV85BANizbq/INdG3S8YPXjrFKp3meq
                6fvp4ti49teAFVXxFIeLg3VHQ4lz2fx+d3pyPQzIa0lOAV9bWNSf3i8p9Gm9/Sl9
                nUsYa3JEDpU2m9r33l6JAhf4PG/KprOD/yta/dcsZp7T0qwg9eN9jS4JJ0PynetV
                uiYHZoLRtNYe5lG6kbhCBI2jpK57Ta6KFzepKHcz3sqBp1KJLiZu5Qt97rAZFeGn
                20YF/Ca3Rn2sNoKfVRZ7XfYSm8/opLsWGgw7+HC+ia6hx9nsBZIi/sRpqEJoNxQ/
                5ycwqx/B8mx7Qwti
                -----END CERTIFICATE-----
                """;

        assertTrue(authenticationService.isCorrectCertificate(certificate));
    }

    @Test
    public void encryptAndDecryptMessage() {
        var publicKey = """
                -----BEGIN CERTIFICATE-----
                MIIEuDCCAqACFFYnhy2U/EapQ2JXlOIIEJ5IQaCGMA0GCSqGSIb3DQEBCwUAMCAx
                HjAcBgNVBAMMFUNlcnRpZmljYXRlIGF1dGhvcml0eTAeFw0yNDA1MDIxNjI4MDJa
                Fw0yNDA1MDkxNjI4MDJaMBExDzANBgNVBAMMBkNsaWVudDCCAiIwDQYJKoZIhvcN
                AQEBBQADggIPADCCAgoCggIBAMdiuHZfc8m9HDUW3dHKVIBMyi596QHzFXvmpGT7
                GVhm07usxHzcjIXC1uBY53LD3jKuaabwH+iYwXZ4l2GywKuORAFEF2NMgNzvyNQQ
                7ETZxGQIkPWD+Yb0OarZbf/ImF2+Qc20wbmHPp0gqCreYbhLbSIUZNzsPjA1gS0b
                mr4wJu8gxNcsm4KHbv8UZwyRmRN4mb0t3Knz9qL6wWAK8B1T7Kvss53oD952KZJ3
                iGr88FojoZ86SoUF7wvlh1SL8oLqNiENtrxkaWBvak6+CBVJ4efyuY+yplrOZg5b
                /rsmlpDIuAlrZZUfHjOwWUam95UlhJzDGpgcZq4OyfpGTWtga0Y6ZR2Pbdq5vWt4
                /GPaMBMopl+q+0/GdoyvDjHG8iIGkwaya/aGuOwnKigi3c+SkkuMRCaGhl1Ktr+5
                EWJbaJ4exVFC7LDsyJcWfoEb62mAzRh+3ofZ7jkseTc8VS4ET45L8GNnAryH7Zj9
                Thai2tSLof2ByZs0hyCqsTfAMKn9C+kyjB2RyaNQrg9Po9DckfGGi7xWxq+wa1mc
                e/qSPQRbGT3ejUpqiYtnydFyNeRfQiw4Cnq2OU5ZlHWwP154KEiduYSuLyAnVlfg
                wiCQyk4sm1orLXovPqxZ0cnyNx8Dno4wU6856jeo5ZwArxNT4haXGvGXPvCSYMT5
                elx1AgMBAAEwDQYJKoZIhvcNAQELBQADggIBAGnevjl7HP9/TG+tO4uaz8U2IuKo
                sl6RiDtoXWi9s9t1z3F8jWIS95v+Oxc9unT5FI/N0jSRavSQm+uRR8KL1dBohUkP
                X3jaMK5I1utQlCaIZq0sPbJUThsK9jaFu2aMM21kqo/G7+Slmgg0xSPDZOKW8DLX
                5RlWgzaC1VDCstaScAejDjWAGftMeiSOuVCwAC/JrF8krVAoWvma31ockj2Oimm+
                f3o01x4u9+u+IkXccgui6EYLigr+lxc1c8yGhO18h3s4WrXg0G3eNu+l5cT4zolo
                q8anxJ6t9PYSbDxK7/iFtY5kOHi0oLjFFH7OZntrbEfFVCuuTkbkgHao3Fhgadc+
                1/kAtGhmB3Wt1FPn0AYP/r/tW6P9qXk7TUV85BANizbq/INdG3S8YPXjrFKp3meq
                6fvp4ti49teAFVXxFIeLg3VHQ4lz2fx+d3pyPQzIa0lOAV9bWNSf3i8p9Gm9/Sl9
                nUsYa3JEDpU2m9r33l6JAhf4PG/KprOD/yta/dcsZp7T0qwg9eN9jS4JJ0PynetV
                uiYHZoLRtNYe5lG6kbhCBI2jpK57Ta6KFzepKHcz3sqBp1KJLiZu5Qt97rAZFeGn
                20YF/Ca3Rn2sNoKfVRZ7XfYSm8/opLsWGgw7+HC+ia6hx9nsBZIi/sRpqEJoNxQ/
                5ycwqx/B8mx7Qwti
                -----END CERTIFICATE-----
                """;
        var message = "Hello, World!";
        var encryptedMessage = authenticationService.encryptMessage(publicKey, message);

        var decryptedMessage = authenticationService.decryptMessage("/cert/Client-keystore.p12", encryptedMessage);
        assertEquals(decryptedMessage, message);
    }
}