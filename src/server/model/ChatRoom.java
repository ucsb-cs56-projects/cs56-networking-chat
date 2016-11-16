package server.model;

import java.util.Set;

/**ChatRoom class that represents a chatroom containing n Users as participants
*@author Jared Leeong
*@version F16
*/
public class ChatRoom{
	private double roomNumber;
	private Set<User> participants;
	private boolean isActive;
	
	public ChatRoom(Set<User> p){
		participants.addAll(p);
		if(participants.size()>0)
			isActive=true;
		else
			isActive=false;
	}
	
	/**Returns true if ChatRoom has at least 1 user online, false otherwise
	*@author Jared Leeong
	*@version F16
	*/
	public boolean isActive(){
		return isActive;
	}
	
	/** Returns the roomNumber associated with the ChatRoom
	*@author Jared Leeong
	*@version F16
	*/
	public double getRoomNumber(){
		return roomNumber;
	}

	/** Overriden equals() inherited from Object. Will consider any other ChatRoom object
	*equal if it has the same room number.
	*@author Jared Leeong
	*@version F16
	*/
	@Override
	public boolean equals(Object o){
		if(o==null) return false;
		if(o instanceof ChatRoom){
			ChatRoom cr = (ChatRoom) o;
			return this.getRoomNumber()==cr.getRoomNumber();
		}
		else return false;
	}
}