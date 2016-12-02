# cs56-chat-client-server

This is a basic "chatroom" program--a barebones implementation of something similar to IRC, Google Chat, AIM, Yahoo Chat, etc.

The code is divided into MVC (model, view controller) portions. It uses Swing GUIs, Threads, Sockets, and the client has code the illustrates playing sounds.

project history
===============
```
YES | mastergberry | ericchen94 | A chat application that uses a client and a server
 W14 | jcneally 4pm | ericchen94 | A chat application that uses a client and a server
```

## Basic Usage

The server needs to be started first (including pressing "Start server" on the server GUI window).   The following ant target will accomplish that:

```
$ ant server
```

To start a client use: 

```
$ ant client
```

If the client is running on the same machine as the server, you can just use 127.0.0.1 (the standard IP address that means "this same machine", sometimes also called the 'loopback' address) as the IP address.

If you are running the client on a different machine, you need to determine the IP address of the machine you are running the server on.   (This IP address needs to be accessible to the outside world to use the client on another machine--there may be firewall and router issues involved here that are beyond the scope of Java code, or CS56 to resolve.)

Start the client and input one of the names and passwords from the server window and the IP address of the server then press "Connect to server." Ensure broadcast is selected and type a message into the lower text input box and press enter to send the message.

Note that the "Username", not the "nickname" should be entered in the client login panel.

## Features
* Contact list of people you want to show as online when they are online and be able to talk directly too (not Broadcast)
* Peer2peer-type chatting
* Server can kick users
* Server show's who's online
* Enforced login (user must have correct name and password)
* Sound effects for message received, contact online, and contact offline
* Check box to enable sound effects

## Possible future improvements
* Font/Color Features - Add new fonts and colors for the text between users. For example different font types such as serif and sans-serif could be added. 
* Private Chat Rooms - Add functionality to allow users to create private chat rooms and invite multiple contacts to join


## Ancient History 

The first version of this code pre-dates the use of github for CS56 projects.   Here are links to those older versions:

* [Archive link](https://foo.cs.ucsb.edu/cs56/issues/0000838/)
* [Mantis link](https://foo.cs.ucsb.edu/56mantis/view.php?id=838)
* Note: the JWS in the older verisons may not work properly. Download and run the .jar files to ensure the program functions.

##M16 final remarks / future improvements

### Update on what the program does (as of this writing):
*refresh online count using a button
*random font switching with display output
*send a message to non-online person returns a failure to send message error output

###Bug
*As of now, the client can delete the user off the list but the list doesn't update correctly- instead of printing out the names of the updated list, it prints out "contact indexNumber" ex) contact02

###The Big Three

####Refactor code

*For example, go to server.java at line 85 to 122 and refactor the list of contacts provided. Create a new separate contact list for the client also. Check other places that definetely could be refactored.

####Implement Observer Pattern

*Once you refactored code, the observer pattern should be implemented to allow easier code implementation such as adding/deleting contact from a contact list. Use controller class to pass code from model class to view class and back.

####Implement code that can cover future issues

*For example, work on the online user count. The label should be updated, not a the press of the button, but when the user gets online and the label should be refreshed as a result.

###Advice
*Definetely refactor the code before doing anything else.
*Refactor the database in server first.
*Follow the pattern especially the observer pattern.

##F16 Final Remarks

## State of the Program:
* Able to select font color and text of the output
* Able to create private chatroom with select user based on their nickname
* Send and receive messages normally
* Send to offline user with an error displayed to the sender
* Able to delete a contact from the ClientWindow for a specific User and have that change be persistent in the Server

### Bugs
* The Chatroom implementation leaves much to desire. it is very basic and only allows the creator of the chatroom to actually talk in the chatroom. Furthermore there can be improvements to the list of Chatrooms(i.e. names of chatrooms). Furthermore, sending to the chatroom displays a user offline error even if the message goes through.
* The ChangeNickname button is not working due to refactor of online status of users
* Using the delete user button for a chat room does not properly work and may cause errors

### Notes about project
* Currently the server and client communicate with a set of service codes that convey information about the message being sent. This set of codes is somewhat inconsistently implemented throughout the project and must be re-evaluated and documented. See Issue 51
* The Chatroom implementation is very basic and lacks a lot of functionality of a full chat room including: adding users, responding to a group message, removing users, chatroom display name.
* The way the server processes closing windows and a user logging out needs fixing up. Originally the Server depended on a message exception being thrown by a badly formed message when the ClientWindow is closed. Now with the log-out button implemented, there exists a proper service code to tell the Server that the ClientWindow is closing and so closing the ClientWindow should now send a properly formed service code to the Server.
* The best way of understanding the way that both the Server and Client work and interact is to really understand the respective Controller classes. The Controller contains an instance of a Client/Server as well as a Window to display them. It therefore has ways of altering the state of both Client/Server and their respective window. This is the proper place to put methods to change the state in a centralized class. Because of it's composition of Server/Client and Window Objects, the Controller is the place to do message parsing and creating properly formed messages. Server/Client objects only need the relevant information, parsed from the raw message received by the Controller.
