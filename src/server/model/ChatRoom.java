package server.model;

import java.util.Set;

/**ChatRoom class that represents a chatroom containing n Users as participants. It is the responsibility of the calling
*class to maintain a function mapping from a double value to a ChatRoom object. e.g. The class that deploys ChatRoom objects
*must devise a method of keeping track of unique instances of ChatRoom objects. The ChatRoom object provides an instance variable
*'roomNumber' and relevant setter and getter function to assign a double value to each ChatRoom object. It does not, however,
*gaurantee uniqueness between different ChatRoom objects. Two ChatRoom objects without an assigned roomNumber will always be equal
*@author jleeong
*@version F16
*/
public class ChatRoom{
	private double roomNumber;
	private Set<User> participants;
	private boolean isActive;

	/**Primary constructor used to create a ChatRoom object. Initializes the interior Set that keeps track of
	*Users that are participants in this ChatRoom. Calling class must also call .setRoomNumber(double d) to
	*assign room number for room tracking.
	*@param p A set of User objects that represent the specific Users that are members of this room
	*@author jleeong
	*@version F16
	*/	
	public ChatRoom(Set<User> p){
		participants.addAll(p);
		if(participants.size()>0)
			isActive=true;
		else
			isActive=false;
	}
	
	/**Secondary constructor used to verify ChatRoom existence. Typically used in Server.java when needing
	*to verify existence of certain ChatRoom. Use ChatRoom(Set<User> p) for proper construction of ChatRoom
	*@param rn A positive double value that represents the room number.
	*@author jleeong
	*@version F16
	*/
	public ChatRoom(double rn){
		roomNumber=rn;
	}
	/**Returns true if ChatRoom has at least 1 user online, false otherwise
	*@author jleeong
	*@version F16
	*/
	public boolean isActive(){
		return isActive;
	}
	
	/**Sets the roomNumber instance variable identifying this ChatRoom object
	*@param rn A double value that uniquely identifies each ChatRoom object
	*@author jleeong
	*@author F16
	*/
	public void setRoomNumber(double rn){
		roomNumber=rn;
	}

	/** Returns the roomNumber associated with the ChatRoom
	*@author jleeong
	*@version F16
	*/
	public double getRoomNumber(){
		return roomNumber;
	}

	/** Function to access set of Users that are participants of this ChatRoom
	*@author jleeong
	*@version F16
	*/
	public Set<User> getParticipants(){
		return participants;
	}

	/** Overriden equals() inherited from Object. Will consider any other ChatRoom object
	*equal if it has the same room number.
	*@author jleeong
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
