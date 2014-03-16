package edu.ucsb.cs56.projects.networking.chat.chatserver.controller;

import edu.ucsb.cs56.projects.networking.chat.chatserver.view.ServerWindow;

/**
 * Application class represent the entrance of the program
 * @author Peng Wang with Andro Stotts
 * @version 0.4
 */
public class RunServer{
	public static void main(String[] agrs){
		//create the application window
		ServerWindow window = ServerWindow.getWindow();
		window.launchWindow();		
	}
}
