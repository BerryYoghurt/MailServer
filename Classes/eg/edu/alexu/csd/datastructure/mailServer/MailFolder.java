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

import eg.edu.alexu.csd.datastructure.linkedList.DoublyLinkedList;

public class MailFolder implements IFolder {

	private IIndex index;
	private File path;
	private Kind type;

	// constructor
	public MailFolder(File path, Kind name, boolean isNew) throws IOException {
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
			/*
			how to get the Mail Folder path
			 */
			index.add(item);
			return new File(((IMail)item).getDirectory());
		}
		return null; 
	}

	@Override
	public Object remove(Object item){ // name??? //*
		if (!this.path.exists()) {
			throw new RuntimeException("folder does not exists!");
		}
		if (item instanceof IMail) {
			Object found = index.remove(item);
			if(found != null) {
				File mail = new File(((IMail)item).getDirectory());
				try {
					removeDir(mail);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return found;  //>>>>>> info object
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
	
	public void clearInTrash(DoublyLinkedList list) throws ParseException, IOException{
	    Date current = new Date();
	    for(int i = 0 ; i<list.size() ; i++){
	        Info temp = (Info)list.traverse(null);
	        Date mailDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse((temp).inTrash);  
	   	    long diff = current.getTime() - mailDate.getTime();
	   	    long diffDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	             if(diffDays >= 30){
	                 removeDir(new File(temp.directory));
	            }
	        }
	}

	@Override
	public DoublyLinkedList getIndex() {
		return (DoublyLinkedList)this.index.readIndex();
	}

}
