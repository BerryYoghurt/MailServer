package eg.edu.alexu.csd.datastructure.mailServer;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Attachement implements IAttachement {
	
	//private static PrintWriter
	private File att;
	
	public Attachement(File attachement) {
		this.att = attachement;
	}
	
	@Override
	public boolean copy(File to) throws IOException{
		Files.copy(att.toPath(), to.toPath().resolve(att.getName()), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
		return false;//can attachement be a folder?
	}

	@Override
	public File getPath() {
		return att;
	}

	@Override
	public boolean delete() {
		return att.delete();
	}

	@Override
	public void view() {
		try {
			Desktop.getDesktop().open(att);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	@Override
	public String getName() {
		return att.getName();
	}

	@Override
	public boolean equals(Object other) {
		if(other instanceof Attachement) {
			if(((Attachement)other).getPath().equals(this.getPath()))
				return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
