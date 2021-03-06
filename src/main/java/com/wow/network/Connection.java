package com.wow.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import com.wow.entities.Client;
import com.wow.entities.packet.Packet;
import com.wow.utils.PacketLog;
import com.wow.utils.PacketLog.PacketType;
import misc.Logger;

/**
 * A connection made between the serverpackets and gameserverpackets,
 * 
 * @author Marijn
 */
public abstract class Connection extends Thread {

	protected Socket clientSocket;
	protected PrintWriter out;
	protected DataInputStream in;
	protected Client client;

	/**
	 * Create a new connection attached based on the given socket
	 * 
	 * @param clientSocket The socket the gameserverpackets connected on.
	 */
	public Connection(Socket clientSocket) {
		this.clientSocket = clientSocket;
		initialize();
	}

	private void initialize() {
		System.out.println("Created Connection.");
		
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new DataInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			Logger.writeLog("Couldn't create input and output streams.", Logger.LOG_TYPE_WARNING);
		}
	}
	
	public void close() {
		try {
			//entity.allConnections.remove(this);
			out.close();
			in.close();
			clientSocket.close();
			interrupt();
		} catch (IOException e) {
			Logger.writeLog("Couldn't close connection properly", Logger.LOG_TYPE_WARNING);
		}
	}

	/**
	 * 
	 * @param p The sent packet
	 * 
	 * @return True if successful, false if the actual capacity exceeds the given size
	 */
	protected boolean sendPacket(Packet p) {
		if (p.size < p.packet.capacity()) {
			String errorMessage = "";
			errorMessage += "Packet Error: The size of the packer (" + p.size;
			errorMessage += ") is less than the total capacity (" + p.packet.capacity() +").";
			Logger.writeLog(errorMessage, Logger.LOG_TYPE_WARNING);
			
			return false;
		}
		PacketLog.logPacket(PacketType.SERVER, p);
		return sendBytes(p.getFull());
	}

	private boolean sendBytes(byte[] b) {
		try {
			clientSocket.getOutputStream().write(b);
		} catch (IOException e) {
			return false;
		}
		return true;

	}

	public PrintWriter getOut() {
		return out;
	}

	public DataInputStream getIn() {
		return in;
	}

	public Socket getSocket() {
		return clientSocket;
	}

	/**
	 * @return The gameserverpackets that belongs to this connection.
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * @param c The gameserverpackets that belongs to this connection.
	 */
	public void setClientParent(Client c) {
		client = c;
	}
}
