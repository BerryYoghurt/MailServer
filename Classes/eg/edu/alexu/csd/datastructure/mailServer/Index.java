package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import eg.edu.alexu.csd.datastructure.linkedList.DoublyLinkedList;
import eg.edu.alexu.csd.datastructure.linkedList.ILinkedList;
import eg.edu.alexu.csd.datastructure.linkedList.SinglyLinkedList;

public class Index implements IIndex {

	protected DoublyLinkedList list;
	private File path;
	protected int size = 0;
	//private boolean isNew;

	// constructor
	public Index(File path, boolean isNew) { // both cases >> path of the mail folder
		// create index file itself and keep its path
		File index = new File(path, "index.txt");
		try {
			if (isNew) {
				// this.isNew = true;
				index.createNewFile();
				this.path = index;

			} else {
				// this.isNew = false;
				this.path = index;
				readIndex();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public File getPath() {
		return this.path;
	}


	@Override
	public ILinkedList readIndex() {
		try {
			Scanner reader = new Scanner(path);
			while (reader.hasNextLine()) {
				Info item = new Info();
				item.stringToInfo(reader.nextLine());
				list.add(item);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return list.copy();
	}


	@Override
	public void writeToIndex(){ // test traverse function
		try {
		PrintWriter writer = new PrintWriter(this.path);
		for (int i = 0; i < list.size(); i++) {
			Object n = list.traverse(null);
			if (n != null) {
				Info item = (Info) n;
				writer.println(item.infoToString());
			}
		}
		writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * we know that object is an email
	 */
	@Override
	public void add(Object item) {// date , sender , recievers , subject , directory
		if (item == null || !(item instanceof IMail)) {
			throw new RuntimeException();
		}
		Mail m = (Mail)item;
		Info item2 = new Info();
		Date mailDate = m.getDate();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		item2.date = formatter.format(mailDate).toString();
		item2.sender = m.getSenderAddress();
		item2.receivers = m.getReceivers().size();
		item2.subject = m.getSubject();
		item2.directory = m.getDirectory();
		item2.priority = m.getPriority().toString();
		Date trashDate = new Date();
		item2.inTrash = formatter.format(trashDate).toString();
		list.add(item2);
		size++;
	}

	@Override
	public Object remove(Object o) {
		if (o == null || !(o instanceof IMail)) {
			throw new RuntimeException();
		}
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Mail m = (Mail)o;
		Date mailDate = m.getDate();
		Info item = new Info();
		item.date = formatter.format(mailDate).toString();
		item.sender = m.getSenderAddress();
		item.receivers = m.getReceivers().size();
		item.subject = m.getSubject();
		item.directory = m.getDirectory();
		item.priority = m.getPriority().toString();
		Object found = find(o);
		size--;
		return found;
	}

	public int search(Object e) {
		int middle, high, low;
		boolean found = false;
		Stack stack = new Stack();
		stack.push(0);
		stack.push(list.size() - 1);
		while (!found) {
			high = (int) stack.pop();
			low = (int) stack.pop();
			if (high < low) {
				stack.push(-1);
				break;
			}
			middle = (high + low) / 2;
			if (list.get(middle).equals(e)) {
				stack.push(middle);
				found = true;
			} else if (((String) list.get(middle)).compareTo((String) e) > 0) { // sorted by ??
				stack.push(low);
				stack.push(middle - 1);
			} else {
				stack.push(middle + 1);
				stack.push(high);
			}
		}
		return (int) stack.pop();
	}

	@Override
	public Object find(Object o) { // search the linked list by ??
		return null;
	}

	@Override
	public int getSize() {
		return size;
	}

	/**
	 * linked list of arrays of size 10 divide the main list into arrays(pages)
	 */
	@Override
	public ILinkedList setPages(int size) {
		ILinkedList pages = new SinglyLinkedList();
		for (int i = 0; i < Math.ceil(list.size() / 10.0); i++) {
			int begin, end;
			if (i == 0) {
				begin = 0;
			} else {
				begin = ((i) * 10) - 1;
			}

			if (i == Math.ceil(list.size() / 10.0) - 1) {
				end = list.size() - 1;
			} else {
				end = ((i + 1) * 10) - 1;
			}

			ILinkedList sublist = list.sublist(begin, end);
			Info[] arr = new Info[sublist.size()];
			for (int j = 0; j < sublist.size(); j++) {
				arr[j] = (Info) ((SinglyLinkedList) sublist).traverse(null);
			}
			pages.add(arr);
		}

		return pages;
	}

}
