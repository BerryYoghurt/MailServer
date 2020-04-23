package eg.edu.alexu.csd.datastructure.mailServer;
import java.io.File;
/**
 * creates a new contact instance that can either be added to the
 * database as a new account or added into a user's phonebook 
 * 
 * Preferrably, we should create 2 classes of this interface, one for
 * new account and one for contact*/

public interface IContact {
	/**
	 * @return contact/user folder path
	 */
	public File getPath();
	/**
	 * sets (or adds) the email address of the conact and validates it depending on whether
	 * it's a new account or an existing user
	 * @param address new email address
	 * @return whether the process has been "successfull"*/
	public boolean setAddress(String address);
	/**
	 * @return the address (if an account) or addresses(if a contact)*/
	public String[] getAddresses();
	/**
	 * removes an email address whose order is give as parameter
	 * @param order
	 * @return 
	 */
	boolean removeAddress(int order);
	/**
	 * sets password, only if IConact is instance of Account
	 * @param password the new password
	 * @return confirmation*/
	public boolean setPassword(String password);
	/**
	 * matches the hash of the argument to the hash stored in the database
	 * @param password entered password
	 * @return true if matches, false otherwise*/
	public boolean matchPassword(String password);
	/**
	 * sets the name of the contact
	 * @param name
	 * @return true if success*/
	public boolean setName(String Fname, String Lname);
	/**
	 * @return name*/
	public String getName();
	
	/**
	 * add the new contact to the index file (contacts of another user, receivers, etc)
	 * @param folder
	 * 		in case 
	 * @return 1 iff added and no duplicates, 0 if duplicate, -1 if other error*/
	public int appendIndex(IFolder folder);
	
	/**
	 * @return draft folder*/
	public IFolder getDraftPath();
	/**
	 * @return trash folder*/
	public IFolder getTrashPath();
	/**
	 * @return inbox folder*/
	public IFolder getInboxPath();
	/**
	 * @return sent folder*/
	public IFolder getSentPath();
}
