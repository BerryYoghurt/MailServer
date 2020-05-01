package eg.edu.alexu.csd.datastructure.mailServer;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class TestDataBase {

	@Test
	void test() {
		App a = new App();
		User u;
		try {
			u = new User("Sherlock", "Holmes", "30/3/1900", true, "shHolmes", "awesome34");
			App.db.remove(u);
			u = new User("Sherlock", "Holmes", "30/3/1900", true, "shHolmes", "awesome34");
			assertTrue(a.signup(u));
			assertFalse(a.signup(u));
			assertFalse(a.signin("shHolmes", "awesom43"));
			assertFalse(a.signin("kkeklesss", "awesom34"));
			assertTrue(a.signin(u.getAddresses()[0], "awesome34"));
			Mail m = new Mail(u);
			m.setSubject("Hello World");
			m.addReceiver("shHolmes");
			m.saveMail();
			assertTrue(a.compose(m));
			assertFalse(u.getInboxPath().isEmpty());
			assertTrue(u.getDraftPath().isEmpty());
			assertFalse(u.getSentPath().isEmpty());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
