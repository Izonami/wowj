package com.wow.entities.packet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;


/**
 * A barebone packet only for the authentication of a gameserverpackets (used in LogonAuth)
 * 
 * @author Marijn
 *
 */
public class AuthPacket extends Packet{

	  public AuthPacket(short size){
		  this.size = size;
		  packet = ByteBuffer.allocate(size);
		  packet.order(ByteOrder.LITTLE_ENDIAN);
	  }
	  
}
