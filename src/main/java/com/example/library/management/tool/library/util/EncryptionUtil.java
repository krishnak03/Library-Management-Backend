package com.example.library.management.tool.library.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtil {

    private static final String SECRET_KEY = "3Dxgo1NroyWLeRctxPZ1RaXo7ufZcDod"; // 32-byte key for AES-256
    private static final String CHARSET = "UTF-8";
    private static final String ALGORITHM = "AES/CBC/PKCS5PADDING";

    public static String encrypt(String plainText) {
        try {
            byte[] iv = new byte[16];
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(CHARSET), "AES");

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] encrypted = cipher.doFinal(plainText.getBytes(CHARSET));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String encryptedText) {
        try {
            byte[] iv = new byte[16];
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(CHARSET), "AES");

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            return new String(original, CHARSET);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
