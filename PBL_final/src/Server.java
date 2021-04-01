
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.LinkedList;

import java.util.Queue;



public class Server  extends UnicastRemoteObject implements Service {
	public String serverName = null;
	public Queue<String> domain;
	public Lamport_Time lamport;
	public Queue<String> REP;
	public Queue<String> REP2;
	public boolean flag;
	public String chat;
	protected Server() throws RemoteException {
		domain = new LinkedList();
		lamport = new Lamport_Time(0);
		REP = new LinkedList();
		REP2 = new LinkedList();
		flag = false;
		chat = "";
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void retrieveMessage(String msg) throws RemoteException {
		System.out.println(msg);
	}
	@Override
	public String getSname() throws RemoteException {
		// TODO Auto-generated method stub
		return this.serverName;
	}


	@Override
	public Queue<String> getDomain() throws RemoteException {
		// TODO Auto-generated method stub
		return this.domain;
	}

	@Override
	public void setDomain() throws RemoteException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setLamport() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void serSname(String name) throws RemoteException {
		// TODO Auto-generated method stub
		this.serverName = name;
		
	}

	@Override
	public int getLamport() throws RemoteException {
		// TODO Auto-generated method stub
		return this.lamport.getLogic_time();
	}

	@Override
	public void lamport_up() throws RemoteException {
		// TODO Auto-generated method stub
		this.lamport.up();
		
	}

	@Override
	public void addDomain(String msg) throws RemoteException {
			this.domain.add(msg);
	}

	@Override
	public void compare(int a, int b) throws RemoteException {
		// TODO Auto-generated method stub
		if(a>=b) this.lamport.setLogic_time(a+1);
		else this.lamport.setLogic_time(b+1);
		
	}

	@Override
	public void addREPs(String rep) throws RemoteException {
		// TODO Auto-generated method stub
		this.REP.add(rep);
	}

	@Override
	public Queue<String> getREPs() throws RemoteException {
		// TODO Auto-generated method stub
		return this.REP;
	}

	@Override
	public boolean checkREPs() throws RemoteException {
		if(this.REP.size() == 3) return true;
		else return false;
	}

	@Override
	public void clearREP() throws RemoteException {
		this.REP.clear();
	}

	@Override
	public void popPeek() throws RemoteException {
		this.domain.poll();
	}

	@Override
	public String peekDomain() throws RemoteException {
		// TODO Auto-generated method stub
		return this.domain.peek();
	}

	@Override
	public int getREP() throws RemoteException {
		// TODO Auto-generated method stub
		return this.REP.size();
	}
	@Override
	public boolean flag() throws RemoteException {
		// TODO Auto-generated method stub
		return this.flag;
	}
	@Override
	public void set_t_flag() throws RemoteException {
		this.flag = true;
		
	}
	@Override
	public void set_f_flag() throws RemoteException {
		this.flag = false;
		
	}
	@Override
	public void addREP2s(String rep) throws RemoteException {
		// TODO Auto-generated method stub
		this.REP2.add(rep);
	}

	@Override
	public Queue<String> getREP2s() throws RemoteException {
		// TODO Auto-generated method stub
		return this.REP2;
	}

	@Override
	public boolean checkREP2s() throws RemoteException {
		if(this.REP2.size() == 2) return true;
		else return false;
	}

	@Override
	public void clearREP2() throws RemoteException {
		this.REP2.clear();
	}
	@Override
	public int getREP2() throws RemoteException {
		// TODO Auto-generated method stub
		return this.REP2.size();
	}
	@Override
	public String getHistory() throws RemoteException {
		// TODO Auto-generated method stub
		return this.chat;
	}
	@Override
	public void setHistory(String lock) throws RemoteException {
		this.chat+=lock;
		
	}
	@Override
	public void printHistory() throws RemoteException {
		System.out.println(this.chat);
		
	}
}
