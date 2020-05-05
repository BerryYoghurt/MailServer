package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import eg.edu.alexu.csd.datastructure.mailServer.User;

class TestUser {

	@Test
	void test() {
		User u1 = new User("Manar","Amr","06-25-2000",false,"dataStructure2023","123456789");
		assertEquals("female",u1.getGender());
		assertEquals("06-25-2000",u1.getBirthDate());
		assertEquals("dataStructure2023@system.com",u1.getAddresses().getFirst());
		assertTrue(u1.matchPassword("123456789"));
		assertEquals("Manar Amr",u1.getName());
		assertEquals(new File("system\\dataStructure2023\\inbox"),u1.getInboxPath().getPath());
		assertEquals(new File("system\\dataStructure2023\\sent"),u1.getSentPath().getPath());
		assertEquals(new File("system\\dataStructure2023\\trash"),u1.getTrashPath().getPath());
		assertEquals(new File("system\\dataStructure2023\\draft"),u1.getDraftPath().getPath());
		assertEquals(new File("system\\dataStructure2023\\contacts"),u1.getContactsPath().getPath());
		
	}
	
	@Test
	void testExisting() {
		User u1 = new User("Manar","Amr","06-25-2000",false,"ABCabc_2023","123456789");
		User u2 = new User("ABCabc_2023");
		assertEquals("female",u2.getGender());
		assertEquals("06-25-2000",u2.getBirthDate());
		assertEquals("ABCabc_2023@system.com",u2.getAddresses().getFirst());
		assertTrue(u2.matchPassword("123456789"));
		assertEquals("Manar Amr",u2.getName());
		assertEquals(new File("system\\ABCabc_2023\\inbox"),u1.getInboxPath().getPath());
		assertEquals(new File("system\\ABCabc_2023\\sent"),u1.getSentPath().getPath());
		assertEquals(new File("system\\ABCabc_2023\\trash"),u1.getTrashPath().getPath());
		assertEquals(new File("system\\ABCabc_2023\\draft"),u1.getDraftPath().getPath());
		assertEquals(new File("system\\ABCabc_2023\\contacts"),u1.getContactsPath().getPath());
	}

}
