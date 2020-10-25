package vue.application.custom.controls.tableview;

import java.time.LocalDate;

import controleur.MainControleur;
import controleur.research.RechercheCommandeControleur;
import entities.Client;
import entities.Commande;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import vue.application.management.Entities;
import vue.application.management.UIManagement;
import vue.application.management.entities.CommandeUIManagement;

public class ClientTableView extends TableView<Client>{

	public ClientTableView(MainControleur mainControleur) {
		TableColumn<Client, Integer> idCol = new TableColumn<Client, Integer>("numero");	
		idCol.setCellValueFactory(new PropertyValueFactory<>("idClient"));
		TableColumn<Client, String> nomCol = new TableColumn<Client, String>("nom");		
		nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
		TableColumn<Client, String> prenomCol = new TableColumn<Client, String>("prenom");		
		prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
		TableColumn<Client, String> identCol = new TableColumn<Client, String>("identifiant");	
		identCol.setCellValueFactory(new PropertyValueFactory<>("identifiant"));
		
		TableColumn<Client, String> adresseCol = new TableColumn<Client, String>("adresse");		
		adresseCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
		TableColumn<Client, String> adrNumeroCol = new TableColumn<Client, String>("numero");		
		adrNumeroCol.setCellValueFactory(new PropertyValueFactory<>("adrNumero"));
		TableColumn<Client, String> adrVoieCol = new TableColumn<Client, String>("voie");		
		adrVoieCol.setCellValueFactory(new PropertyValueFactory<>("adrVoie"));
		TableColumn<Client, String> adrCodePostalCol = new TableColumn<Client, String>("code postal");		
		adrCodePostalCol.setCellValueFactory(new PropertyValueFactory<>("adrCodePostal"));
		TableColumn<Client, String> adrVilleCol = new TableColumn<Client, String>("ville");		
		adrVilleCol.setCellValueFactory(new PropertyValueFactory<>("adrVille"));
		TableColumn<Client, String> adrPaysCol = new TableColumn<Client, String>("pays");		
		adrPaysCol.setCellValueFactory(new PropertyValueFactory<>("adrPays"));
		
		TableColumn<Client, Button> commandeCol = new TableColumn<Client, Button>("commandes");
		commandeCol.setCellValueFactory(Client -> {
            SimpleObjectProperty<Button> property = new SimpleObjectProperty<Button>();
            Button boutonCommande = new Button("Voir");
            boutonCommande.setMaxWidth(Double.MAX_VALUE);
            boutonCommande.setOnAction(new EventHandler<ActionEvent>() {
     
				@Override
				public void handle(ActionEvent arg0) {
					mainControleur.showCommandes();
					mainControleur.getManagementControleur().lancerRecherche(new Commande(-1, LocalDate.of(1, 1, 1), Client.getValue().getIdClient()));
					((RechercheCommandeControleur)((CommandeUIManagement)UIManagement.getUIManagement(Entities.COMMANDE)).getResearch()).getEdtIdClient().setText(String.valueOf(Client.getValue().getIdClient()));
				}
            	
            });
            property.setValue(boutonCommande);
            return property;
        });
		
		idCol.setSortType(TableColumn.SortType.DESCENDING);

		this.getColumns().clear();
		this.getColumns().add(idCol);
		this.getColumns().add(nomCol);
		this.getColumns().add(prenomCol);
		this.getColumns().add(identCol);
		
		adresseCol.getColumns().addAll(adrNumeroCol, adrVoieCol, adrCodePostalCol, adrVilleCol, adrPaysCol);
		
		this.getColumns().add(adresseCol);
		this.getColumns().add(commandeCol);
		
		for(int i = 0; i < this.getColumns().size(); i++) {
			this.getColumns().get(i).setSortable(false);
		}
		this.getSelectionModel().clearSelection();
	}
}
