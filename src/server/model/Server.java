package server.model;
import server.controller.ServerController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/*
 *SPECIAL CODE used by system to indicate the type of message
 *   MESSAGE FORMAT (everything inside <> is variable)
 *
 *1001-regular one-to-one or broadcast message
 *   [Client@<client-ip>] <sender's nickname>: <message>&<recipient's nickname>:1001
 *   or
 *   [Client@<client-ip>] <sender's nickname>(Broadcast): <message>&Broadcast:1001
 *
 *1002-let contacts know that someone has logged on
 *   <nickname of online user>&1002
 *
 *1003-let contacts know that someone has logged off
 *   <nickname of offline user>&1003
 *
 *1004-send the client the user's contact list
 *   <nickname1:nickname2:nickname3(Online):nickname4...>&1004
 *
 *1005-user has logged in already
 *
 *
 *1006-tell other clients that someone has changed their nickname
 *   <oldnickname>:<newnickname>&1006
 *
 *1007-tell the client its new nickname
 *   <newnickname>&1007
 *
 *1008-regular message sent to a chatroom
 *   [Client@<client-ip>] <sender's nickname>: <message>&<room-number>:1008
 *
 *1009-client registering a new chatroom with the server
 *   &1009:participant1:participant2:participant3:...
 */

/**
 * Represents the server model
 * @author Peng Wang, Andro Stotts, Max Hinson, and Bryce Filler, jleeong
 * @version F16
 */
public class Server{
    private final int port = 8888;
    private static Server server = null;
    private boolean isServerStart;
    private ServerSocket ss;

    private String serverMsg;
    private String serverMsgPrefix;
    private String clientMsgPrefix;
    private String [] clientsOnline;
    private ArrayList<Client> clients;
    private ArrayList<User> users;
    private ArrayList<ChatRoom> rooms;

    private String usage = "------------------------------------------SERVER USAGE-----------------------------------------------\n" +
	"             We currently have a FAKE users with only the users listed below:         \n" +
	"                     Username: Peng Wang        Nickname: peng     Password: 123abc      \n" +
	"                     Username: Andro Stotts     Nickname: andro    Password: abc123      \n" +
	"                     Username: Phillip Conrad   Nickname: phill    Password: 9876543     \n" +
	"                     Username: Steve Jobs       Nickname: steven   Password: 2as134      \n" +
	"                     Username: Bill Gates       Nickname: billy    Password: idu?e3e     \n" +
	"                     Username: Orange Juice     Nickname: oj       Password: orangejuice \n" +
	"                     Username: Max Hinson       Nickname: max      Password: password    \n" +
	"                     Username: Bryce Filler     Nickname: bry      Password: bryce       \n" +
	"-----------------------------------------------------------------------------------------------------\n" +
	"      NOTE: PLEASE USE ABOVE ACCOUNTS TO LOGIN OUR CLIENT APPLICATION FOR TESTING PURPOSES           \n" +
	"-----------------------------------------------------------------------------------------------------\n";
	
    /**
     * Default constructor
     */
    private Server(){
	clientsOnline = null;
	isServerStart = false;
	ss = null;
	serverMsg = "";
	serverMsgPrefix = "[Server Message] ";
	clients = new ArrayList<Client>();
	rooms = new ArrayList<ChatRoom>();
	
	//make our own fake users
	users = new ArrayList<User>();
	User peng = new User("Peng Wang", "peng", "123abc");		
	User andro = new User("Andro Stotts", "andro", "abc123");
	User philp = new User("Phillip Conrad", "phill", "9876543");
	User bill = new User("Bill Gates", "billy", "idu?e3e");
	User steve = new User("Steve Jobs", "steven", "2as134");
	User orange = new User("Orange Juice ", "oj", "orangejuice");
	User max = new User("Max Hinson", "max", "password");
	User bryce = new User("Bryce Filler", "bry", "bryce");
	peng.addToContactList(andro);
	peng.addToContactList(philp);
	peng.addToContactList(steve);
	peng.addToContactList(bill);
	andro.addToContactList(peng);
	andro.addToContactList(bill);
	andro.addToContactList(orange);
	philp.addToContactList(peng);
	philp.addToContactList(andro);
	philp.addToContactList(max);
	steve.addToContactList(orange);
	steve.addToContactList(bill);
	steve.addToContactList(max);
	max.addToContactList(peng);
	max.addToContactList(andro);
	max.addToContactList(philp);
	max.addToContactList(bill);
	max.addToContactList(steve);
	max.addToContactList(orange);
	max.addToContactList(bryce);
	bryce.addToContactList(max);
	users.add(peng);
	users.add(andro);
	users.add(philp);
	users.add(bill);
	users.add(steve);
	users.add(orange);
	users.add(max);
	users.add(bryce);	
    }
	
