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
import java.util.Scanner;

import eg.edu.alexu.csd.datastructure.linkedList.SinglyLinkedList;

public class User implements IContact { //needs a filter folder

	private File path;
	private File infoFile;
	private String[] info = new String[7]; // 1-Fname 2-Lname 3-address 4-date 5-gender 6-password 7-salt
	private byte[] salt;
	private MailFolder draft;
	private MailFolder trash;
	private MailFolder inbox;
	private MailFolder sent;

	User() { // if we want to upload an existing user??

	}

	User(String Fname, String Lname, String birthDate, boolean gender, String address, String password)
			throws IOException {
		// address // without @ //dateformat ="MM-dd-yyyy"

		File folder = new File("system\\" + address);
		if (!folder.mkdir()) {
			throw new RuntimeException("folder is not created!");
		}
		this.path = folder;

		File file = new File(folder.getAbsolutePath() + "\\info.txt");
		if (!folder.createNewFile()) {
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

		draft = new MailFolder(this.path, Kind.DRAFT, true);
		trash = new MailFolder(this.path, Kind.TRASH, true);
		inbox = new MailFolder(this.path, Kind.INBOX, true);
		sent = new MailFolder(this.path, Kind.SENT, true);
	}

	public void writeToFile() {
		try {
			PrintWriter writer = new PrintWriter(this.path);
			for (int i = 0; i < info.length; i++) {
				writer.println(info[i]);

			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public String getPassHash() {
		return info[5];
	}

	public void read(){
		try {
			Scanner reader = new Scanner(infoFile);
			int i = 0;
			while (reader.hasNextLine() && i < info.length) {
				info[i] = reader.nextLine();
				i++;
			}
			this.salt = info[6].getBytes();
			reader.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
	}

	public File getPath() {
		return this.path;
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
		if (address.length() > 20 || address.length() < 10) {
			return false;
		}
		address = address + "@system.com";
		info[2] = address;
		return true;
	}	
	
	@Override
	public SinglyLinkedList getAddresses() { // contact + getAddresses //user >> returns only one address
	    SinglyLinkedList get = new SinglyLinkedList();
		get.add(info[2]);
		return get;
	}

	@Override
	public boolean removeAddress(int order) {// contact
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
			if (str == info[5]) {
				return true;
			}
		} catch (NoSuchAlgorithmException e) {
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
	
	@Override
	public IFolder getContactsPath() {
		// TODO Auto-generated method stub
		return null;
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
		String salt = new String(saltArray);
		info[6] = salt;
	}

}
