package controleur.entities;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import controleur.MainControleur;
import dao.DAOFactory;
import entities.Client;
import entities.Commande;
import entities.LigneCommande;
import entities.Produit;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import vue.application.custom.utils.PrixDoubleStringConverter;
import vue.application.custom.utils.QuantiteIntegerStringConverter;

public class CommandeManagementControleur implements ImplManagementControleur<Commande>, Initializable{
	
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
	private TableColumn<LigneCommande, String> produitCol;
	@FXML
	private TableColumn<LigneCommande, Integer> quantiteCol;
	@FXML 
	private TableColumn<LigneCommande, Double> prixAchatCol;
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
		tableLigneCommande.setEditable(true);
		produitCol.setCellValueFactory(LigneCommande -> {
            SimpleObjectProperty<String> property = new SimpleObjectProperty<String>();
            property.setValue(DAOFactory.getDAOFactory(parent.getPersistance()).getProduitDAO()
            				  .getById(LigneCommande.getValue().getIdProduit()).getNom());
            return property;
        });
		produitCol.setPrefWidth(150);
		tableLigneCommande.getColumns().set(0, produitCol);
		
		quantiteCol.setCellValueFactory(new PropertyValueFactory<>("quantite"));
		quantiteCol.setCellFactory(TextFieldTableCell.<LigneCommande, Integer> forTableColumn(new QuantiteIntegerStringConverter()));
		quantiteCol.setPrefWidth(75);
		tableLigneCommande.getColumns().set(1, quantiteCol);
		
		prixAchatCol.setCellValueFactory(new PropertyValueFactory<>("tarifUnitaire"));
		prixAchatCol.setCellFactory(TextFieldTableCell.<LigneCommande, Double> forTableColumn(new PrixDoubleStringConverter()));
		prixAchatCol.setPrefWidth(72);
		tableLigneCommande.getColumns().set(2, prixAchatCol);
		
