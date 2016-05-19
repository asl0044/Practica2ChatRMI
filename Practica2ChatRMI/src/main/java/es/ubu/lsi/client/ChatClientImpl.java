package es.ubu.lsi.client;

import java.rmi.RemoteException;

import es.ubu.lsi.common.ChatMessage;

public class ChatClientImpl implements ChatClient {

	private int id;

	private String nickname;
	
	public ChatClientImpl(String nickname){
		this.nickname = nickname;
	}

	public int getId() throws RemoteException {
		return this.id;
	}

	public void setId(int id) throws RemoteException {
		this.id = id;

	}

	public void receive(ChatMessage msg) throws RemoteException {
		System.out.println(this.nickname + ": " + msg.getMessage());

	}

	public String getNickName() throws RemoteException {
		return this.nickname;
	}

}
