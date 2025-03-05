package com.zerobase.zbfintech.util;

import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Aes256Util {


    public static String alg = "AES/CBC/PKCS5Padding";
    @Value("${aes.key}")
    private static String KEY;

    @Value("${aes.iv}")
    private static String IV;

    public static String encrypt(String text) {
        try {
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            return null;
        }
    }

    public static String decrypt(String encrypted) {
        try {
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decodedByte = Base64.getUrlDecoder().decode(encrypted);
            byte[] decrypted = cipher.doFinal(decodedByte);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }
}