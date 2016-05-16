package es.ubu.lsi.server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import es.ubu.lsi.client.ChatClient;
import es.ubu.lsi.common.ChatMessage;

public class ChatServerImpl implements ChatServer {

	private List<ChatClient> listaClientes;

	private int contId;

	public ChatServerImpl() {
		this.listaClientes = new ArrayList<ChatClient>();
		this.contId = 0;
	}

	public int checkIn(ChatClient client) throws RemoteException {
		listaClientes.add(client);
		publish(new ChatMessage(client.getId(),"Se ha conectado"+client.getNickName()));
		return contId++;
	}

	public void logout(ChatClient client) throws RemoteException {
		listaClientes.remove(client);
		publish(new ChatMessage(client.getId(),"Se ha desconectado"+client.getNickName()));

	}

	public void privatemsg(String tonickname, ChatMessage msg) throws RemoteException {
		for (ChatClient cliente : this.listaClientes) {
			if (cliente.getNickName().equals(tonickname)){
				cliente.receive(msg);
				return;
			}			
		}
	}

	public void publish(ChatMessage msg) throws RemoteException {
		for (ChatClient cliente : this.listaClientes)
			cliente.receive(msg);
	}

	public void shutdown(ChatClient client) throws RemoteException {
		// TODO Auto-generated method stub

	}

}
