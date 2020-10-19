package vue.application.management.entities;

import java.util.ArrayList;

import entities.Client;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import vue.application.management.Management;
import vue.application.management.UIManagement;

public class ClientUIManagement extends UIManagement implements Management<Client>{

	private static ClientUIManagement instance;
	
	private ClientUIManagement() {}
	
	public static ClientUIManagement getInstance() {
		if(instance==null) instance = new ClientUIManagement();
		return instance;
	}

	@Override
	public TableView<Client> getTableModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Client> getDatas() {
		return null;
	}
	
	@Override
	public ArrayList<Client> research(Client objet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pane getActionPane(Client objet, boolean bool) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Client objet) {
		// TODO Auto-generated method stub
		return false;
	}
}
