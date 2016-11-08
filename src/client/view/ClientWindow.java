package client.view;

import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.*;
import javax.swing.JComboBox;
import java.awt.GraphicsEnvironment;
import javax.swing.*;
import java.awt.Dimension;

//Import random library
import java.util.Random;

import client.controller.ClientController;
import client.model.Client;
import client.model.Contact;

/**
 * Represents a JFrame window which has components that are needed for chatting
 * @author Peng Wang, Andro Stotts, Bryce Filler, Max Hinson
 * @version 0.4
 */
public class ClientWindow extends JFrame{

    private static final long serialVersionUID = 1L;
    private JTextField tfInput;
    private JTextArea taOutput;
    private JTextField tfUsername;
    private JTextField tfNickName;
    private JTextField tfServerIp;
    private JPasswordField pfPassword;
    private JScrollPane spScrollPane;
    private JList<String> listContacts;
    private DefaultListModel<String> model;
    private String[] contactList;
    private JLabel lblContact;
    private JLabel lblUserName;
    private JLabel lblNickName;
    private JLabel lblPassword;
    private JLabel lblServerIp;
    private JLabel lblLoginError;
    private JLabel onlineCountNum;
    private JLabel onlineCountText;
    private JCheckBox soundbox;
    private JButton btConnect;
    private JButton btNickname;
    private JButton btChangeFont;
    private JButton onlineCountUpdate;
    private static ClientWindow window;
    private ClientController controller;
    private String name;
    private String nickname;
    private String ip;
    private String password;
    private JFrame nicknameWindow;

    //Pre-determined color to randomly use
    java.awt.Color redColor = new java.awt.Color(255,000,000);
    java.awt.Color greenColor = new java.awt.Color(000,255,000);
    java.awt.Color blueColor = new java.awt.Color(000,000,255);
    String names[] = {"Black", "Blue", "Cyan",  "Gray", "Green", 
		       "Orange", "Pink", "Red", "White", "Yellow"};
	
    Color colors[] = {Color.BLACK, Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW};
    

    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    Font fonts[] = ge.getAllFonts();
   

    /**
     * Default constructor
     */
    private ClientWindow(){
	controller = ClientController.getController();
	tfInput = new JTextField();
	taOutput = new JTextArea();
	spScrollPane = new JScrollPane(taOutput);
	taOutput.setLineWrap(true);
	taOutput.setWrapStyleWord(true);
	taOutput.setForeground(Color.ORANGE);
	taOutput.setFont(new Font("default", Font.PLAIN, 12));
	taOutput.setEditable(false);
	model = new DefaultListModel<String>();
	listContacts = new JList<String>();
	listContacts.setModel(model);
	lblContact = new JLabel("Contacts");
	lblUserName = new JLabel("Username: ");
	lblNickName = new JLabel("New Nickname:");
	lblServerIp = new JLabel("Server IP: ");
	lblPassword = new JLabel("Password: ");
	onlineCountText = new JLabel("Online Count: ");
	onlineCountNum = new JLabel("ERROR");
	onlineCountUpdate = new JButton("Refresh");
	onlineCountNum.setText("");
	lblLoginError = new JLabel("");
	tfUsername = new JTextField(20);
	tfNickName = new JTextField(20);
	tfServerIp = new JTextField(20);
	pfPassword = new JPasswordField(20);
	
	//default connection
	tfServerIp.setText("127.0.0.1");
	soundbox = new JCheckBox("Play Sounds");
	btConnect = new JButton("Connect to Server");
	btNickname = new JButton("Accept");
	name = "";
	password = "";
	ip = "";
    }
	
    /**
     * get the instance of the ClientWindow object
     * @return instance of the ClientWindow 
     */
    public static ClientWindow getWindow(){
	if(window == null){
	    window = new ClientWindow();
	}
	return window;
    }
	
