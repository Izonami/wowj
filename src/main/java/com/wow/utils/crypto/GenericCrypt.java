package com.wow.utils.crypto;

/**
 * Tells what a Crypts should have, en/decrypting methods differ each gameserverpackets version
 * 
 * @author Marijn
 *
 */
public interface GenericCrypt {

	/**
	 * @param data The entity that has to be encrypted, a serverpackets packet.
	 * @return The encrypted entity.
	 */
	public byte[] encrypt(byte[] data);
	/**
	 * @param data The entity that has to be decrypted, a gameserverpackets packet
	 * @return The decrypted entity.
	 */
	public byte[] decrypt(byte[] data);
	/**
	 * Initializes the crypt with the session key, has to be done before en/decrypting
	 * @param key The session key of the gameserverpackets
	 */
	public void init(byte[] key);
	
	
}
