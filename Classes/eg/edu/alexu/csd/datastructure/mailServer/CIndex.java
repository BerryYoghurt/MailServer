package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import eg.edu.alexu.csd.datastructure.linkedList.Interfaces.ILinkedList;

public class CIndex extends Index{
    
    class Info { // name , directory 
	
	    public String name;
	    public String directory;
	    
	    String infoToString(){
	        return name + "," + directory; 
	    }
	    
	    void stringToInfo (String line){
	        String[] arr = line.split("," , 0);
	        name = arr[0];
	        directory= arr[1];
	    }
	    //deal with queue & contact & date
	}
    
    
	public CIndex(File path) throws FileNotFoundException {
		super(path);
		// TODO Auto-generated constructor stub
	}
	
	public ILinkedList readIndex(){
	    try(Scanner reader = new Scanner(super.getPath())){ 
	        while (reader.hasNextLine()){ 
	            Info item = new Info();
	            item.stringToInfo(reader.nextLine());
	            list.add(item);
	        }
	        return list;
	    } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return null;
	}
	
	public void writeToIndex(){
		try(PrintWriter writer = new PrintWriter(getPath())){
			list.resetNext();
			for(; list.hasNext() ; list.getNext()) {
			    Info item = (Info)list.next();
			    writer.println(item.infoToString());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void add(Object item) {
		if(item == null || !(item instanceof Contact)){
	        throw new RuntimeException();
	    }
        Info i = new Info();
        i.name = ((Contact)item).getName(); // string
        i.directory = ((Contact) item).getPath().getAbsolutePath();
        list.add(i);
		size++;
	}
	
	public Object remove(Object o) {    //remove it from the linked list
		find(o);
		size--;
		return null;
	}


	public Object find(Object o) {     //find it in the linked list
	    // convert it to an info object
		// binary search
		//return the found object or null(if not found)
		return null;
	}

}
