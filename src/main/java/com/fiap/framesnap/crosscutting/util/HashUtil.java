package com.fiap.framesnap.crosscutting.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HashUtil {

    public static String calculateSecretHash(String clientId, String clientSecret, String username) {
        if (clientId == null || clientId.isEmpty()) {
            throw new RuntimeException("ClientId não pode ser nulo ou vazio");
        }
        if (clientSecret == null || clientSecret.isEmpty()) {
            throw new RuntimeException("ClientSecret não pode ser nulo ou vazio");
        }
        if (username == null || username.isEmpty()) {
            throw new RuntimeException("Username não pode ser nulo ou vazio");
        }

        try {
            String message = username + clientId;
            SecretKeySpec signingKey = new SecretKeySpec(clientSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(rawHmac);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular Secret Hash", e);
        }
    }
}