    /**
     * Gets the server object
     * @return the server object
     */
    public static Server getServer(){
	if(server == null)
	    server = new Server();
	return server;
    }
	
    /**
     * start the server
     */
    public void start(){
	try{
	    //set the server's port and create a new thread that accepts connections
	    ss = new ServerSocket(port);
	    isServerStart = true;
	    Connection c = new Connection();
	    new Thread(c).start();
			
	    //set the server message
	    serverMsg = usage + serverMsgPrefix + "Server has been started and waiting for client connections\n";
	} catch(IOException ex){
	    serverMsg = serverMsgPrefix + "Server started failed, please try to use another port\n";
	}
    }
	
    /**
     * close the server
     */
    public void close(){
	try {
	    //close the server socket and set the server status to be false
	    ss.close();			
	    isServerStart = false;
			
	    //set the server message
	    serverMsg = serverMsgPrefix + "Server shut down successfully\n";
	} catch (IOException e) {
	    serverMsg = serverMsgPrefix + "Problem occured when server shutting down\n";
	}
    }
	
    /**
     * Kick the user out of the server
     * @param userName user's name
     */
    public void kick(String userName){
	String nickname = "";
	//find the client that you want to kick
	for(int i = 0; i < clients.size(); i++){
	    if(clients.get(i).getUser().getName().equals(userName)){
		nickname = clients.get(i).getUser().getNickname();
		//Removes client login privileges
		clients.get(i).setIsAuthorized(false);
		try {
		//closes the socket
		    clients.get(i).s.close();
		} catch (IOException e) {
		    serverMsg = serverMsgPrefix + "Kicking user failed\n";
		}
		serverMsg = serverMsgPrefix + userName + " has been kicked\n";
	    }
	}
	//goes through each contact and removes deauthorized account from their contact lists
	for(Client c : clients){
	    if(!c.getUser().getNickname().equals(nickname))
		c.sendMsg(nickname + "&1003");
	}
    }
	
    /**
     * Get server status
     * @return true when server is on, otherwise false
     */
    public boolean getServerStatus(){
	return isServerStart;
    }
	
    /**
     * Gets the server message
     * @return server message
     */
    public String getServerMsg(){
	return serverMsg;
    }

    /**
     * inner class Client only provide service to the outer class
     * @author Peng Wang, Andro Stotts, Max Hinson, Bryce Filler, jleeong
     * @version F16
     */
    class Client implements Runnable{
	private Socket s;
	private SocketAddress ip;
	private boolean isClientConnect;
	private boolean isAuthorized = false;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	private ServerController controller = ServerController.getController();
	private User currentUser;
	/**
	 * Constructor initialize data for a client
	 * @param s client's socket
	 */
	public Client(Socket s){
	    //save the client socket
	    this.s = s;
			
	    //get the client ip address
	    ip = s.getRemoteSocketAddress();

	    try {
		//get input and output stream
		this.dis = new DataInputStream(s.getInputStream());
		this.dos = new DataOutputStream(s.getOutputStream());
				
		//set the connection status to be true
		this.isClientConnect = true;
				
		//set the connection message
		controller.displayMsg(serverMsgPrefix + "A client has connected from IP: " + ip + '\n');
	    } catch (IOException e) {
		e.printStackTrace();
	    } 
	}
		
