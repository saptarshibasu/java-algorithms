package com.sapbasu.javastudy;

import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.Destroyable;

/**
 * 
 * A class for cryptographic utilities. <br>
 * This class provides encryption and decryption methods which use AES block
 * cipher in GCM mode for authenticated encryption <br>
 * To ensure additional security, in the following implementation SecureRandom
 * is re-seeded after producing every 2^16 bytes of pseudo random byte
 * generation. <br>
 * No provider is hard coded in the code following the general recommendations
 * <br>
 * for transmission over network or storage, the key or the cipher text should
 * be encoded using Base64 encoding.
 * 
 */
public class Crypto {
  
  private static final int AUTH_TAG_SIZE = 128; // bits
  
  // NIST recommendation: "For IVs, it is recommended that implementations
  // restrict support to the length of 96 bits, to
  // promote interoperability, efficiency, and simplicity of design."
  private static final int IV_LEN = 12; // bytes
  
  // number of random number bytes generated before re-seeding
  private static final double PRNG_RESEED_INTERVAL = Math.pow(2, 16);
  
  private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
  
  private static final List<Integer> ALLOWED_KEY_SIZES = Collections
      .unmodifiableList(Arrays.asList(new Integer[] {128, 192, 256})); // bits
  
  private static SecureRandom prng;
  
  // Used to keep track of random number bytes generated by PRNG
  // (for the purpose of re-seeding)
  private static int bytesGenerated = 0;
  
  public static byte[] encrypt(byte[] input, SecretKeySpec key)
      throws Exception {
    
    Objects.requireNonNull(input, "Input message cannot be null");
    Objects.requireNonNull(key, "key cannot be null");
    
    if (input.length == 0) {
      throw new IllegalArgumentException("Length of message cannot be 0");
    }
    
    if (!ALLOWED_KEY_SIZES.contains(key.getEncoded().length * 8)) {
      throw new IllegalArgumentException("Size of key must be 128, 192 or 256");
    }
    
    Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
    
    byte[] iv = getIV(IV_LEN);
    
    GCMParameterSpec gcmParamSpec = new GCMParameterSpec(AUTH_TAG_SIZE, iv);
    
    cipher.init(Cipher.ENCRYPT_MODE, key, gcmParamSpec);
    byte[] messageCipher = cipher.doFinal(input);
    
    // Prepend the IV with the message cipher
    byte[] cipherText = new byte[messageCipher.length + IV_LEN];
    System.arraycopy(iv, 0, cipherText, 0, IV_LEN);
    System.arraycopy(messageCipher, 0, cipherText, IV_LEN,
        messageCipher.length);
    return cipherText;
  }
  
  public static byte[] decrypt(byte[] input, SecretKeySpec key)
      throws Exception {
    Objects.requireNonNull(input, "Input message cannot be null");
    Objects.requireNonNull(key, "key cannot be null");
    
    if (input.length == 0) {
      throw new IllegalArgumentException("Input array cannot be empty");
    }
    
    byte[] iv = new byte[IV_LEN];
    System.arraycopy(input, 0, iv, 0, IV_LEN);
    
    byte[] messageCipher = new byte[input.length - IV_LEN];
    System.arraycopy(input, IV_LEN, messageCipher, 0, input.length - IV_LEN);
    
    GCMParameterSpec gcmParamSpec = new GCMParameterSpec(AUTH_TAG_SIZE, iv);
    
    Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
    cipher.init(Cipher.DECRYPT_MODE, key, gcmParamSpec);
    
    return cipher.doFinal(messageCipher);
  }
  
  /**
   * 
   * This method creates the Initialization Vector (IV). An initialization
   * Vector (IV) is required for GCM. The IV is not a secret. The only
   * requirement being it has to be random or unpredictable. In Java, the
   * SecuredRandom class is meant to produce cryptographically strong pseudo
   * random numbers. The pseudo-random number generation algorithm can be
   * specified in the {@code getInstance()} method. However, since Java 8, the
   * recommended way is to use {getInstanceStrong()} method which will use the
   * strongest algorithm configured and provided by the Provider. <br>
   * The recipient needs to know the IV to be able to decrypt the cipher text.
   * Therefore the IV needs to be transferred along with the cipher text. Some
   * implementations send the IV as AD (Associated Data) which means that the
   * authentication tag will be calculated on both the cipher text and the IV.
   * However, that is not required. The IV can be simply pre-pended with the
   * cipher text because if the IV is changed during transmission due to a
   * deliberate attack or network/file system error, the authentication tag
   * validation will fail anyway.
   * 
   * @param bytesNum
   *          The size of the Initialization Vector (IV)
   * @return
   */
  public static byte[] getIV(int bytesNum) {
    
    if (bytesNum < 1) throw new IllegalArgumentException(
        "Number of bytes must be greater than 0");
    
    byte[] iv = new byte[bytesNum];
    
    prng = Optional.ofNullable(prng).orElseGet(() -> {
      try {
        prng = SecureRandom.getInstanceStrong();
      } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException("Wrong algorithm name", e);
      }
      return prng;
    });
    
    if (bytesGenerated > PRNG_RESEED_INTERVAL || bytesGenerated == 0) {
      prng.setSeed(prng.generateSeed(bytesNum));
      bytesGenerated = 0;
    }
    
    prng.nextBytes(iv);
    bytesGenerated = bytesGenerated + bytesNum;
    
    return iv;
  }
  
  /**
   * 
   * Strings should not be used to hold the clear text message or the key, as
   * Strings go in the String pool and they will show up in a heap dump. For the
   * same reason, the client calling these encryption or decryption methods
   * should clear all the variables or arrays holding the message or the key
   * after they are no longer needed. Since Java 8 does not provide an easy
   * mechanism to clear the key from {@code SecretKeySpec}, this method uses
   * reflection to clear the key
   * 
   * @param key
   *          The secret key used to do the encryption
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   * @throws NoSuchFieldException
   * @throws SecurityException
   */
  @SuppressWarnings("unused")
  public static void clearSecret(Destroyable key)
      throws IllegalArgumentException, IllegalAccessException,
      NoSuchFieldException, SecurityException {
    Field keyField = key.getClass().getDeclaredField("key");
    keyField.setAccessible(true);
    byte[] encodedKey = (byte[]) keyField.get(key);
    Arrays.fill(encodedKey, Byte.MIN_VALUE);
  }
}
