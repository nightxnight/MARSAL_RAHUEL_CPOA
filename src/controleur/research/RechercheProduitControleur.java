package controleur.research;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.DAOFactory;
import dao.Persistance;
import entities.Categorie;
import entities.Produit;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class RechercheProduitControleur implements Initializable, RechercheControleur<Produit>{

	@FXML 
	private TextField edtNom;
	@FXML
	private Label labelPrix;
	@FXML
	private Slider sliderPrix;
	private ChangeListener<? super Number> sliderChangeList;
	@FXML
	private ChoiceBox<Categorie> choicebCategorie;
	
	@Override
	public Produit getResearchParameters() {
		String nomProduit = edtNom.getText().trim();		
		int prixProduit = (int) sliderPrix.getValue();
		int numeroCategorie = choicebCategorie.getSelectionModel().getSelectedItem().getIdCategorie();		
		return new Produit(nomProduit, "", prixProduit, "", numeroCategorie);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeSliderPrix();
		initializeChoicebCategorie();
	}
	
	private void initializeSliderPrix() {
		sliderPrix.setMin(5);
		sliderPrix.setMax(200); //On a des pieces de collection dans la boutique!
		sliderPrix.setMajorTickUnit(25);
		sliderPrix.setMinorTickCount(5);
		sliderPrix.setBlockIncrement(5);
		sliderChangeList = (priceRange, oldPrice, newPrice) -> {
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(0);
			labelPrix.setText(String.valueOf(df.format(newPrice)) + " €"); 
		};
		sliderPrix.valueProperty().addListener(sliderChangeList);
		sliderPrix.setValue(sliderPrix.getMax());
	}
	//FIXME Il faut faire parvenir la persistance ici
	private void initializeChoicebCategorie() {
		ArrayList<Categorie> listeCategorie = new ArrayList<Categorie>();
		listeCategorie.add(new Categorie(-1, "Non selectionnée", ""));
		listeCategorie.addAll(DAOFactory.getDAOFactory(Persistance.LISTEMEMOIRE).getCategorieDAO().getAll());
		choicebCategorie.getItems().addAll(listeCategorie);
		choicebCategorie.setConverter(new StringConverter<Categorie>() {
			@Override
			public Categorie fromString(String nom) {
				return choicebCategorie.getItems().stream().filter(categ -> categ.getTitre().equals(nom)).findFirst().orElse(null);
			}

			@Override
			public String toString(Categorie objet) {
				return objet.getTitre();
			}
		});
		choicebCategorie.getSelectionModel().selectFirst();
	}
}
