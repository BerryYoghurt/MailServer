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
    
    public CIndex index;
	private File path;
	//private boolean isNew;

	// constructor
	public ContactFolder(File path , boolean isNew){ //path of the user folder in both cases
		File folder = new File(path, "contacts");
	    if(isNew){ //new contact folder
	        //this.isNew = true;
	        // create IFolder
  		    if (!folder.mkdir()) {
		    	throw new RuntimeException("folder is not created!");
		    }
		    this.path = folder;
		    try {
				index = new CIndex(folder,true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		    // fpath >>> folder path
	    }else{ //existing contact folder
	        //this.isNew = false;
	        this.path = folder;
	        //upload existing index
	        try {
				index = new CIndex(folder,false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
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
		if (!this.path.exists() || item == null || !(item instanceof Contact)) {
			throw new RuntimeException("folder does not exists!");
		}
		File file = new File(this.path , ((Contact) item).getName() + ".txt");
		try {
		if (!file.createNewFile()) {
			throw new RuntimeException("file is not created!");
		}
		PrintWriter writer = new PrintWriter(file);
		writer.println(((Contact) item).emails[0]);
		writer.close();
		index.add(item);
		return file;
		}catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
     /**
       *search index
       *remove from index and folder
       * @return found folder directory
     * @throws IOException 
       */
	@Override
	public Object remove(Object item){ 
        if(item instanceof String){ //contact name
            CInfo temp = (CInfo)index.remove(item);
            if(temp != null){
                File found = new File(temp.directory);
                try {
					removeDir(found);
				} catch (IOException e) {
					System.out.println(e);
					//e.printStackTrace();
				}
                return found;
            }
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
