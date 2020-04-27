package eg.edu.alexu.csd.datastructure.mailServer;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Date;

import org.junit.jupiter.api.Test;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.SLinkedList;
import eg.edu.alexu.csd.datastructure.linkedList.Interfaces.ILinkedList;

class TestMail {
	User u = new User("Jehad", "Aly", "batteekh");
	Mail m = new Mail(u);
	void createEmail() {
		m.setSubject("Hello world");//exclamation!
		m.addReceiver("farawla@gmail.com");
		m.setPriority(Priority.HIGH);
		Attachement a = new Attachement(new File("D:\\Uni\\Term4\\MailServer\\Notes\\folderClassMap.png"));
		m.addAttachement(a);
		m.addReceiver("gazar@yahoo.com");
		m.saveMail();
	}
	@Test
	void testCreation() {
		createEmail();
		Mail n = Mail.loadMail(new File("D:\\Uni\\Term4\\MailServer\\Tests\\eg\\edu\\alexu\\csd\\datastructure\\mailServer\\batteekh\\draft\\"+m.toString()), 2);
		assertEquals(n.getSenderName(), m.getSenderName());
		//assertTrue(n.getDate().equals(m.getDate()));
		assertEquals(n.getPriority(), m.getPriority());
		assertEquals(n.toString(), m.toString());
		assertEquals(n.getSenderAddress(), m.getSenderAddress());
		ILinkedList att = n.getAttachements();
		Attachement a = (Attachement)att.get(0);
		a.view();
	}
	@Test
	void testCreation1Receiver() {
		User u = new User("Jehad", "Aly", "batteekh");
		Mail m = new Mail(u);
		m.setSubject("Hello world");//exclamation!
		m.addReceiver("farawla@gmail.com");
		m.setPriority(Priority.HIGH);
		Attachement a = new Attachement(new File("D:\\Uni\\Term4\\MailServer\\Notes\\folderClassMap.png"));
		m.addAttachement(a);
		m.saveMail();
		Mail n = Mail.loadMail(new File("D:\\Uni\\Term4\\MailServer\\Tests\\eg\\edu\\alexu\\csd\\datastructure\\mailServer\\batteekh\\draft\\"+m.toString()), 1);
		assertEquals(n.getSenderName(), m.getSenderName());
		//assertTrue(n.getDate().equals(m.getDate()));
		assertEquals(n.getPriority(), m.getPriority());
		assertEquals(n.toString(), m.toString());
		assertEquals(n.getSenderAddress(), m.getSenderAddress());
		SLinkedList att = (SLinkedList) n.getAttachements();
		a = (Attachement)att.get(0);
		a.view();
	}
	
	@Test
	void testSendInternally() {
		
	}

}
