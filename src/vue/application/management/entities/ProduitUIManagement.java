package vue.application.management.entities;

import java.util.ArrayList;

import controleur.entities.ProduitManagementControleur;
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
	public Pane getActionPane(Produit produit, boolean modif) {
		Pane actionPane = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/application/AjoutProduit.fxml"));
			actionPane = loader.load();
	        ProduitManagementControleur controller = loader.getController();
	        controller.setFormMode(produit, modif);
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
		return actionPane;
	}
}
