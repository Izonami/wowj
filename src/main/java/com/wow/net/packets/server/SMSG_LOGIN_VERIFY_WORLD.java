package com.wow.net.packets.server;

import com.wow.entities.character.Char;
import com.wow.entities.packet.ServerPacket;
import com.wow.utils.Opcodes;

/**
 * Verifying world upon worldlogin
 * 
 * @author Marijn
 *
 */
public class SMSG_LOGIN_VERIFY_WORLD extends ServerPacket {

	private Char character;
	public SMSG_LOGIN_VERIFY_WORLD(Char character) {
		super(Opcodes.SMSG_LOGIN_VERIFY_WORLD, 20);
		this.character = character;
	}
	
	public boolean writeCata(){
		putInt(character.getMapID());
		putFloat(character.getPosition().getX());
		putFloat(character.getPosition().getY());
		putFloat(character.getPosition().getZ());
		putFloat(character.getPosition().getO()); // orientation
		return true;
	}
	
	public boolean writeMoP(){
		return writeCata();
	}
}
