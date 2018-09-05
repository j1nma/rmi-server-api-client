package pod.rmi.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pod.rmi.GenericService;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
	
	private static Logger logger = LoggerFactory.getLogger(Server.class);
	
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		logger.info("sac Server Starting ...");
		
		final GenericService gs = new NewGenericServiceImpl();
		
		final Remote remote = UnicastRemoteObject.exportObject(gs, 0);
		
//		final Registry registry = LocateRegistry.getRegistry();
        final Registry registry = LocateRegistry.getRegistry("192.168.0.150");
		
		registry.rebind("service", remote);
		
		logger.info("Service bound ...");
		
	}
}
