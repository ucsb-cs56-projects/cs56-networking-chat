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
