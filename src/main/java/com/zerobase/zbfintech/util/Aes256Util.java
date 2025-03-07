package com.zerobase.zbfintech.util;

import com.zerobase.zbfintech.config.Aes256Config;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Component
public class Aes256Util {

    private static final String ALG = "AES/CBC/PKCS5Padding";

    private static Aes256Config aes256Config;

    public Aes256Util(Aes256Config aes256Config) {
        Aes256Util.aes256Config = aes256Config;
    }

    public static String encrypt(String text) {
        try {
            Cipher cipher = Cipher.getInstance(ALG);
            SecretKeySpec keySpec = new SecretKeySpec(aes256Config.getKey().getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(aes256Config.getIv().getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);

            byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeBase64String(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String decrypt(String cipherText) {
        try {
            Cipher cipher = Cipher.getInstance(ALG);
            SecretKeySpec keySpec = new SecretKeySpec(aes256Config.getKey().getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(aes256Config.getIv().getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

            byte[] decodeBytes = Base64.decodeBase64(cipherText);
            byte[] decrypted = cipher.doFinal(decodeBytes);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}