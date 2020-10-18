package vue.application.custom.controls;

import java.time.LocalDate;

import entities.Commande;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CommandeTableView extends TableView<Commande> {

	public CommandeTableView() {
		TableColumn<Commande, Integer> idComCol = new TableColumn<Commande, Integer>("id commande");	
		idComCol.setCellValueFactory(new PropertyValueFactory<>("idCommande"));
		TableColumn<Commande, LocalDate> dateCol = new TableColumn<Commande, LocalDate>("date commande");		
		dateCol.setCellValueFactory(new PropertyValueFactory<>("dateCommande"));
		TableColumn<Commande, String> idClientCol = new TableColumn<Commande, String>("id client");
		idClientCol.setCellValueFactory(new PropertyValueFactory<>("idClient"));
		
		idComCol.setSortType(TableColumn.SortType.DESCENDING);

		this.getColumns().clear();
		this.getColumns().add(idComCol);
		this.getColumns().add(idClientCol);
		this.getColumns().add(dateCol);

		this.getSelectionModel().clearSelection();
	}
}
