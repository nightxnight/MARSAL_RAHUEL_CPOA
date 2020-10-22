package vue.application.custom.controls;

import dao.DAOFactory;
import dao.Persistance;
import entities.Produit;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProduitTableView extends TableView<Produit>{

	private Persistance persistance;
	
	public ProduitTableView(Persistance persistance) {
		this.persistance = persistance;
		TableColumn<Produit, Integer> idCol = new TableColumn<Produit, Integer>("id");	
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Produit, String> nameCol = new TableColumn<Produit, String>("nom");		
		nameCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
		TableColumn<Produit, String> descCol = new TableColumn<Produit, String>("description");
		descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		TableColumn<Produit, Double> priceCol = new TableColumn<Produit, Double>("prix");
		descCol.setMaxWidth(200);
		priceCol.setCellValueFactory(new PropertyValueFactory<>("tarif"));
		TableColumn<Produit, String> visuelCol = new TableColumn<Produit, String>("visuel");
		visuelCol.setCellValueFactory(new PropertyValueFactory<>("visuel"));
		TableColumn<Produit, String> libelleCategCol = new TableColumn<Produit, String>("categorie");
		libelleCategCol.setCellValueFactory(Produit -> {
            SimpleObjectProperty<String> property = new SimpleObjectProperty<String>();
            property.setValue(DAOFactory.getDAOFactory(persistance).getCategorieDAO().getById(Produit.getValue().getIdCategorie()).getTitre());
            return property;
        });
		
		idCol.setSortType(TableColumn.SortType.DESCENDING);

		this.getColumns().clear();
		this.getColumns().add(idCol);
		this.getColumns().add(nameCol);
		this.getColumns().add(descCol);
		this.getColumns().add(priceCol);
		this.getColumns().add(visuelCol);
		this.getColumns().add(libelleCategCol);
		this.getSelectionModel().clearSelection();
	}
}
