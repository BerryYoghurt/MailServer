package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.DLinkedList;


public class SenderFilter implements IFilter {

	File folder;
	private String field;
	private IFolder filteredFolder;
	
	SenderFilter(File path, IFolder filteredFolder) {
		this.folder = path;
		this.filteredFolder = filteredFolder;
	}

	@Override
	public DLinkedList applyFilter(DLinkedList list) {
		DLinkedList filtered = new DLinkedList();
		for(Object o : list) {// add clone in MailInfo
			MailInfo item = (MailInfo) o;
			while (item != null) {
				if (item.sender.contains(field)) {
					filtered.add(item);
					String newDirectory = this.folder.getAbsolutePath() +item.directory;
					try {
						MailFolder.copyFolder(new File(filteredFolder.getPath(), item.directory), new File(newDirectory));
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
