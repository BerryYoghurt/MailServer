package eg.edu.alexu.csd.datastructure.mailServer;

import java.io.File;

public class Mail implements IMail{
	
	private File path;
	private IContact composer;
	
	@Override
	public void setSubject(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBody(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean changePath(IFolder newFolder) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addReceiver(IContact receiver) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IContact removeReceiver(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IContact[] getReceivers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addAttachement(IAttachement attachement) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IAttachement removeAttachement(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IAttachement[] getAttachements() {
		// TODO Auto-generated method stub
		return null;
	}

}
