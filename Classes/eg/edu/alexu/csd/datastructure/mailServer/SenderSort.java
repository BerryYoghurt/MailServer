package eg.edu.alexu.csd.datastructure.mailServer;

import eg.edu.alexu.csd.datastructure.linkedList.Classes.DLinkedList;
import eg.edu.alexu.csd.datastructure.stack.Stack;

public class SenderSort implements ISort{

	@Override
	public void applySort(DLinkedList list) {    //LinkedList of MailInfo
		if (list == null || list.size() == 0)
			return;

		int low = 0;
		int high = list.size() - 1;
		String pivot = (String) ((MailInfo)list.get((low + high) / 2)).sender;
		Stack s = new Stack();
		s.push(low);
		s.push(pivot);
		s.push(high);

		while (low < high && !s.isEmpty()) {

			high = (int) s.pop();
			pivot = (String) s.pop();
			low = (int) s.pop();

			int i = low, j = high;
			while (((MailInfo) list.get(i)).sender.compareTo(pivot) < 0) {
				i++;
			}

			while (((MailInfo) list.get(j)).sender.compareTo(pivot) > 0) {
				j--;
			}

			if (i <= j) {
				Object temp = list.get(i);
				list.set(i, list.get(j));
				list.set(j, temp);
				i++;
				j--;
			}

			// low >> j , i >> high
			if (low < j) {
				s.push(low);
				s.push(((MailInfo)list.get((low + j) / 2)).sender);
				s.push(j);
			}
			if (high > i) {
				s.push(i);
				s.push(((MailInfo)list.get((i + high) / 2)).sender);
				s.push(high);
			}
		}
	}

}
