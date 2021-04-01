
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public interface Service extends Remote{
		//message
	    public void retrieveMessage(String msg) throws RemoteException;
	    
	    //server
	    public void serSname(String name) throws RemoteException;
	    public String getSname() throws RemoteException;
	    // history
	    public String getHistory() throws RemoteException;
	    public void setHistory(String lock) throws RemoteException;
	    public void printHistory() throws RemoteException;
	    
	    // domain
	    public Queue<String> getDomain() throws RemoteException;
	    public void setDomain() throws RemoteException;
	    public void addDomain(String msg) throws RemoteException;
	    public String peekDomain() throws RemoteException;
	    public void popPeek() throws RemoteException;
	    
	    //lamport
	    public int getLamport() throws RemoteException;
	    public void setLamport() throws RemoteException;
	    public void lamport_up() throws RemoteException;
	    public void compare(int a,int b) throws RemoteException;
	    
	   //check in domain
	    public void addREPs(String rep) throws RemoteException;
	    public Queue<String> getREPs() throws RemoteException;
	    public boolean checkREPs() throws RemoteException;
	    public void clearREP() throws RemoteException;
	    public int getREP() throws RemoteException;
	    public boolean flag() throws RemoteException;
	    public void set_t_flag() throws RemoteException;
	    public void set_f_flag() throws RemoteException;
	    public void addREP2s(String rep) throws RemoteException;
	    public Queue<String> getREP2s() throws RemoteException;
	    public boolean checkREP2s() throws RemoteException;
	    public void clearREP2() throws RemoteException;
	    public int getREP2() throws RemoteException;
}
