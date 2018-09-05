package pod.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GenericService extends Remote {
	
	String echo(String message) throws RemoteException;
	
	String toUpper(String message) throws RemoteException;
	
	void addVisit() throws RemoteException;
	
	int getVisitCount() throws RemoteException;
	
	boolean isServiceQueueEmpty() throws RemoteException;
	
	void addToServiceQueue(String name) throws RemoteException;
	
	String getFirstInServiceQueue() throws RemoteException;
	
	boolean isUserQueueEmpty() throws RemoteException;
	
	void addToUserQueue(User user) throws RemoteException;
	
	void getFirstInUserQueue(UserAvailableCallbackHandler callbackHandler) throws RemoteException;
}
