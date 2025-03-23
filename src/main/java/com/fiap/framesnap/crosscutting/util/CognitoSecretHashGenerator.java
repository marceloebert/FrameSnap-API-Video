package com.fiap.framesnap.crosscutting.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CognitoSecretHashGenerator {
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    public static String calculateSecretHash(String clientId, String clientSecret, String username) {
        try {
            String message = username + clientId;
            SecretKeySpec keySpec = new SecretKeySpec(clientSecret.getBytes(), HMAC_SHA256_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(keySpec);
            byte[] rawHmac = mac.doFinal(message.getBytes());
            return Base64.getEncoder().encodeToString(rawHmac);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular o SECRET_HASH", e);
        }
    }
}
