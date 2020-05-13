package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.DLinkedList;

public class SubjectFilter implements IFilter {
	
	File folder;
	private String field;
	private IFolder filteredFolder;
	
	SubjectFilter(File path, IFolder filteredFolder) {
		this.folder = path;
		this.filteredFolder = filteredFolder;
	}

	@Override
	public DLinkedList applyFilter(DLinkedList list) { // linked list of info
		DLinkedList filtered = new DLinkedList();
		for(Object o : list) {// add clone in MailInfo
			MailInfo item = (MailInfo) o;
			while (item != null) {
				if (item.subject.contains(field)) {
					filtered.add(item);
					//String newDirectory = this.folder.getAbsolutePath() +item.directory;
					try {
						MailFolder.copyFolder(new File(filteredFolder.getPath(), item.directory), new File(this.folder,item.directory));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return filtered;
	}

	public void setParameter(String parameter) {
		this.field = parameter;		
	}
}
