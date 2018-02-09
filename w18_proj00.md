### Chi X, Shengjia Y





### a.Brief description of the project
This is an online chatroom program with basic with the usage of both server control and client actions including features such as logging in, displaying status and so on. It requires us to fix some bugs and enhance features.

### b.Set of user storie
- As an administrator, I can kick any online user in the chatting room.
- As a user, I can login and connect to server by using my username and passwords.
- As a user, I can see the contact list of people as online when they are online.
- As a user, I can chat with any one on my contact list.
- As an administrator, I can see who is online.
- As a user, I can hear sound effects for message received and contact's online and offline status.
- As a user, I can control whether I want to listen to sound effects or not.

### c.Brief assessment of whether the software runs or not
We have tried current functions of the project. Most of main features are working properly. We can login by using the given combination of users and passwords. We can kick users from server. We also can chat with another person on my contact list. However, there are some bugs on current features. For example, we cannot send messages in the chat room. There are also a lot of new features can be implemented, such as sending emoji, pictures and user registration.

### d.Set of user stories
- As a user, I can register when it is my first time using the chat system.
- As a user, I can send emojis to better express what I mean.

### e.Assessment of the current README.md
I think that the current README.md is a pretty good sample of introducing the project to next generation. It provides all the descriptions of current working functions. However, I think that the reminders for improving features can be more detailed and abundant. They should be added more current problems, for example bugs waiting for fix and current features waiting for enhencement. I think that this is one aspect the README.me does not do well.

### f.Assessment of the current build.xml
The build.xml is based on Ant. It is working well for current project. We may change or revise some parts of the file so that it appropriately runs our new features (such as the use of database).

### g.An assessment of the current “issues”
Definitely, we can earn up to 1000 points by working on this project. They clearly explain what is wrong with current functions and what we need to enhance. Additionally, we also come up with good new ideas for adding new features, especially for database functions. We may discuss with our tutor later about this. Overall, the current issues are good enough for us to refer.

### h. A list of additional issues
Clients log out error. After the client shut down the window rather than log out, SOMETIMES it doesn’t log out the client. When you enter same user name and password, it shows “This user has logged in already”, which means only way to log in again is to ask server to kick the client.
    Clients count error. After the client shut down the window rather than log out, the client count does Not reduces. When you log in the same client again, the client count will increase by 1, where actually there is no more new client being added. 
    The Broadcast function does not work well. Even though there are clients online, the message can not send by broadcast.
    Some combinations of username and password fail to logging in. 

### i.an assessment of the actual code
The source code is well organized, easy to comprehend. It is divided into two main parts: the server and the client. For each part, it includes the controller, which organizes the model and view, and the Junit test.
For the Server part, the controller involves RunServer() to start an instance of ClientWindow(which is the view of the Server), and also involves the main methods like start() close() , kick(user) and registerChatroom (which is the model of the Server)
For the Client part, the controller involves RunClient() to create a new ClientWindow(which is the view of the Client), and also involves the main methods like sendIM(msg), recieveMsg(msg) and sendChatRoomRegistration(reg) (which is the model of the Client)
The purpose of the classes and their methods is clear. Methods of Server is in server.java, and for Client is in client.java. The comments in each .java is precise and concise explaining the functions.  It divides the methods about Chatroom out for Server and Client, making it clear when create an instance of a chatroom and use the methods of the chatroom. The interaction between classes is very clear by how they methods act and the comments. e.g. for the contact class in Client, in client.java it is initialized in constructor and also has addContact methods.  

### g.the test coverage
There are Junit tests covered for the Server, the user and the Client. The test is really basic for the Server and the Client and it’s very poor. There is 1 failure for the Client in 8, and 2 failures and 1 error for the Server in 5. It could include more test for method, but not limited to : test for kickUser, registerChatroom, broadcastMsg,  checkConnection for the Server, and checkSound, addContact, updateContact.

What’s more important, it is better to set up as many as possible user stories or user cases for this program.(10 at least ) And for each user stories or user cases there should be a test for it. In that way, the test can be more valuable for checking the program at running time.

