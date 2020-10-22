package vue.application.management.entities;

import java.util.ArrayList;

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
	
	//FIXME parametre de recherche 
	@Override
	public ArrayList<Categorie> research(Categorie categorieRecherche) {
		return DAOFactory.getDAOFactory(parent.getPersistance()).getCategorieDAO().research(categorieRecherche);
	}	

	@Override
	public Pane getActionPane(Categorie objet, boolean bool) {
		Pane actionPane = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/management/entities/categorieForm.fxml"));
			actionPane = loader.load();
			/*
	        ControleurAjoutCategorie controller = loader.getController();
	        controller.setProduit(objet);
	        controller.setModif(bool);
	        */
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
		return actionPane;
	}
	
	@Override
	public Pane getResearchPane() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Categorie categorie) {
		return DAOFactory.getDAOFactory(parent.getPersistance()).getCategorieDAO().delete(categorie);
	}
}
