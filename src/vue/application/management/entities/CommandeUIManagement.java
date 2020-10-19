package vue.application.management.entities;

import java.util.ArrayList;

import entities.Commande;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import vue.application.management.Management;
import vue.application.management.UIManagement;

public class CommandeUIManagement extends UIManagement implements Management<Commande>{
	
	private static CommandeUIManagement instance;
	
	private CommandeUIManagement() {}
	
	public static CommandeUIManagement getInstance() {
		if(instance==null) instance = new CommandeUIManagement();
		return instance;
	}

	@Override
	public TableView<Commande> getTableModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Commande> getDatas() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<Commande> research(Commande objet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pane getActionPane(Commande objet, boolean bool) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Commande objet) {
		// TODO Auto-generated method stub
		return false;
	}
}
