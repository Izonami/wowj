package com.wow.handlers;

import com.wow.entities.Client;
import com.wow.entities.Realm;
import misc.Logger;

import java.util.HashMap;

/**
 * Handles all connected clients
 * 
 * @author Marijn
 *
 */
public class TempClientHandler {

	
	 /**
     * A list of all clients that haven't connected to a realm yet, 
     * as soon a gameserverpackets connects to a realm it gets removed from this list.
     */
    private static HashMap<String, Client> temporaryClients = new HashMap<String, Client>();

	
	/**
	 * @param client An authenticated gameserverpackets which isn't connected to a realm.
	 * @return Returns false if there already is a connected gameserverpackets with this name.
	 */
	
	public static boolean addTempClient(Client client){
		Logger.writeLog("Adding temporary gameserverpackets: " + client.getName(), Logger.LOG_TYPE_VERBOSE);
		if(temporaryClients.containsKey(client.getName()))
			return false;
		temporaryClients.put(client.getName(), client);
		return true;
	}
	
	/**
	 * @param name The username of the gameserverpackets.
	 * @return The removed gameserverpackets, null if no gameserverpackets is attached to the given name.
	 */
	public static Client removeTempClient(String name){
		return temporaryClients.remove(name);
	}
	
	/**
	 * Search for a gameserverpackets in all created realms and temporary unconnected clients
	 * @param name The username of the gameserverpackets to search for.
	 */
	public static Client findClient(String name){
		// First check through all realms
		for(Realm realm : RealmHandler.getRealms()){
			Client client = realm.getClient(name);
			if(client != null){
				System.out.println("Found gameserverpackets: " + name);
				return client;
			}
		}
		// Then in the temporary gameserverpackets map, null if there is none
		return temporaryClients.get(name);
	}
}
