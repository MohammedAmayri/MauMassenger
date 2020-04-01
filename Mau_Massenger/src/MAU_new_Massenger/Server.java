package MAU_new_Massenger;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

// Delete port

public class Server extends Thread {
	private InetAddress ip;
	private ServerSocket socket ;
	private HashMap<String, ObjectOutputStream> userSocket=new HashMap<String,ObjectOutputStream>();
	private User user;
	private Message message;
	private ObjectOutputStream ous;
	private ObjectInputStream ois;
	private MessageController msgController;
	private ServerSocket serverSocket;
	private LinkedList<MessageController> msgControllerList;
	private ArrayList<String> userArrayList=new ArrayList<String>();
	private Server server;
	private ArrayList<MessageController> msgctrlList=new ArrayList<MessageController>();
	private ArrayList<String> onlineUser=new ArrayList<>();
	private Queue <Message> offMsg=new LinkedList();
	private HashMap<String,Queue> oflinMsgList=new HashMap<String,Queue>();
	public Server(int port) throws IOException {
		serverSocket=new ServerSocket(port);

		run();
	}


	public void run() {
		System.out.println("Server running, port: " + serverSocket.getLocalPort());
		while(true) {
			try  {
				 Socket clientSocket = serverSocket.accept();
				//System.out.println("Accepted connection from "+clientSocket);
				MessageController msgController= new MessageController(this,clientSocket); // WHY
				msgctrlList.add(msgController);
				//msgControllerList.add(msgController); // maybe still have it here instead!
				msgController.start();
				if(msgctrlList.size()==2)
				{
					closeSocket();
				}
			} catch(IOException e) {
				//System.err.println(e);
				break;
			}
		}
	}


	private void closeSocket() throws IOException {
		
		for(int x=0;x<msgctrlList.size();x++)
		{
			
				msgctrlList.get(x).closeInputStream();

		}
		serverSocket.close();
		
		
	}


	public void setHashObject (String user,ObjectOutputStream socket)
	{
		userSocket.put(user, socket);
	}
	public HashMap getMap()
	{
		return userSocket;
	}
	public int sizeList()
	{
		return msgctrlList.size();
	}
	public void setOnline (String user)
	{
		onlineUser.add(user);
	}
	public ArrayList getOnlineList()
	{
		return onlineUser;
	}


	public static void main(String[] args) throws IOException {

		new Server(9020);


	}
	public void addUser(User user)
	{
		userArrayList.add(user.getName());
	}
	public void addOflinObject(String name,Queue line)
	{
		oflinMsgList.put(name, line);
	}
	public void addToQueue(String user,Message msg)
	{
		if(oflinMsgList.get(user)==null)
		{
			addOflinObject(user, offMsg);
			oflinMsgList.get(user).add(msg);
			//System.out.print("Have saved "+oflinMsgList.get(user).size()+" Offline Msgs");
		}
		else
		oflinMsgList.get(user).add(msg);
		//System.out.print("Have saved "+oflinMsgList.get(user).size()+" Offline Msgs");
		//System.out.println("ourQueue size is"+ offMsg.size());
		
		
	}
	public boolean HasoflinMsg(String user)
	{	
		Queue ourQueue=oflinMsgList.get(user);
		if(ourQueue==null||ourQueue.isEmpty())
		{
			return false;
		}
		else
			return true;
	}
	public Queue getoflinQueu(String user)
	{	
		
		return oflinMsgList.get(user);
	}
	

	public ArrayList getUsers()
	{
		return userArrayList;
	}
	public void userOffline(User user)
	{
		int index=onlineUser.indexOf(user.getName());
		onlineUser.remove(index);
	}
	
	
}


