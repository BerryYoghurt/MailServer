package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Index implements IIndex {
	
	class Info { //date , sender , recievers , subject , directory 
	
	    String date;
	    String sender;
	    IQueue recievers; // multi reciever 
	    String subject;
	    String directory;
	    
	    String infoToString(){
	        return date + "," + sender + "," + /*recievers+*/  "," + subject + "," + directory ; 
	    }
	    
	    void stringToInfo (String line){
	        String[] arr = line.split("," , 0);
	        date = arr[0];
	        sender = arr[1];
	        //recievers = arr[2];
	        //String[] arr2 = recievers.spilt(" " , 0); 
	        subject = arr[3];
	        directory= arr[4];
	    }
	    //deal with queue & contact & date
	}
	
	private ILinkedList list;
    private File path;
    private int size = 0;
    private ISort sort; //set
    private IFilter filter; // set
    
    //constructor
    public Index(File path) throws FileNotFoundException {
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
	public void add(IMail mail) {//date , sender , recievers , subject , directory
        Info item = new Info();
        item.date = mail.getDate(); // string
        //item.sender = mail.getSender(); //we need a function in IContact to get the sender email address as a string
        //item.recievers = mail.getRecievers();
       //item.subject = mail.getsubject();
        //item.directory = 
		size++;
	}

	@Override
	public Object remove(Object o) {
		// TODO Auto-generated method stub
		size--;
		return null;
	}
    @Override//*****************************************
    public void setSort(ISort s){
        sort = s;
    }

    @Override
    public void setfilter(IFilter f){
        filter = f;
    }

	@Override
	public void sort() { //second
		// TODO Auto-generated method stub
		sort.applySort(list);
	}

	@Override
	public void filter() { //first //applied in setViewing 
		// TODO Auto-generated method stub
		filter.applyFilter(list); 
	}//***************************************************

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

	@Override
	public ILinkedList setPages(int size) { //*************************************************
		ILinkedList pages; //linked list of arrays of size 10
		//divide the main list into arrays(pages)
		return null;
	}

	
	
}
