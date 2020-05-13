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

	File folder; // directory of the folder which the filtered items are directed to
	private String field; // all/part of the subject which needs to be in all filtered items

	public SubjectFilter(File path) {
		this.folder = path;
	}

	@Override
	public DLinkedList applyFilter(DLinkedList list) { // linked list of MailInfo
		if (field == null || list == null || folder == null) return null;
		DLinkedList filtered = new DLinkedList();
		for (Object o : list) {
			MailInfo item = (MailInfo) o;
			if (item.subject.contains(field)) {
				filtered.add(item);
				String newDirectory = this.folder.getAbsolutePath() + Paths.get(item.directory).getFileName();
				try {
					MailFolder.copyFolder(new File(item.directory), new File(newDirectory));
				} catch (IOException e) {
					e.printStackTrace();
				}
				// item.directory = newDirectory;
			}

		}
		return filtered;
	}

	public void setParameter(String parameter) {
		this.field = parameter;
	}
}