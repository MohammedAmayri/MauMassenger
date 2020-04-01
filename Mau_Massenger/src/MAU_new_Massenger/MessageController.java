package MAU_new_Massenger;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;

public class MessageController extends Thread {
	private ArrayList<User> userArrayList;
	private ArrayList<String> onlineUser;
	private ArrayList<User> contactsList;
	private HashMap<User, ArrayList<Message>> messageList;
	private TrafficRegister trafficRegister=new TrafficRegister();
	private HashMap<String, ObjectOutputStream> userSocket=new HashMap<String,ObjectOutputStream>();
	private User user;
	private Message message;
	private Object messageObject;
	private final Server server;
	private final Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream ous;
	private int x=0;
	private HashMap<String,ObjectOutputStream> oosMap=new HashMap<String,ObjectOutputStream>();
	private Queue <Message> offMsg=new LinkedList();

	public MessageController(Server server,Socket socket) throws IOException{

		this.server=server;

		//this.messageObject = messageObject;
		userArrayList=new ArrayList<User>();
		contactsList=new ArrayList<User>();
		onlineUser=new ArrayList<String>();
		//sortOf(messageObject);
		this.socket = socket;




	}

	public void run() {
		
		try {
			 ois = new ObjectInputStream((socket.getInputStream()));

			while (true) {
				try {

					Object o=ois.readObject();
					sortOf(o);
					trafficRegister.printTraffic();
				} catch (ClassNotFoundException e) {
				}
			}
		} catch (IOException e) {
			try {

				//socket.close();
				//ous.close();
				//ois.close();
				server.userOffline(user);
				trafficRegister.userIsOffline(user);
			} catch (Exception e2) {
			}
		}
		System.out.println("we have a problem sending this Message:");
	}


	public void sortOf(Object messageObject) throws IOException {
		if (messageObject instanceof User) {

			userHandler((User) messageObject);
		} else if (messageObject instanceof Message) {
			//System.out.println("FIRST WE GOT A MESSAGE");
			messageHandler((Message) messageObject);
		}
	}

	public synchronized void userHandler(User user) throws IOException   {
		this.user = user;
		userArrayList=server.getUsers();

		if(userArrayList.contains(user))
		{
			server.setOnline(user.getName());
			trafficRegister.userIsOnline(user);

			if(server.HasoflinMsg(user.getName()))
			{
				sendOflinMsg(user.getName());
			}
			System.out.println(user.getName()+"already exist");

		}
		else
		{
			server.addUser(user);
			//System.out.println(user.getName()+" is Online!");
			ObjectOutputStream oosm=new ObjectOutputStream(socket.getOutputStream());
			server.setHashObject(user.getName(), oosm);

			server.setOnline(user.getName());
			trafficRegister.userIsOnline(user);
			if(server.HasoflinMsg(user.getName()))
			{
				sendOflinMsg(user.getName());
			}
			//System.out.print("there are: "+ server.sizeList()+" Elemets in our msgCtrl List");
		}
	}

	private void sendOflinMsg(String name) throws IOException {

		Queue offMssg=server.getoflinQueu(name);
		System.out.print(server.getoflinQueu(name).size());
		if(offMssg==null)
		{
			System.out.print("this Queue is empty");
		}

		while(!server.getoflinQueu(name).isEmpty())
		{	
			//Object ms=server.getoflinQueu(name).remove();

			messageHandler((Message) offMssg.remove());

			//messageHandler();

		}  

	}
	 public synchronized void OnlineBrodcast (CommandHandler cmdHandler) throws IOException { ////CHANGE IN THE OBJECT SENT
	       User us;
	        onlineUser = server.getOnlineList();
	        for(int i=0; i<onlineUser.size();i++){
	            us=new User(onlineUser.get(i));
	            if(!us.getName().equals(cmdHandler.getUser().getName())) {
	                us = new User(onlineUser.get(i));

	                ObjectOutputStream socketToSendTo = getReciverSocket(cmdHandler.getUser());
	                try {
	                socketToSendTo.writeObject(user);
	                }
	                catch(IOException e)
	                {
	                	System.out.println(e);
	                }
	                socketToSendTo.flush();
	            }
	        }
	    }

	public synchronized void messageHandler(Message msg) throws IOException {

		User rec=msg.getReciver();
		User sen=msg.getSender();

		ArrayList onlineUsers=server.getOnlineList();

		if(onlineUsers.contains(rec.getName()))
		{	trafficRegister.msgOnline(msg);
		ObjectOutputStream socketToSendTo=getReciverSocket(rec);
		socketToSendTo.writeObject(msg);
		socketToSendTo.flush();
		}

		else
		{	trafficRegister.msgOffline(msg);
		server.addToQueue(rec.getName(), msg);
		}

		/*
		ObjectOutputStream oosFinal;
		if(!oosMap.containsKey(rec.getName()))
		{
			ous=new ObjectOutputStream(socketToSendTo.getOutputStream());
			oosMap.put(rec.getName(), ous);
			 oosFinal=ous;
		}
		else
		{
			 oosFinal=oosMap.get(rec.getName());
		}

		 */
		//System.out.print(socketToSendTo.getInetAddress());


		//System.out.print("ALMOST THERE");

		//System.out.println("ALMOST DID IT");


		//oosFinal.close();
		//System.out.println("WE HAVE OFFICIALLY FLUSHED");
	}

	public  synchronized ObjectOutputStream getReciverSocket(User reciver) throws SocketException
	{	
		userSocket=server.getMap();
		Entry<String, ObjectOutputStream> entry = userSocket.entrySet().iterator().next();
		String key= entry.getKey();
		ObjectOutputStream value=entry.getValue();
		//System.out.println("Our user is "+key);
		if(value==null)
		{
			System.out.print("EVEN OUR VALUE IS FUCKED UP!");
		}
		//System.out.println("there are "+userSocket.size()+" elements in our map");
		String name=reciver.getName();
		//System.out.println("the name isssss:"+name);
		ObjectOutputStream so=userSocket.get(name);

		if(so==null)
		{
			System.out.print("OUR SOCKET IS FUCKED UP!");
		}
		else
		{
			//System.out.println("FOUND IT");
		}


		return so;
	}

	public void closeInputStream() {
		
		
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.print("HERE WE GO AGAIN");
				e.printStackTrace();
			}
		
		
		 
		
	}



}
