package es.ubu.lsi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import es.ubu.lsi.common.ChatMessage;
import es.ubu.lsi.server.ChatServer;

public class ChatClientStarter {
	public ChatClientStarter(String nickname, int clave, String host) {
		ChatClient client = new ChatClientImpl(nickname);
		ChatServer server;
		try {
			server = (ChatServer) Naming.lookup("rmi://" + host + "/ChatServerImpl");
			client.setId(server.checkIn(client));
			String userInput;
			Scanner inputConsola = new Scanner(System.in);
			boolean continua = true;
			while (continua) {
				userInput = inputConsola.nextLine();
				ChatMessage message = null;
				if (userInput.compareToIgnoreCase("logout") == 0) {
					server.logout(client);
					continua = false;
				} else if (message.getMessage().startsWith("#encrypted")) {
					message.setMessage(nickname + ": " + descifrar(message.getMessage(), clave).substring(10));
					server.publish(message);
				} else if (message.getMessage().startsWith("#private")) {
					message.setMessage(nickname + ": " + message.getMessage().substring(8));
					server.privatemsg(nickname, message);
				} else {
					message.setMessage(nickname + ": " + userInput);
					message = new ChatMessage(client.getId(), userInput);
					server.publish(message);
				}
			}
			inputConsola.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * MÈtodo que descifra un mensaje
	 * 
	 * @param mensaje
	 *            Mensaje a descifrar
	 * @param clave
	 *            Clave para el descifrado
	 * @return Mensaje descifrado
	 * 
	 */
	private String descifrar(String mensaje, int clave) {
		StringBuilder descifrado = new StringBuilder(mensaje.length());
		int ASCIIcifrado = 0;
		for (int i = 0; i < mensaje.length(); i++) {
			ASCIIcifrado = (int) (mensaje.charAt(i));
			ASCIIcifrado = ASCIIcifrado - clave % 255;
			descifrado.append((char) (ASCIIcifrado));
		}
		return descifrado.toString();
	}
}