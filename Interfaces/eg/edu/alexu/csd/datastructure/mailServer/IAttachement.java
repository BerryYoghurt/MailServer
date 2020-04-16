package eg.edu.alexu.csd.datastructure.mailServer;
import java.io.File;
import java.io.IOException;

public interface IAttachement {
	public boolean copy(File to) throws IOException;
	public File getPath();
	//public boolean viewAttachement();-- It's better to getPath then view it in the GUI part
	public boolean delete();
}
