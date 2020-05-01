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
	/**
	 * adds user to database*/
	public int add(User user) {
		try {
			Statement s = conn.createStatement();
			ResultSet set = s.executeQuery("SELECT * FROM Users WHERE address = '"+user.getAddresses()[0]+"'");
			if(set.next())//this address exists
			{
				return 0;
			}
			StringBuilder str = new StringBuilder("insert into Users(");
			str.append("address,"); str.append("password)");
			str.append("values('");
			str.append(user.getAddresses()[0]+"','"); str.append(user.getPassHash()+"')");
			s.executeUpdate(str.toString());
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
			s = conn.createStatement();
			s.executeUpdate("DELETE FROM Users WHERE address = '"+user.getAddresses()[0]+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public User loadUser(String email) {
		Statement s;
		try {
			s = conn.createStatement();
			ResultSet set = s.executeQuery("SELECT * FROM Users WHERE address = '"+email+"'");
			if(!set.next())//this address doesnt exist
			{
				return null;
			}
			User u = new User(set.getString("address"));
			return u;
		}catch(SQLException e) {
			
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
