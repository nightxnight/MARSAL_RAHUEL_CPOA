package controleur.entities;

import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import utils.stringConverter.PrixDoubleStringConverter;
import utils.stringConverter.QuantiteIntegerStringConverter;
import vue.application.custom.alert.ConfirmationAlert;
import vue.application.custom.alert.ErrorAlert;
import vue.application.custom.alert.InfoAlert;

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
	private Label labelInfoModifDetail;
	
	@FXML
	private Button boutonCreer;
	@FXML
	private Button boutonModif;
	@FXML
	private Button boutonRetour;	
	
	@FXML
	private Label labelClientErreur;
	@FXML
	private Label labelDateErreur;
	@FXML 
	private Label labelErreurDetail;
	
	@FXML
	private Label labelProduitErreur;
	@FXML
	private Label labelQuantiteErreur;
	@FXML
	private Label labelTarifErreur;
	
	@Override 
	public void initialize(URL arg0, ResourceBundle arg1) {
		tableLigneCommande.setEditable(true);
		produitCol.setCellValueFactory(LigneCommande -> {
            SimpleObjectProperty<String> property = new SimpleObjectProperty<String>();
            try {
	            property.setValue(DAOFactory.getDAOFactory(parent.getPersistance()).getProduitDAO()
	            				  .getById(LigneCommande.getValue().getIdProduit()).getNom());
            } catch(IllegalArgumentException iae) {
            	property.setValue("inconnu");
            }
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
		datePckCommande.setValue(LocalDate.now());
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
					ErrorAlert errorAlert = new ErrorAlert("Quantite invalide", "Une quantite est postive.", parent);
					errorAlert.showAndWait();
				}
			} catch(NumberFormatException e) {
				ErrorAlert errorAlert = new ErrorAlert("Quantite invalide", "La quantite est un entier.", parent);
				errorAlert.showAndWait();
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
					ErrorAlert errorAlert = new ErrorAlert("Prix invalide", "Un prix est positif.", parent);
					errorAlert.showAndWait();
				}
			} catch(NumberFormatException e) {
				ErrorAlert errorAlert = new ErrorAlert("Prix invalide", "Un prix est un reel.", parent);
				errorAlert.showAndWait();
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
			tableLigneCommande.getColumns().get(1).setEditable(modif);
			tableLigneCommande.getColumns().get(2).setEditable(modif);
			gridPaneDetailForm.setVisible(modif);
			labelInfoModifDetail.setVisible(modif);
			fillForm(commande);
		}
	}

	@Override
	public void fillForm(Commande commande) {
		Client clientConcerne = new Client(commande.getIdClient(), "", "Introuvable", "", "", "", "", "", "", "");
		if(!choicebClient.getItems().stream().anyMatch(Client -> Client.getIdClient() == clientConcerne.getIdClient())) {
			choicebClient.getItems().add(clientConcerne);
		}
		choicebClient.getSelectionModel().select(clientConcerne);
		datePckCommande.setValue(commande.getDateCommande());
		
		ArrayList<LigneCommande> listeLigneCommande = new ArrayList<LigneCommande>(DAOFactory.getDAOFactory(parent.getPersistance()).getLigneCommandeDAO().getById(commande.getIdCommande()));
		tableLigneCommande.getItems().addAll(listeLigneCommande);
		this.commande = commande.clone();
	}

	@Override
	public void create() {
		if(!checkErrors()) {
			ConfirmationAlert alert = new ConfirmationAlert("Creation d'une commande", "La commande va etre creee, etes-vous sur ?", parent);
			Optional<ButtonType> confirmation = alert.showAndWait();
			if(confirmation.get() == alert.getValider()) {
				Commande nouvelleCommande = new Commande(datePckCommande.getValue(),
														 choicebClient.getSelectionModel().getSelectedItem().getIdClient());
				DAOFactory.getDAOFactory(parent.getPersistance()).getCommandeDAO().create(nouvelleCommande);
				
				for(int i = 0; i < tableLigneCommande.getItems().size(); i++) {
					tableLigneCommande.getItems().get(i).setIdCommande(nouvelleCommande.getIdCommande());
					DAOFactory.getDAOFactory(parent.getPersistance()).getLigneCommandeDAO().create(tableLigneCommande.getItems().get(i));
				}
				InfoAlert infoAlert = new InfoAlert("Commande creee!", "La commande de reference : \n\"" + nouvelleCommande.getIdCommande() +"\"\na ete ajoutee."
						+ "\n\nVous allez retourner sur la liste des commandes.", parent);
				infoAlert.showAndWait();
				retourPage();
				parent.getManagementControleur().getDatas().add(nouvelleCommande);
				parent.getManagementControleur().refresh(-1);
			}
		}
	}

	@Override
	public void update() {
		if(!checkErrors()) {
			ConfirmationAlert alert = new ConfirmationAlert("Modification d'une commande", "La commande va etre modifiee, etes-vous sur ?", parent);
			Optional<ButtonType> confirmation = alert.showAndWait();
			if(confirmation.get() == alert.getValider()) {
				try {
					Commande commandeModifie = new Commande(commande.getIdCommande(), datePckCommande.getValue(),
															 choicebClient.getSelectionModel().getSelectedItem().getIdClient());
					DAOFactory.getDAOFactory(parent.getPersistance()).getCommandeDAO().update(commandeModifie);
				
					ArrayList<LigneCommande> ancienneLigneCommandes = new ArrayList<LigneCommande>(DAOFactory.getDAOFactory(parent.getPersistance()).getLigneCommandeDAO().getById(commande.getIdCommande()));
					ArrayList<LigneCommande> nouvelleLigneCommande = new ArrayList<LigneCommande>(tableLigneCommande.getItems());
					for(int i = 0; i < nouvelleLigneCommande.size(); i++) {
						nouvelleLigneCommande.get(i).setIdCommande(commandeModifie.getIdCommande());
						if(ancienneLigneCommandes.contains(nouvelleLigneCommande.get(i))) {
							DAOFactory.getDAOFactory(parent.getPersistance()).getLigneCommandeDAO().update(nouvelleLigneCommande.get(i));
							ancienneLigneCommandes.remove(nouvelleLigneCommande.get(i));
						} else {
							DAOFactory.getDAOFactory(parent.getPersistance()).getLigneCommandeDAO().create(nouvelleLigneCommande.get(i));
						}			
					}
					
					for(LigneCommande elementRestant : ancienneLigneCommandes) {
						DAOFactory.getDAOFactory(parent.getPersistance()).getLigneCommandeDAO().delete(elementRestant);
					}
					InfoAlert infoAlert = new InfoAlert("Commande modifiee!", "La commande de reference : \n\"" + commandeModifie.getIdCommande() +"\"\na ete modifiee."
							+ "\n\nVous allez retourner sur la liste des commandes.", parent);
					infoAlert.showAndWait();
					retourPage();
					parent.getManagementControleur().getDatas().set(parent.getManagementControleur().getDatas().indexOf(commandeModifie), commandeModifie);
					parent.getManagementControleur().refresh();
				} catch(IllegalArgumentException iae) {
					ErrorAlert errorAlert = new ErrorAlert("Erreur lors de la modification", iae.getMessage(), parent);
					errorAlert.showAndWait();
				}
			}
		}
	}

	@Override
	public boolean checkErrors() {
		boolean erreur = false;
		
		labelClientErreur.setText("");
		if(choicebClient.getSelectionModel().getSelectedIndex() == -1) {
			labelClientErreur.setText("a selectionner.");
			erreur = true;
		}
		
		labelDateErreur.setText("");
		if(datePckCommande.getValue() == null) {
			labelDateErreur.setText("a selectionner.");
			erreur = true;
		}
		else if(datePckCommande.getValue().isAfter(LocalDate.now())) {
			labelDateErreur.setText("date inferieur ou egale a celle d'aujourd'hui");
			erreur = true;
		}
		
		labelErreurDetail.setText("");
		if(tableLigneCommande.getItems().size()==0) {
			labelErreurDetail.setText("La commande doit concerner au moins un produit.");
			erreur = true;
		}
		
		if(erreur) {
			ErrorAlert errorAlert = new ErrorAlert("Formulaire invalide", "Certains champs sont invalides,\nveuillez les corriger.", parent);
			errorAlert.showAndWait();
		}
		return erreur;
	}
	
	public void ajouterLigneDetail() {
		if(!checkDetailsErrors()) {
			tableLigneCommande.getItems().add(new LigneCommande(0, choicebProduit.getSelectionModel().getSelectedItem().getId(), 
																Integer.parseInt(edtQuantite.getText()),
																Double.parseDouble(edtPrix.getText())));
			labelErreurDetail.setText("");
		}
	}
	
	public void supprimerLigneDetail() {
		tableLigneCommande.getItems().remove(tableLigneCommande.getSelectionModel().getSelectedIndex());
		tableLigneCommande.getSelectionModel().clearSelection();
	}
	
	public boolean checkDetailsErrors() {
		boolean erreur = false;
		
		labelProduitErreur.setText("");
		if(choicebProduit.getSelectionModel().getSelectedIndex() == -1) {
			labelProduitErreur.setText("a selectionner.");
			erreur = true;
		} else if(tableLigneCommande.getItems().stream()
											   .anyMatch(ligneCommande -> ligneCommande.getIdProduit() 
													   					  == choicebProduit.getSelectionModel().getSelectedItem().getId())
				 ) {
			labelProduitErreur.setText("deja concerne.");
			erreur = true;
		}
			
		labelQuantiteErreur.setText("");
		if(edtQuantite.getText().trim().equals("")) {
			labelQuantiteErreur.setText("a saisir.");
			erreur = true;
		} else {
			try {
				if(Integer.parseInt(edtQuantite.getText().trim()) <= 0) {
					labelQuantiteErreur.setText("strictement superieur a 0.");;
					erreur = true;
				}
			} catch (Exception e) {
				labelQuantiteErreur.setText("nombre entier.");
				erreur = true;
			}
		}
		
		labelTarifErreur.setText("");
		if(edtPrix.getText().trim().equals("")) {
			labelTarifErreur.setText("a saisir.");
			erreur = true;
		}
		else {
			try {
				if(Double.parseDouble(edtPrix.getText().trim()) <= 0) {
					labelTarifErreur.setText("strictement superieur a 0.");
					erreur = true;
				}
			} catch(Exception e) {
				labelTarifErreur.setText("nombre reel.");
				erreur = true;
			}
		}
		
		if(erreur) {
			ErrorAlert errorAlert = new ErrorAlert("Formulaire invalide", "Certains champs sont invalides,\nveuillez les corriger.", parent);
			errorAlert.showAndWait();
		}
		
		return erreur;
	}

	@Override
	public void retourPage() {
		parent.showManagementPane();
		
	}

	public void setParent(MainControleur parent) {
		this.parent = parent;
	}
}
