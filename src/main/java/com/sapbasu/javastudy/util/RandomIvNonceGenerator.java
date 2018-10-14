package com.sapbasu.javastudy.util;

import java.security.SecureRandom;

public class RandomIvNonceGenerator {
	
	public byte[] getNextBytes(int bytesNum) throws Exception {
		
		if(bytesNum <1)
			throw new IllegalArgumentException("Number of bytes must be greater than 0");
		
		byte[] ivNonce = new byte[bytesNum];
		SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
		prng.setSeed(prng.generateSeed(bytesNum));
		prng.nextBytes(ivNonce);
		return ivNonce;
	}
}