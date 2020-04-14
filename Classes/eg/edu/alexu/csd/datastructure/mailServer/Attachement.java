package eg.edu.alexu.csd.datastructure.mailServer;

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
		Files.copy(att.toPath(), to.toPath());
		return false;
	}

	@Override
	public File getPath() {
		return att;
	}

	@Override
	public boolean delete() {
		return att.delete();
	}

}
