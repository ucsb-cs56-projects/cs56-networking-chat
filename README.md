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
$ ant runserver
```

To start a client use: 

```
$ ant runclient
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
