package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

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
	
	public void readIndex() throws FileNotFoundException {
	    Scanner reader = new Scanner(getPath()); 
        while (reader.hasNextLine()){ 
            Info item = new Info();
            item.stringToInfo(reader.nextLine());
            list.add(item);
        }
        reader.close();
	}
	
	public void writeToIndex() throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(getPath());
		for(int i=0 ; i < list.size() ; i++){
		  Object n = list.traverse(null);
		  if(n!=null){
		    Info item = (Info)n; 
		    writer.println(item.infoToString());
		  }
		}
		writer.close();
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
