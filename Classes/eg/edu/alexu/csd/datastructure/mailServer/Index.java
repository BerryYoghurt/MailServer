package eg.edu.alexu.csd.datastructure.mailServer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.SLinkedList;
import eg.edu.alexu.csd.datastructure.linkedList.Interfaces.ILinkedList;

public class Index implements IIndex {
	
	
	private SLinkedList list;
    private File path;
    private int size = 0;
    
    //constructor
    public Index(File path) {
    	//create index file itself and keep its path
    	String IPath = path.getAbsolutePath() + "\\index.txt"; // get position
    	File Index = new File(IPath);
    	this.path  = Index;
        readIndex();
    }
    
    File getPath() {
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
	            Info item = new Info();
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
			Info i;
			for( ; list.hasNext() ; i = (Info)list.getNext()){
				i = (Info)list.next(); 
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
	public void add(IMail mail) {//date , sender , recievers , subject , directory
        Info item = new Info();
        item.date = mail.getDate().toString(); // string
        item.sender = mail.getSenderAddress(); //we need a function in IContact to get the sender email address as a string
        item.receivers = mail.getReceivers().size();
        item.subject = mail.getSubject();
        item.directory = mail.toString();
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
		// TODO Auto-generated method stub
		return size;
	}
	
}

