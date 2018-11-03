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

### GCM / CCM / EAX (Authenticated Encryption Modes)

CBC and CTR modes provide confidentiality but not integrity. GCM / CCM / EAX are the modes that provide both. 

GCM is CTR-based encryption then CW-MAC. It performs well in Intel architecture as Intel provides specialized instructions for GCM.

CCM is CBC-MAC then CTR-mode encryption. CCM uses AES for both encryption and MAC.

EAX is CTR-mode encryption then CMAC

Since all three of GCM, CCM and EAX are CTR-based encryption, they don't need any padding and therefore they are safe from the padding related attacks.

All these modes support AEAD (Authenticated Encryption with Associated Data). AEAD means that the associated data is not encrypted, whereas the body of the message is encrypted. However, the MAC is computed on both the associated data ad message body taken together.

GCM uses a very fast MAC and hence its performance is better as compared to the other two which uses block cipher for both encryption and MAC.

OCB is the fastest mode among all of these. However, it has patents. Therefore, GCM is the right mode to be used in most of the cases. It may, however, have a large code size (if not implemented on an Intel architecture).

## Password Hashing

Password hashing is something where we don't want speed because speed would enable a brute-force attacker to quickly find out the password.

Hence SHA256 is not suitable for password hashing. Instead, PBKDF2, bcrypt or scrypt should be used for password hashing.

bcrypt is currently the defacto standard for passwoord hashing. scrypt is supposed to be stronger but it is relatively new and therefore less vetted.

## TLS

TLS latest version is 1.2. The TLS cipher suites are provided by Sun JSSE provider in Java. The cipher suite to be used is determined by the client preference. In the server side, it is a good idea to disable the cipher suites that use the old and weak algorithms. For e.g. the cipher suites that use CBC mode are vulnerable to certain types of attacks. However, disabling all cipher suites using CBC would mean blocking out pre-4.3 android, pre-7 java, pre-11 internet explorer, pre-1.0 openssl as well as pre-7 safari on OS X.

Given that GCM is the one of the most secured encryption modes available, it may be a good idea to enable only the cipher suites using GCM. However, it should be noted that the GCM is introduced in TLS 1.2 only and the major browser versions started supporting GCM since Oct, 2013. The browser versions released before that date will be blocked with the GCM-only strategy.

We can use [CanIUse](https://caniuse.com/#search=tls) to find out which browser version supports which version of TLS. 

[Qualis](https://www.ssllabs.com/ssltest/) shows some of the best SSL configured websites and the cipher suites used. [This](https://www.ssllabs.com/ssltest/viewMyClient.html) also shows the cipher suites used by your browser. The best practices for TLS deployment and the recommended list of cipher suites are listed [here](https://github.com/ssllabs/research/wiki/SSL-and-TLS-Deployment-Best-Practices). Finally, [this](https://www.ssllabs.com/ssltest/clients.html) table lists which browser versions support what.

PCI DSS no longer supports TLS1.0 and below versions.

### TLS Cipher Suite

A typical cipher suite name looks like this:

*TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256*

Here 
* *ECDHE* - Symmetric key exchange algorithm
* *ECDSA* - Digital Signature algorithm used for signing the key
* *AES_128_GCM* - Block cipher and mode with 128 bit key

*ECDHE* stands for Elliptic Curve Diffie Hellman Ephemeral. The *Elliptic* variant (the first *E*) is used for performance, whereas the *Ephemeral* variant (the last *E*) is for *forward secrecy*. Forward secrecy means that if an attacker keeps recording all the communications over TLS and at a later point of time somehow gets hold of the private key, he/she cannot decrypt the past recorded communications. 

RSA is an alternative to ECDHE (as in TLS_RSA_WITH_AES_128_GCM_SHA256) and used in older cipher suites. However, RSA does not provide forward secrecy and hence such cipher suites are considered weak today.

The older cipher suites using the other variants of Diffie Hellman algorithm that don't use either the Elliptic Curve or the Ephemeral keys shouldn't be used anymore.

*ECDSA* is used for authenticating the shared secret. ECDSA is weaker and slower than the other authenticating algorithms like HMAC. Yet it is used for shared key authentication because it does not need the verifier know the secret key used to create the authentication tag. The verifier can very well use the public key to verify the integrity of the message. Here also RSA is an alternative. However, to acheive a certain level of security, RSA needs much longer key than ECDSA. Therefore ECDSA provides better performance in mobile devices or other devices with limited resources.

*AES_128_GCM* - Once a common secret key is shared between both the parties (usually a browser and a web server), a symmetric algorithm is used to encrypt the message exchanges between the parties. In this particular case, the block cipher *AES* with *128* bit key and *GCM* authentication mode is used. (Note: AES block size is always 128 bits which is not usually mentioned. AES_256 or AES_128 actually indicate the key size in bits).

