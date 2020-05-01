package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import eg.edu.alexu.csd.datastructure.linkedList.DoublyLinkedList;

public class SenderFilter implements IFilter {

	File folder;
	private String field;

	SenderFilter(File path) {
		this.folder = path;
	}

	@Override
	public DoublyLinkedList applyFilter(DoublyLinkedList list) {
		DoublyLinkedList filtered = new DoublyLinkedList();
		Info item = (Info) list.traverse(null);// add clone in Info
		while (item != null) {
			if (item.sender.contains(field)) {
				filtered.add(item);
				String newDirectory = this.folder.getAbsolutePath() + Paths.get(item.directory).getFileName();
				try {
					MailFolder.copyFolder(new File(item.directory), new File(newDirectory));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				item.directory = newDirectory;
			}
			item = (Info) list.traverse(null);
		}
		return filtered;
	}

	
	public void setParameter(String parameter) {
		this.field = parameter;		
	}

}
