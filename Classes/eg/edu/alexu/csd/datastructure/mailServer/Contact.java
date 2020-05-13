package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.SLinkedList;

public class Contact implements IContact{  //remove uncommon methods ?????
    
    String name;
    String[] emails;
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
		if(emails == null) {
			emails = new String[1];
		}
		else {
			for(String e : emails) {
				if(e.equals(address))
					return false; //already exists
			}
			emails = Arrays.copyOfRange(emails, 0, emails.length+1);
		}
		this.emails[emails.length-1] = address;
		if(this.emails.length > 1) {
			writeToFile();
		}
		return true;
	}

	@Override
	public String[] getAddresses() {    //linked lists of strings
	    if(this.emails != null && this.emails.length != 0){
	        return this.emails.clone();            // the copy method returns a copy of the current linked list
	    }
		Scanner reader;
		try {
			reader = new Scanner(this.path);
			SLinkedList addresses = new SLinkedList();
			while (reader.hasNextLine()) {
				addresses.add(reader.nextLine());
			}
			reader.close();
			String[] arr = new String[addresses.size()];
			addresses.resetNext();
			for(int i = 0; i < addresses.size(); i++) {
				arr[i] = (String)addresses.getNext();
			}
			return arr;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String removeAddress(int order) { // 0 based
        if(this.emails == null || order < 0 || order >= this.emails.length){
            throw new ArrayIndexOutOfBoundsException();
        }
        else if (this.emails.length == 1){
            return null;
        }
        else{
        	String[] temp = new String[emails.length-1];
        	int j = 0;
            for(int i = 0; i < emails.length; i++) {
            	if(i == order) {
            		continue;
            	}
            	temp[i] = emails[i];
            }
            emails = temp;
            j++;
            return temp[order];
        }
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
	
	public void writeToFile(){
	    if(this.emails == null){
            throw new RuntimeException();
	    }
		
		try (PrintWriter writer = new PrintWriter(this.path)){
		
			for (String s : emails) {
				writer.println(s);      // we need the traverse method in SinglyLinkedList ?
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
