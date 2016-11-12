package server.model;

import java.util.ArrayList;

/**
 * User class represent a regular user
 * @author Peng Wang, Andro Stotts, Max Hinson, and Bryce Filler
 * @version 0.5
 */
public class User {
    private String name;
    private String nickname;
    private String password;
    private ArrayList<User> contactList;
	private boolean isOnline;	
    /**
     * Constructor that takes username and password
     * @param name
     * @param nickname
     * @param password
     */
    public User(String name, String nickname, String password){
	isOnline=false;
	setName(name);
	setNickname(nickname);
	setPassword(password);
	contactList = new ArrayList<User>();
    }
	
    /**
     * Sets contact list
     * @param list contact list
     */
    public void setContactList(ArrayList<User> list){
	contactList = list;
    }
	
    /**
     * Add user to the contact list
     * @param u a User object
     */
    public void addToContactList(User u){
	contactList.add(u);
    }
	
    /**
     * Gets the contact list
     * @return contact list
     */
    public ArrayList<User> getContactList(){
	return contactList;
    }
	
    /**
     * Gets the name of the user
     * @return user's name
     */
    public String getName() {
	return name;
    }
	
    /**
     * Sets the user's name
     * @param name user's name
     */
    public void setName(String name) {
	this.name = name;
    }
	
    /**
     * Gets the user's nickname
     * @return user's nickname
     */
    public String getNickname() {
	return nickname;
    }
	
    /**
     * Sets the user's nickname
     * @param nickname user's new nickname
     */
    public void setNickname(String nickname) {
	this.nickname = nickname;
    }

    /**
     * Gets the user's password
     * @return user's password
     */
    public String getPassword() {
	return password;
    }
	
    /**
     * Sets the user's password
     * @param password user's passwrod
     */
    public void setPassword(String password) {
	this.password = password;
    }
	/**
 	* method to delete contact based on nickname
 	* @param nickname the nickname to be deleted
 	*/
 
	public void deleteContact(String nickname){
		for(int i=0; i<contactList.size(); i++){
			if(contactList.get(i).getNickname().equals(nickname))
				contactList.remove(i);
		}
	}
	
	/**
	 * returns online status of the User
	 * @author Jared Leeong
	 */
	public boolean isOnline(){
		return isOnline;
	}

	/**
 	* sets online status
 	* @param status the new online status of current user
 	* @author Jared Leeong
 	*/
	public void setOnline(boolean status){
		isOnline=status;
	}	
	
}
