package edu.ucsb.cs56.W14.ericchen.chatserver.model;

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
	
    /**
     * Constructor that takes username and password
     * @param name
     * @param nickname
     * @param password
     */
    public User(String name, String nickname, String password){
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
	
	
}
