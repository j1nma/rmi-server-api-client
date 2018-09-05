package pod.rmi.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pod.rmi.User;
import pod.rmi.UserAvailableCallbackHandler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ClientUserAvailableCallbackHandler implements UserAvailableCallbackHandler {
	
	private static Logger logger = LoggerFactory.getLogger(ClientUserAvailableCallbackHandler.class);
	
	@Override
	public void userAvailable(User user) {
		CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
			// Simulate a long-running Job
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			System.out.println("I'll run in a separate thread than the main thread.");
			System.out.println(user.toString());
			logger.info("mirame el user: " + user.toString());
		});
		
		try {
			cf.get();
			logger.info("chau get");
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}
