package vue.application.management.entities;

import java.util.ArrayList;

import controleur.entities.CommandeManagementControleur;
import dao.DAOFactory;
import entities.Commande;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import vue.application.custom.controls.tableview.CommandeTableView;
import vue.application.management.Management;
import vue.application.management.UIManagement;

public class CommandeUIManagement extends UIManagement implements Management<Commande>{
	
	private static CommandeUIManagement instance;
	private CommandeManagementControleur controleur;
	
	private CommandeUIManagement() {}
	
	public static CommandeUIManagement getInstance() {
		if(instance==null) instance = new CommandeUIManagement();
		return instance;
	}

	@Override
	public TableView<Commande> getTableModel() {
		return new CommandeTableView(parent);
	}

	@Override
	public ArrayList<Commande> getDatas() {
		return DAOFactory.getDAOFactory(parent.getPersistance()).getCommandeDAO().getAll();
	}

	@Override
	public ArrayList<Commande> research(Commande commandeRecherche) {
		return DAOFactory.getDAOFactory(parent.getPersistance()).getCommandeDAO().research(commandeRecherche);
	}

	@Override
	public Pane getActionPane(Commande commande, boolean modif) {
		Pane actionPane = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/management/entities/commandeForm.fxml"));
			actionPane = loader.load();
			controleur = loader.getController();
			controleur.setParent(parent);
			controleur.initializeComponents();
			controleur.setFormMode(commande, modif);
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
		return actionPane;
	}
	
	@Override
	public Pane getResearchPane() {
		Pane researchPane = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/management/entities/research/PanelRechercheCommande.fxml"));
			researchPane = loader.load();
			researchControler = loader.getController();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return researchPane;
	}

	@Override
	public boolean delete(Commande commande) {
		return DAOFactory.getDAOFactory(parent.getPersistance()).getCommandeDAO().delete(commande);
	}
}
