# cs56-chat-client-server

This is a client-server chat program. The client and server both have a Swing GUI. The code can also be found here:

* [Archive link](https://foo.cs.ucsb.edu/cs56/issues/0000838/)
* [Mantis link](https://foo.cs.ucsb.edu/56mantis/view.php?id=838)
* Note: the JWS seems to have networking problems in CSIL (or maybe Linux in general). Download and run the .jar files to ensure the program functions.

## Basic Usage
The server needs to be started first (including pressing "Start server" on the server GUI window). After starting the server find the IP address of your machine (this address needs to be accessible to the outside world to use the client on another machine). Start the client and input one of the names and passwords from the server window and the IP address of the server then press "Connect to server." Ensure broadcast is selected and type a message into the lower text input box and press enter to send the message.

## Features
* Contact list of people you want to show as online when they are online and be able to talk directly too (not Broadcast)
* Peer2peer-type chatting
* Server can kick users
* Server show's who's online
* Enforced login (user must have correct name and password)
* Sound effects for message received, contact online, and contact offline
* Check box to enable sound effects

## Possible future improvements
* Font/Color Features
	* Add new fonts and colors for the text between users. For example different font types such as serif and sans-serif could be added. 
* Private Chat Rooms
	* Add functionality to allow users to create private chat rooms and invite multiple contacts to join