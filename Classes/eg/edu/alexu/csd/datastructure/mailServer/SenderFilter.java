package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.DLinkedList;

public class SenderFilter implements IFilter {

	File folder; // directory of the folder which the filtered items are directed to
	private String field; // all/part of the sender name which needs to be in all filtered items

	public SenderFilter(File path) {
		this.folder = path;
	}

	@Override
	public DLinkedList applyFilter(DLinkedList list) {
		if (field == null || list == null || folder == null) return null;
		DLinkedList filtered = new DLinkedList();
		for (Object o : list) {// add clone in MailInfo
			MailInfo item = (MailInfo) o;
			if (item.sender.contains(field)) {
				filtered.add(item);
				String newDirectory = this.folder.getAbsolutePath() + Paths.get(item.directory).getFileName();
				try {
					MailFolder.copyFolder(new File(item.directory), new File(newDirectory));
				} catch (IOException e) {
					e.printStackTrace();
				}
				//item.directory = newDirectory;
			}

		}
		return filtered;
	}

	public void setParameter(String parameter) {
		this.field = parameter;
	}

}