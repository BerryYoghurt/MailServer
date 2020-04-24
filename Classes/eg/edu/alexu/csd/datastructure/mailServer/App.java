package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;

import eg.edu.alexu.csd.datastructure.linkedList.Interfaces.ILinkedList;

public class App implements IApp{
	protected DataBase db;
	protected File systemFile;
	
	@Override
	public boolean signin(String email, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean signup(IContact contact) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setViewingOptions(IFolder folder, IFilter filter, ISort sort) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IMail[] listEmails(int page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteEmails(ILinkedList mails) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveEmails(ILinkedList mails, IFolder des) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean compose(IMail email) {
		// TODO Auto-generated method stub
		return false;
	}

}
