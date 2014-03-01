package edu.ucsb.cs56.W14.ericchen.chatclient.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ucsb.cs56.W14.ericchen.chatclient.model.Contact;

public class ContactTest {

	@Test
	public void testGetName() {
		Contact c = new Contact();
		c.setName("Peng Wang");
		assertEquals("Peng Wang", c.getName());
	}

}
