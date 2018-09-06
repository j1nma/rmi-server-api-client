package pod.rmi.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pod.rmi.GenericService;
import pod.rmi.UserAvailableCallbackHandler;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Client {
	private static Logger logger = LoggerFactory.getLogger(Client.class);
	
	public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
		logger.info("sac Client Starting ...");

//		final Registry registry = LocateRegistry.getRegistry();
		
		final GenericService service = (GenericService) Naming.lookup("//localhost:1099/service");
//		final GenericService service = (GenericService) registry.lookup("service");
		
		// Instantiate handler
		UserAvailableCallbackHandler handler = new ClientUserAvailableCallbackHandler();
		
		// Export handler
		final Remote remote = UnicastRemoteObject.exportObject(handler, 0);
		
		
		for (int i = 0; i < 3; i++) {
			service.addVisit();
		}
		
		System.out.println(service.getVisitCount());


//		for (int i = 0; i < 4; i++) {
//			service.addToUserQueue(new UserExtended(new Integer(i).toString(), "name_" + i, i * i, i * i * i));
//		}
//
//		while (!service.isUserQueueEmpty()) {
//			service.getFirstInUserQueue(handler);
//		}
	}
}
