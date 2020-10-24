package vue.application.custom.controls;

import java.sql.Date;
import java.text.SimpleDateFormat;
import dao.DAOFactory;
import dao.Persistance;
import entities.Client;
import entities.Commande;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CommandeTableView extends TableView<Commande> {

	private Persistance persistance;
	
	public CommandeTableView(Persistance persistance) {
		this.persistance = persistance;
		TableColumn<Commande, Integer> idComCol = new TableColumn<Commande, Integer>("numero");	
		idComCol.setCellValueFactory(new PropertyValueFactory<>("idCommande"));
		TableColumn<Commande, String> dateCol = new TableColumn<Commande, String>("date commande");		
		dateCol.setCellValueFactory(Commande -> {
			SimpleObjectProperty<String> property = new SimpleObjectProperty<String>();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
			property.setValue(formatter.format(Date.valueOf(Commande.getValue().getDateCommande())));
			return property;
		});
		TableColumn<Commande, String> idClientCol = new TableColumn<Commande, String>("client");
		idClientCol.setCellValueFactory(Commande -> {
            SimpleObjectProperty<String> property = new SimpleObjectProperty<String>();
            String libelleClient = "";
            try {
            	Client clientConcerne = DAOFactory.getDAOFactory(this.persistance).getClientDAO().getById(Commande.getValue().getIdClient());
            	libelleClient = clientConcerne.getIdClient() + " - " + clientConcerne.getNom() + " " + clientConcerne.getPrenom();
            } catch(IllegalArgumentException iae) {
            	libelleClient = "introuvable";
            }
            property.setValue(libelleClient);
            return property;
        });
		
		idComCol.setSortType(TableColumn.SortType.DESCENDING);

		this.getColumns().clear();
		this.getColumns().add(idComCol);
		this.getColumns().add(idClientCol);
		this.getColumns().add(dateCol);

		this.getSelectionModel().clearSelection();
	}
}
