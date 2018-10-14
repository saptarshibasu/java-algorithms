package com.sapbasu.javastudy.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

public class RandomIvNonceGeneratorTest {
	
	private static final Logger logger = LogManager.getLogger(RandomIvNonceGeneratorTest.class);
	
	@Test
	public void whenGetNextBytesCalled_GivenNumberOfBytes_ReturnsByteStringOfRequestedSize() {
		RandomIvNonceGenerator rng = new RandomIvNonceGenerator();
		try {
			byte[] ivNonce = rng.getNextBytes(8);
			assertEquals(ivNonce.length, 8);
			logger.debug("The generated random IV / Nonce is: {}", ivNonce);
			
		} catch(Exception e) {
			fail("Exception occured", e);
		}
	}
}
