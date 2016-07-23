package client.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import client.model.Contact;

public class ContactTest {

	@Test
	public void testGetName() {
		Contact c = new Contact();
		c.setName("Peng Wang");
		assertEquals("Peng Wang", c.getName());
	}

}
