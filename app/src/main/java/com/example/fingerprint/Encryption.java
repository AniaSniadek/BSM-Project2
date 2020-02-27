package com.example.fingerprint;

import android.util.Base64;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {

    String AES = "AES";

    public String encrypt(String Data, String klucz) throws Exception {
        SecretKeySpec key = generateKey(klucz);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedValue;
    }

    public String decrypt(String Data, String klucz) throws Exception {
        SecretKeySpec key = generateKey(klucz);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodeValue = Base64.decode(Data, Base64.DEFAULT);
        byte[] decValue = c.doFinal(decodeValue);
        String descryptedValue = new String(decValue);
        return descryptedValue;
    }

    public SecretKeySpec generateKey(String password) throws Exception {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, AES);
        return secretKeySpec;
    }
}
