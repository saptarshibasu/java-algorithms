package com.sapbasu.javastudy;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class PBK {
  
  private static final String KEY_GEN_FUNC = "PBKDF2WithHmacSHA256";
  
  public static SecretKeySpec getPBK(char[] password, int iterations,
      int keyLength, byte[] salt, String algorithm) throws Exception {
    PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterations, keyLength);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_GEN_FUNC);
    SecretKey secretKey = keyFactory.generateSecret(keySpec);
    SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), algorithm);
    return secretKeySpec;
  }
}
