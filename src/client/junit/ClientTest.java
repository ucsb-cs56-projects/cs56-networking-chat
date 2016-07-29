package client.junit;

import static org.junit.Assert.*;
import org.junit.Test;
import client.model.Client;

public class ClientTest {

	/**
	 * Tests the return of getClient() to see if it is an object of the Client class
	 */
	@Test
	public void testGetClient() {
		Client client = Client.getClient();
		assertEquals(Client.class, client.getClass());
	}

	@Test
	public void testGetName() {
		Client client = Client.getClient();
		client.setUserName("Peng Wang");
		assertEquals("Peng Wang", client.getUserName());
	}

        @Test
	public void testgetNickname() {
		Client client = Client.getClient();
		client.setNickname("Peng Wang");
		assertEquals("Peng Wang", client.getNickname());
	}



	@Test
	public void testGetServerIP() {
		Client client = Client.getClient();
		client.setServerIP("127.0.0.1");
		assertEquals("127.0.0.1", client.getServerIP());
	}

	@Test
	public void testGetClientIP() {
		Client client = Client.getClient();
		client.setClientIP("127.0.0.1");
		assertEquals("127.0.0.1", client.getClientIP());
	}

	@Test
	public void testConnectServer() {
		Client client = Client.getClient();
		assertEquals(8, client.connectServer("127.0.0.1", "Peng Wang", "abc123"));
	}
	
	@Test
	public void testParseContactList() {
		Client client = Client.getClient();
		String[] strs = client.parseContactList("Peng Wang:Andro Stotts:Phillp Conrad");
		assertEquals("Broadcast", strs[0]);
		assertEquals("Phillp Conrad", strs[3]);
		
	}

	@Test
	public void testParseReceivingMsg() {
		Client client = Client.getClient();
		String[] strs = client.parseReceivingMsg("Hello World!&1001");
		assertEquals("1001", strs[1]);
		assertEquals("Hello World!", strs[0]);
	}


}
