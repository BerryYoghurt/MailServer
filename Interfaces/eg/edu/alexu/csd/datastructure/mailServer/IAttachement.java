package eg.edu.alexu.csd.datastructure.mailServer;
import java.io.File;

public interface IAttachement {
	public boolean setPath(File path);
	public File getPath();
	public boolean viewAttachement();
	public boolean delete();
	public boolean move(File newPath);
	public boolean copy(File newPath);
}
