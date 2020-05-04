package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import eg.edu.alexu.csd.datastructure.linkedList.SinglyLinkedList;

public class Contact implements IContact{  //remove uncommon methods ?????
    
    String name;
    SinglyLinkedList emails;
    IFolder contacts;
    File path;
    
    public Contact(String name , String email , IFolder contacts){ //new contact
        setName(name,"");
        setAddress(email);
        this.contacts = contacts;
        this.path = new File(contacts.getPath(), name + ".txt");
        contacts.add(this);
    }
    
    public Contact(File path){ //already exists //path of the contact file
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
		Scanner reader;
		try {
			reader = new Scanner(this.path);
			SinglyLinkedList adresses = new SinglyLinkedList();
			while (reader.hasNextLine()) {
				adresses.add(reader.nextLine());
			}
			reader.close();
			return adresses;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String removeAddress(int order) { // 0 based
        if(this.emails == null || order < 0 || order >= this.emails.size()){
            throw new RuntimeException();
        }
        else if (this.emails.size() == 1){
            return null;
        }
        else{
        	String temp = (String)emails.get(order);
            this.emails.remove(order);
            return temp;
        }
	}
	
	public void addAddress(String address) {
	   if(this.emails == null){
            throw new RuntimeException();
        }
	   	if(!this.emails.contains(address))
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
	public boolean setName(String Fname, String Lname) { // Lname xx .. null or ""   //no special Characters
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
	public int appendIndex(IFolder indexFile) { // xxxxxxxxxxxxxxxxxx
		return 0;
	}
	
	public void writeToFile(){
	    if(this.emails == null){
            throw new RuntimeException();
        }
		PrintWriter writer;
		try {
			writer = new PrintWriter(this.path);
			String temp = (String)this.emails.traverse(null);
			while(temp != null) {
				writer.println(temp); 
				temp = (String)this.emails.traverse(null);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		
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

	@Override
	public IFolder getContactsPath() {
		throw new RuntimeException();
	}

}
