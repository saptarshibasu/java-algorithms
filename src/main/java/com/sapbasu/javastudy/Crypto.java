package com.sapbasu.javastudy;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Optional;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {

	private static int AUTH_TAG_SIZE = 128; // bits
	private static int IV_LEN = 12; // bytes

	private static SecureRandom prng;
	private static int bytesGenerated;

	public byte[] encrypt(byte[] input, SecretKeySpec key) throws Exception {

		Objects.requireNonNull(input, "Input message cannot be null");
		Objects.requireNonNull(key, "key cannot be null");

		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

		byte[] iv = getIV(IV_LEN);

		GCMParameterSpec gcmParamSpec = new GCMParameterSpec(AUTH_TAG_SIZE, iv);
		cipher.init(Cipher.ENCRYPT_MODE, key, gcmParamSpec);
		byte[] messageCipher = cipher.doFinal(input);

		byte[] cipherText = new byte[messageCipher.length + IV_LEN];
		System.arraycopy(iv, 0, cipherText, 0, IV_LEN);
		System.arraycopy(messageCipher, 0, cipherText, IV_LEN, messageCipher.length);
		return cipherText;
	}

	public byte[] decrypt(byte[] input, SecretKeySpec key) throws Exception {
		Objects.requireNonNull(input, "Input message cannot be null");
		Objects.requireNonNull(key, "key cannot be null");

		byte[] iv = new byte[IV_LEN];
		System.arraycopy(input, 0, iv, 0, IV_LEN);

		byte[] messageCipher = new byte[input.length - IV_LEN];
		System.arraycopy(input, IV_LEN, messageCipher, 0, input.length - IV_LEN);

		GCMParameterSpec gcmParamSpec = new GCMParameterSpec(AUTH_TAG_SIZE, iv);
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, key, gcmParamSpec);
		return cipher.doFinal(messageCipher);
	}

	public byte[] getIV(int bytesNum) {

		if (bytesNum < 1)
			throw new IllegalArgumentException("Number of bytes must be greater than 0");

		byte[] iv = new byte[bytesNum];

		prng = Optional.ofNullable(prng).orElseGet(() -> {
			try {
				prng = SecureRandom.getInstanceStrong();
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException("Wrong algorithm name", e);
			}
			return prng;
		});

		if (bytesGenerated > Math.pow(2, 16) || bytesGenerated == 0) {
			prng.setSeed(prng.generateSeed(bytesNum));
			bytesGenerated = 0;
		}

		prng.nextBytes(iv);
		bytesGenerated = bytesGenerated + bytesNum;

		return iv;
	}
}