		tableLigneCommande.getSelectionModel().selectedItemProperty().addListener((listeLigneCommande, ancienneSelection, nouvelleSelection) -> {
		    boutonDetailSupprimer.setDisable(nouvelleSelection == null);
		});
	}
	
	public void quantiteColEdit(TableColumn.CellEditEvent<LigneCommande, Integer> celluleEdite) {
		LigneCommande ligneCommande = tableLigneCommande.getSelectionModel().getSelectedItem();
		if(celluleEdite.getNewValue() == null) {
			ligneCommande.setQuantite(celluleEdite.getOldValue());
		} else {
			int valeur = celluleEdite.getOldValue();
			try {
				valeur = celluleEdite.getNewValue();
				if(valeur <= 0) {
					valeur = celluleEdite.getOldValue();
					Alert alert = new Alert(AlertType.ERROR, "Une quantite est strictement positive", ButtonType.OK);
					alert.showAndWait();
				}
			} catch(NumberFormatException e) {
				Alert alert = new Alert(AlertType.ERROR, "Une quantite est un entier", ButtonType.OK);
				alert.showAndWait();
			}
			ligneCommande.setQuantite(valeur);
		}
		tableLigneCommande.refresh();
	}
	
	public void prixColEdit(TableColumn.CellEditEvent<LigneCommande, Double> celluleEdite) {
		LigneCommande ligneCommande = tableLigneCommande.getSelectionModel().getSelectedItem();
		if(celluleEdite.getNewValue() == null) {
			ligneCommande.setTarifUnitaire(celluleEdite.getOldValue());
		} else {
			double valeur = celluleEdite.getOldValue();
			try {
				valeur = celluleEdite.getNewValue();
				if(valeur <= 0) {
					valeur = celluleEdite.getOldValue();
					Alert alert = new Alert(AlertType.ERROR, "Un prix est strictement positif.", ButtonType.OK);
					alert.showAndWait();
				}
			} catch(NumberFormatException e) {
				Alert alert = new Alert(AlertType.ERROR, "Un prix est un reel", ButtonType.OK);
				alert.showAndWait();
			}
			ligneCommande.setTarifUnitaire(valeur);
		}
		tableLigneCommande.refresh();
	}
	
	public void initializeComponents() {
		choicebClient.getItems().addAll(DAOFactory.getDAOFactory(parent.getPersistance()).getClientDAO().getAll());
		choicebClient.setConverter(new StringConverter<Client>() {
			@Override
			public Client fromString(String denomination) {
				return choicebClient.getItems().stream().filter(client -> client.getNom().concat(" ").concat(client.getPrenom()).equals(denomination)).findFirst().orElse(null);
			}

			@Override
			public String toString(Client objet) {
				return objet.getNom() + " " + objet.getPrenom();
			}
		});
		choicebClient.getSelectionModel().clearSelection();
		
		choicebProduit.getItems().addAll(DAOFactory.getDAOFactory(parent.getPersistance()).getProduitDAO().getAll());
		choicebProduit.setConverter(new StringConverter<Produit>() {
			@Override
			public Produit fromString(String nom) {
				return choicebProduit.getItems().stream().filter(produit -> produit.getNom().equals(nom)).findFirst().orElse(null);
			}

			@Override
			public String toString(Produit objet) {
				return objet.getNom();
			}
		});
		choicebProduit.valueProperty().addListener((listePage, oldValue, newValue) -> edtPrix.setText(String.valueOf(newValue.getTarif())));
		choicebProduit.getSelectionModel().clearSelection();
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
		
		ArrayList<LigneCommande> listeLigneCommande = new ArrayList<LigneCommande>(DAOFactory.getDAOFactory(parent.getPersistance()).getLigneCommandeDAO().getById(commande.getIdCommande()));
		tableLigneCommande.getItems().addAll(listeLigneCommande);
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
		if(checkErrors()) {
			Alert alert = new Alert(AlertType.CONFIRMATION, "La commande va etre ajoutee.\netes-vous sur ?\n", ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> confirmation = alert.showAndWait();
			if(confirmation.get() == ButtonType.YES) {
				Commande commandeModifie = new Commande(commande.getIdCommande(), datePckCommande.getValue(),
														 choicebClient.getSelectionModel().getSelectedItem().getIdClient());
				DAOFactory.getDAOFactory(parent.getPersistance()).getCommandeDAO().update(commandeModifie);
			
				ArrayList<LigneCommande> ancienneLigneCommandes = new ArrayList<LigneCommande>(DAOFactory.getDAOFactory(parent.getPersistance()).getLigneCommandeDAO().getById(commande.getIdCommande()));
				ArrayList<LigneCommande> nouvelleLigneCommande = new ArrayList<LigneCommande>(tableLigneCommande.getItems());
				System.out.println("Element dans ancienne liste : " + ancienneLigneCommandes.size());
				for(int i = 0; i < nouvelleLigneCommande.size(); i++) {
					nouvelleLigneCommande.get(i).setIdCommande(commandeModifie.getIdCommande());
					if(ancienneLigneCommandes.contains(nouvelleLigneCommande.get(i))) {
						DAOFactory.getDAOFactory(parent.getPersistance()).getLigneCommandeDAO().update(nouvelleLigneCommande.get(i));
						ancienneLigneCommandes.remove(nouvelleLigneCommande.get(i));
						System.out.println("Modif");
					} else {
						System.out.println("Ajout");
						DAOFactory.getDAOFactory(parent.getPersistance()).getLigneCommandeDAO().create(nouvelleLigneCommande.get(i));
					}			
				}
				
				System.out.println("Element restant ancienne liste : " + ancienneLigneCommandes.size());
				for(LigneCommande elementRestant : ancienneLigneCommandes) {
					DAOFactory.getDAOFactory(parent.getPersistance()).getLigneCommandeDAO().delete(elementRestant);
				}
			}
		}
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
		tableLigneCommande.getSelectionModel().clearSelection();
	}
	
	public boolean checkDetailsErrors() {
		String erreurs = "";
		if(choicebProduit.getSelectionModel().getSelectedIndex() == -1) erreurs += "Un produit doit etre selectionner pour ajouter un detail.\n";
		if(tableLigneCommande.getItems().stream()
										.anyMatch(ligneCommande -> ligneCommande.getIdProduit() == choicebProduit.getSelectionModel().getSelectedItem().getId()))
			erreurs += "On ne peut pas ajouter deux fois un meme produit à la commande.\n";
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
