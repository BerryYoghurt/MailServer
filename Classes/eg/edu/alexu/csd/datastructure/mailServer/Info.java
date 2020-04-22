package eg.edu.alexu.csd.datastructure.mailServer;

public class Info {
	public String date;
    public String sender;
    public int receivers = 0; // multi reciever 
    public String subject;
    public String directory;
    
    String infoToString(){
        return date + "," + sender + "," + receivers+  "," + subject + "," + directory ; 
    }
    
    void stringToInfo (String line){
        String[] arr = line.split("," , 0);
        date = arr[0];
        sender = arr[1];
        char[] number = arr[2].toCharArray();
        for(char digit :number) {
        	receivers += Character.digit(digit,10);
        	receivers*=10;
        }
        receivers /= 10;
        //String[] arr2 = recievers.spilt(" " , 0); 
        subject = arr[3];
        directory= arr[4];
    }
    //deal with queue & contact & date
}
