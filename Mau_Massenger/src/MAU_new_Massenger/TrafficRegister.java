package MAU_new_Massenger;

import java.util.LinkedList;
import java.util.Queue;

public class TrafficRegister {
	private Queue <String> traficLog=new LinkedList<String>();
	
	public TrafficRegister()
	{
		
	}
	
	public void userIsOnline(User user)
	{
		String logStr=user.getName()+" is Online!";
		traficLog.add(logStr);
		//System.out.println(logStr);
	}
	
	public void msgOffline(Message msg)
	{
		String logStr=msg.getReciver().getName()+" recived a message while offline from"+ msg.getSender().getName();
		traficLog.add(logStr);
		//System.out.println(logStr);
	}
	
	public void msgOnline(Message msg)
	{
		String logStr=msg.getReciver().getName()+" recived a message from "+ msg.getSender().getName();
		traficLog.add(logStr);
		//System.out.println(logStr);
	}
	
	public void userIsOffline(User user)
	{
		String logStr=user.getName()+" is Offline!";
		traficLog.add(logStr);
		//System.out.println(logStr);
	}
	
	public void printTraffic()
	{
		while(!traficLog.isEmpty())
		{
			System.out.println(traficLog.remove());
		}
	}

}
