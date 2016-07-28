package client.model;

/**
* Represents a contact in the client's contact list
* @author Peng Wang, Andro Stotts, Bryce Filler, Max Hinson
* @version 0.4
*/
public class Contact {
    private String name;
    private String nickname;
    
    /**
     * Default constructor
     */
    public Contact(){
	name = "";
	nickname = "";
    }
    
    /**
     * Overloaded constructor that takes the user's name
     * @param name
     */
    public Contact(String name){
	this.name = name;
	
    }
    
    /**
     * Set user's name
     * @param name the user's name
     */
    public void setName(String name){
	this.name = name;
    }
    
    
    /**
     * Get user's name
     * @return user's name
     */
    public String getName(){
	return name;
    }

}
