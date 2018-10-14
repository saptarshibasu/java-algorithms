package com.sapbasu.javastudy.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;

public class RandomIvNonceGenerator {
	
	private static SecureRandom prng;
	private static int bytesGenerated;
	
	public byte[] getNextBytes(int bytesNum) {
		
		if(bytesNum <1)
			throw new IllegalArgumentException("Number of bytes must be greater than 0");
		
		byte[] ivNonce = new byte[bytesNum];
		
		prng = Optional.ofNullable(prng).orElseGet(() -> {
			try {
				prng = SecureRandom.getInstance("SHA1PRNG");
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException("Wrong algorithm name", e);
			}
			return prng;
		});

		if(bytesGenerated > Math.pow(2, 16) || bytesGenerated == 0) {
			prng.setSeed(prng.generateSeed(bytesNum));
		}
		
		prng.nextBytes(ivNonce);
		bytesGenerated = bytesGenerated + bytesNum;

		return ivNonce;
	}
}