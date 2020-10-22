package vue.application.management.entities;

import java.util.ArrayList;

import controleur.entities.CategorieManagementControleur;
import dao.DAOFactory;
import entities.Categorie;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import vue.application.custom.controls.CategorieTableView;
import vue.application.management.Management;
import vue.application.management.UIManagement;

public class CategorieUIManagement extends UIManagement implements Management<Categorie>{

	private static CategorieUIManagement instance;
	private TableView<Categorie> table;
	
	private CategorieUIManagement() {}
	
	public static CategorieUIManagement getInstance() {
		if(instance==null) instance = new CategorieUIManagement();
		return instance;
	}

	@Override
	public TableView<Categorie> getTableModel() {
		if(table == null) table = new CategorieTableView();
		return table;
	}

	@Override
	public ArrayList<Categorie> getDatas() {
		return DAOFactory.getDAOFactory(parent.getPersistance()).getCategorieDAO().getAll();
	}
	 
	@Override
	public ArrayList<Categorie> research(Categorie categorieRecherche) {
		return DAOFactory.getDAOFactory(parent.getPersistance()).getCategorieDAO().research(categorieRecherche);
	}	

	@Override
	public Pane getActionPane(Categorie categorie, boolean modif) {
		Pane actionPane = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/management/entities/categorieForm.fxml"));
			actionPane = loader.load();
	        CategorieManagementControleur controller = loader.getController();
	        controller.setParent(parent);
	        controller.setFormMode(categorie, modif);
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
		return actionPane;
	}
	
	@Override
	public Pane getResearchPane() {
		Pane researchPane = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/management/entities/research/PanelRechercheCategorie.fxml"));
			researchPane = loader.load();
			researchControler = loader.getController();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return researchPane;
	}

	@Override
	public boolean delete(Categorie categorie) {
		return DAOFactory.getDAOFactory(parent.getPersistance()).getCategorieDAO().delete(categorie);
	}
}
