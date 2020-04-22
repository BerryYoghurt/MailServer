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


public class Mail implements IMail{

	transient private File containingFolder = null;// no need to save
	transient private File attFolder = null;//no need to save
	//transient private File receiversFile;
	private String /*IContact*/ composerName;//how to read or write?
	private String composerAddress;
	private Date date;//serial
	private String subject;//serial
	private Priority p;//serial
	transient private String identifier;
	transient private SLinkedList attachements;//no need to save
	transient private DLinkedList receivers;
	//transient private File metadata;//no need to save, carries the serial data
	transient private File bodyTxt;//no need to save
	
	public Mail(IContact from) {
		
		this.date = new Date();
		this.composerName = from.getName();
		this.composerAddress = from.getAddresses()[0];
		this.p = Priority.NORMAL;
		containingFolder = from.getDraftPath().getPath();
		bodyTxt = new File(containingFolder.getPath(), "bodyTxt.txt");
		//metadata = new File(containingFolder.getPath(), "metadata.eml");
		this.attachements = new SLinkedList();
		this.receivers = new DLinkedList();
		StringBuilder s = new StringBuilder(this.composerName);
		s.append(this.date.toString());
		this.identifier = s.toString();
		this.identifier.replaceAll("[ \\\\.@:]", "");
	}
	
	private Mail() {
		
	}
	@Override
	public void saveMail() {
		File eml = new File(this.containingFolder, "metadata.eml");
		try {
			eml.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try(Writer s = new OutputStreamWriter(new FileOutputStream(eml), Charset.forName("US-ASCII"))){
			//StringBuilder s = new StringBuilder();
			s.append("Subject: "); s.append(this.subject);
			s.append("\n\r");
			s.append("From: "); s.append("\""); s.append(this.composerName); 
			s.append("\""); s.append('<');
			s.append(this.composerAddress); s.append("@this.server"); s.append('>');
			s.append("\n\r");
			s.append("Date: ");s.append(this.date.toString()); s.append("\n\r");
			s.append("To: ");
			for(Object receiver: this.receivers) {
				s.append((String) receiver);
				s.append(';');
			}
			s.append("\n\r");
			s.append("Priority: ");s.append(this.p.toString());
			s.append("\n\r");
			s.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		for(char c : str) {
			if(c == '<') {
				continue;
			}
			if(c == '>') {
				emails[i++] = s.toString();
				s = new StringBuilder();
			}
			s.append(c);
		}
		return emails;
	}
	
	public static IMail loadMail(IFolder thisMailFolder, int numberOfReceivers) {
		Mail m = new Mail();
		m.containingFolder = thisMailFolder.getPath();
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
							for(int j = token.length(); j < arr.length; j++) {
								if(arr[j] == '\"') {
									continue;
								}
								if(arr[j] == '<') {
									break;
								}
								s.append(arr[j]);
							}
							m.composerName = s.toString();
							m.composerAddress = readEmails(arr, 1)[0];
							break;
						case "Subject":
							i++;
							m.subject = line.substring("Subject: ".length(), line.length()-2);
							break;
						case "To":
							i++;
							String[] receivers = readEmails(arr, numberOfReceivers);
							for(String rec : receivers) {
								m.addReceiver(rec);
							}
							break;
						case "Priority":
							i++;
							m.p = Priority.valueOf(line.substring("Priority: ".length(), line.length()-2));
							break;
						case "Date":
							i++;
							SimpleDateFormat d =new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
							m.date = d.parse(line);
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
				//m.attFolder = f; Constructor of IFolder?
				m.attachements = new SLinkedList();
				for(File temp : f.listFiles()) {
					m.attachements.add(new Attachement(temp));
				}
			}/*else if(f.getName().equals("receivers.txt")) {
				m.receiversFile = f;
				try(RandomAccessFile stream = new RandomAccessFile(f, "rw")){
					m.receivers = new DLinkedList();
					for(String line = stream.readLine(); line != null; line = stream.readLine()) {
						if(line.charAt(0) =='D') {
							continue;
						}
						else {
							m.receivers.add(line);
						}
					}
				} catch (FileNotFoundException e) {//SHOULD THESE EVEN BE THROWN!!
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
		}
		StringBuilder s = new StringBuilder(m.composerAddress);
		s.append(m.date.toString());
		m.identifier = s.toString().replaceAll("[ \\\\.@:]", "");
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
	
	private void copy(File root, File parentDest) throws IOException {
		Files.copy(root.toPath(),parentDest.toPath(),java.nio.file.StandardCopyOption.REPLACE_EXISTING);
		for(File f : root.listFiles()) {
			File copiedRoot = new File(parentDest, root.getName());
			if(f.isDirectory()) {
				copy(f, copiedRoot);
			}
			else {
				Files.copy(f.toPath(),copiedRoot.toPath(),java.nio.file.StandardCopyOption.REPLACE_EXISTING);
			}
		}
	}
	
	@Override
	public boolean copy(IFolder to) {//done
		to.add(this);
		try {
			copy(this.containingFolder, to.getPath());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean move(IFolder newFolder) {//done
		if(this.copy(newFolder))
			return this.containingFolder.delete();
		else return false;
	}

	@Override
	public boolean addReceiver(String receiverEmail) {//done
		receiverEmail = receiverEmail.toLowerCase();
		receivers.add(receiverEmail);
		/*if(receiversFile == null) {
			receiversFile = new File(containingFolder.getPath(), "receivers.txt");
		}
		try(RandomAccessFile f = new RandomAccessFile(receiversFile, "rw")){
			f.seek(f.length());
			f.writeChars(receiverEmail);
			f.writeChars(System.lineSeparator());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return true;
	}
	
	@Override
	public String removeReceiver(int index) {//done
		String r = (String)receivers.get(index);
		receivers.remove(index);
		//receiversFolder.remove(r);
		/*try(RandomAccessFile f = new RandomAccessFile(receiversFile, "rw")){
			for(int i = 0; i < index; i++) {
				f.readLine();
			}
			f.writeChar('D');//EMAILS SHOULD BE LOWER CASE
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
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
		if(this.attFolder == null) {
			attFolder = new File(containingFolder, "attachements");
		}
		else {
			try {
				attachement.copy(attFolder);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
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
	public String toString() {//done	
		return this.identifier;
	}
	
	
}
