package com.example.library.management.tool.library.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncryptionUtil {

    private static final String SECRET_KEY = "pEyPxZvTPAG8xw2ymAITb6GZnXd+93H96FR/JDXZH/4=";
    private static final String ALGORITHM = "AES/CBC/PKCS5PADDING";
    private static final byte[] IV = new byte[16];

    public static String encrypt(String plainText) {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);

            // Decode the Base64-encoded key
            byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
            SecretKeySpec secretKeySpec = new SecretKeySpec(decodedKey, "AES");

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String encryptedText) {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);

            // Decode the Base64-encoded key
            byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
            SecretKeySpec secretKeySpec = new SecretKeySpec(decodedKey, "AES");

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
