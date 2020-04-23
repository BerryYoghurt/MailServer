package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.SLinkedList;

public class Contact implements IContact{
    
    String name;
    SLinkedList emails;
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
        getAddresses();
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
		this.emails = new SLinkedList();
        this.emails.add(address);
		return true;
	}

	@Override
	public String[] getAddresses() {    //linked lists of strings
		String[] s;
		if(this.emails == null || this.emails.size() == 0){
			try(Scanner reader = new Scanner(this.path)){
				this.emails = new SLinkedList();
				while (reader.hasNextLine()) {
					this.emails.add(reader.nextLine());
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		s = new String[emails.size()];
		int i = 0;
        for(emails.resetNext(); emails.hasNext(); emails.getNext()) {
        	s[i++] = (String) emails.next();
        }
		return s;
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
	
	public void writeToFile(){
	    if(this.emails == null){
            throw new RuntimeException();
        }
		try(PrintWriter writer = new PrintWriter(this.path)){
			for (this.emails.resetNext(); emails.hasNext() ; ) {
				writer.println((String)this.emails.getNext());      // we need the traverse method in SLinkedList ?
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

}
