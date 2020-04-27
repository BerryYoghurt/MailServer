package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.DLinkedList;

public class SubjectFilter implements IFilter {
	File folder;
	private String field;
	
	SubjectFilter(File path) {
		this.folder = path;
	}

	@Override
	public DLinkedList applyFilter(DLinkedList list) { // linked list of info
		DLinkedList filtered = new DLinkedList();
		for(Object o : list) {
			MailInfo item = (MailInfo) o;//TODO add clone in Info
			if (item.subject.contains(field)) {
				filtered.add(item);
				String newDirectory = this.folder.getAbsolutePath() + Paths.get(item.directory).getFileName();
				try {
					MailFolder.copyFolder(new File(item.directory), new File(newDirectory));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//item.directory = newDirectory;
			}
		}
		return filtered;
	}

	@Override
	public void setParameter(String parameter) {
		this.field = parameter;		
	}
}
