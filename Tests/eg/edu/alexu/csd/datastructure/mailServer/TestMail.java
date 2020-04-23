package eg.edu.alexu.csd.datastructure.mailServer;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Date;

import org.junit.jupiter.api.Test;

class TestMail {

	@Test
	void testCreation() {
		User u = new User("Jehad", "Aly", "batteekh");
		Mail m = new Mail(u);
		m.setSubject("Hello world");//exclamation!
		m.addReceiver("farawla@gmail.com");
		m.setPriority(Priority.HIGH);
		Attachement a = new Attachement(new File("D:\\Uni\\Term4\\MailServer\\Notes\\folderClassMap.png"));
		m.addAttachement(a);
		m.addReceiver("gazar@yahoo.com");
		m.saveMail();
		Mail n = Mail.loadMail(new File("D:\\Uni\\Term4\\MailServer\\Tests\\eg\\edu\\alexu\\csd\\datastructure\\mailServer\\batteekh\\draft\\"+m.toString()), 1);
		assertEquals(n.getSenderName(), m.getSenderName());
		//assertTrue(n.getDate().equals(m.getDate()));
		assertEquals(n.getPriority(), m.getPriority());
		assertEquals(n.toString(), m.toString());
		assertEquals(n.getSenderAddress(), m.getSenderAddress());
	}
	
	@Test
	void testSendInternally() {
		
	}

}
