package eg.edu.alexu.csd.datastructure.mailServer;

public class CInfo {
	// name , directory 
	
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

}
