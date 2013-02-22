package edu.ucsb.cs56.S12.maxhinson.chatserver.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ucsb.cs56.S12.maxhinson.chatserver.model.Server;

public class ServerTest {

	
	@Test
	public void testGetServer() {
		Server server = Server.getServer();
		assertEquals(Server.class, server.getClass());
	}

	@Test
	public void testStart() {
		Server server = Server.getServer();
		server.start();
		assertEquals("------------------------------------------SERVER USAGE-----------------------------------------------\n" +
					 "             We currently have a FAKE database with only the users listed below:     \n" +
					 "                     Username: Peng Wang        Nickname: peng     Password: 123abc      \n" +
					 "                     Username: Andro Stotts     Nickname: andro    Password: abc123      \n" +
					 "                     Username: Phillip Conrad   Nickname: phill    Password: 9876543     \n" +
					 "                     Username: Steve Jobs       Nickname: steven   Password: 2as134      \n" +
				 	 "                     Username: Bill Gates       Nickname: billy    Password: idu?e3e     \n" +
				 	 "                     Username: Orange Juice     Nickname: oj       Password: orangejuice \n" +
			                 "                     Username: Max Hinson       Nickname: max      Password: password    \n" +
                                         "                     Username: Bryce Filler     Nickname: bry      Password: bryce       \n" +
				 	 "-----------------------------------------------------------------------------------------------------\n" +
				 	 "      NOTE: PLEASE USE ABOVE ACCOUNTS TO LOGIN OUR CLIENT APPLICATION FOR TESTING PURPOSES           \n" +
				 	 "-----------------------------------------------------------------------------------------------------\n" +
				 	 "[Server Message] " + "Server has been started and waiting for client connections\n", server.getServerMsg());
	}

	@Test
	public void testClose() {
		Server server = Server.getServer();
		server.close();
		assertEquals("[Server Message] " + "Server shut down successfully\n", server.getServerMsg());
	}

	@Test
	public void testGetServerStatus() {
		Server server = Server.getServer();
		assertEquals(false, server.getServerStatus());
	}

	@Test
	public void testGetServerMsg() {
		Server server = Server.getServer();
		assertEquals("connection failed\n", server.getServerMsg());
	}

}
