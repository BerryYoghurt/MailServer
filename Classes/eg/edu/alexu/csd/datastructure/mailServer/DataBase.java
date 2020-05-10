package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;
import java.io.IOException;

//import org.apache.derby.jdbc.EmbeddedDriver;

public class DataBase{
		
	public boolean userExists(String email) {
		email = email.toLowerCase();
		File file = new File(App.systemFile, email);
		return file.exists();
	}
	/**
	 * adds user to database*/
	public int add(User user) {
		return 1;
	}

	public void remove(User user) {
		try {
			MailFolder.cleanDir(user.getPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.getPath().delete();
	}

	
	public User loadUser(String email) {
		email = email.toLowerCase();
		if(!userExists(email))
		{
			return null;
		}
		User u = new User(email, false);
		return u;
	}

}
