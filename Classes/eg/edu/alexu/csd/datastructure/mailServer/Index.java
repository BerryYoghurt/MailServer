package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.DLinkedList;
import eg.edu.alexu.csd.datastructure.linkedList.Interfaces.ILinkedList;
import eg.edu.alexu.csd.datastructure.stack.Stack;

public class Index implements IIndex {
	
	protected DLinkedList list;
    private File path;
    private MailInfo currentLine;
    //constructor
    public Index(File path, boolean isNew){
    	//create index file itself and keep its path
    	list = new DLinkedList();
    	File index = new File(path, "index.txt");
    	try {
    		if(isNew){
        	    index.createNewFile();
        	    this.path  = index;
        	}else{ 
        	    this.path  = index;
                readIndex();
        	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    @Override
    public File getPath() {
    	return this.path;
    }
		/**open File
		*read line by line
		* item = call stringToInfo
		*list.add(item);
		 * @throws FileNotFoundException 
		*/
    @Override
	public ILinkedList readIndex() {
		list = new DLinkedList();
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
     * @throws FileNotFoundException 
     */
    @Override
	public void writeToIndex() { // test traverse function 
        
		try(PrintWriter writer = new PrintWriter(this.path)){
			MailInfo i;
			for( Object o : list){
				i = (MailInfo)o; 
			    writer.println(i.infoToString());
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
	} 
    
	@Override
	public void add(Object mail) {//date , sender , recievers , subject , directory
		if(mail == null || !(mail instanceof IMail)){
	        throw new RuntimeException();
	    }
		Mail m = (Mail)mail;
		currentLine = new MailInfo();
		currentLine.date = m.getDate().toString(); // string
		currentLine.sender = m.getSenderAddress(); //we need a function in IContact to get the sender email address as a string
		currentLine.receivers = m.getReceivers().size();
		currentLine.subject = m.getSubject();
		currentLine.directory = m.getIdentifier();
		currentLine.priority = m.getPriority().toString();
        KeySort k = new KeySort();
        k.applySort(this.list);
        int ind = (int)find(m);
        if(ind != -1) {
			list.remove(ind);
			list.add(ind, currentLine);
		}else
			list.add(currentLine);
	}

	@Override
	public Object remove(Object o) {
	    if(o == null || !(o instanceof IMail)){
	        throw new RuntimeException();
	    }
        MailInfo item = new MailInfo();
        item.date = ((IMail) o).getDate().toString();
        item.sender = ((IMail) o).getSenderAddress();
        item.receivers = ((IMail) o).getReceivers().size();
        item.subject = ((IMail) o).getSubject();
        item.directory = ((IMail) o).getIdentifier();
        item.priority = ((IMail) o).getPriority().toString();
        Integer foundIndex = (Integer)find(o);
        list.remove(foundIndex);
        //writeToIndex();
		return foundIndex;
	}


	@Override
	public Object find(Object o) { //search the linked list by ??
		int middle, high, low;
		boolean found = false;
		Stack stack = new Stack();
		stack.push(0);
		stack.push(list.size()-1);
		while(!found) {
			high = (Integer)stack.pop();
			low = (Integer)stack.pop();
			if(high < low) {
				stack.push(-1);
				break;
			}
			
			middle = (high + low)/2;
			MailInfo i =(MailInfo)list.get(middle);
			if(i.directory.equals(((Mail)o).getIdentifier())) {
				stack.push(middle);
				found = true;
			}else if(i.directory.compareTo(((Mail)o).getIdentifier()) > 0) { //sorted by ??
				stack.push(low);
				stack.push(middle-1);
			}else {
				stack.push(middle+1);
				stack.push(high);
			}
		}
		return stack.pop();
	}

	@Override
	public int getSize() {
		return list.size();
	}


	public void setInTrash() {
		currentLine.inTrash = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
	}
}