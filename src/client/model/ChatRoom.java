package client.model;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

/**ChatRoom object to represent a ChatRoom to be displayed by the ChatRoomWindow
*@author jleeong
*@version F16
*/
public class ChatRoom{
	private double roomnumber;
	private ArrayList<String> participants;
	
	public ChatRoom(){
		roomnumber = 0;
	}

	public void setRoomNumber(double rn){
		roomnumber = rn;
	}
	
	public void registerParticipant(String p){
		participants.add(p);
	}	
	public ArrayList<String> getParticipants(){
		return participants;
	}
}
