package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;

public class MailFolder implements IFolder {
	private IIndex index;
	private File path;
	private kind type;
	
	enum kind{
		TRASH,
		DRAFT,
		SENT,
		INBOX
	};
	
	// constructor
	public MailFolder(File path, kind k) {
		this.path = new File(path, k.toString().toLowerCase());
		this.path.mkdir();
		index = new Index(this.path); // fpath >>> folder path	
		type = k;
		switch(type) {
		case TRASH://TODO ONLY FOR WINDOWS, update to Unix too
			//File root = path.getParentFile().toPath().resolve("trash").toFile();//navigate to trash
			//createDeletionBatch(this.path);
			break;
		case DRAFT:
			break;
		case INBOX:
			break;
		case SENT:
			break;
		}
	}
	/*private void createDeletionBatch(File root) {
		SystemUtils;
		File batch = new File(root, "AutoDelete.bat");
		try(Writer s = new OutputStreamWriter(new FileOutputStream(batch), Charset.forName("US-ASCII"))){
			batch.createNewFile();
			s.append("@Echo off \r\n");
			s.append("ForFiles /p "); s.append(root.getPath());
			s.append(" /s /d -30 /c \" cmd /c del @file\"");
			s.flush();
			Runtime r = Runtime.getRuntime();//temporary path name
			String command = "SCHTASKS /CREATE /SC DAILY /TN \"MailServer\\AutoDelete\" /TR \""+batch.getPath()+"\" /ST 00:00";
			r.exec(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	//another constructor
	public MailFolder(File pathToLoadFrom) {
		this.path = pathToLoadFrom;
		index = new Index(this.path);
		type = kind.valueOf(pathToLoadFrom.getName().toUpperCase());
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
	public File add(Object item) {
		if (!this.path.exists()) {
			throw new RuntimeException("folder does not exist!");
		}
		if (!(item instanceof IMail)) {
			throw new RuntimeException("Should be a mail");
		}
		Mail i = (Mail)item;
		File thisMail = new File(this.path, i.toString());
		thisMail.mkdir();
			/*
			 * //set name , check if repeated String newPath = path.getAbsolutePath() + "\\"+ name; 
			 * SingleMailFolder mail = new SingleMailFolder(newPath...); // set mail
			 * inside its folder return mail;
			 */
		index.add(item);
		return thisMail; // folder of singleMailFolder
	}

	@Override
	public Object remove(Object item) { // name???
		if(!(item instanceof Mail)) {
			throw new IllegalArgumentException();
		}
		Mail i = (Mail) item;
		File mFolder = new File(this.path, i.toString());
		try {
			MailFolder.removeDir(mFolder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		index.remove(item);
		// remove item itself
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
		// TODO Auto-generated method stub
		return index.getSize();
	}

	public void move(File from, File to) throws IOException {
		Files.move(from.toPath(), to.toPath());
		//System.out.println();
	}

	public void copyFolder(File sourceFolder, File destinationFolder) throws IOException {
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

	public void cleanDir(File folder) throws IOException {

		File[] files = folder.listFiles();
		if (files != null && files.length > 0) {
			for (File f : files) {
				if (!f.equals(index.getPath()))
					removeDir(f);
			}
		}
	}
}