	/**
	 * parse message send by the client
	 * @param s message send by the client
	 * @return array of strings that contains message, contact person, and special code
	 */
	public String[] parseMsg(String s){
	    int index = s.lastIndexOf("&");
	    String msg = s.substring(0,index);
	    String[] params = s.substring(index+1, s.length()).split(":");
	    ArrayList<String> parsed = new ArrayList<String>();
	    parsed.add(msg);
	    parsed.addAll(Arrays.asList(params));
	    return parsed.toArray(new String[parsed.size()]);
	}
		
	/**
	 * Update user list
	 */
	public void updateWhoIsOnline(){
	    //update who's online (server side)
	    clientsOnline = new String[clients.size()];
	    for(int i=0; i < clients.size();i++){
		clientsOnline[i] = clients.get(i).getUser().getName();
	    }
	    controller.updateList(clientsOnline);
	}
		
	/**
	 * broadcast message to all users who are current online
	 * @param strs message after parsing
	 */
	public void broadcast2all(String[] strs){
	    for(Client c : clients){
		c.sendMsg(strs[0] + "&" + strs[2]);
	    }
	}
		
	/**
	 * broadcast message to one particular person who is currently online
	 * @param msg The instant message to be delivered
	 * @param rec The nickname of the User to send to
	 * @author jleeong
	 * @version F16
	 */
	public void broadcast2one(String msg, String rec){
	    boolean isOnline = false;
	    this.sendMsg(msg + "&1001");
	    for(Client c : clients){
		if(rec.equals(c.getUser().getNickname())){
		    c.sendMsg(msg + "&1001");
		    isOnline = true;
		    break;
		}
	    }
	    if(!isOnline){
		this.sendMsg("***THE USER YOU ARE TRYING TO MESSAGE IS NOT ONLINE***&1001");
	    }	
	}
	
	/**
	* broadcast message to all users in a single chatroom
	* @param msg The instant message to be sent
	* @param rn A double representing the roomnumber of the specified chatroom
	*@author jleeong
	*@version F16
	*/
	public void broadcast2room(String msg, double rn){
		int rIndex = rooms.indexOf(new ChatRoom(rn));
		if(rIndex != -1){
			ChatRoom cr = rooms.get(rIndex);
			Set<User> participants = cr.getParticipants();
			for(Client c : clients){
				if(participants.contains(c.getUser()))
					c.sendMsg(msg+"&1001");
			}
		}
		else{
			this.sendMsg("***THE ROOM YOU ARE TRYING TO MESSAGE DOES NOT EXIST***&1001");
		}
	}

	/**
	 * change a user's nickname
	 * @param strs message after parsing
	 */
	public void changeNickname(String[] strs){
	    //input string format (everything inside <> is variable):
	    //<username>(NAME_CHANGE): <newnickname>

	    //split input to get user's data
	    String[] strings1 = strs[0].split("\\(");
	    String[] strings2 = strs[0].split(": ");
	    String username = strings1[0];
	    String oldNickname = "";
	    String newNickname = strings2[1];

	    //search the server's client list for the one that is changing its nickname
	    for(Client c : clients){
		if(c.getUser().getName().equals(username))
		    {
			oldNickname = c.getUser().getNickname();

			//update server's users and send message to client to change its nickname 
			if (c.sendMsg(newNickname + "&1007") == 0){
			c.getUser().setNickname(newNickname);
			//display messages on the server and client about the nickname change
			controller.displayMsg(serverMsgPrefix + username + "'s nickname has been changed from " + oldNickname + " to " + newNickname);
			c.sendMsg("Your nickname has been changed from " + oldNickname + " to " + newNickname + "&1001");
            }
			else
			c.sendMsg("Nickname change failed" + "&1001");
			break;
		    }
	    }

	    //notify all other contacts that the user has changed their nickname
	    for(Client c : clients)
		if(!c.getUser().getName().equals(username))
		    c.sendMsg(oldNickname + ":" + newNickname + "&1006");

	    //update the user's new nickname in the users
	    for(User u : users)
		if(u.getName().equals(username))
		    u.setNickname(newNickname);
	}
		
