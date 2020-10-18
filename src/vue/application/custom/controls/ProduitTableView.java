package vue.application.custom.controls;

import entities.Produit;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProduitTableView extends TableView<Produit>{

	public ProduitTableView() {
		TableColumn<Produit, Integer> idCol = new TableColumn<Produit, Integer>("id");	
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Produit, String> nameCol = new TableColumn<Produit, String>("nom");		
		nameCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
		TableColumn<Produit, String> descCol = new TableColumn<Produit, String>("description");
		descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		TableColumn<Produit, Double> priceCol = new TableColumn<Produit, Double>("prix");
		priceCol.setCellValueFactory(new PropertyValueFactory<>("tarif"));
		TableColumn<Produit, String> visuelCol = new TableColumn<Produit, String>("visuel");
		visuelCol.setCellValueFactory(new PropertyValueFactory<>("visuel"));
		TableColumn<Produit, Integer> libelleCategCol = new TableColumn<Produit, Integer>("categorie");
		libelleCategCol.setCellValueFactory(new PropertyValueFactory<>("idCategorie"));
		
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
