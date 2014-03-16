package edu.ucsb.cs56.projects.networking.chat.chatserver.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import edu.ucsb.cs56.projects.networking.chat.chatserver.model.User;

public class UserTest {

    User u = new User("Tester", "test", "123456");
	
	
    @Test
	public void testGetContactList() {
	u.addToContactList(new User("Peng Wang", "peng", "abc123"));
	u.addToContactList(new User("Andro Stotts", "andro", "123abc"));
	ArrayList<User> list = u.getContactList();
	assertEquals(2, list.size());
    }

    @Test
	public void testGetName() {
	assertEquals("Tester", u.getName());
    }

  @Test
	public void testGetNickname() {
	assertEquals("test", u.getNickname());
    }

    @Test
	public void testGetPassword() {
	assertEquals("123456", u.getPassword());
    }
}
