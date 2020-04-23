package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Contact implements IContact{
    
    String name;
    SinglyLinkedList emails;
    IFolder contacts;
    File path;
    
    Contact(String name , String email , IFolder contacts){ //new contact
    
        setName(name,"");
        setAddress(email);
        this.contacts = contacts;
        this.path = contacts.add(this);
        
    }
    
    Contact(File path){ //already exists 
        String str = path.getName();
        this.name = str.replace(".txt" , "");
        this.path = path;
        this.emails = getAddresses();
    }

	@Override
	public File getPath() {
		return this.path;
	}

	@Override
	public boolean setAddress(String address) {
	    if(this.emails != null){
	        return false;
	    }
		this.emails = new SinglyLinkedList();
        this.emails.add(address);
		return true;
	}

	@Override
	public SinglyLinkedList getAddresses() {    //linked lists of strings
	    if(this.emails != null && this.emails.size() != 0){
	        return this.emails.copy();            // the copy method returns a copy of the current linked list
	    }
		Scanner reader = new Scanner(this.path);
		SinglyLinkedList adresses = new SinglyLinkedList();
		while (reader.hasNextLine()) {
			adresses.add(reader.nextLine());
		}
		reader.close();
		return adresses;
	}

	@Override
	public boolean removeAddress(int order) { // 0 based
        if(this.emails == null || order < 0 || order >= this.emails.size()){
            throw new RuntimeException();
        }
        else if (this.emails.size() == 1){
            return false;
        }
        else{
            this.emails.remove(order);
            return true;
        }
	}
	
	public void addAddress(String address) {
	   if(this.emails == null){
            throw new RuntimeException();
        }
         this.emails.add(address);
	}

	@Override
	public boolean setPassword(String password) {
		throw new RuntimeException();
	}

	@Override
	public boolean matchPassword(String password) {
		throw new RuntimeException();
	}

	@Override
	public boolean setName(String Fname, String Lname) { // Lname xx   //no special Characters
		if(Fname.length() == 0 || Fname.length() > 50){
		    return false;
        }
        this.name = Fname;
		return true;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int appendIndex(IFolder indexFile) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void writeToFile() throws FileNotFoundException {
	    if(this.emails == null){
            throw new RuntimeException();
        }
		PrintWriter writer = new PrintWriter(this.path);
		for (int i = 0; i < this.emails.size() ; i++) {
			writer.println((String)this.emails.traverse());      // we need the traverse method in SinglyLinkedList ?
		}
		writer.close();
	}

	@Override
	public IFolder getDraftPath() {
	    throw new RuntimeException();
	}

	@Override
	public IFolder getTrashPath() {
		throw new RuntimeException();
	}

	@Override
	public IFolder getInboxPath() {
		throw new RuntimeException();
	}

	@Override
	public IFolder getSentPath() {
		throw new RuntimeException();
	}

}
