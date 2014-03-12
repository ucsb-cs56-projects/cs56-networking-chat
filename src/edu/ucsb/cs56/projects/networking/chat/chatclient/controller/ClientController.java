package edu.ucsb.cs56.W14.ericchen.chatclient.controller;

import edu.ucsb.cs56.W14.ericchen.chatclient.model.Client;
import edu.ucsb.cs56.W14.ericchen.chatclient.model.Contact;
import edu.ucsb.cs56.W14.ericchen.chatclient.view.ClientWindow;
import java.awt.Color;

/**
 * ClientController class controlls the communication between client view and client model
 * @author Peng Wang, Andro Stotts, Bryce Filler, Max Hinson
 * @version 0.4
 */
public class ClientController {
    private Client client;
    private static ClientController controller;
	
    /**
     * Constructor saves a instance of a client object
     */
    private ClientController(){
	client = Client.getClient();
    }
	
    /**
     * Gets instance of ClientController
     * @return instance of ClientController
     */
    public static ClientController getController(){
	if(controller == null)
	    controller = new ClientController();
	return controller;
    }

    /**
     * sets the nickname of the client from the controller
     */
    public void setNickname(String nickname){
	client.setNickname(nickname);
    }

    /**
     * controller gets nickname from Client
     */
    public String getNickname(){
	return client.getNickname();
    }

    /**
     * sets the client's sound status
     */
    public void setSound(boolean soundOn){
	client.setSound(soundOn);
    }

    /**
     * controller gets the sound status from Client
     */
    public boolean isSoundOn(){
	return client.isSoundOn();
    }

    /**
     * Connects to the server
     * @param ip Server IP address
     * @param name username
     * @param password password of the user
     * @return true when the username and password are valid, otherwise return false
     */
    public int connectServer(String ip, String name, String password){
	return client.connectServer(ip, name, password);
    }

    /**
     * Displays a message on the client window
     * @param msg message to display on client window
     */
    public void displayMsg(String msg){
	ClientWindow.getWindow().getTaOutput().append(msg);
	//ClientWindow.getWindow().getTaOutput().append(msg);
	//appendToPane(ClientWindow.getWindow().getTaOutput(), msg, Color.BLACK);
        ClientWindow.getWindow().getTaOutput().setCaretPosition(ClientWindow.getWindow().getTaOutput().getDocument().getLength());
    }
	
    /**
     * Sends a message to the server
     * @param s message being send to the server
     */
    public void sendMsg2Server(String s){
	client.sendMsg(s);
    }
	
    /**
     * Initiated the contact list and return contact list to the client window
     * @return contact names
     */
    public String[] getContacts(){
	Contact[] contacts = new Contact[5];
	String[] names = new String[5];
	for(int i = 0; i < 5; i++){
	    contacts[i] = new Contact("contact" + i);
	    names[i] = contacts[i].getName();
	}
	return names;
    }
	
    /**
     * Update the contact list
     * @param contact an array of strings represents a contact list
     */
    public void updateContactList(String[] contact){
	ClientWindow.getWindow().getContactList().setListData(contact);
    }
}