    /**
     * Creates the login window
     */
    public void launchLoginWindow(){
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setLocation(100, 100);
	this.setSize(300, 270);
	this.setTitle("Chatting Client");
	this.setLayout(new FlowLayout());

	this.getContentPane().add(lblUserName);
	this.getContentPane().add(tfUsername);
	this.getContentPane().add(lblPassword);
	this.getContentPane().add(pfPassword);
	this.getContentPane().add(lblServerIp);
	this.getContentPane().add(tfServerIp);
	this.getContentPane().add(lblLoginError);
	this.getContentPane().add(btConnect);
	LoginListener loginListener = new LoginListener();
	btConnect.addActionListener(loginListener);
	tfUsername.addActionListener(loginListener);
	pfPassword.addActionListener(loginListener);
	tfServerIp.addActionListener(loginListener);
	btConnect.setSelected(true);
	this.setVisible(true);
    }
	
    /**
     * Creates the chat window
     */
    public void launchChatWindow(){
	//add window listener for closing window
	//which tells the server to broadcast the message
	//that this client is going offline
	this.addWindowListener(new WindowAdapter(){
		public void windowClosing(WindowEvent we){
		    controller.sendMsg2Server(name + "&Broadcast:1003");
		    System.exit(0);
		}
	    });
	this.getContentPane().removeAll();
	this.setLayout(new BorderLayout());
	this.setSize(800, 400);
	this.setTitle("Chatting Client-" + name);
	JPanel leftPanel = new JPanel();
	JPanel rightPanel = new JPanel();
	JPanel menuPanel = new JPanel();

	menuPanel.add(onlineCountUpdate, BorderLayout.WEST);
	menuPanel.add(onlineCountText, BorderLayout.WEST);
	menuPanel.add(onlineCountNum, BorderLayout.WEST);
	onlineCountUpdate.addActionListener(new MyButtonListener6());
	
	menuPanel.setBorder(BorderFactory.createLineBorder(Color.black));
	menuPanel.setLayout(new FlowLayout());
	menuPanel.add(soundbox, BorderLayout.EAST);
	
	

	
	MyCellRenderer fontCellRender = new MyCellRenderer();
	JComboBox fontList = new JComboBox(fonts);
	fontList.setRenderer(fontCellRender);
	fontList.setPreferredSize(new Dimension(150,25));
	menuPanel.add(fontList,BorderLayout.EAST);

	//*************here i start to add stuff about change color***************
	ColorCellRenderer colorCellRender = new ColorCellRenderer();
	JComboBox colorList = new JComboBox(colors);
	colorList.setRenderer(colorCellRender);
	colorList.setPreferredSize(new Dimension(200,25));
	menuPanel.add(colorList,BorderLayout.EAST);
	

	JButton deleteUser = new JButton("Delete User");
	menuPanel.add(deleteUser,BorderLayout.EAST);
	deleteUser.addActionListener(new MyButtonListener5());
	
	rightPanel.setBorder(BorderFactory.createLineBorder(Color.black));
	rightPanel.setLayout(new BorderLayout());
	rightPanel.add(spScrollPane, BorderLayout.CENTER);
	rightPanel.add(tfInput, BorderLayout.SOUTH);
		
	leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
	leftPanel.setLayout(new BorderLayout());
	leftPanel.add(lblContact, BorderLayout.NORTH);
	leftPanel.add(listContacts, BorderLayout.CENTER);

	JButton nickName = new JButton("Change nickname");
	JButton privateRoom = new JButton("Private Room");
	
	leftPanel.add(nickName, BorderLayout.SOUTH);
	leftPanel.add(privateRoom, BorderLayout.NORTH);
		
	listContacts.setSelectedIndex(0);
		
	this.getContentPane().add(leftPanel, BorderLayout.WEST);
	this.getContentPane().add(rightPanel, BorderLayout.CENTER);
	this.getContentPane().add(menuPanel, BorderLayout.NORTH);
	this.repaint();
	tfInput.addActionListener(new InputListener());
	nickName.addActionListener(new MyButtonListener2());
	privateRoom.addActionListener(new MyButtonListener3());
	fontList.addActionListener(new FontListener());
	colorList.addActionListener(new ColorListener());
	soundbox.addItemListener(new CheckListener());
	soundbox.setSelected(true);
    }

