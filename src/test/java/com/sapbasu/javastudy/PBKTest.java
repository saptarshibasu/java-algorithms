package com.sapbasu.javastudy;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.SecureRandom;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.Test;

public class PBKTest {
  
  @Test
  public void whenPBKCalled_givenPassword_generatesKey()
      throws Exception {
    
    char[] input = {'e', 'n', 'c', 'r', 'y', 'p', 't', 'i', 'o', 'n'};
    char[] password = {'t', 'e', 's', 't', 'p', 'w', 'd'};
    byte[] inputBytes = convertInputToBytes(input);
    
    SecureRandom random = SecureRandom.getInstanceStrong();
    byte[] salt = new byte[32];
    random.nextBytes(salt);
    
    SecretKey secretKey = PBK.getPBK(password, 400, 256, salt, "AES");
    
    SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(),
        "AES");
    
    Crypto.clearSecret(secretKey);
    
    byte[] encryptedBytes = Crypto.encrypt(inputBytes, secretKeySpec);
    byte[] decryptedBytes = Crypto.decrypt(encryptedBytes, secretKeySpec);
    
    Crypto.clearSecret(secretKeySpec);
    
    assertArrayEquals(inputBytes, decryptedBytes);
    
  }
  
  private byte[] convertInputToBytes(char[] input) {
    CharBuffer charBuf = CharBuffer.wrap(input);
    ByteBuffer byteBuf = Charset.forName(Charset.defaultCharset().name())
        .encode(charBuf);
    byte[] inputBytes = byteBuf.array();
    charBuf.clear();
    byteBuf.clear();
    return inputBytes;
  }
}
