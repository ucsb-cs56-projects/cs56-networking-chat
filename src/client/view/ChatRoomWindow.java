package client.view;

import javax.swing.*;
import java.awt.*;
import client.model.ChatRoom;
/**ChatRoomWindow object to display the TextArea provided by a ChatRoom object.
*@author jleeong
*@version F16
*/
public class ChatRoomWindow extends JFrame{
	private JTextArea roomDisplay;
	private JTextField tfInput;
	private JList<String> participantsDisplay;
	private DefaultListModel<String> participantList = new DefaultListModel<String>();
	private JScrollPane centerPane;
	
	public ChatRoomWindow(ChatRoom room){
		roomDisplay = room.getTextArea();
		tfInput = new JTextField();
		centerPane = new JScrollPane(roomDisplay);
		roomDisplay.setLineWrap(true);
		roomDisplay.setWrapStyleWord(true);
		roomDisplay.setEditable(false);
		for(String p : room.getParticipants()){
			participantList.addElement(p);
		}
		participantsDisplay = new JList(participantList);;
	}

	
}	
