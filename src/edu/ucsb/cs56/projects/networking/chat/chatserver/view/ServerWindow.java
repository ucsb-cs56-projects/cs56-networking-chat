package edu.ucsb.cs56.projects.networking.chat.chatserver.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import edu.ucsb.cs56.projects.networking.chat.chatserver.controller.ServerController;

//import server
import edu.ucsb.cs56.projects.networking.chat.chatserver.model.Server;

/**
 * Represents a JFrame window of a server
 * @author Peng Wang, Andro Stotts, Max Hinson, and Bryce Filler
 * @version 0.5
 */
public class ServerWindow extends JFrame{

    private static final long serialVersionUID = 1L;
    private JButton btStart;
    private JButton btClose;
    private JButton btKick;
    private TextArea taConsole;
    private static ServerWindow window;
    private ServerController controller;
    private JList<String> userList;
    private Server server;
    
    //contact count
    private JLabel onlineCountText;
    // private JLabel onlineCountNum;
    private JButton onlineCountUpdate;
    

    /**
     * Default constructor
     */
    private ServerWindow(){
	// instance of server
	//server = Server.getServer();

	btStart = new JButton("Start Server");
	btClose = new JButton("Close Server");
	btKick = new JButton("Kick User");
	userList = new JList<String>();
	taConsole = new TextArea();
	
	//online count
	onlineCountText = new JLabel("Users Online: ");
	onlineCountUpdate = new JButton ("Refresh");


	//get an instance of controller
	controller = ServerController.getController();

    }
	
    /**
     * Gets the ServerWindow object
     * @return instance of ServerWindow
     */
    public static ServerWindow getWindow(){
	if(window == null)
	    window = new ServerWindow();
	return window;
    }
	
    /**
     * Creates the server window
     */
    public void launchWindow(){
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setLocation(100, 100);
	this.setSize(800, 600);
	this.setTitle("Chatting Server");
	JPanel rightPanel = new JPanel();
	JPanel leftPanel = new JPanel();
	JPanel buttonPanel = new JPanel();
		
	rightPanel.setBorder(BorderFactory.createLineBorder(Color.black));
	rightPanel.setLayout(new BorderLayout());
	buttonPanel.add(btStart);
	buttonPanel.add(btClose);
	buttonPanel.add(btKick);


	// online count button
	buttonPanel.add(onlineCountUpdate);



	//might need a .setEnabled for onlineCountUpdate
	btStart.setEnabled(true);
	btClose.setEnabled(false);
	btKick.setEnabled(false);
	taConsole.setEditable(false);
	taConsole.setBackground(Color.WHITE);
	rightPanel.add(buttonPanel, BorderLayout.NORTH);
	rightPanel.add(taConsole,BorderLayout.CENTER);
		
	leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
	leftPanel.setLayout(new BorderLayout());
	leftPanel.add(onlineCountText,BorderLayout.NORTH);
	leftPanel.add(userList,BorderLayout.CENTER);
		
	this.getContentPane().add(rightPanel, BorderLayout.CENTER);
	this.getContentPane().add(leftPanel, BorderLayout.WEST);
		
	btStart.addActionListener(new myButtonListener());
	btClose.addActionListener(new myButtonListener());
	btKick.addActionListener(new myButtonListener());
	//call actionListener for update


	onlineCountUpdate.addActionListener(new myButtonListener2());

	this.setVisible(true);
    }
	
    /**
     * Gets the TextArea component of the server window
     * @return TextArea component that represents the server output
     */
    public TextArea getTaConsole(){
	return taConsole;
    }
	
    /**
     * Gets the JList component of the server window
     * @return JList component that represents the user list
     */
    public JList getUserList(){
	return userList;
    }


   
    /**
     * Handles the actions when buttons have been clicked on the server window
     * @author Peng Wang, Andro Stotts, Max Hinson, and Bryce Filler
     * @version 0.5
     */
    class myButtonListener implements ActionListener{
	public void actionPerformed(ActionEvent e) {
	    if(e.getActionCommand() == "Start Server"){
		taConsole.setText("");
		taConsole.append(controller.startServer());	
		btStart.setEnabled(false);
		btClose.setEnabled(true);
		btKick.setEnabled(true);
	    }
	    else if (e.getActionCommand() == "Kick User"){
		if(userList.getSelectedValue() == null)
		    taConsole.append("[Server Message] No user selected to kick\n");
		else
		    taConsole.append(controller.kickUser((String)userList.getSelectedValue()));
	    }
	    else{
		taConsole.append(controller.closeServer());
		btStart.setEnabled(true);
		btClose.setEnabled(false);
		btKick.setEnabled(false);
	    }
	}		
    }


 //action for onlineUpdateCount button has been clicked
    class myButtonListener2 implements ActionListener{
	public void actionPerformed(ActionEvent e){
	    int count = 0;
	    ListModel<String> list= userList.getModel();
	    count = list.getSize();
	    onlineCountText.setText("Users Online: " + String.valueOf(count));
	}






    }
}