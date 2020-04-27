package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.DLinkedList;

public class ContactFolder implements IFolder{
    
    private IIndex index;
	private File path;

	// constructor
	public ContactFolder(File path) throws FileNotFoundException {
		String fPath = path.getAbsolutePath() + "\\Contacts"; // get position
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
	public File add(Object item){
	    if (!this.path.exists()) {
			throw new RuntimeException("folder does not exists!");
		}
		if (item instanceof Contact) {
			File file = new File(this.path.getAbsolutePath() + "\\" + ((Contact) item).getName() + ".txt");
		    try {
				if (!file.createNewFile()) {
				    throw new RuntimeException("file is not created!");
				}
			
			    PrintWriter writer = new PrintWriter(file);
				writer.println(((Contact) item).getName());
			    writer.close();
			    index.add(item);
		     	return file; 
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Object remove(Object item) {
        if(item instanceof String){
            //search index
            //remove from index and folder  
            
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
		// TODO Auto-generated method stub
		return index.getSize();
	}
	
		public void removeDir(File folder) throws IOException {
		Files.walk(folder.toPath()).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
	}

	public void cleanDir(File folder) throws IOException {

		File[] files = folder.listFiles();
		if (files != null && files.length > 0) {
			for (File f : files) {
				if (!f.equals(index.getPath())) {
					removeDir(f);
				}
			}
		}
	}

	@Override
	public DLinkedList getIndex() {
		return (DLinkedList)index.readIndex();
	}
}
