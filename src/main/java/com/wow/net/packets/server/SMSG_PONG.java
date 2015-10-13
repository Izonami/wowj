package com.wow.net.packets.server;

import com.wow.entities.packet.ServerPacket;
import com.wow.utils.Opcodes;

/**
 * Response to ping, unrequired?
 * 
 * @author Marijn
 *
 */
public class SMSG_PONG extends ServerPacket{
	
	private static int currentPing = 1;
	
	public SMSG_PONG(){
		super(Opcodes.SMSG_PONG, 4);
		this.put((byte) currentPing++);
	}

}
