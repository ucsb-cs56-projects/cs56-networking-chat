package client.controller;

import client.model.Client;
import client.model.Contact;
import client.view.ClientWindow;
import java.awt.Color;
import javax.swing.DefaultListModel;

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
    }
    
       /**Method to send a message to a single recipient. This is the preferred method to use when sending a single message to another Client.
	*Takes the raw input from the ClientWindow and adds details relevant to the server (e.g. sender, receiver, service code)
	*@param msg The instant message intended to be sent to another User
	*@param rec The nickname of the intended User
	*@author jleeong
	*@version F16
	*/ 
	public void sendIM(String msg, String rec){
		client.sendIM(" to "+rec+": "+msg+"&"+rec+":1001");
	}

	/**Method to send a message to a ChatRoom on the server.
	*@param msg The message inteded to be sent to ChatRoom
	*@param rn The room number of the ChatRoom
	*@author jleeong
	*@version F16
	*/
	public void sendGrpMsg(String msg, String rn){
		client.sendIM(" to ChatRoom: "+msg+"&"+rn+":1010");
	}
	
    /**
     * Sends a message to the server
     * @param s message being send to the server
     */
    public void sendMsg2Server(String s){
	client.sendMsg(s);
    }
	
	/** Adds contact to the Client contact list and appends the contact to the ClientWindow list
	*@param contact A string containing the nickname of the contact to be added
	*@author jleeong
	*@version F16
	*/
	public void addContact(String contact){
		String nickname = contact;
		if(contact.contains("(Online)")){
			nickname = contact.substring(0, contact.indexOf('('));
		}
		client.addContact(new Contact(nickname));
		ClientWindow.getWindow().getContactList().addElement(contact);
	}
	
	/** Updates the Client's contact list to reflect the online status of a contact and mirrors the result in ClientWindow list
	*@param contact A string containing the nickname of the contact that has changed their online status
	*@param status A boolean that represents the online status of the Contact
	*@author jleeong
	*@version F16
	*/
	public void updateContact(String contact, boolean status){
		client.updateContact(contact, status);
		String online = "";
		DefaultListModel windowlist = ClientWindow.getWindow().getContactList();
		if(status) windowlist.set(windowlist.lastIndexOf(contact), contact+"(Online)");
		else windowlist.set(windowlist.lastIndexOf(contact+"(Online)"), contact);
	}
	
	/** Method to send a ChatRoom registration the server.
	* @param reg A string representing the participants list as inputed from the launchRegisterChatRoomWindow
	* @author jleeong
	* @version F16
	*/
	public void sendChatRoomRegistration(String reg){
		String [] parts = reg.split(",");
		String msg = "&1009:";
		for(String x : parts){
			msg += x +":";
		}
		client.sendChatRoomRegistration(msg);
	}
}
