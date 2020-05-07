package eg.edu.alexu.csd.datastructure.mailServer;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

class TestMailEdit {

	@Test
	void test() {
		App a = new App();
		User u;
		u = new User("ebrontiii");
		assertTrue(a.signin(u.getAddresses()[0], "12345678"));
		
		Mail m = new Mail(a.signedInUser);
		m.saveMail();//IMPORTANT
		JFrame frame = new JFrame();
		frame.add(new EditMail(m,a,frame));
	}

}
