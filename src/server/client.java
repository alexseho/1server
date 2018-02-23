package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.Socket;
/**
 * This object handles the execution for a single user.
 */
public class client
{
	private static final int USER_THROTTLE = 200;
	private Socket socket;
	private boolean connected;
	private w worker;
	
	private class w extends Thread
	{
		private ObjectInputStream in;
		private OutputStream out = null;
		public void run()
		{
			
			// Announce
			System.out.println(socket+" has connected input.");
			// Enter process loop
			while(true)
			{
				// Sleep
				byte[] data=null;
				
					
					try {
						data=readBytes();
					} catch (Exception e) {
						purge();
						break;
					}
					
					
					String text= new String(data);
					
					System.out.println(text);
				
				
				try {
					sendBytes(data);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				try
				{
					Thread.sleep(50);
				}
				catch(Exception e)
				{
					System.out.println(toString()+" has input interrupted.");
				}
			}
		}
	}
	
	public byte[] readBytes() throws IOException {
	    // Again, probably better to store these objects references in the support class
		
	    InputStream in = socket.getInputStream();
	
	    if(in.available()==1) {
			System.out.println("okbok");
		}
	    DataInputStream dis = new DataInputStream(in);

	    int len = dis.readInt();
	    byte[] data = new byte[len];
	    if (len > 0) {
	        dis.readFully(data);
	    }
	    return data;
	}
	
	public void sendBytes(byte[] myByteArray) throws IOException {
	    sendBytes(myByteArray, 0, myByteArray.length);
	}

	public void sendBytes(byte[] myByteArray, int start, int len) throws IOException {
	    if (len < 0)
	        throw new IllegalArgumentException("Negative length not allowed");
	    if (start < 0 || start >= myByteArray.length)
	        throw new IndexOutOfBoundsException("Out of bounds: " + start);
	    // Other checks if needed.

	    // May be better to save the streams in the support class;
	    // just like the socket variable.
	    OutputStream out = socket.getOutputStream(); 
	    DataOutputStream dos = new DataOutputStream(out);

	    dos.writeInt(len);
	    if (len > 0) {
	        dos.write(myByteArray, start, len);
	    }
	}
	
	public client(Socket newSocket)
	{
		// Set properties
		socket = newSocket;
		connected = true;
		// Get input
		worker = new w();
		worker.start();
	}
	
	public boolean isConnected()
	{
		return connected;
	}
	
	public void purge()
	{
		// Close everything
		try
		{
			connected = false;
			socket.close();
		}
		catch(IOException e)
		{
			System.out.println("Could not purge "+socket+".");
		}
	}
	
	public String toString()
	{
		return new String(socket.toString());
	}
}
