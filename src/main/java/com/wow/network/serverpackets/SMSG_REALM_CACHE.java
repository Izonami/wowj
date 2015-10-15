package com.wow.network.serverpackets;

import com.wow.entities.Realm;
import com.wow.entities.packet.ServerPacket;
import com.wow.utils.BitPack;
import com.wow.utils.Opcodes;

/**
 * Realm entity, MoP only.
 * 
 * @author Marijn
 * 
 */
public class SMSG_REALM_CACHE extends ServerPacket {

	private Realm realm;

	public SMSG_REALM_CACHE(Realm realm) {
		super(Opcodes.SMSG_REALM_CACHE, 100);
		this.realm = realm;
	}

	@Override
	public boolean writeMoP() {

		BitPack bitPack = new BitPack(this);

		this.putInt(realm.id);
		this.put((byte) 0); // unknown
		
		bitPack.write(realm.name.length(), 8);
		bitPack.write(realm.name.length(), 8);
		bitPack.write(1);
		
		bitPack.flush();
		
		this.putString(realm.name);
		this.putString(realm.name);
		
		this.wrap();
		return true;
	}

}
