package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class Index implements IIndex {
    	
	protected ILinkedList list;
    private File path;
    protected int size = 0;
    private boolean isNew;
    //constructor
    public Index(File path , boolean isNew) throws IOException { //both cases >> path of the mail folder
    	//create index file itself and keep its path
    	String iPath = path.getAbsolutePath() + "\\index.txt"; // get position
    	File index = new File(iPath);
    	if(isNew){
    	    this.isNew = true;
    	    index.createNewFile();
    	    this.path  = index;
           
    	}else{
    	    this.isNew = false; 
    	    this.path  = index;
            readIndex();
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
	public void readIndex() throws FileNotFoundException {
		Scanner reader = new Scanner(path); 
        while (reader.hasNextLine()){ 
            Info item = new Info();
            item.stringToInfo(reader.nextLine());
            list.add(item);
        }
        reader.close();
	}

    /**
     * write line by line 
     * @throws FileNotFoundException 
     */
	@Override
	public void writeToIndex() throws FileNotFoundException { // test traverse function 
        
		PrintWriter writer = new PrintWriter(this.path);
		for(int i=0 ; i < list.size() ; i++){
		  Object n = list.traverse(null);
		  if(n!=null){
		    Info item = (Info)n; 
		    writer.println(item.infoToString());
		  }
		}
		writer.close();
	} 
    /**
     * we know that object is an email 
     */
	@Override
	public void add(Object item) {//date , sender , recievers , subject , directory
	    if(item == null || !(item instanceof IMail)){
	        throw new RuntimeException();
	    }
        Info item2 = new Info();
        Date mailDate = ((IMail) item).getDate();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
        item2.date = formatter.format(mailDate).toString();
        item2.sender = ((IMail) item).getSender();
        item2.recievers = ((IMail) item).getRecievers();
        item2.subject = ((IMail) item).getSubject();
        item2.directory = ((IMail) item).getDirectory();
        item2.prioriy = ((IMail) item).getPrioriy().name();
        Date trashDate = new Date();
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
        item2.inTrash = formatter1.format(trashDate).toString();
        list.add(item2);
		size++;
	}
	@Override
	public Object remove(Object o) {
	    if(o == null || !(o instanceof IMail)){
	        throw new RuntimeException();
	    }
        Info item = new Info();
        item.date = ((IMail) o).getDate().toString();
        item.sender = ((IMail) o).getSender();
        item.recievers = ((IMail) o).getRecievers();
        item.subject = ((IMail) o).getSubject();
        item.directory = ((IMail) o).getDirectory();
        item.prioriy = ((IMail) o).getPrioriy().name();
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
			high = (int) stack.pop();
			low = (int) stack.pop();
			if(high < low) {
				stack.push(-1);
				break;
			}
			middle = (high + low)/2;
			if(list.get(middle).equals(e)) {
				stack.push(middle);
				found = true;
			}else if(((String) list.get(middle)).compareTo((String) e) > 0) { //sorted by ??
				stack.push(low);
				stack.push(middle-1);
			}else {
				stack.push(middle+1);
				stack.push(high);
			}
		}
		return (int) stack.pop();
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
		    Info[] arr = new Info[sublist.size()];
		    for(int j = 0; j < sublist.size(); j++){
		        arr[j] = (Info)sublist.traverse(null);
		    }
		    pages.add(arr);
		}
		
		return pages;
	}
	
	
	
}
