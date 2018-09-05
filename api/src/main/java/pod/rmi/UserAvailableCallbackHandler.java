package pod.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserAvailableCallbackHandler extends Remote {
	
	void userAvailable(final User user) throws RemoteException;
}
