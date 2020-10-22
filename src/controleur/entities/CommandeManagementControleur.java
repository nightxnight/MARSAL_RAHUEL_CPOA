package controleur.entities;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import controleur.MainControleur;
import dao.DAO;
import dao.DAOFactory;
import entities.Client;
import entities.Commande;
import entities.LigneCommande;
import entities.Produit;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class CommandeManagementControleur implements Initializable, ImplManagementControleur<Commande>{
	
	private MainControleur parent;
	private Commande commande;
	
	@FXML
	private ChoiceBox<Client> choicebClient;
	@FXML
	private DatePicker datePckCommande;
	
	@FXML
	private GridPane gridPaneDetailForm;
	@FXML
	private TableView<LigneCommande> tableLigneCommande;
	@FXML
	private ChoiceBox<Produit> choicebProduit;
	@FXML
	private TextField edtQuantite;
	@FXML
	private TextField edtPrix;
	@FXML
	private Button boutonDetailAjouter;
	@FXML
	private Button boutonDetailSupprimer;
	
	@FXML
	private Button boutonCreer;
	@FXML
	private Button boutonModif;
	@FXML
	private Button boutonRetour;	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	@Override
	public void setFormMode(Commande commande, boolean modif) {
		if(commande == null) boutonModif.setVisible(false);
		else {
			boutonCreer.setVisible(false);
			boutonModif.setVisible(modif);
			choicebClient.setDisable(!modif);
			datePckCommande.setDisable(!modif);
			choicebProduit.setDisable(!modif);
			gridPaneDetailForm.setVisible(modif);
			fillForm(commande);
		}
	}

	@Override
	public void fillForm(Commande commande) {
		choicebClient.getSelectionModel().select(new Client(commande.getIdClient(), "", "", "", "", "", "", "", "", ""));
		datePckCommande.setValue(commande.getDateCommande());
		
		ArrayList<LigneCommande> listeLigneCommande = DAOFactory.getDAOFactory(parent.getPersistance()).getLigneCommandeDAO().getById(commande.getIdCommande());
		for(int i = 0; i < listeLigneCommande.size(); i++) {
			tableLigneCommande.getItems().add(listeLigneCommande.get(i));
		}
		this.commande = commande;
	}

	@Override
	public void create() {
		if(checkErrors()) {
			Alert alert = new Alert(AlertType.CONFIRMATION, "La commande va etre ajoutee.\netes-vous sur ?\n", ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> confirmation = alert.showAndWait();
			if(confirmation.get() == ButtonType.YES) {
				Commande nouvelleCommande = new Commande(datePckCommande.getValue(),
														 choicebClient.getSelectionModel().getSelectedItem().getIdClient());
				DAOFactory.getDAOFactory(parent.getPersistance()).getCommandeDAO().create(nouvelleCommande);
				
				for(int i = 0; i < tableLigneCommande.getItems().size(); i++) {
					tableLigneCommande.getItems().get(i).setIdCommande(nouvelleCommande.getIdCommande());
					DAOFactory.getDAOFactory(parent.getPersistance()).getLigneCommandeDAO().create(tableLigneCommande.getItems().get(i));
				}
			}
		}
	}

	@Override
	public void update() {
		//TODO update une commande
	}

	@Override
	public boolean checkErrors() {
		String erreurs = "";
		
		if(choicebClient.getSelectionModel().getSelectedIndex() == -1) erreurs += "Le client concerne par la commande doit etre selectionner.\n";
		
		if(datePckCommande.getValue() == null) erreurs += "La date de la commande doit etre indiquer.\n";
		
		if(tableLigneCommande.getItems().size()==0) erreurs += "La commande doit au moins concerner un produit dans son detail.\n";
		
		if(!erreurs.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR, erreurs, ButtonType.OK);
			alert.showAndWait();
		}
		return erreurs.isEmpty();
	}
	
	public void ajouterLigneDetail() {
		if(checkDetailsErrors()) {
			tableLigneCommande.getItems().add(new LigneCommande(0, choicebProduit.getSelectionModel().getSelectedItem().getId(), 
																Integer.parseInt(edtQuantite.getText()),
																Double.parseDouble(edtPrix.getText())));
		}
	}
	
	public void supprimerLigneDetail() {
		tableLigneCommande.getItems().remove(tableLigneCommande.getSelectionModel().getSelectedIndex());
	}
	
	public boolean checkDetailsErrors() {
		String erreurs = "";
		if(choicebProduit.getSelectionModel().getSelectedIndex() == -1) erreurs += "Un produit doit etre selectionner pour ajouter un detail.\n";
		
		if(edtQuantite.getText().trim().equals("")) erreurs += "La quantite doit etre preciser.\n";
		else {
			try {
				if(Integer.parseInt(edtQuantite.getText().trim()) <= 0) erreurs += "La quantite est strictement superieur a 0.\n";
			} catch (Exception e) {
				erreurs += "Le quantite commandee est un entier.\n";
			}
		}
		
		if(edtPrix.getText().trim().equals("")) erreurs += "Le prix doit etre preciser.\n";
		else {
			try {
				if(Double.parseDouble(edtPrix.getText().trim()) <= 0) erreurs += "Le prix est strictement superieur a 0.\n";
			} catch(Exception e) {
				erreurs += "Le prix a l'achat est un reel.\n";
			}
		}
		
		if(!erreurs.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR, erreurs, ButtonType.OK);
			alert.showAndWait();
		}
		
		return erreurs.isEmpty();
	}

	@Override
	public void retourPage() {
		parent.showCommandes();
		
	}

	public void setParent(MainControleur parent) {
		this.parent = parent;
	}

}