	/**
	 * send message to the client
	   @return returns 1 or 0 depending upon whether or not an exception has been thrown.
	 * @param msg message to send
	 */
	public int sendMsg(String msg){
	    try {
		dos.writeUTF(msg);
	        return 0;			
	    } catch (IOException e) {
		controller.displayMsg(serverMsgPrefix + " server send message to the client failed\n");
	        return 1;
	    }
	}
	
	/**Method to register a new chatroom with the server. Receives a registerChatRoom message from the Client, creates and stores
	* a new instance of a ChatRoom containing all the participants, assigns a room number identifying the room, and sends the 
	* room number back to the client thus completing the registration process.
	*@param participants is a List containing the nicknames of all Users who will participate in the chatroom, with first element equal to "1009"
	*@author jleeong
	*@version F16
	*/
	public void registerChatRoom(List<String> participants){
		//for (String parts : participants){System.out.println(parts);}
		controller.displayMsg("registering new ChatRoom...");
		ArrayList<String> pnames = new ArrayList<String>();
		pnames.addAll(participants);
		HashSet<User> p = new HashSet<User>();
		for(User u : users){
			if(pnames.contains(u.getNickname()))
				p.add(users.get(users.indexOf(u)));
		}
		ChatRoom newRoom = new ChatRoom(p);
		controller.displayMsg("ChatRoom created...");
		newRoom.setRoomNumber(Math.random());
		while(rooms.contains(newRoom)){
			newRoom.setRoomNumber(Math.random());
		}
		rooms.add(newRoom);
		controller.displayMsg("new ChatRoom registered at: "+newRoom.getRoomNumber()+"\n");
		this.sendMsg(newRoom.getRoomNumber()+"&1009");
	}	
	/**
	 * Gets the contact list for the current client and appends "(Online)" to those users
	 * that are online
	 * @author jleeong
	 * @version F16
	 */
	public String getContacts(User u){
		String list="";
	    	for(User contact : u.getContactList()){
			list += contact.getNickname();
			if(contact.isOnline()) list += "(Online)";
			list += ":";
		}
		return list;
	}
		
	/**
 	* Gets the current logged in User on the connected client
 	* @author jleeong
 	* @version F16
	*/ 
	public User getUser(){
		return currentUser;
	}
	/**
	 * Sets the authorization of the cilent
	 * @param b true when client is authorized, otherwise false
	 */
	public void setIsAuthorized(boolean b){
	    isAuthorized = b;
	}

