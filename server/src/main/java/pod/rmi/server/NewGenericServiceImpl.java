package pod.rmi.server;

import pod.rmi.GenericService;
import pod.rmi.User;
import pod.rmi.UserAvailableCallbackHandler;

import java.rmi.MarshalledObject;
import java.rmi.RemoteException;
import java.rmi.activation.Activatable;
import java.rmi.activation.ActivationID;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;

public class NewGenericServiceImpl implements GenericService {
	
	private int visits;
	private final Queue<String> services;
	private final Queue<User> users;
	private final Queue<UserAvailableCallbackHandler> handlers;
	
	public NewGenericServiceImpl(ActivationID id, MarshalledObject data) throws RemoteException {
		Activatable.exportObject(this, id, 0);
		
		this.visits = 0;
		this.services = new LinkedList<>();
		this.users = new LinkedList<>();
		this.handlers = new LinkedList<>();
	}
	
	@Override
	public String echo(String message) {
		return message;
	}
	
	@Override
	public String toUpper(String message) {
		
		return Optional.ofNullable(message).map(String::toUpperCase).orElse(null);
	}
	
	@Override
	public void addVisit() {
		
		synchronized (this) {
			this.visits++;
		}
		
	}
	
	@Override
	public int getVisitCount() {
		
		synchronized (this) {
			return visits;
		}
	}
	
	@Override
	public boolean isServiceQueueEmpty() {
		return services.isEmpty();
	}
	
	@Override
	public void addToServiceQueue(String name) {
		services.add(name);
	}
	
	@Override
	public String getFirstInServiceQueue() {
		
		if (services.isEmpty())
			throw new IllegalStateException();
		
		return services.poll();
	}
	
	@Override
	public boolean isUserQueueEmpty() {
		return users.isEmpty();
	}
	
	@Override
	public void addToUserQueue(User user) throws RemoteException {
		
		users.add(user);
		
		UserAvailableCallbackHandler handler = handlers.poll();
		
		if(handler != null) {
			handler.userAvailable(user);
		}
		
	}
	
	@Override
	public void getFirstInUserQueue(UserAvailableCallbackHandler callbackHandler) throws RemoteException {
		
		if (users.isEmpty()) {
			handlers.add(callbackHandler);
		} else {
			callbackHandler.userAvailable(users.poll());
		}
		
	}
	
}
