package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.DLinkedList;
import eg.edu.alexu.csd.datastructure.linkedList.Classes.SLinkedList;
import eg.edu.alexu.csd.datastructure.linkedList.Interfaces.ILinkedList;
import eg.edu.alexu.csd.datastructure.queue.AQueue;
import eg.edu.alexu.csd.datastructure.queue.IQueue;


public class Mail implements IMail, Cloneable{

	transient private File containingFolder = null;// no need to save
	transient private File attFolder = null;//no need to save
	private IContact composer;
	private String composerName;//how to read or write?
	private String composerAddress;
	private Date date;//serial
	private String subject;//serial
	private Priority p;//serial
	transient private String identifier;
	transient private SLinkedList attachements;//no need to save
	transient private DLinkedList receivers;
	transient private File bodyTxt;//no need to save
	
	public Mail(IContact from) {//any time a new mail is created, it is in draft
		this.date = new Date();
		this.subject = "";
		composer = from;
		this.composerName = from.getName();
		this.composerAddress = from.getAddresses()[0];
		this.p = Priority.NORMAL;
		this.attachements = new SLinkedList();
		this.receivers = new DLinkedList();
		StringBuilder s = new StringBuilder(this.composerAddress);
		s.append(this.date.toString());
		this.identifier = s.toString();
		this.identifier = this.identifier.replaceAll("[\\s\\\\.@:]", "");
		containingFolder = new File(from.getDraftPath().getPath(), this.identifier);
		containingFolder.mkdir();
		bodyTxt = new File(containingFolder, "bodyTxt.txt");
		try {
			bodyTxt.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Mail() {
		
	}
	@Override
	public void saveMail() {
		composer.getDraftPath().add(this);//add to index
		File eml = new File(this.containingFolder, "metadata.eml");
		try {
			eml.createNewFile();
		} catch (IOException e1) {
			System.out.println("EML file not created");
			//e1.printStackTrace();
		}
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(eml);
			
			try(Writer s = new OutputStreamWriter(fos, Charset.forName("US-ASCII"))){
				//StringBuilder s = new StringBuilder();
				s.append("Subject: "); s.append(this.subject == null? "": this.subject);
				s.append("\r\n");
				s.append("From: "); s.append("\""); s.append(this.composerName); 
				s.append("\""); s.append('<');
				s.append(this.composerAddress); /*s.append("@this.server");*/ s.append('>');
				s.append("\r\n");
				SimpleDateFormat d = new SimpleDateFormat("EEE dd MMM yyyy hh:mm:ss zzz");
				s.append("Date: ");s.append(d.format(this.date)); s.append("\r\n");
				s.append("To: ");
				for(Object receiver: this.receivers) {
					s.append('<');
					s.append((String) receiver); s.append('>');
					s.append(';');
				}
				s.append("\r\n");
				s.append("Priority: ");s.append(this.p.toString());
				s.append("\r\n");
				s.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private static String readKeyWord(char[] str) {
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < str.length; i++) {
			if(str[i] == ':') {
				return s.toString();
			}
			s.append(str[i]);
		}
		return null;
	}
	
	private static String[] readEmails(char[] str, int number) {
		String[] emails = new String[number];
		StringBuilder s = new StringBuilder();
		int i = 0;
		boolean started = false;
		for(char c : str) {
			if(c == '<') {
				started = true;
				continue;
			}
			if(c == '>') {
				started = false;
				emails[i++] = s.toString();
				s = new StringBuilder();
			}
			if(started)
				s.append(c);
		}
		return emails;
	}
	
	public static Mail loadMail(File thisMailFolder, int numberOfReceivers) {
		Mail m = new Mail();
		m.containingFolder = thisMailFolder;
		//long lastModified = thisMailFolder.lastModified();
		File[] list = m.containingFolder.listFiles();
		for(File f : list) {
			if(f.getName().contentEquals("metadata.eml")) {//load subject and date
				try (BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(f),Charset.forName("US-ASCII")))){//load subject, date, sender, priority. Implement method to save
					for(int i = 0; i < 5;) { //at least the five elements in header
						String line = r.readLine();
						char[] arr = line.toCharArray();
						String token = readKeyWord(arr);
						switch (token) {
						case "From":
							i++;
							StringBuilder s = new StringBuilder();
							boolean started = false;
							for(int j = token.length(); j < arr.length; j++) {
								if(arr[j] == '\"') {
									started = true;
									continue;
								}
								if(arr[j] == '<') {
									break;
								}
								if(started)
									s.append(arr[j]);
							}
							m.composerName = s.toString();
							m.composerAddress = readEmails(arr, 1)[0];
							break;
						case "Subject":
							i++;
							m.subject = line.substring(token.length()+2);
							break;
						case "To":
							i++;
							String[] receivers = readEmails(arr, numberOfReceivers);
							m.receivers = new DLinkedList();
							for(String rec : receivers) {
								m.addReceiver(rec);
							}
							break;
						case "Priority":
							i++;
							m.p = Priority.valueOf(line.substring(token.length()+2));
							break;
						case "Date":
							i++;
							SimpleDateFormat d =new SimpleDateFormat("EEE dd MMM yyyy hh:mm:ss zzz");
							m.date = d.parse(line.substring(token.length() + 2));
							break;
						}
					}
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(f.getName().equals("bodyTxt.txt")){//load body
				m.bodyTxt = f;
			}else if(f.getName().equals("attachements")){
				m.attFolder = f;
				m.attachements = new SLinkedList();
				for(File temp : f.listFiles()) {
					m.attachements.add(new Attachement(temp));
				}
			}
		}
		m.identifier = thisMailFolder.getName();
		if(m.attachements == null) {
			m.attachements = new SLinkedList();
		}
		m.composer = App.db.loadUser(m.composerAddress);
		//if(trash)
		//	m.containingFolder.setLastModified(lastModified);//ensure last modified is not changed for the sake of deletion
		return m;
	}
	
	@Override
	public void setSubject(String s) {//done
		if(s.length() > 20)
			throw new IllegalArgumentException("subject too long");
		this.subject = s;
	}
	
	public String getSubject() {//done
		return this.subject;
	}
	

	@Override
	public File getBody() {//done
		return bodyTxt;
	}
	
	private void copy(File root, File dest) throws IOException {
		Files.copy(root.toPath(),dest.toPath(),java.nio.file.StandardCopyOption.REPLACE_EXISTING);
		for(File f : root.listFiles()) {
			File copied = new File(dest, f.getName());
			if(f.isDirectory()) {
				copy(f, copied);
			}
			else {
				Files.copy(f.toPath(),copied.toPath(),java.nio.file.StandardCopyOption.REPLACE_EXISTING);
			}
		}
	}
	
	@Override
	public boolean copy(IFolder to) {//done
		//to.add(this);
		try {
			copy(this.containingFolder, to.add(this));
			/*File newParent = new File(to.getPath(), toString());
			for(File f : containingFolder.listFiles()) {
				copy(f, newParent);
			}*/
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean move(IFolder newFolder) {//done
		if(this.copy(newFolder)) {
			IFolder f = new MailFolder(this.containingFolder.getParentFile());
			f.remove(this);
			return true;
		}
		else return false;
	}
	
	@Override
	public boolean delete() {
		IFolder to = new MailFolder(new File(this.containingFolder.getParentFile(),"trash"));
		return move(to);
	}

	@Override
	public boolean addReceiver(String receiverEmail) {//done
		receiverEmail = receiverEmail.toLowerCase();
		receivers.add(receiverEmail);
		return true;
	}
	
	@Override
	public String removeReceiver(int index) {//done
		String r = (String)receivers.get(index);
		receivers.remove(index);
		return r;
	}
	
	@Override
	public IQueue getReceivers() {//done
		IQueue q = new AQueue(receivers.size());
		for(Object o : receivers) {
			q.enqueue(o);
		}
		return q;
	}
	
	@Override
	public boolean addAttachement(IAttachement attachement) {//done, remember attFolder class
		if(this.attFolder == null || !this.attFolder.exists()) {//not yet assigned or removed single attachement
			attFolder = new File(containingFolder, "attachements");
			attFolder.mkdir();
		}
		try {
			attachement.copy(attFolder);
		} catch (IOException e) {
			e.printStackTrace();//TODO make window to show error logs
			return false;
		}
		this.attachements.add(attachement);
		return true;
	}
	
	@Override
	public IAttachement removeAttachement(int index) {//done, but edit in IFolder
		IAttachement att = (IAttachement)attachements.get(index);
		att.delete();
		attachements.remove(index);
		if(attachements.size() == 0) {
			attFolder.delete();
		}
		//att.delete();//I think this should be called in the above function
		return att;
	}
	
	@Override
	public ILinkedList getAttachements() {//done
		return this.attachements;
	}

	@Override
	public Date getDate() {//done
		return this.date;
	}

	@Override
	public String getSenderName() {//done
		return this.composerName;
	}
	
	@Override
	public String getSenderAddress() {
		return this.composerAddress;
	}
	/**
	 * updates the date to the instance at which it was called*/
	public void updateDate() {
		if(containingFolder.getParentFile().getName().equals("draft")) {//in draft
			this.date = new Date();
		}
		else {
			throw new IllegalStateException("The mail is not in draft");
		}
	}
	
	@Override
	public boolean setPriority(Priority p) {//done
		this.p = p;
		return true;
	}

	@Override
	public Priority getPriority() {//done
		return this.p;
	}
	
	@Override
	public String getIdentifier() {//done	
		return this.identifier;
	}

	@Override
	public String getDirectory() {
		return this.containingFolder.getPath();
	}
	
	@Override
	public Mail clone() {
		Mail m = Mail.loadMail(this.containingFolder, this.receivers.size());
		m.composer = this.composer;
		return m;
	}
	
	@Override 
	public String toString() {
		return "priority: " + this.p + " | " + this.date + " | " + "subject: " + this.subject + " | " +  "From: " + this.composerName + " | " + "to: " + Integer.toString(this.receivers.size()) + " receivers";
	}
}
