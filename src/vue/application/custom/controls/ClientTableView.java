package vue.application.custom.controls;

import entities.Client;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClientTableView extends TableView<Client>{

	public ClientTableView() {
		TableColumn<Client, Integer> idCol = new TableColumn<Client, Integer>("id");	
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

		
		idCol.setSortType(TableColumn.SortType.DESCENDING);

		this.getColumns().clear();
		this.getColumns().add(idCol);
		this.getColumns().add(nomCol);
		this.getColumns().add(prenomCol);
		this.getColumns().add(identCol);
		
		adresseCol.getColumns().addAll(adrNumeroCol, adrVoieCol, adrCodePostalCol, adrVilleCol, adrPaysCol);
		
		this.getColumns().add(adresseCol);
		this.getSelectionModel().clearSelection();
	}
}
