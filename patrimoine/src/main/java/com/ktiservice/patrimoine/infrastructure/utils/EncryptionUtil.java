package com.ktiservice.patrimoine.infrastructure.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class for AES-256 encryption/decryption of sensitive data.
 * Used for encrypting phone numbers, addresses, etc.
 */
@Slf4j
@Component
public class EncryptionUtil {

    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 256;

    @Value("${app.encryption.secret-key}")
    private String secretKeyString;

    private SecretKey secretKey;

    /**
     * Initialize the secret key from configuration.
     */
    private SecretKey getSecretKey() {
        if (secretKey == null) {
            try {
                byte[] decodedKey = Base64.getDecoder().decode(secretKeyString);
                secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);
            } catch (Exception e) {
                log.error("Failed to decode secret key", e);
                throw new RuntimeException("Failed to initialize encryption key", e);
            }
        }
        return secretKey;
    }

    /**
     * Encrypt a plain text string using AES-256.
     *
     * @param plainText The text to encrypt
     * @return Encrypted and Base64-encoded string
     */
    public String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            log.error("Encryption failed for data", e);
            throw new RuntimeException("Encryption failed", e);
        }
    }

    /**
     * Decrypt an encrypted and Base64-encoded string using AES-256.
     *
     * @param encryptedText The encrypted Base64-encoded text
     * @return Decrypted plain text
     */
    public String decrypt(String encryptedText) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey());
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            log.error("Decryption failed for data", e);
            throw new RuntimeException("Decryption failed", e);
        }
    }

    /**
     * Generate a new AES-256 secret key (for key rotation).
     * Returns Base64-encoded key.
     */
    public static String generateNewSecretKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            keyGenerator.init(KEY_SIZE, new SecureRandom());
            SecretKey key = keyGenerator.generateKey();
            return Base64.getEncoder().encodeToString(key.getEncoded());
        } catch (Exception e) {
            log.error("Failed to generate new secret key", e);
            throw new RuntimeException("Key generation failed", e);
        }
    }
}
