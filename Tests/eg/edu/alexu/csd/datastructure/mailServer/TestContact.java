package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import eg.edu.alexu.csd.datastructure.linkedList.SinglyLinkedList;
import eg.edu.alexu.csd.datastructure.mailServer.Contact;
import eg.edu.alexu.csd.datastructure.mailServer.ContactFolder;
import eg.edu.alexu.csd.datastructure.mailServer.User;

class TestContact {

	@Test
	void test() {
		
		User u = new User("this_is_a_test"); //existing user  "data2023"
		ContactFolder myContacts = (ContactFolder) u.getContactsPath();
		Contact c1 = new Contact("friend","my_friend123@system.com",myContacts);
		c1.getPath().exists();
		assertEquals(new File("system\\this_is_a_test\\contacts\\friend.txt"),c1.getPath());
		
		SinglyLinkedList emails1 = new SinglyLinkedList();
		emails1.add("my_friend123@system.com");
		emails1.add("my_friend@gmail.com");
		emails1.add("my_friend3456@system.com");
		
		c1.addAddress("my_friend@gmail.com");
		c1.addAddress("my_friend3456@system.com");
		c1.writeToFile();
		
		assertEquals(emails1.get(0) , c1.getAddresses().get(0));
		assertEquals(emails1.get(1) , c1.getAddresses().get(1));
		assertEquals(emails1.get(2) , c1.getAddresses().get(2));
		
		c1.addAddress("my_friend@gmail.com");//won't be added
		assertEquals(3,c1.getAddresses().size());
		assertEquals(emails1.get(0) , c1.getAddresses().get(0));
		assertEquals(emails1.get(1) , c1.getAddresses().get(1));
		assertEquals(emails1.get(2) , c1.getAddresses().get(2));
		
		c1.removeAddress(1);
		emails1.remove(1);
		
		c1.removeAddress(1);
		emails1.remove(1);
		c1.writeToFile();
		
		assertEquals(1,c1.getAddresses().size());
		assertEquals(emails1.get(0) , c1.getAddresses().get(0));
		
		assertEquals(null,c1.removeAddress(0));
		
		Contact c2 = new Contact("friend2","aaa111@system.com",myContacts);
		c2.addAddress("ccc333@system.com");
		c2.addAddress("ddd444@system.com");
		c2.writeToFile();
		
		Contact c3 = new Contact("friend3","bbb222@system.com",myContacts);
		c3.addAddress("ccc333@system.com");
		c3.addAddress("ddd444@system.com");
		c3.writeToFile();
		
		myContacts.index.writeToIndex();

	}
	
	@Test
	void test1() {
		User u = new User("this_is_a_test"); //existing user  "data2023"
		ContactFolder myContacts = (ContactFolder) u.getContactsPath();
		
		Contact c1 = new Contact(new File(myContacts.getPath(),"friend.txt"));
		assertEquals("friend",c1.getName());
		SinglyLinkedList emails1 = new SinglyLinkedList();
		emails1.add("my_friend123@system.com");
		assertEquals(emails1.get(0) , c1.getAddresses().get(0));
		
		Contact c2 = new Contact(new File(myContacts.getPath(),"friend2.txt"));
		assertEquals("friend2",c2.getName());
		SinglyLinkedList emails2 = new SinglyLinkedList();
		emails2.add("aaa111@system.com");
		emails2.add("ccc333@system.com");
		emails2.add("ddd444@system.com");
		assertEquals(emails2.get(0) , c2.getAddresses().get(0));
		assertEquals(emails2.get(1) , c2.getAddresses().get(1));
		assertEquals(emails2.get(2) , c2.getAddresses().get(2));
		
		Contact c3 = new Contact(new File(myContacts.getPath(),"friend3.txt"));
		assertEquals("friend3",c3.getName());
		SinglyLinkedList emails3 = new SinglyLinkedList();
		emails3.add("bbb222@system.com");
		emails3.add("ccc333@system.com");
		emails3.add("ddd444@system.com");
		assertEquals(emails3.get(0) , c3.getAddresses().get(0));
		assertEquals(emails3.get(1) , c3.getAddresses().get(1));
		assertEquals(emails3.get(2) , c3.getAddresses().get(2));
		
	}
	
	@Test
	void test2() {
		User u = new User("this_is_a_test"); //existing user  "data2023"
		ContactFolder myContacts = (ContactFolder) u.getContactsPath();
		
		myContacts.remove("friend3");
		myContacts.index.writeToIndex();
		int size = myContacts.getSize();
		assertEquals(2,size);
		
	}
	

}
