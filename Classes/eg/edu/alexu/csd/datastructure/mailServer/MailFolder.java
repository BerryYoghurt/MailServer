package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;

public class MailFolder implements IFolder {
	
	private File self;
	private boolean editableMails;
	
	
	public MailFolder(File path, String string) {
		self = new File(path, string);
		self.mkdir();
		switch(string) {
		case "trash":
			break;
		case "draft":
			editableMails = true;
			break;
		case "inbox":
		case "sent":
			editableMails = false;
			//enum
		}
	}

	@Override
	public File getPath() {
		return self;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IFolder add(Object item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object remove(Object item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean copy(IFolder to) {
		// TODO Auto-generated method stub
		return false;
	}

}
