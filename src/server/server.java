package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.IOException;
import java.util.ArrayList;

public class server extends Thread
{
	private static final int UMBRA_PORT = 30480;
	private static final int ROOM_THROTTLE = 200;
	private ServerSocket serverSocket;
	private InetAddress hostAddress;
	private Socket socket;
	private ArrayList<client> users = new ArrayList<client>();
	/**
	 * Creates a new Umbra room for clients to connect to.
	 */
	public server()
	{
		try
		{
			hostAddress = InetAddress.getLocalHost();
		}
		catch(UnknownHostException e)
		{
			System.out.println("Could not get the host address.");
			return;
		}
		System.out.println("Server host address is: "+hostAddress);
		
		try
		{
			serverSocket = new ServerSocket(UMBRA_PORT,0,hostAddress);
		}
		catch(IOException e)
		{
			System.out.println("Could not open server socket.");
			return;
		}
		// Announce the socket creation
		System.out.println("Socket "+serverSocket+" created.");
		
	}
	/**
	 * Starts the client accepting process.
	 */
	public void start()
	{
		// Announce the starting of the process
		System.out.println("Room has been started.");
		// Enter the main loop
		while(true)
		{
			// Remove all disconnected clients
			for(int i = 0;i < users.size();i++)
			{
				// Check connection, remove on dead
				if(!users.get(i).isConnected())
				{
					//System.out.println(users.get(i)+" removed due to lack of connection.");
					users.remove(i);
					
				}
			}
			// Get a client trying to connect
			try
			{
				socket = serverSocket.accept();
			}
			catch(IOException e)
			{
				System.out.println("Could not get a client.");
			}
			// Client has connected
			System.out.println("Client "+socket+" has connected.");
			// Add user to list
			users.add(new client(socket));
			// Sleep
			try
			{
				Thread.sleep(ROOM_THROTTLE);
			}
			catch(InterruptedException e)
			{
				System.out.println("Room has been interrupted.");
			}
		}
	}
}
