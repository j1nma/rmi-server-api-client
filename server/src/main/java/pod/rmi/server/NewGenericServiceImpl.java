package pod.rmi.server;

import org.slf4j.LoggerFactory;
import pod.rmi.GenericService;
import pod.rmi.User;
import pod.rmi.UserAvailableCallbackHandler;

import java.io.*;
import java.rmi.MarshalledObject;
import java.rmi.RemoteException;
import java.rmi.activation.Activatable;
import java.rmi.activation.ActivationID;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class NewGenericServiceImpl implements GenericService {
	
	private int visits;
	private final Queue<String> services;
	private final Queue<User> users;
	private final Queue<UserAvailableCallbackHandler> handlers;
	
	private File storage;
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(NewGenericServiceImpl.class);
	
	public NewGenericServiceImpl(ActivationID id, MarshalledObject data) throws IOException, ClassNotFoundException {
		Activatable.exportObject(this, id, 0);
		
		this.visits = 0;
		this.services = new LinkedList<>();
		this.users = new LinkedList<>();
		this.handlers = new LinkedList<>();
		
		if (data != null) {
			storage = (File) data.get(); // Ej: storage es de tipo File
			if (storage.exists()) {
				System.out.println("Storage exists!");
				read(); // recuperar el estado interno
			} else {
				logger.info("No habia store lo genero...");
				store();
			}
		}
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
			store();
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
		
		if (handler != null) {
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
	
	private void read() {
		if (storage != null) {
			logger.info("Leyendo del storage");
			try (ObjectInputStream ois = new ObjectInputStream(new
					FileInputStream(storage))) {
				visits = ois.readInt();
			} catch (final IOException e) {
				logger.error("error al leer el storage", e);
			}
		}
	}
	
	private void store() {
		if (storage != null) {
			logger.info("Guardando en el storage");
			try (ObjectOutputStream oos = new ObjectOutputStream(new
					FileOutputStream(storage))) {
				oos.writeInt(visits);
				oos.flush();
			} catch (final IOException e) {
				logger.error("error al escribir el storage", e);
			}
		}
	}
	
	
}
