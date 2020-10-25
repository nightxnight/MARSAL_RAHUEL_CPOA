package vue.application.custom.controls.tableview;

import entities.Categorie;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CategorieTableView extends TableView<Categorie>{
	
	public CategorieTableView() {
	TableColumn<Categorie, Integer> idCol = new TableColumn<Categorie, Integer>("numero");	
	idCol.setCellValueFactory(new PropertyValueFactory<>("idCategorie"));
	TableColumn<Categorie, String> titreCol = new TableColumn<Categorie, String>("titre");		
	titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
	TableColumn<Categorie, String> visuelCol = new TableColumn<Categorie, String>("visuel");
	visuelCol.setCellValueFactory(new PropertyValueFactory<>("visuel"));
	
	idCol.setSortType(TableColumn.SortType.DESCENDING);

	this.getColumns().clear();
	this.getColumns().add(idCol);
	this.getColumns().add(titreCol);
	this.getColumns().add(visuelCol);
	
	for(int i = 0; i < this.getColumns().size(); i++) {
		this.getColumns().get(i).setSortable(false);
	}

	this.getSelectionModel().clearSelection();
	}
}