	/**
	 * Waits for a message, and broadcasts it.
	 * On failure it removes client from list and it closes all streams
	 */
	public void run() {
	try{
		//when the client is not authorized, check the identity 
		//if the identity checking success, then start to broadcasting message
		//if the identity checking failed, return a string with nothing and throws an exception
		while(!isAuthorized){
			//read the username and password
			String identity = dis.readUTF();
			String[] identities = identity.split("&");
			controller.displayMsg(serverMsgPrefix + "User trying to login with Username: " 
				+ identities[0] + " Password: " + identities[1] + '\n');
			if(identities[0].isEmpty()) throw new BlankNameException();
			//parse through and check if user is logged in already
			//and check password
			for(User u : users){
				if(u.getName().equals(identities[0])){
					if(u.isOnline()) throw new UserExistException();
					else if(u.getPassword().equals(identities[1])){
						//send the client its contacts list
						//
						this.sendMsg(getContacts(u)+"&1004");
						//send the client its nickname
						this.sendMsg(u.getNickname()+"&1007");
						isAuthorized=true;
						currentUser=u;
						currentUser.setOnline(true);
					}
				}
			}
			//if authentication failure
			if(!isAuthorized){
				controller.displayMsg(serverMsgPrefix+"User login failed\n");
				this.sendMsg("Wrong username or password");
				throw new Exception();
			}
		}

		///broadcast online notification when a client is online and successfully login
		controller.displayMsg(serverMsgPrefix + "User login success, " + currentUser.getName()+
			" (" + currentUser.getNickname() + ") "  + " is online now!\n");
		controller.displayMsg(serverMsgPrefix + "TOTAL # OF CLIENTS: " + clients.size() + '\n');
            	for(Client c : clients) {
                	c.sendMsg("TOTAL # OF CLIENTS: " + clients.size() + "&1001");
            	}
                //update online list (server side)
		updateWhoIsOnline();
					
		//send online notification to the clients
		for(int i = 0; i < clients.size(); i++){
			Client c = clients.get(i);
			if(!c.getUser().getName().equals(currentUser.getNickname()))
			    c.sendMsg(currentUser.getNickname() + "&1002");
		}
	
				
		while(isClientConnect && isServerStart && isAuthorized){
			//readUTF always waiting unless there is something has been inputed
			String msg = dis.readUTF();
			if (!isServerStart)
				throw new Exception();
			clientMsgPrefix = "[Client@"+ip.toString()+" ]";
			controller.displayMsg(clientMsgPrefix + msg + '\n');
			String[] strs = parseMsg(msg);
						
			//client going offline
			if(strs[2].equals("1003")){
				controller.displayMsg(serverMsgPrefix + currentUser.getName() + " (" 
					+currentUser.getNickname() + 
					") is trying to logoff and disconnect with the server\n");
				broadcast2all(strs);
				currentUser.setOnline(false);
			}
			
			//client chatting in chatroom
			if(strs[2].equals("1008")){
				broadcast2room(strs[0], Double.parseDouble(strs[1]));
			}

			//client registering a new chatroom
			if(strs[1].equals("1009")){
				registerChatRoom(Arrays.asList(strs));	
			}		
			//client doing regular chatting
			else{
			//if the client doing broadcasting
			if(strs[1].equals("Broadcast")){
				broadcast2all(strs);
			}
			//the user is requesting a nickname change
			else if(strs[1].equals("NAME_CHANGE")){
				changeNickname(strs);
			}
			//the user has deleted someone from their contact list
			else if(strs[1].equals("DELETE")){
				currentUser.deleteContact(strs[2]);
			}
			else{
				broadcast2one(strs[0],strs[1]);					
			}
			}			
		}	

	}
	catch(UserExistException uee){
		this.sendMsg("This user has logged in already");
		clients.remove(this);
		controller.displayMsg(serverMsgPrefix + "User has logged in already!\n");
	}
	catch(BlankNameException bl){
		this.sendMsg("No name was entered");
		clients.remove(this);
		controller.displayMsg(serverMsgPrefix + "User Login error!\n");	

	}
	catch(Exception e){
	//remove the current client from the list on server
	if(!isServerStart)
		clients.clear();
	else{
		clients.remove(this);
		controller.displayMsg(serverMsgPrefix + currentUser.getName() + " (" + currentUser.getNickname() + ") has successfully disconnected\n");
		for(Client c : clients){
			if(!c.getUser().getNickname().equals(currentUser.getNickname()))
				c.sendMsg(currentUser.getNickname() + "&1003");
		}
	}
	updateWhoIsOnline();
				
	}
	finally{
		try {
		    if(dos != null)
			dos.close();
		    if(dis != null)
			dis.close();
		    if(s != null)
			s.close();	
	
		} catch (IOException e1) {
		    controller.displayMsg("Server is down\n");
		}
	}
	}
}
	
	
    /**
     * Connection class is used for listening new client connections
     * on a separate thread
     * @author Peng Wang with Andro Stotts
     * @version 0.4
     */
    class Connection implements Runnable{
	public void run() {
	    while(isServerStart){
		try {
		    Socket s = ss.accept();
		    Client client = new Client(s);
		    clients.add(client);
		    new Thread(client).start();
		} catch (IOException e) {
		    serverMsg = "Connection failed\n";
		}
	    }
	}
    }
}
