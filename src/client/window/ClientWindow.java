
package client.view;

import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.*;

import javax.swing.*;

//Import random library
import java.util.Random;

import client.controller.ClientController;

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
    //private DefaultListModel<String> model; //NEW
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

    //delete user
    private JButton btDeleteUser;

   

    //Pre-determined color to randomly use
    java.awt.Color redColor = new java.awt.Color(255,000,000);
    java.awt.Color greenColor = new java.awt.Color(000,255,000);
    java.awt.Color blueColor = new java.awt.Color(000,000,255);
    Color[] colors = { Color.red, Color.blue, Color.cyan,
            Color.green, Color.gray, new Color(0xFFAA00) };
    Random Random = new Random();
    int x = Random.nextInt(colors.length);
    //Pre-determined font to randomly use
    private Font font0 = new Font("Arial", Font.ITALIC, 13);
    private Font font1 = new Font("Arial", Font.BOLD, 14);
    private Font font2 = new Font("Arial Bold", Font.PLAIN, 15);
    private Font font3 = new Font("Arial Bold", Font.ROMAN_BASELINE, 20);
    Font fonts[] = {font0, font1, font2, font3};
    Random random = new Random();
    int y = random.nextInt(fonts.length);
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
	taOutput.setForeground(colors[x]);
	taOutput.setFont(fonts[y]);
	taOutput.setEditable(false);
	//model = new DefaultListModel<String>(controller.getContacts()); //NEW
	listContacts = new JList<String>(controller.getContacts());
	//listContacts = new JList<String>(model); //NEW 
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
	btConnect.addActionListener(new MyButtonListener());
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
	this.setSize(600, 400);
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
	
	JButton changeFont = new JButton("Change Font");
	menuPanel.add(changeFont,BorderLayout.EAST);

	//delete user
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
	changeFont.addActionListener(new MyButtonListener4());
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

    /*
     * append method for JTextPane, includes font and color

    public void appendToPane(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Arial");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
*/


    /*
    this.text_panel = taOutput;
    this.text_panel.setContentType("text/html");
    this.text_panel.setEditable(false);
    this.text_panel.setBackground(this.text_background_color);
    this.text_panel_html_kit = new HTMLEditorKit();
    this.text_panel.setEditorKit(text_panel_html_kit);
    this.text_panel.setDocument(new HTMLDocument());


    // Appending a string using HTML
    public void append(String line){
	SimpleDateFormat date_format = new SimpleDateFormat("HH:mm:ss");
	Date date = new Date();
	
	line = "<div><font size=3 color=GRAY>[" + date_format.format(date) + "]</font><font size=3 color=BLACK>"+ line + "</font></div>";
	
	try {
	    this.text_panel_html_kit.insertHTML((HTMLDocument) this.text_panel.getDocument(), this.text_panel.getDocument().getLength(), line, 0, 0, null);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    */
    

    /*
     * adding a style for JTextPane
     
    public void {

	StyledContext doc = textPane.getStyledDocument();

        Style style = textPane.addStyle("I'm a Style", null);
        StyleConstants.setForeground(style, Color.red);

        try { 
	    doc.insertString(doc.getLength(), "BLAH ",style);
	}
        catch (BadLocationException e){}
	
        StyleConstants.setForeground(style, Color.blue);
	
        try {
	    doc.insertString(doc.getLength(), "BLEH",style);
	}
        catch (BadLocationException e){}
    }
    */


    /**
     * Get the contact list
     * @return the list component of the client window
     */
    public JList getContactList(){
	return listContacts;
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
     *Handles actions when Change Font button is clicked
     *@author Winfred Huang and Arturo Milanes
     */ 
    class MyButtonListener4 implements ActionListener{
	public void actionPerformed(ActionEvent e){
	    Random random = new Random();
	    int x = random.nextInt(fonts.length);
	    taOutput.setFont(fonts[x]);
	    String fontName = fonts[x].getName();
	    int size = fonts[x].getSize();
	    controller.displayMsg("You have changed the font to " + fontName + ", size: " + size + '\n');
	}
    }

    //delete contact in client's contact list
    class MyButtonListener5 implements ActionListener{
	public void actionPerformed(ActionEvent e){
	    /*
	    final DefaultListModel<String> model = new DefaultListModel<String>();
	    for(int i = 0; i < listContacts.getSize(); i++){
		model.addElement(

	    int index = listContacts.getSelectedIndex();
	    if(index == 0)
		controller.displayMsg("You can't delete broadcast");
	    else{
		model.remove(index);
	    }
	    if(index == model.getSize())
		index--;
	    listContacts.setSelectedIndex(index);
	    listContacts.ensureIndexIsVisible(index);
	    */

	    //int index = listContacts.getSelectedIndex();
	    //if(index != -1)
	    //((DefaultListModel)listContacts.getModel).remove(listContacts.getSelectedIndex());

	    /*
	    DefaultListModel<String> list = new DefaultListModel<>();
	    for (String val : controller.getContacts())
		list.addElement(val);
	    if (list.getSize()>0)
		list.removeElementAt(0);
	    */
	    
	    //DefaultListModel<String> list = (DefaultListModel<String>)listContacts.getModel();
	    ListModel<String> list = listContacts.getModel();

	    //int contact = listContacts.getSelectedIndex();
	    
	    //if(contact != -1)
	    //list.removeElementAt(contact);

	    
	    String contact = listContacts.getSelectedValue();
	    int j = -1;
	    for(int i = list.getSize()-1; i >= 0; i--){
		if(list.getElementAt(i).equals("Broadcast")){
		    controller.displayMsg("[ERROR] You cannot delete broadcast\n");
		}
		else if(list.getElementAt(i).equals(contact)){
		    j = i;
		}
	    }
	    if(j != -1){
		//list.remove(j);
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
     * Handles actions when buttons are clicked
     * @author Peng Wang with Andro Stotts
     * @version 0.4
     */		
    class MyButtonListener implements ActionListener{
	private ClientWindow window = ClientWindow.getWindow();
	public void actionPerformed(ActionEvent e) {			
	    name = tfUsername.getText();
	    password = pfPassword.getText();
	    boolean tester = false;
	    if (name.isEmpty()) 
		tester = true;
	    else 
		tester = false;
	    boolean tester2 = false;
	    if (password.isEmpty()) 
		tester2 = true;
	    else 
		tester2 = false;
	
	    
	
	    password = new String(pfPassword.getPassword());
	    ip = tfServerIp.getText();
			
	    int result; 
	    if (tester && !tester2) 
		result = 3;

	    else if (!tester && tester2)
		result = 4;

	    else if (tester && tester2)
		result = 5;

	    else 
		result = controller.connectServer(ip, name, password);
	    
	    if(result == 0)
		window.launchChatWindow();
			
	    else{
		
		pfPassword.setText("");
		lblLoginError.setForeground(Color.RED);
		if(result == 1){
		    tfUsername.setText("");	
		    lblLoginError.setText("Wrong username or password");
		}
		else if(result == 2){
		    tfUsername.setText("");	
		    lblLoginError.setText("This user has logged in already");
		}
	        else if(result == 3){
		    tfUsername.setText("");
		    lblLoginError.setText("No name was entered");
		}
		else if(result == 4){
		    tfUsername.setText("");
		    lblLoginError.setText("No password entered");
		}
		else if(result == 5){
		    tfUsername.setText("");
		    lblLoginError.setText("Please enter login information");
		}
		else if(result == 9){
		    tfUsername.setText("");	
		    lblLoginError.setText("Server is not available");
		}
		else{
		    tfServerIp.setText("");
		    lblLoginError.setText("Server unavailable on this IP address");
		}
	      }
		window.getContentPane().repaint();
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
		taOutput.append("***PLEASE SELECT A CONTACT PERSON FIRST, THEN SENT YOUR MESSAGE***\n");
	    }
	    else{
		String text = tfInput.getText().trim();
		tfInput.setText("");

		nickname = controller.getNickname();



		if(!listContacts.getSelectedValue().equals("Broadcast")) {
		    String parts[] = listContacts.getSelectedValue().toString().split("");
		    String receiverName = "";
		    ListModel<String> list  = listContacts.getModel();
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

