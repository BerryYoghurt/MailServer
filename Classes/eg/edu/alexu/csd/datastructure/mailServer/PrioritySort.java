package eg.edu.alexu.csd.datastructure.mailServer;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.DLinkedList;

public class PrioritySort implements ISort{

	public PriorityQueue sorted = new PriorityQueue();
    
    public void applySort(DLinkedList list){
		for (Object o : list){
           	sorted.insert(o,Priority.valueOf(((MailInfo)o).priority).ordinal());
		}   
    }

}

