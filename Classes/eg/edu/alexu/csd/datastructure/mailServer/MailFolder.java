package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.DLinkedList;

public class MailFolder implements IFolder {

	private Index index;
	private File path;
	private Kind type;
	enum Kind{
		TRASH,
		DRAFT,
		SENT,
		INBOX
	};
	// constructor
	public MailFolder(File path, Kind name, boolean isNew) {
		// create IFolder
		this.type = name;
		File folder = new File(path, name.toString().toLowerCase());
		if(isNew) {
			if (!folder.mkdir()) {
				throw new RuntimeException("folder is not created!");
			}
		}
		this.path = folder;
		index = new Index(folder,isNew); // fpath >>> folder path
		
		if(!isNew) {
			if(name == Kind.TRASH) {
				try {
					clearInTrash((DLinkedList)index.readIndex());
				} catch (ParseException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public MailFolder(File pathToLoadFrom) {
		this.path = pathToLoadFrom;
		index = new Index(this.path,false);
		type = Kind.valueOf(pathToLoadFrom.getName().toUpperCase());
	}
	
	@Override
	public File getPath() { 
		return this.path;
	}

	@Override
	public boolean isEmpty() {
		return index.getSize() == 0;
	}

	@Override
	public File add(Object item) { //**************************
		if (!this.path.exists()) {
			throw new RuntimeException("folder does not exists!");
		}
		if (item instanceof IMail) {
			Mail m = (Mail) item;
			File thisMail = new File(this.path, m.getIdentifier());
			thisMail.mkdir();
			index.add(item);
			index.setInTrash();
			index.writeToIndex();
			return thisMail;
		}
		return null; 
	}

	@Override
	public Object remove(Object item){ // name??? //*
		if (!this.path.exists()) {
			throw new RuntimeException("folder does not exists!");
		}
		if (item instanceof IMail) {
			Integer found = (Integer)index.remove(item);
			index.writeToIndex();
			if(found != -1) {
				File mail = new File(((IMail)item).getDirectory());
				try {
					removeDir(mail);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return item;
			}
			else {
				return null;
			}
			 //>>>>>> info object
		}
		return null;
	}

	@Override
	public boolean delete() {
		if (!this.path.exists()) { // Not found
			return false;
		}
		try {
			removeDir(this.path);
			return true;
		} catch (IOException e) {
			return false;
		}

	}

	@Override
	public int getSize() {
		return index.getSize();
	}

	public Kind getKind() {
		return this.type;
	}
	public void move(File from, File to) throws IOException {
		Files.move(from.toPath(), to.toPath());
		//System.out.println();
	}

	public static void copyFolder(File sourceFolder, File destinationFolder) throws IOException {
		// Check if sourceFolder is a directory or file
		// If sourceFolder is file; then copy the file directly to new location
		if (sourceFolder.isDirectory()) {
			// Verify if destinationFolder is already present; If not then create it
			if (!destinationFolder.exists()) {
				destinationFolder.mkdir();
				// System.out.println("Directory created :: " + destinationFolder);
			}

			// Get all files from source directory
			String[] files = sourceFolder.list();

			// Iterate over all files and copy them to destinationFolder one by one
			for (String file : files) {
				File srcFile = new File(sourceFolder, file);
				File destFile = new File(destinationFolder, file);

				// Recursive function call
				copyFolder(srcFile, destFile);
			}
		} else {
			// Copy the file content from one place to another
			Files.copy(sourceFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
			// System.out.println("File copied :: " + destinationFolder);
		}
	}

	public static void removeDir(File folder) throws IOException {
		Files.walk(folder.toPath()).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
	}

	public static void cleanDir(File folder) throws IOException {

		File[] files = folder.listFiles();
		if (files != null && files.length > 0) {
			for (File f : files) {
				if (f.getName() != "index.txt")
					removeDir(f);
			}
		}
	}
	
	public void clearInTrash(DLinkedList list) throws ParseException, IOException{
	    Date current = new Date();
	    for(Object o : list){
	        MailInfo temp = (MailInfo)o;
	        Date mailDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse((temp).inTrash);  
	   	    long diff = current.getTime() - mailDate.getTime();
	   	    long diffDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	             if(diffDays >= 30){
	                 removeDir(new File(temp.directory));
	            }
	        }
	}

	@Override
	public DLinkedList getIndex() {
		return (DLinkedList)this.index.readIndex();
	}

}
