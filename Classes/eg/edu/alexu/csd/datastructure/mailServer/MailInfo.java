package eg.edu.alexu.csd.datastructure.mailServer;

import eg.edu.alexu.csd.datastructure.queue.IQueue;

public class MailInfo {
	public String date;
    public String sender;
    public int receivers = 0; // multi reciever 
    public String subject;
    public String directory;
    public String priority;
    public String inTrash;
    
    public String receiversToString(IQueue receivers) {
    	if(receivers == null || receivers.size() == 0){
	        throw new RuntimeException();
	    }
	    String r = "";
	    for(int i = 0; i < receivers.size() ; i++){
	        r = receivers.dequeue() + " ";
	    }
	    r = r.trim();
	    return r;
    }
    String infoToString(){
        return date + "," + sender + "," + receivers+  "," + subject + "," + directory + "," + priority + ","+ inTrash; 
    }
    
    void stringToInfo (String line){
        String[] arr = line.split("," , 0);
        date = arr[0];
        sender = arr[1];
        receivers = Integer.parseInt(arr[2]);
        //String[] arr2 = recievers.spilt(" " , 0); 
        subject = arr[3];
        directory= arr[4];
        priority = arr[5];
        inTrash = arr[6];
    }
    //deal with queue & contact & date
    @Override
    public String toString() {
		return "priority: " + this.priority + " | " + this.date + " | " + "subject: " + this.subject + " | " +  "From: " + this.sender + " | " + "to: " + Integer.toString(this.receivers) + " receivers";
    }
}
