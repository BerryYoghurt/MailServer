package eg.edu.alexu.csd.datastructure.mailServer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.SLinkedList;
import eg.edu.alexu.csd.datastructure.linkedList.Interfaces.ILinkedList;

public class Index implements IIndex {
	
	
	protected SLinkedList list;
    private File path;
    protected int size = 0;
    
    //constructor
    public Index(File path){
    	//create index file itself and keep its path
    	File index = new File(path, "index.txt");
    	try {
			index.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	this.path  = index;
        readIndex();
    }
    @Override
    public File getPath() {
    	return this.path;
    }
		/**open File
		*read line by line
		* item = call stringToInfo
		*list.add(item);
		 * @return 
		*/
	@Override
	public ILinkedList readIndex() {
		
		try(Scanner reader = new Scanner(path);) {
			while (reader.hasNextLine()){ 
	            MailInfo item = new MailInfo();
	            item.stringToInfo(reader.nextLine());
	            list.add(item);
	        }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list; 
	}

    /**
     * write line by line 
     */
	@Override
	public void writeToIndex() { // test traverse function 
        
		try(PrintWriter writer = new PrintWriter(this.path)){
			list.resetNext();
			MailInfo i;
			for( ; list.hasNext() ; i = (MailInfo)list.getNext()){
				i = (MailInfo)list.next(); 
			    writer.println(i.infoToString());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
    /**
     * we know that object is an email 
     */
	@Override
	public void add(Object mail) {//date , sender , recievers , subject , directory
		if(mail == null || !(mail instanceof IMail)){
	        throw new RuntimeException();
	    }
		Mail m = (Mail)mail;
		MailInfo item = new MailInfo();
        item.date = m.getDate().toString(); // string
        item.sender = m.getSenderAddress(); //we need a function in IContact to get the sender email address as a string
        item.receivers = m.getReceivers().size();
        item.subject = m.getSubject();
        item.directory = m.toString();
		size++;
	}

	@Override
	public Object remove(Object o) {//jehad
		// TODO Auto-generated method stub
		size--;
		return null;
	}
   
	@Override
	public Object find(Object o) { 
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSize() {
		return size;
	}
	
}    
