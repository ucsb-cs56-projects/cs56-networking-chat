package client.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;



import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;


import client.controller.ClientController;

/**
 * Represents a client who can connect/disconnect to the server 
 * and chat with other people on the server  
 * @author Peng Wang, Andro Stotts, Bryce Filler, Max Hinson
 * @version 0.4
 */
public class Client {
    private static Client client;
    private ClientController controller;
    private int portNumber = 8888;

    private String userName;
    private String nickName;
    private String serverIP;
    private String clientIP;
    private String[] contactList;

    private Socket mySocket;

    private DataOutputStream dos;
    private DataInputStream dis;

    private boolean isConnected;

    private boolean soundOn = true;

    
    /**
     * Initialize instance variables
     */
    private Client(){
	userName = "";
	nickName = "";
	serverIP = "";
	clientIP = "";
	mySocket = null;
	dos = null;
	dis = null;
	isConnected = false;
	contactList = null;
    }
	
    /**
     * getClient method is used for get a client object
     * @return instance of Client
     */
    public static Client getClient(){
	if(client == null)
	    client = new Client();
	return client;
    }

    /**
     * setuserName method is used for setting user's userName
     * @param userName the user's userName
     */
    public void setUserName(String userName){
	this.userName = userName;
    }

    /**

     * setnickName method is used for setting user's nickName
     * @param nickName the user's nickName
     */
    public void setNickname(String nickName){
	this.nickName = nickName;
    }
	
	
    /**
     * getuserName method is used for getting the user's userName of a Client object
     * @return userName the user's userName
     */
    public String getUserName(){
	return userName;
    }

    /**
     * getnickName method is used for getting the user's nickName of a Client object
     * @return nickName the user's nickName
     */
    public String getNickname(){
	return nickName;
    }

	
    /**
     * setServerIP method is used for setting the IP address which the client is connecting 
     * @param serverIP the IP address of the server
     */
    public void setServerIP(String serverIP){
	this.serverIP = serverIP;
    }
	
    /**
     * getServerIP returns the server IP address which the client is connecting
     * @return the server's IP address
     */
    public String getServerIP(){
	return serverIP;
    }
	
    /**
     * setClientIP is used for setting up 
     * @param clientIP the IP address of the client
     */
    public void setClientIP(String clientIP){
	this.clientIP = clientIP;
    }
	
    /**
     * get the client IP address
     * @return clientIP the IP address of the client
     */
    public String getClientIP(){
	return clientIP;
    }

    /**
     * get the sound status
     * @return soundOn whether the client's sound is on
     */
    public boolean isSoundOn(){
	return soundOn;
    }

    /**
     * set the sound status
     * @param soundOn set the client's sound status
     */
    public void setSound(boolean soundOn){
	this.soundOn = soundOn;
    }
    
    /**
     * ConnectServer method takes server IP address parameter to connect the specific
     * server and takes client userName and password in order to check login authority 
     * @param IP Server IP address
     * @param userName userName
     * @param password user's password associate with the useruserName
     * @return boolean true when useruserName and password are valid, otherwise false
     */	
		
    public int connectServer(String IP, String userName, String password){
	//save the userName and IP address of client
	this.setUserName(userName);
	this.setServerIP(IP);
	controller = ClientController.getController();
	try {
	    //create a new socket and connect to IP address at socket 8888
	    //get the input and output stream wrapped with DataOutput/InputStream
	    mySocket = new Socket(serverIP, portNumber);
	    dos = new DataOutputStream(mySocket.getOutputStream());
	    dis = new DataInputStream(mySocket.getInputStream());
			
	    int result = checkAuthority(userName + "&" + password);
	    //check login authority
	    if(result == 0){
		controller.displayMsg(userName + "(you) has connected to the server at " + serverIP + '\n');
		isConnected = true;
		new Thread(new RecieveMsg()).start();
		//if valid useruserName and password, return 1
		return 0;
	    }
	    //if useruserName or password wrong
	    else if(result == 1){
		return 1;
	    }
	    //if user logged in already
	    else if(result == 2)
		return 2;
	    //if server is down
	    else
		return 9;
	} catch (IOException e) {
	    //if IP address not valid
	    return 8;
	}
    }
	
    /**
     * disconnectServer method is used for disconnecting the client with the server
     */
    public void disconnectServer(){
		
    }
	
