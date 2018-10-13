# Quick Cryptography Facts To Build a Secured Software

## Stream Cipher

Stream Cipher usually uses a pseudo random generator (PRG) function to generate a long key stream from a secret small key. The long key stream is then used to encrypt the message. In modern Stream Ciphers (like Salsa 20 of the eStream project), a nonce is used along with the key to generate the key stream. Unlike the key, the nonce is not secret. It's transferred along with the ciphertext. The only requirement for the nonce is to ensure that for a given key, the nonce should not repeat. In other words, the key and nonce combination must be unique. Thus if the same message text is encrypted multiple times with the same secret key, the cipher text will be different each time and the attacker will have no way to know that the original message text is the same in all these cases.

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

To be detailed.




