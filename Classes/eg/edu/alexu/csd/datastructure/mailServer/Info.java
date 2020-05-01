package eg.edu.alexu.csd.datastructure.mailServer;

public class Info {

	// date , sender , recievers , subject , directory

	public String date;
	public String sender;
	public int receivers = 0; // multiple reciever
	public String subject;
	public String directory;
    public String priority;
    public String inTrash; //date for trash emails
    
    public String receiversToString(IQueue receivers) {
    	if(receivers == null || receivers.size() == 0){
	        throw new RuntimeException();
	    }
	    String recievers = "";
	    for(int i = 0; i < receivers.size() ; i++){
	        recievers = receivers.dequeue() + " ";
	    }
	    recievers = recievers.trim();
    }

	public String infoToString() {
		return date + "," + sender + "," + Integer.toString(receivers) + "," + subject + "," + directory + "," + priority  + inTrash ;
	}

	public void stringToInfo(String line) {
		String[] arr = line.split(",", 0);
		date = arr[0];
		sender = arr[1];
		receivers = Integer.parseInt(arr[2]);
		/*String[] arr2 = r.split(" " , 0);
		receivers = new LQueue();
		for(int i = 0; i < arr2.length ; i++){
			receivers.enqueue(arr2[i]);
	    }
	    this.receivers = receivers;*/
		subject = arr[3];
		directory = arr[4];
		priority = arr[5];
		inTrash = arr[6];
	}
}
