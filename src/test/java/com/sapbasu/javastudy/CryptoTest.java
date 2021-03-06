package com.sapbasu.javastudy;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.Test;

public class CryptoTest {
  
  private int KEY_LEN = 256; // bits
  
  @Test
  public void whenDecryptCalled_givenEncryptedTest_returnsDecryptedBytes()
      throws Exception {
    
    char[] input = {'e', 'n', 'c', 'r', 'y', 'p', 't', 'i', 'o', 'n'};
    byte[] inputBytes = convertInputToBytes(input);
    
    KeyGenerator keyGen = KeyGenerator.getInstance("AES");
    keyGen.init(KEY_LEN, SecureRandom.getInstanceStrong());
    SecretKey secretKey = keyGen.generateKey();
    
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
