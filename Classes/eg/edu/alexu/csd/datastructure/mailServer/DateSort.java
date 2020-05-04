package eg.edu.alexu.csd.datastructure.mailServer;

import java.text.ParseException;
import java.text.SimpleDateFormat;


import eg.edu.alexu.csd.datastructure.linkedList.Classes.DLinkedList;
import eg.edu.alexu.csd.datastructure.stack.Stack;

public class DateSort implements ISort{

	@Override
	public void applySort(DLinkedList list) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		if (list == null || list.size() == 0)
			return;

		int low = 0;
		int high = list.size() - 1;
		String pivot = (String) list.get((low + high) / 2);
		Stack s = new Stack();
		s.push(low);
		s.push(pivot);
		s.push(high);

		while (low < high && !s.isEmpty()) {

			high = (int) s.pop();
			pivot = (String) s.pop();
			low = (int) s.pop();

			int i = low, j = high;
			try {
				while (formatter.parse(((MailInfo) list.get(i)).date).compareTo(formatter.parse(pivot)) < 0) {
					i++;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				while (formatter.parse(((MailInfo) list.get(j)).date).compareTo(formatter.parse(pivot)) > 0) {
					j--;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
				s.push(list.get((low + j) / 2));
				s.push(j);
			}
			if (high > i) {
				s.push(i);
				s.push(list.get((i + high) / 2));
				s.push(high);
			}
		}
	}
	

}
