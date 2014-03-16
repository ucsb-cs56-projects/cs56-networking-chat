package edu.ucsb.cs56.projects.networking.chat.chatclient.controller;

import edu.ucsb.cs56.projects.networking.chat.chatclient.view.ClientWindow;

/**
 * Application class represent the entrance of the program
 * @author Peng Wang, Andro Stotts, Bryce Filler, Max Hinson
 * @version 0.4
 */
public class RunClient {
	public static void main(String[] args){
		ClientWindow window = ClientWindow.getWindow();
		window.launchLoginWindow();
	}
}
