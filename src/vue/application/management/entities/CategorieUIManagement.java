package vue.application.management.entities;

import java.util.ArrayList;

import dao.DAOFactory;
import dao.Persistance;
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
		return DAOFactory.getDAOFactory(Persistance.LISTEMEMOIRE).getCategorieDAO().getAll();
	}

	@Override
	public Pane getActionPane(Categorie objet, boolean bool) {
		Pane actionPane = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/application/AjoutCategorie.fxml"));
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
}
