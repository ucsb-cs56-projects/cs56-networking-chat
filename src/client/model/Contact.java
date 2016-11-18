package client.model;

/**
* Represents a contact in the client's contact list
* @author Peng Wang, Andro Stotts, Bryce Filler, Max Hinson
* @version 0.4
*/
public class Contact {
    private String nickname;
    private boolean isOnline;
    /**
     * Overloaded constructor that takes the user's name
     * @param nn The nickname to be associated with this Contact object.
     *@author jleeong
     *@version F16
     */
    public Contact(String nn){
	this.nickname = nn;
	isOnline=false;	
    }
    
    /**
     * Get the nickname associated with this contact
     * @return user's nickname
     * @author jleeong
     * @version F16
     */
    public String getNickname(){
	return nickname;
    }

	/**Get online status of the Contact
	* @return True if this contact is online and connected to the same server. False otherwise
	* @author jleeong
	* @version F16
	*/
	public boolean isOnline(){
		return isOnline;
	}
	
	/**Set online status of the Contact
	* @param status True if Contact is online and connected to the same server. False otherwise
	* @author jleeong
	* @version F16
	*/
	public void setOnline(boolean status){
		isOnline=status;
	}

	/**Overriden equals() inherited from Object.
	* @return True if the nicknames match. False otherwise
	* @author jleeong
	* @version F16
	*/
	@Override
	public boolean equals(Object o){
		if(o==null) return false;
		if(o instanceof Contact){
			Contact c = (Contact) o;
			return nickname.equals(c.getNickname());
		}
		return false;
	}
}
