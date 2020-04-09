package eg.edu.alexu.csd.datastructure.mailServer;
import java.io.File;

public interface IAttachement {
	public boolean copy(File from, File to);
	public File getPath();
	public boolean viewAttachement();
	public boolean delete();
}
