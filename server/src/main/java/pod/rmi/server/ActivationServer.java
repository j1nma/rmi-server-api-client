package pod.rmi.server;

import java.io.File;
import java.io.IOException;
import java.rmi.MarshalledObject;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.activation.Activatable;
import java.rmi.activation.ActivationDesc;
import java.rmi.activation.ActivationException;
import java.rmi.activation.ActivationGroup;
import java.rmi.activation.ActivationGroupDesc;
import java.rmi.activation.ActivationGroupID;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivationServer {
	private static Logger logger = LoggerFactory.getLogger(ActivationServer.class);
	
	private static final String SERVANT_CLASS = "pod.rmi.server.NewGenericServiceImpl";
	private static final String CODEBASE_PATH = "file:///Users/JuanmaAlonso/ITBA/POD/TP_3_RMI/server-api-client/sac/server/target/classes";
	private static final String POLICY_PATH = "file:///Users/JuanmaAlonso/ITBA/POD/TP_3_RMI/server-api-client/sac/server/target/sac-server-1.0-SNAPSHOT/java.policy";
	
	
	public static void main(final String[] args) throws IOException, ActivationException {
		logger.info("rmi activation Server Starting ...");
		
		// 1) instalar un SecurityManager porque hay que levantar un JVM, etc.
		System.setProperty("java.security.policy", POLICY_PATH);
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		
		// 2) registar un ActivationGroup en la instancia Activator que lanzó el
		// RMID o sea, RMID debe estar levantado!
		final ActivationGroupID activationGroupID = registrarActivationGroup();
		
		// 3) generar un ActivationDesc con la info de creacion para el objeto
		// remoto ActivationGroupID que lo manejara, nombre de la clase y
		// codebase(classpath)
//		final ActivationDesc servantDescription = new ActivationDesc(activationGroupID, SERVANT_CLASS,
//				CODEBASE_PATH, null);
		
		final ActivationDesc servantDescription = new
				ActivationDesc(activationGroupID, SERVANT_CLASS,
				CODEBASE_PATH, new MarshalledObject(new
				File("/tmp/objectStore.ser")));
		
		// 4) dicho ActivationDesc del servant se lo debe pasar a la unica
		// instancia Activator del RMID que está levantado para que cuando este
		// precise poner activo al servant se lo pase al ActivationGroup
		// correspondiente.
		// Para ello se registra a dicho ActivationDesc y se genera en esta
		// sentencia el stub del servant (ya que no habrá creación explícita de
		// la instancia servant codificada)
		final Remote stub = Activatable.register(servantDescription);
		logger.info("Activation descriptor registered: " + stub);
		
		// 5) si tenemos el stub, listo, hacemos el binding!
		final Registry registry = LocateRegistry.getRegistry();
		registry.rebind("service", stub);
		logger.info("Service bound");
		
		// 6) Salimos de la aplicacion. No somos responsables de mantener
		// objetos
		System.exit(0);
		
	}
	
	public static ActivationGroupID registrarActivationGroup() throws ActivationException, RemoteException {
		final Properties properties = new Properties();
		properties.put("java.security.policy", POLICY_PATH);
		final ActivationGroupDesc.CommandEnvironment configInfo = null;
		final ActivationGroupDesc groupDescription = new ActivationGroupDesc(properties, configInfo);
		logger.info("groupDesc = " + groupDescription);
		
		final ActivationGroupID groupID = ActivationGroup.getSystem().registerGroup(groupDescription);
		logger.info("groupID = " + groupID);
		
		return groupID;
	}
	
}
