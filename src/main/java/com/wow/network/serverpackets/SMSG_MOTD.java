package com.wow.network.serverpackets;

import com.wow.entities.packet.ServerPacket;
import com.wow.utils.BitPack;
import com.wow.utils.Opcodes;

/**
 * Message of the day message
 * 
 * @author Marijn
 *
 */
public class SMSG_MOTD extends ServerPacket {
	
	String message;
	
	public SMSG_MOTD(String message) {
		super(Opcodes.SMSG_MOTD, 4 +  message.length() + 1);
		this.message = message;
	}
	
	public boolean writeGeneric(){
		putInt(4);
		putString(message);
		return true;
	}
	
	public boolean writeMoP(){
		BitPack bitPack = new BitPack(this);

		bitPack.write(3, 4);
		bitPack.write(message.length(), 7);
		bitPack.flush();
		putString(message);

		wrap();
		return true;
	}

}
