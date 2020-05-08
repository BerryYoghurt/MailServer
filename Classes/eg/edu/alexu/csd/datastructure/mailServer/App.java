package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.DLinkedList;
import eg.edu.alexu.csd.datastructure.linkedList.Interfaces.ILinkedList;
import eg.edu.alexu.csd.datastructure.queue.IQueue;

public class App implements IApp{
	protected static DataBase db;
	protected static File systemFile;
	protected User signedInUser;
	protected DLinkedList index;
	protected IFolder currentFolder;
	
	App(){
		systemFile = new File(App.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		db = new DataBase();
	}
	
	@Override
	public boolean signin(String email, String password) {
		//Pattern p = Pattern.compile("[^A-Za-z_0-9]+");
		if(Pattern.matches("[^A-Za-z_0-9]+", email)) {
			return false;
		}
		//check that email exists in Database
		User u = db.loadUser(email);//should upload from database
		if(u == null)
			return false;
		if(u.matchPassword(password)) {
			signedInUser = u;
			return true;
		}
		u = null;
		return false;
	}

	@Override
	public boolean signup(IContact contact) {//contact should be filled in GUI part
		//User u = db.loadUser(contact.getAddresses()[0]);
		//if(u == null)
		//{
			return db.add((User)contact) == 1;
		//}
		//return false;
	}

	@Override
	public void setViewingOptions(IFolder folder, IFilter filter, ISort sort) {//constructor of filter should take user path and setParameter should be called
		if(folder == null) {
			currentFolder = null;
			index = null;
			return;
		}
		if(currentFolder != folder) {
			index = folder.getIndex();
			currentFolder = folder;
		}
		if(filter != null)
			index = filter.applyFilter(index);
		
		if(sort != null)
			sort.applySort(index);
	}

	@Override
	public IMail[] listEmails(int page) {
		
	    int begin , end;
	    if(page == 1){
	        begin = 0;
	    }else{
	        begin = ((page-1)*10)-1;
	    }
	    
	    if(page == Math.ceil(index.size()/10.0)){
	        end = index.size()-1;
	    }else{
	        end = ((page)*10)-1;
	    }
	   
	    DLinkedList sublist = index.sublist(begin,end);
	    Mail[] arr = new Mail[sublist.size()];
	    int j = 0;
	    for(Object o : sublist){
	        MailInfo f = (MailInfo)o;
	        arr[j++] = Mail.loadMail(new File(f.directory), f.receivers);
	    }
		return arr;
	}

	@Override
	public void deleteEmails(ILinkedList mails) {
		DLinkedList m = (DLinkedList)mails;
		for(Object o : m) {
			((Mail)o).delete();
		}
		
	}

	@Override
	public void moveEmails(ILinkedList mails, IFolder des) {
		DLinkedList m = (DLinkedList)mails;
		for(Object o : m) {
			((Mail)o).move(des);
		}
	}

	@Override
	public boolean compose(IMail email) {
		IQueue q = email.getReceivers();
		while(!q.isEmpty()) {
			//check if receiver is in database, if one is not valid, return false
			if(!App.db.userExists((String)q.dequeue())) {
				return false;
			}
		}
		q = email.getReceivers();
		while(!q.isEmpty()) {
			//if reciever in this server
			User u = db.loadUser((String)q.dequeue()); //load from database
			if(u.equals(signedInUser)) {
				u = signedInUser;
			}
			//MailFolder.copyFolder(sourceFolder, destinationFolder);
			email.copy(u.getInboxPath());
		}
		email.move(signedInUser.getSentPath());
		return true;
	}

}