    /**
     * Randomly generates a color
     */
    public void randomColorGenerator(){
	Random rand = new Random();
	int randNumber = rand.nextInt(3);
	if (randNumber == 0)
	    ;
	else if (randNumber == 1)
	    ;
	else if (randNumber == 2)
	    ;
    }

    /**
     * Creates the name change window
     */
    public void launchChangeWindow(){
	//creates the window that appears
	//upon requesting to change user name
	nicknameWindow = new JFrame();
	nicknameWindow.setLocation(100, 100);
	nicknameWindow.setSize(300, 125);
	nicknameWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
	nicknameWindow.setTitle("Set Nickname");
	nicknameWindow.setLayout(new FlowLayout());
	nicknameWindow.getContentPane().add(lblNickName);
	nicknameWindow.getContentPane().add(tfNickName);
	nicknameWindow.getContentPane().add(btNickname);
	nicknameWindow.setVisible(true);
	btNickname.addActionListener(new InputListener2());
	tfNickName.addKeyListener(new InputListener2());
    }

    /**
     * Get the message display component
     * @return the text area which displays the message
     */
    public JTextArea getTaOutput(){
	return taOutput;
    }      


    /**
     * Get the contact list
     * @return the list component of the client window
     */
    public DefaultListModel getContactList(){
    	return model;
    }
    
    /**
     * Handles actions when buttons are clicked
     * @author Bryce Filler and Max Hinson
     * @version 0.4
     */
    class MyButtonListener2 implements ActionListener{
    	private ClientWindow window2 = ClientWindow.getWindow();
	public void actionPerformed(ActionEvent e){
	    
	    window2.launchChangeWindow();

	}
    }

    class MyButtonListener3 implements ActionListener{
        public void actionPerformed(ActionEvent e){

	    

        }
    }

    /**
     * Inner class that formats the list output by JComboBox
     * Only prints name of font that exists in the list instead of entire Font object	
     */

    //helper for Fontlistener
   class MyCellRenderer extends JLabel implements ListCellRenderer<Object> {
     public MyCellRenderer() {
         setOpaque(true);
     }

     public Component getListCellRendererComponent(JList<?> list,
                                                   Object value,
                                                   int index,
                                                   boolean isSelected,
                                                   boolean cellHasFocus) {
	 Font thisfont = (Font) value;
         setText(thisfont.getName());

         Color background;
         Color foreground;

         // check if this cell represents the current DnD drop location
         JList.DropLocation dropLocation = list.getDropLocation();
         if (dropLocation != null
                 && !dropLocation.isInsert()
                 && dropLocation.getIndex() == index) {

             background = Color.BLUE;
             foreground = Color.WHITE;

         // check if this cell is selected
         } else if (isSelected) {
             background = Color.RED;
             foreground = Color.WHITE;

         // unselected, and not the DnD drop location
         } else {
             background = Color.WHITE;
             foreground = Color.BLACK;
         };

         setBackground(background);
         setForeground(foreground);

         return this;
     }
 } 
   /**
    * Listener class that registers when the changeFont JComboBox is pressed
    * @author Xinyuan Zhang and Jared Leeong
   */
   
    
    class FontListener implements ActionListener{
	public void actionPerformed(ActionEvent e){
	    JComboBox ex = (JComboBox)e.getSource();
	    Font getFont = (Font)ex.getSelectedItem();
	    Font newFont = getFont.deriveFont(12F);
	    //newFont.setForeground(Color.Grey);
	    taOutput.setFont(newFont);
	}
    }

<<<<<<< HEAD