    /**
     * checkAuthority method is used for checking if the useruserName and password are valid
     * @param s the combination of useruserName and password
     * @return boolean true when the server returns null, otherwise false
     */

		
    public int checkAuthority(String s){
	String result = "";
	try {
	    //write userName and password to the server and read result from the server
	    this.sendMsg(s);
			
	    //get the contact list from the server and update the contact list of client window
	    result = dis.readUTF();
	    String[] temp = parseReceivingMsg(result);
			
	    if(result.equals("Wrong username or password")){			
		return 1;
	    }		
	    else if(result.equals("This user has logged in already")){
		return 2;
	    }
	    else if (result.equals("No name was entered")){
		return 3;
	    }
	    else{
		contactList = parseContactList(temp[0]);
		controller.updateContactList(contactList);
		return 0;
	    }
				
	} catch (IOException e) {
	    controller.displayMsg("Error occured when sending message to the server\n");
	    return 9;
	}
    }
	
    /**
     * Parse the contact list
     * @param s a contact list represented by a string (eg. "Peng Wang:Andro Stotts")
     * @return array of strings that has contact userNames seperated
     */
    public String[] parseContactList(String s){	
	String[] temp = s.split(":");
	String[] temp2 = new String[temp.length+1];
	//add the broadcast to all option to the contact list
	temp2[0] = "Broadcast";
	for(int i = 1; i < temp.length+1; i++)
	    temp2[i] = temp[i-1];
	return temp2;
    }
	
    /**
     * Parse the receiving message from the server
     * @param s received message
     * @return array of strings
     */
    public String[] parseReceivingMsg(String s){
	String[] temp = null;
	if(s.contains("&")){
	    int index = s.lastIndexOf("&");
	    String msg = s.substring(0, index);
	    String c = s.substring(index+1, s.length());
	    String[] str = {msg, c};
	    temp = str;
	}
	return temp;
    }

    /**
     * sendMsg is the method that used for send a text message to the server
     * @param s text message
     */
    public void sendMsg(String s){
	try {
	    dos.writeUTF(s);
	} catch (IOException e) {
	    controller.displayMsg("Error occured when sending message to the server\n");
	}
    }
	
    /**
     * RecieveMsg class represent a seperate thread that constantly recieving message from the server
     * @author Peng Wang and Andro Stotts
     * @version 0.4
     */
    class RecieveMsg implements Runnable{
	public void run() {
	    try{
		while(isConnected){
		    String msg = dis.readUTF();
		    String[] strs = parseReceivingMsg(msg);
		    //client go online
		    if(strs[1].equals("1002")){
			for(int i = 0; i < contactList.length; i++){
			    if(contactList[i].equals(strs[0])){
				contactList[i] += "(Online)";
				controller.updateContactList(contactList);
				//play a sound
				if(soundOn)
				    {
					AePlayWave onlineSound = new AePlayWave("OhYeah.aiff");
					onlineSound.start();
				    }
				break;
			    }
			}
			
		    }
		    //client go offline
		    else if(strs[1].equals("1003")){
			for(int i = 0; i < contactList.length; i++){
			    if(contactList[i].equals(strs[0]+"(Online)")){
				contactList[i] = strs[0];
				controller.updateContactList(contactList);
				//play a sound
				if(soundOn)
				    {
					AePlayWave offlineSound = new AePlayWave("HitWallObs.aiff");
					offlineSound.start();
				    }
				break;
			    }
			}
		
		    }
		    //update the contact's nickname
		    else if(strs[1].equals("1006")){
			String[] nicknames = strs[0].split(":");
			for(int i = 0; i < contactList.length; i++){
			    if(contactList[i].equals(nicknames[0] + "(Online)")){
				contactList[i] = nicknames[1] + "(Online)";
				controller.updateContactList(contactList);
				controller.displayMsg(nicknames[0] + "'s nickname has been changed to " + nicknames[1] + "\n");
				break;
			    }
			}
		    }
		    else if(strs[1].equals("1007")){
			controller.setNickname(strs[0]);
		    }
		    else if(strs[1].equals("1001")){
			// display the incoming message
			controller.displayMsg(strs[0] + '\n');
			//play a sound
			if(soundOn)
			    {
				AePlayWave msgSound = new AePlayWave("Glass.aiff");
				msgSound.start();
			    }
		    }
		}
	    } catch (SocketException e){
		controller.displayMsg("The server has shutdown\n");
	    } catch(IOException e){
		controller.displayMsg("You have been kicked from the server.\n");
	    }
	}		
    }
	
}
