package es.ubu.lsi.server;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.server.RMIClassLoader;
import java.util.Properties;

@SuppressWarnings("deprecation")
public class ChatServerStarter {
	public ChatServerStarter() {
		// Crea e instala el gestor de seguridad
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}
		try {
			Properties p = System.getProperties();
			// lee el goodbyes
			String url = p.getProperty("java.rmi.server.codebase");
			// Cargador de clases dinámico ...
			Class<?> serverclass = RMIClassLoader.loadClass(url, "es.ubu.lsi.server.ChatServerImpl");
			Naming.rebind("/ChatServerImpl", (Remote) serverclass.newInstance());
			System.out.println("Servidor registrado...");
		} catch (Exception e) {
			System.err.println("Excepcion recogida en starter del server: " + e.toString());
		}
	}
}