    //******************here I start colorlistener*****************************
  class ColorListener implements ActionListener{
	public void actionPerformed(ActionEvent e){
	    JComboBox ex = (JComboBox)e.getSource();
	    Color newColor = (Color)ex.getSelectedItem();
	    taOutput.setForeground(newColor);
=======
    /**
     * Handles actions when Delete user button is clicked
     * @author Winfred Huang, Arturo Milanes, and Jared Leeong
     * @version F16
     */
    class MyButtonListener5 implements ActionListener{
	public void actionPerformed(ActionEvent e){
		int index = listContacts.getSelectedIndex();
	    if(index == 0){
		controller.displayMsg("Can't delete broadcast.\n");
	    }
	    else if(index > 0){
		controller.displayMsg("You tried to delete " + listContacts.getSelectedValue() +".\n");
		controller.sendMsg2Server("&DELETE:"+listContacts.getSelectedValue());
		model.remove(index);
		controller.displayMsg("Successful");
	    }
	    else{
		controller.displayMsg("No contact to delete. Please select one.\n");
	    }
>>>>>>> 43f59077ceb95952ef9ad14a07b33457abe694a8
	}
    }
    
    class ColorCellRenderer extends JLabel implements ListCellRenderer<Object> {
	 
	public ColorCellRenderer() {
	    setOpaque(true);
	}
	  
	public Component getListCellRendererComponent(JList<?> list,
						      Object value,
						      int index,
						      boolean isSelected,
						      boolean cellHasFocus) {
	    Color thiscolor = (Color) value;
	    /*  for(int i = 0;i<10;i++){
		if (thiscolor.toString()= colors[i].toString()){
		    setText(names[i]);
		    break;
		}
		}*/
	     setText(thiscolor.toString());
		Color background;
		Color foreground;
		
		// check if this cell represents the current DnD drop location
		JList.DropLocation dropLocation = list.getDropLocation();
		if (dropLocation != null
		       && !dropLocation.isInsert()
		    && dropLocation.getIndex() == index) {
		    
		    background = Color.RED;
		    foreground = Color.WHITE;
		    
		    // check if this cell is selected
		} else if (isSelected) {
		    background = Color.BLUE;
		       foreground = Color.WHITE;
		       
		       // unselected, and not the DnD drop location
		} else {
		    background = Color.WHITE;
             foreground = Color.BLACK;
		};
		
		setBackground(background);
		setForeground(foreground);
		
		return this;
		
	}
    }
	/**
	 * Handles actions when Delete user button is clicked
	 * @author Winfred Huang and Arturo Milanes
	 */
	class MyButtonListener5 implements ActionListener{
	    public void actionPerformed(ActionEvent e){
		if(listContacts.getSelectedIndex() == 0){
		    controller.displayMsg("Can't delete broadcast.\n");
		}
		else if(listContacts.getSelectedIndex() > 0){
		    controller.displayMsg("You tried to delete " + listContacts.getSelectedValue() +".\n");
		    model.remove(listContacts.getSelectedIndex());
		    listContacts.setModel(model);
		    controller.displayMsg("Successful");
		}
		else{
		    controller.displayMsg("No contact to delete. Please select one.\n");
		}
	    }
	   } 
	    
	    /**
	     * Handles actions when Refresh button is pressed
	     * @author Winfred Huang and Arturo Milanes
	     */
	    class MyButtonListener6 implements ActionListener{
		public void actionPerformed(ActionEvent e){
		    int count = 1;
		    ListModel<String> list = listContacts.getModel();
		    for(int i = 0; i < list.getSize(); i++){
			if(list.getElementAt(i).endsWith("(Online)")){
			    count++;
			}
		    }
		    onlineCountNum.setText(String.valueOf(count));
		}
	    }
	    
	    /**
	     * Handles actions when login button is pressed or when enter is pressed on LoginWindow
	     * @author Peng Wang with Andro Stotts
	     * @version 0.4
	     */		
	    class LoginListener implements ActionListener, KeyListener{
		private ClientWindow window = ClientWindow.getWindow();
		private void attemptLogin(){
		    name = tfUsername.getText();
		    password = new String(pfPassword.getPassword());
		    if(name.isEmpty()||password.isEmpty()){
			tfUsername.setText("");
			lblLoginError.setText("Incorrect Login Credentials");
		    }
		    else{
			ip = tfServerIp.getText();
			int connected = controller.connectServer(ip,name,password);
			if(connected==0){
			    window.launchChatWindow();
			}
			else if(connected==1){
			    tfUsername.setText("");
			    lblLoginError.setText("Wrong username or password");
			}
			else if(connected==2){
			    tfUsername.setText("");
			    lblLoginError.setText("This user has logged in already");
			}
			else{
			    tfServerIp.setText("");
			    lblLoginError.setText("Server unavailable on this IP address");
			}
		    }
		}
		public void actionPerformed(ActionEvent e) {			
		    attemptLogin();
		    window.getContentPane().repaint();
		}
		
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
		    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			attemptLogin();
		    }
		    else{
			lblLoginError.setText("incorrect keypress");
		    }
		}
	    }	
	    
