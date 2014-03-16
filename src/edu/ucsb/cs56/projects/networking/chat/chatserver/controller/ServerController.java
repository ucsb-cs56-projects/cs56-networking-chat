package edu.ucsb.cs56.projects.networking.chat.chatserver.controller;

import edu.ucsb.cs56.projects.networking.chat.chatserver.model.Server;
import edu.ucsb.cs56.projects.networking.chat.chatserver.view.ServerWindow;

/**
 * Represent the controller which handles the communication between server view and server model
 * @author Peng Wang, Andro Stotts, Max Hinson, and Bryce Filler
 * @version 0.2
 */
public class ServerController{
	private Server server;
	private static ServerController controller;
	
	/**
	 * Default constructor
	 */
	private ServerController(){
		server = Server.getServer();
	}
	
	/**
	 * Gets the instance of ServerController class
	 * @return instance of ServerController
	 */
	public static ServerController getController(){
		if(controller == null)
			controller = new ServerController();
		return controller;
	}
	
	/**
	 * Starts the server
	 * @return server message
	 */
	public String startServer(){	
		server.start();
		return server.getServerMsg();
	}
	
	/**
	 * Closes the server
	 * @return server message
	 */
	public String closeServer(){
		server.close();
		return server.getServerMsg();
	}
	
	/**
	 * Kick user out of the server
	 * @param userName User's name
	 * @return server message
	 */
	public String kickUser(String userName){
		server.kick(userName);
		return server.getServerMsg();
	}
	
	/**
	 * Gets server status
	 * @return true when server is on, false when server is down
	 */
	public boolean getServerStatus(){
		return server.getServerStatus();
	}
	
	/**
	 * Displays the message onto the server window
	 * @param s
	 */
	public void displayMsg(String s){
		ServerWindow.getWindow().getTaConsole().append(s);
	}
	
	/**
	 * Update the list of users
	 * @param list array of strings which contains users' names
	 */
	public void updateList(String[] list){
		ServerWindow.getWindow().getUserList().setListData(list);
		//ServerWindow.getWindow().repaint();
	}
}
