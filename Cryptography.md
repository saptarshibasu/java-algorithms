# Quick Cryptography

## Stream Cipher

Stream Cipher usually uses a pseudo random generator (PRG) function to generate a long key stream from a secret small key. The long key stream is then used to encrypt the message. In modern Stream Ciphers (like Salsa 20 of the eStream project), a nonce is used along with the key to generate the key stream. Unlike the key, the nonce is not secret. It's transferred along with the cipher text. The only requirement for the nonce is to ensure that for a given key, the nonce should not repeat. In other words, the key and nonce combination must be unique. Thus if the same message text is encrypted multiple times with the same secret key, the cipher text will be different each time and the attacker will have no way to know that the original message text is the same in all these cases.

For a stateless system, it is usually easy to generate random nonce. Hence, it is better to use a PRG to generate a 128 bit random number and assume that it is not going to repeat.

RC4 and CSS are some earlier stream ciphers that are broken and are not used in new projects.

## Block Cipher 

Block cipher takes a fixed-length block of bits and securely encrypt or decrypt it. A mode of operation is required to define how to repeatedly apply the block cipher's single block operation to encrypt a large amount of data. In block cipher, a small secret key is expanded to form multiple round keys. Each round key is then used to generate a round function. The block of bits is then passed through the round functions. 

DES, Triple DES are block ciphers that were in use earlier, but are now broken and not used in new projects. The most widely used block cipher algorithm is AES.

Since block ciphers operate on fixed-length blocks, if the input message length is not an exact multiple of blocks, padding extra bytes at the end of the message is required usually. However, there are certain modes such as CFB, OFB and CTR modes which effectively use block cipher as a stream cipher and therefore they do not need any padding. Hence, with these modes, the cipher text has the same size as the message text.

It should also be noted that the padding can be exploited in padding oracle attacks.

## Block Cipher Modes

### ECB (Electronic Code Book)

Not to be used as it is badly broken. It always produces the same cipher text for the same input text.

### CBC (Cipher Block Chaining)

CBC is a way to prevent chosen plain text attack. That means the cipher text of the same message text will be different at different times. In this mode a random Initialization Vector (IV) is XORed with the 1st message block before passing it through the encryption algorithm along with the secret key. The resultant cipher text is then XORed with the next message text block before passing it through the encryption algorithm in the same way. and this goes on. 

As a result, the process is sequential which means that the 4th message block cannot be encrypted before the 3rd block and so on. Also, here the cipher text is longer than the message text as the cipher text has an extra block to hold the IV. Note that the IV is not secret. It just have to be unpredictable or random and it should not repeat for a given key. The IV will be passed to the recipient as the first block of the cipher text.

The decryption process is very similar to the encryption process except that the XOR is done at the bottom after the decryption algorithm is run with the key and the cipher text block.

It can be mathematically proved that in order to remain sufficiently secure, the secret key should be changed after the encryption of 2^48 blocks.  

Alternatively, a nonce can be used instead of a IV. The nonce can be a counter which will be known to the recipient and hence it need not be part of the cipher text. However, here it is important that the nonce is encrypted with a second secret key (different from the key used to encrypt the message).

CBC requires padding. If the length of the message text is an exact multiple of the block size, an additional block with padded bytes will be add where each byte will contain 16. This will tell the decryption algorithm to discard the last 16 bytes from the cipher text. 

### CTR (Randomized Counter Mode)

This mode is completely parallelizable and it also uses an unpredictable or random IV which acts as a counter and is used along with the secret key to encrypt each blocks. Padding is not required here and the secret key should change after encrypting 2^64 blocks.

Counter mode uses a PRF and does not use the block cipher decryption capabilities which uses PRP. Hence, counter mode is more general and we can use any primitive like Salsa 20 as a PRF.

Here PRP (Pseudo Random Permutation) is a bijective function i.e. the input and the output are paired - for the same input we'll always get the same output. And then, there has to be an equivalent decryption algorithm in PRP. On the other hand, in PRF is not bijective. In the counter mode we are using the counter IV to generate a PRF.
