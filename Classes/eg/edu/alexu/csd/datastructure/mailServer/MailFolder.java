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

	// constructor
	public MailFolder(File path, String name) throws FileNotFoundException {
		String fPath = path.getAbsolutePath() + "\\" + name; // get position
		// create IFolder
		File folder = new File(fPath);
		if (!folder.mkdir()) {
			throw new RuntimeException("folder is not created!");
		}
		this.path = folder;
		index = new Index(folder); // fpath >>> folder path

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
	public File add(Object item) { //*
		if (!this.path.exists()) {
			throw new RuntimeException("folder does not exists!");
		}
		if (item instanceof IMail) {
			/*
			how to get the Mail Folder path
			 */
			index.add(item);
		}
		return null; 
	}

	@Override
	public Object remove(Object item) { // name??? //*
		// TODO Auto-generated method stub
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

	public void removeDir(File folder) throws IOException {
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
