package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Scanner;

public class User implements IContact {

	private File path;
	private File infoFile;
	private String[] info = new String[7]; // 1-Fname 2-Lname 3-address 4-date 5-gender 6-password 7-salt
	private byte[] salt;
	private MailFolder draft;
	private MailFolder trash;
	private MailFolder inbox;
	private MailFolder sent;
  
	  User(String address){
		  	File folder = new File(App.systemFile,address);
			folder.mkdir();
			this.path = folder;
			File file = new File(folder,"info.txt");
			this.infoFile = file;
			try {
				read();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			draft = new MailFolder(this.path,MailFolder.kind.DRAFT);
			trash = new MailFolder(this.path,MailFolder.kind.TRASH);
			inbox = new MailFolder(this.path,MailFolder.kind.INBOX);
			sent = new MailFolder(this.path,MailFolder.kind.SENT);
	  }
	  
	  
	  User(String Fname, String Lname, String address){//TESTING ONLY
		  File folder = new File("D:\\Uni\\Term4\\MailServer\\Tests\\eg\\edu\\alexu\\csd\\datastructure\\mailServer\\" + address);
			if (!(folder.exists() || folder.mkdir())) {
				throw new RuntimeException("folder is not created!");
			}
			this.path = folder;

			File file = new File(folder.getAbsolutePath() + "\\info.txt");
			try {
				if (!(file.exists() || file.createNewFile())) {
					throw new RuntimeException("file is not created!");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.infoFile = file;
			setAddress(address);
			setName(Fname, Lname);
			writeToFile();

			draft = new MailFolder(this.path,MailFolder.kind.DRAFT);
			trash = new MailFolder(this.path,MailFolder.kind.TRASH);
			inbox = new MailFolder(this.path,MailFolder.kind.INBOX);
			sent = new MailFolder(this.path,MailFolder.kind.SENT);
	  }

	  /**
	   * @param Fname
	   * @param Lname
	   * @param birthDate
	   * @param gender
	   * @param address
	   * @param password*/
	  User(String Fname, String Lname, String birthDate, boolean gender, String address, String password) throws IOException { 
		// address // without @  //dateformat ="MM-dd-yyyy"

		File folder = new File(App.systemFile, address);
		if (!(folder.exists() || folder.mkdir())) {
			throw new RuntimeException("folder is not created!");
		}
		this.path = folder;

		File file = new File(folder.getAbsolutePath() + "\\info.txt");
		if (!(file.exists() || file.createNewFile())) {
			throw new RuntimeException("file is not created!");
		}
		this.infoFile = file;

		setSalt();
		setAddress(address);
		setPassword(password);
		setName(Fname, Lname);
		setBirthDate(birthDate);
		setGender(gender);
		writeToFile();
		draft = new MailFolder(this.path,MailFolder.kind.DRAFT);
		trash = new MailFolder(this.path,MailFolder.kind.TRASH);
		inbox = new MailFolder(this.path,MailFolder.kind.INBOX);
		sent = new MailFolder(this.path,MailFolder.kind.SENT);
	}

	public void writeToFile() {
		try(PrintWriter writer = new PrintWriter(this.infoFile)){
			for(int i=0 ; i < info.length ; i++){
			    writer.println(info[i]);
			     
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
  
  
	  public File getPath() {
	    return this.path;
	  }
	  
	  public String getPassHash() {
		  return info[5];
	  }
	public void read() throws FileNotFoundException {
		Scanner reader = new Scanner(infoFile);
		int i = 0;
		while (reader.hasNextLine() && i < info.length) {
			info[i] = reader.nextLine();
			i++;
		}
		this.salt = Base64.getDecoder().decode(info[6]);
		reader.close();
	}

	public void setGender(boolean gender) { // true >> male //false >> female
		if (gender) {
			info[4] = "male";
		} else {
			info[4] = "female";
		}
	}
	
	public String getGender() {
		return info[4];
	}

	public boolean setBirthDate(String date) {
		try {
			DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
			df.setLenient(false);
			df.parse(date);
			info[3] = date;
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	
	public String getBirthDate() {
		return info[3];
	}

	@Override
	public boolean setAddress(String address) { // without the @ //both
		if (address.length() > 20 || address.length() < 5) {//why < 10??
			return false;
		}
		//address = address + "@system.com";
		info[2] = address.toLowerCase();
		return true;
	}

	@Override
	public String[] getAddresses() { // contact + getAddresses //user >> returns only one address
		String[] get = new String[1];
		get[0] = info[2];
		return get;
	}

	@Override
	public boolean removeAddress(int order) {// contact
		// TODO Auto-generated method stub
		throw new RuntimeException();
	}

	@Override
	public boolean setPassword(String password) {// user
		if (password.length() > 20 || password.length() < 8) {
			return false;
		}
		try {
			info[5] = getHash(password, this.salt);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean matchPassword(String password) {// user
		if (password.length() > 20 || password.length() < 8) {
			return false;
		}
		String str;
		try {
			str = getHash(password, this.salt);
			if (str.equals(info[5])) {
				return true;
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean setName(String Fname, String Lname) {// both
		if (Fname.length() < 2 || Fname.length() > 10 || Lname.length() < 2 || Lname.length() > 10) {
			return false;
		} else {
			info[0] = Fname;
			info[1] = Lname;
			return true;
		}
	}

	@Override
	public String getName() { // both
		// TODO Auto-generated method stub
		String get = info[0] + " " + info[1];
		return get;
	}

	@Override
	public int appendIndex(IFolder indexFile) { // user only >> appends to database
		App.db.add(this);
		return 0;
	}
	
	
	@Override
	public IFolder getDraftPath() {// user
		return this.draft;
	}

	@Override
	public IFolder getTrashPath() {// user
		return this.trash;
	}

	@Override
	public IFolder getInboxPath() {// user
		return this.inbox;
	}

	@Override
	public IFolder getSentPath() {// user
		return this.sent;
	}

	private String getHash(String password, byte[] salt) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.reset();
		digest.update(salt);
		byte[] hash = digest.digest(password.getBytes());
		return bytesToStringHex(hash);
	}

	private String bytesToStringHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		char[] hexArray = "0123456789ABCDEF".toCharArray();
		for (int i = 0; i < bytes.length; i++) {
			int v = bytes[i] & 0xFF;
			hexChars[i * 2] = hexArray[v >>> 4];
			hexChars[i * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	private byte[] createSalt() {
		byte[] bytes = new byte[20];
		SecureRandom random = new SecureRandom();
		random.nextBytes(bytes);
		return bytes;
	}


	private void setSalt() {
		byte[] saltArray = createSalt();
		this.salt = saltArray;
		String salt = Base64.getEncoder().encodeToString(saltArray);
		info[6] = salt;
	}
}