	    /**
	     * Handles checkbox that toggles sound
	     * @author Bryce Filler and Max Hinson
	     * @version 0.4
	     */
	    class CheckListener implements ItemListener {
		
		public CheckListener(){};
		
		public void itemStateChanged(ItemEvent e) {
		    Object source = e.getItemSelectable();
		    
		    if (e.getStateChange() == ItemEvent.DESELECTED)
			controller.setSound(false);
		    else
			controller.setSound(true);
		}
    }	
	    
	    /**
     * Handles actions when name is changed
     * @author Bryce Filler and Max Hinson
     * @version 0.4
     */
	    class InputListener2 implements ActionListener, KeyListener{
		//input listener for name change
		public void actionPerformed(ActionEvent e) {
		    String text = tfNickName.getText().trim();
		    if(!text.isEmpty())
			{
			    tfNickName.setText("");
			    controller.sendMsg2Server(name + "(NAME_CHANGE): " + text + "&" + "NAME_CHANGE" + ":1001");
			    nicknameWindow.dispose();
			}
		}
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
		    int key = e.getKeyCode();
		    if (key == KeyEvent.VK_ENTER) {
			String text = tfNickName.getText().trim();
			if(!text.isEmpty())
			    {
				tfNickName.setText("");
				controller.sendMsg2Server(name + "(NAME_CHANGE): " + text + "&" + "NAME_CHANGE" + ":1001");
				nicknameWindow.dispose();
			    }
		    }
		    
		}
	    }
	    
	    /**
	     * Handles the action when user clicks enter on keyboard
	     * @author Peng Wang with Andro Stotts
	     * @version 0.4
	     */
	    class InputListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
		    if(listContacts.isSelectionEmpty()){
			taOutput.append("***PLEASE SELECT A CONTACT PERSON FIRST, THEN SEND YOUR MESSAGE***\n");
		    }
		    else{
			String text = tfInput.getText().trim();
			tfInput.setText("");
			
			nickname = controller.getNickname();
			
			if(!listContacts.getSelectedValue().equals("Broadcast")) {
			    String parts[] = listContacts.getSelectedValue().toString().split("");
			    String receiverName = "";
			    ListModel<String> list = listContacts.getModel();
		    for (int i = 0; i < listContacts.getSelectedValue().length(); i++) {
			String ch = parts[i];
			if (!ch.equals("(")) {
			    receiverName = receiverName + ch;
			} else
			    break;
		    }
		    
		    controller.sendMsg2Server(nickname + " to " + receiverName + ": " + text + "&" + listContacts.getSelectedValue() + ":1001");
			}
			else
			    controller.sendMsg2Server(nickname + "(Broadcast): " + text + "&" + listContacts.getSelectedValue() + ":1001");
		    }
		}	
	    }
}
