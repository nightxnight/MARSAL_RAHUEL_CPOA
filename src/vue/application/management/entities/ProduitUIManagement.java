package vue.application.management.entities;

import java.util.ArrayList;

import controleur.entities.ProduitManagementControleur;
import controleur.research.RechercheProduitControleur;
import dao.DAOFactory;
import dao.Persistance;
import entities.Produit;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import vue.application.custom.controls.ProduitTableView;
import vue.application.management.Management;
import vue.application.management.UIManagement;

public class ProduitUIManagement extends UIManagement implements Management<Produit>{

	private static ProduitUIManagement instance;
	private TableView<Produit> table;
	
	private RechercheProduitControleur rechercheControleur;
	
	private ProduitUIManagement() {}
	
	public static ProduitUIManagement getInstance() {
		if(instance==null) instance = new ProduitUIManagement();
		return instance;
	}

	@Override
	public TableView<Produit> getTableModel() {
		if(table == null) table = new ProduitTableView();
		return table;
	}

	@Override
	public ArrayList<Produit> getDatas() {
		return DAOFactory.getDAOFactory(Persistance.LISTEMEMOIRE).getProduitDAO().getAll();
	}
	
	@Override
	public ArrayList<Produit> research() {
		return DAOFactory.getDAOFactory(Persistance.LISTEMEMOIRE).getProduitDAO().research(rechercheControleur.getResearchParameter());
	}

	@Override
	public Pane getActionPane(Produit produit, boolean modif) {
		Pane actionPane = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/management/entities/produitForm.fxml"));
			actionPane = loader.load();
	        ProduitManagementControleur controller = loader.getController();
	        controller.setFormMode(produit, modif);
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
		return actionPane;
	}
	
	@Override
	public Pane getResearchPane() {
		Pane researchPane = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/management/entities/research/PanelRechercheProduit.fxml"));
			researchPane = loader.load();
			rechercheControleur = loader.getController();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return researchPane;
	}

	@Override
	public boolean delete(Produit objet) {
		return DAOFactory.getDAOFactory(Persistance.LISTEMEMOIRE).getProduitDAO().delete(objet);
	}
}
