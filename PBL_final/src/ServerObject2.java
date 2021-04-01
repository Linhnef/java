import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerObject2 implements Runnable {

	ArrayList<Service> Svs;
	ArrayList<Service> Svs2;
	public String serverUrl1 = "rmi://localhost:2000/Server2000";
	public String serverUrl = "rmi://localhost:2001/Server2001";
	public String serverUrl2 = "rmi://localhost:2002/Server2002";
	int port = 2001;
	int num_Server;
	Service stub;
	static int priority;
	public ServerObject2() throws RemoteException, MalformedURLException, NotBoundException {
		register(port);
		Svs = new ArrayList<>();
		Svs2 = new ArrayList<>();
		stub = (Service)Naming.lookup(this.serverUrl);
		num_Server = 1;
		stub.serSname("Server 2");
		stub.setHistory("-----------------Server 2------------------\n");
		priority = 0;
	}
	public void register(int port) {
		
		Registry reg;
		try {
			Server chatServer = new Server();
			reg = LocateRegistry.createRegistry(port);
			reg.rebind("Server" + port,chatServer);
			System.out.println("RMI Server in port " + port + " is  ready....");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	public Message castStringToMessage(String mess) throws RemoteException {
		String regex = "--------";
		String[] tmp = mess.split(regex);
		return new Message(tmp[1],tmp[3],Integer.valueOf(tmp[2]),Integer.valueOf(tmp[4]),tmp[0]);
	}
	public void broadcasts(Message msg) throws RemoteException {
		for (Service sv : Svs) {
			msg.setReceivei(sv.getSname());
			msg.setTime_Receive(sv.getLamport());
            sv.retrieveMessage("[" + msg.getSender()+"] : " + msg.getContent() + "| at " + msg.getTime_Send());
            stub.setHistory("Send> : " + "[" + msg.getSender()+"] : " + msg.getContent() + "| at " + msg.getTime_Send() + "\n");
            sv.setHistory("Receive> : " + "[" + msg.getSender()+"] : " + msg.getContent() + "| at " + msg.getTime_Send() + "\n");
            sv.setHistory("\n");
		}
	}
	public String castToViewStrings(Message mess) {
		return "["+mess.getTime_Receive()+"]"+"["+mess.getSender()+"] : " + mess.getContent();
	}
	//send 3 sides message
	public void broadcast(Message msg) throws RemoteException {
		for (Service sv : Svs) {
            sv.compare(sv.getLamport(), msg.getTime_Send());
			msg.setReceivei(sv.getSname());
			msg.setTime_Receive(sv.getLamport());
			String tmp = this.castMessageToString(msg);
			stub.setHistory("Send> : " + tmp + "\n");
			sv.setHistory("Receive> : " + tmp + "\n");
            switch(msg.getContent()) {
            case "REQ":
            	sv.addDomain(tmp);
            	if(sv.getDomain().peek().equals(tmp)) {
            		sv.lamport_up();
            		Message mes = this.castStringToMessage(sv.getDomain().peek());
            		Message REP = new Message(sv.getSname(),mes.getSender(),sv.getLamport(),0,"REP");
            		this.REP(msg, REP);
            	}
            	break;
            case "REL":
            	sv.set_f_flag();
            	sv.popPeek();
            	if(sv.peekDomain()!=null) {
            		Message des = this.castStringToMessage(sv.peekDomain());
            		Message REP = new Message(sv.getSname(),des.getSender(),sv.getLamport(),0,"REP");
            		this.REP(des, REP);
            	}
            	break;
            default :
            	break;
            }
        }
	}
	//send 2 sides message
	public void multicast(Message msg) throws RemoteException {
		for (Service sv : Svs2) {
            sv.compare(sv.getLamport(), msg.getTime_Send());
			msg.setReceivei(sv.getSname());
			msg.setTime_Receive(sv.getLamport());
			String tmp = this.castMessageToString(msg);
			stub.setHistory("Send> : " + tmp + "\n");
			sv.setHistory("Receive> : " + tmp + "\n");
            switch(msg.getContent()) {
            case "REQ":
            	sv.addDomain(tmp);
            	if(sv.getDomain().peek().equals(tmp)) {
            		sv.lamport_up();
            		Message mes = this.castStringToMessage(sv.getDomain().peek());
            		Message REP = new Message(sv.getSname(),mes.getSender(),sv.getLamport(),0,"REP");
            		this.REP2(msg, REP);
            	}
            	break;
            case "REL":
            	sv.set_f_flag();
            	sv.popPeek();
            	if(sv.peekDomain()!=null) {
            		Message des = this.castStringToMessage(sv.peekDomain());
            		Message REP = new Message(sv.getSname(),des.getSender(),sv.getLamport(),0,"REP");
            		this.REP2(des, REP);
            	}
            	break;
            default :
            	break;
            }
        }
	}
	//send 2 sides chat
	public void multicasts(Message msg) throws RemoteException {
		for (Service sv : Svs2) {
			msg.setReceivei(sv.getSname());
			msg.setTime_Receive(sv.getLamport());
            sv.retrieveMessage("[" + msg.getSender()+"] : " + msg.getContent() + "| at " + msg.getTime_Send());
            stub.setHistory("Send> : " + "[" + msg.getSender()+"] : " + msg.getContent() + "| at " + msg.getTime_Send() + "\n");
            sv.setHistory("Receive> : " + "[" + msg.getSender()+"] : " + msg.getContent() + "| at " + msg.getTime_Send() + "\n");
            sv.setHistory("\n");
		}
	}
	//REP 2 sides message
	public void REP2(Message des,Message REP) throws RemoteException {
		for (Service sv : Svs2) {
			if(sv.flag()==false) {
			if(REP.getReceivei().equals(sv.getSname())){
				sv.compare(sv.getLamport(),REP.getTime_Send());
				REP.setTime_Receive(sv.getLamport());
				String tmp = this.castMessageToString(REP);
				sv.addREP2s(tmp);
				for (Service s : Svs2) {
					if (s.getSname().equals(REP.getSender())){
						s.setHistory("Send> : " + tmp +"\n");
					}
				}
				sv.setHistory("Receive> : " + tmp + "\n");
				if(sv.getREP2s().size()==2) {
					sv.clearREP2();
					sv.set_t_flag();
				}else {
					sv.set_f_flag();
				}
			}
		}
		}
	}
	//REP 3 sides message
	public void REP(Message des,Message REP) throws RemoteException {
		for (Service sv : Svs) {
			if(sv.flag()==false) {
			if(REP.getReceivei().equals(sv.getSname())){
				sv.compare(sv.getLamport(),REP.getTime_Send());
				REP.setTime_Receive(sv.getLamport());
				String tmp = this.castMessageToString(REP);
				sv.addREPs(tmp);
				for (Service s : Svs) {
					if (s.getSname().equals(REP.getSender())){
						s.setHistory("Send> : " + tmp +"\n");
					}
				}
				sv.setHistory("Receive> : " + tmp + "\n");
				if(sv.getREPs().size()==3) {
					sv.clearREP();
					sv.set_t_flag();
				}else {
					sv.set_f_flag();
				}
			}
		}
		}
	}
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		Thread t = new Thread(new ServerObject2());
		priority = t.getPriority();
		t.start();
	}
	public String castMessageToString(Message mess) {
		return mess.getContent()+"--------"+mess.getSender()+"--------"+mess.getTime_Send()+"--------"+mess.getReceivei()+"--------"+mess.getTime_Receive();
	}
	
	public String castToViewString(Message mess) {
		return mess.getContent()+"--------"+mess.getSender()+"--------"+"["+mess.getTime_Send()+"]"+"--------"+mess.getReceivei()+"--------"+"["+mess.getTime_Receive()+"]";
	}
	public void setConnect() {
		Svs.clear();
		Svs2.clear();
		try {
			Service stub1 = (Service)Naming.lookup(this.serverUrl1);
			Service stub2 = (Service)Naming.lookup(this.serverUrl2);
			Svs.add(stub2);
			Svs.add(stub1);
			Svs.add(stub);
			this.num_Server = 3;
		}catch(Exception e) {
			try {
				Service stub1 = (Service)Naming.lookup(this.serverUrl1);
				Svs2.add(stub1);
				Svs2.add(stub);
				this.num_Server = 2;
			}catch(Exception e1) {
				try {
					Service stub2 = (Service)Naming.lookup(this.serverUrl2);
					Svs2.add(stub2);
					Svs2.add(stub);
					this.num_Server = 2;
				}catch(Exception e2) {
					this.num_Server = 1;
				}
			}
		}
	}
	@Override
	public void run() {		
			String mess = null;
			Scanner sc = new Scanner(System.in);
			while(true) {
				mess = sc.nextLine();
				this.setConnect();
				switch(mess) {
				case "LOCK":
					try {
						stub.printHistory();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				default :
				if(this.num_Server == 3) {
					try {
						stub.lamport_up();
						Message REQ = new Message(stub.getSname(),null,stub.getLamport(),0,"REQ");
						Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
						this.broadcast(REQ);
						stub.lamport_up();
						Message REL = new Message(stub.getSname(),null,stub.getLamport(),0,"REL");
						this.broadcast(REL);
						Message defaul = new Message(stub.getSname(),null,stub.getLamport(),0,mess);
						this.broadcasts(defaul);
						Thread.currentThread().setPriority(this.priority);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(this.num_Server == 2){
					try {
						stub.lamport_up();
						Message REQ = new Message(stub.getSname(),null,stub.getLamport(),0,"REQ");
						Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
						this.multicast(REQ);
						stub.lamport_up();
						Message REL = new Message(stub.getSname(),null,stub.getLamport(),0,"REL");
						this.multicast(REL);
						Message defaul = new Message(stub.getSname(),null,stub.getLamport(),0,mess);
						this.multicasts(defaul);
						Thread.currentThread().setPriority(this.priority);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(this.num_Server == 1) {
					try {
						stub.retrieveMessage("you alone !!!");
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
					break;
			}   	
		}
	}
}
