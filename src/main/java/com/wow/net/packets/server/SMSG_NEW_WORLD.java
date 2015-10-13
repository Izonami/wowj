package com.wow.net.packets.server;

import com.wow.entities.character.Char;
import com.wow.entities.packet.ServerPacket;
import com.wow.utils.Opcodes;

/**
 * Instantly teleports the character to a new map.
 * 
 * @author Marijn
 * 
 */
public class SMSG_NEW_WORLD extends ServerPacket {

	private Char character;

	public SMSG_NEW_WORLD(Char character) {
		super(Opcodes.SMSG_NEW_WORLD, 20);
		this.character = character;
	}

	public boolean writeGeneric() {
		putInt(character.getMapID());
		putFloat(character.getPosition().getX());
		putFloat(character.getPosition().getO()); 
		putFloat(character.getPosition().getY());
		putFloat(character.getPosition().getZ());
		return true;
	}
}
