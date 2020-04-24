package eg.edu.alexu.csd.datastructure.mailServer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Scanner;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.DLinkedList;
import eg.edu.alexu.csd.datastructure.linkedList.Classes.SLinkedList;
import eg.edu.alexu.csd.datastructure.linkedList.Interfaces.ILinkedList;

public class Index implements IIndex {
    
	
	protected DLinkedList list;
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
		 * @throws FileNotFoundException 
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
    
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
        item.priority = m.getPriority().toString();
        list.add(item);
		size++;
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
        item.directory = ((IMail) o).getDirectory();
        item.priority = ((IMail) o).getPriority().toString();
        Object found = find(o);
		size--;
		return found;
	}


    public int search(Object e) {
		int middle, high, low;
		boolean found = false;
		Stack stack = new Stack();
		stack.push(0);
		stack.push(list.size()-1);
		while(!found) {
			high = stack.pop();
			low = stack.pop();
			if(high < low) {
				stack.push(-1);
				break;
			}
			middle = (high + low)/2;
			if(list.get(middle).equals(e)) {
				stack.push(middle);
				found = true;
			}else if(list.get(middle).compareTo(e) > 0) { //sorted by ??
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
	public Object find(Object o) { //search the linked list by ??
		return null;
	}

	@Override
	public int getSize() {
		return size;
	}
	/**
    *linked list of arrays of size 10
	*divide the main list into arrays(pages)
     */
	@Override
	public ILinkedList setPages(int size) { 
		ILinkedList pages = new SLinkedList();
		for(int i=0 ; i < Math.ceil(list.size()/10.0) ; i++){
		    int begin , end;
		    if(i == 0){
		        begin = 0;
		    }else{
		        begin = ((i)*10)-1;
		    }
		    
		    if(i == Math.ceil(list.size()/10.0)-1){
		        end = list.size()-1;
		    }else{
		        end = ((i+1)*10)-1;
		    }
		   
		    ILinkedList sublist = list.sublist(begin,end);
		    MailInfo[] arr = new MailInfo[sublist.size()];
		    for(int j = 0; j < sublist.size(); j++){
		        arr[j] = (MailInfo)sublist.traverse(null);
		    }
		    pages.add(arr);
		}
		
		return pages;
	}	
	
}
