package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import org.apache.derby.jdbc.EmbeddedDriver;

public class DataBase implements Closeable{
	Connection conn;
	/**
	 * creates database (or loads it if already created)*/
	public DataBase() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");//what if already created??
			File dbFolder = new File(App.systemFile, "users");
			if(!dbFolder.exists()) {
				conn = DriverManager.getConnection("jdbc:derby:users;create=true");
				Statement create = conn.createStatement();
				create.execute("create table Users(address varchar(255), password varchar(255),primary key(address))");
				}
			else {
				conn = DriverManager.getConnection("jdbc:derby:users");
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean userExists(String email) {
		email = email.toLowerCase();
		Statement s;
		try {
			s = conn.createStatement();
			ResultSet set = s.executeQuery("SELECT * FROM Users WHERE address = '"+email+"'");
			if(set.next())//this address exists
			{
				return true;
			}
			set.close();
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * adds user to database*/
	public int add(User user) {
		try {
			if(userExists(user.getAddresses()[0]))
				return 0;
			Statement s = conn.createStatement();
			StringBuilder str = new StringBuilder("insert into Users(");
			str.append("address,"); str.append("password)");
			str.append("values('");
			str.append(user.getAddresses()[0]+"','"); str.append(user.getPassHash()+"')");
			s.executeUpdate(str.toString());
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}

	public void remove(User user) {
		Statement s;
		try {
			MailFolder.cleanDir(user.getPath());
			user.getPath().delete();
			s = conn.createStatement();
			s.executeUpdate("DELETE FROM Users WHERE address = '"+user.getAddresses()[0]+"'");
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public User loadUser(String email) {
		email = email.toLowerCase();
		if(!userExists(email))
		{
			return null;
		}
		Statement s;
		try {
			s = conn.createStatement();
			ResultSet set = s.executeQuery("SELECT * FROM Users WHERE address = '"+email+"'");
			set.next();
			User u = new User(set.getString("address"));
			set.close();
			s.close();
			return u;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void close() throws IOException {
			try {
				conn.close();
				DriverManager.getConnection("jdbc:derby:users;shutdown=true");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
