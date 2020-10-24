package controleur;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import vue.application.management.Entities;
import vue.application.management.UIManagement;

public class ManagementControleur implements Initializable{
	
	private MainControleur parent;
	private Entities dataModel;
	private ArrayList<Object> datas;
	
	private int nombreDePage;
	private int pageCourante;
	
	@FXML
	private Label labelNombreRes;
	@FXML
	private TableView<Object> dataTable;
	@FXML
	private Button boutonPagePrec;
	@FXML
	private ChoiceBox<Integer> choicebPage;
	private ChangeListener<Integer> choicebPageChangeList;
	@FXML
	private Button boutonPageSuiv;
	@FXML
	private Spinner<Integer> spinnerNombreLigne;
	
	@FXML
	private Button boutonCreer;
	@FXML
	private Button boutonDetail;
	@FXML
	private Button boutonEdit;
	@FXML
	private Button boutonDelete;
	
	@FXML
	private BorderPane panelRecherche;
	
	public void setParent(MainControleur parent) {
		this.parent = parent;
	}
	
	public void render(Entities entities) {
		spinnerNombreLigne.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 40, 10));
		this.dataModel = entities;
		((UIManagement)UIManagement.getUIManagement(dataModel)).setParent(parent);
		setModel(dataModel);
		showResearchPane();
		loadDatas(UIManagement.getUIManagement(dataModel).getDatas());
		refresh(1);
	}
	
	private void refresh(int refreshedPage) {
		pageCourante = refreshedPage;
		updateLinesDisplayed();
		disableButtonsIfNoRowSelected();
	}
	
	public void setModel(Entities entities) {
		dataTable.getColumns().clear();
		dataTable.getColumns().setAll(UIManagement.getUIManagement(entities).getTableModel().getColumns());
	}
	
	public void loadDatas(ArrayList<Object> datas) {
		this.datas = datas;
		labelNombreRes.setText(String.valueOf(this.datas.size()));
	}
	
	public void showDatas(int pageNumber) {
		dataTable.getItems().clear();
		
		int nbResultat = datas.size();
		if(nbResultat == 0) return;
		
		int nbLigneParPage = spinnerNombreLigne.getValue();
		
		int idxDebut = (pageNumber - 1) * nbLigneParPage;
		int idxFin = (pageNumber == nombreDePage) ? nbLigneParPage * (pageNumber - 1) + (nbResultat - 1)%nbLigneParPage : nbLigneParPage * pageNumber - 1; 
		for(int i = idxDebut ; i <= idxFin ; i ++)  {
			dataTable.getItems().add(datas.get(i));
		}
	}
	
	public void updateLinesDisplayed() {
		int ancienNombrePage = nombreDePage;
		setPaginationParameters();
		if(pageCourante == 1) pageCourante = 1;
		else pageCourante = nombreDePage * pageCourante / ancienNombrePage;
		showDatas(pageCourante);
		updatePageControls(pageCourante);
	}
	
	private void setPaginationParameters() {
		int nombreResultat = datas.size();
		int nombreDeLigneParPage = spinnerNombreLigne.getValue(); 
		nombreDePage = nombreResultat / nombreDeLigneParPage;
		if((nombreResultat % nombreDeLigneParPage != 0)) nombreDePage += 1;
		disableChangeListener();
		choicebPage.getItems().clear();
		for(int i = 1; i <= nombreDePage; i++) {
			choicebPage.getItems().add(i);
		}
		enableChangeListener();
	}
	
	public void boutonPagePrecClick() {
		--pageCourante;
		updatePageControls(pageCourante);
		showDatas(pageCourante);
	}
	
	public void boutonPageSuivClick() {
		++pageCourante;
		updatePageControls(pageCourante);
		showDatas(pageCourante);
	}
	
	public void choicebPageChange(int numeroPage) {
		pageCourante = numeroPage;
		updatePageControls(pageCourante);
		showDatas(pageCourante);		
	}
	
	private void updatePageControls(int numeroPage) {
		disableChangeListener();
		boutonPagePrec.setDisable(numeroPage == 1);
		boutonPageSuiv.setDisable(numeroPage == nombreDePage);
		choicebPage.getSelectionModel().select(numeroPage-1); //Index start at 0
		dataTable.getSelectionModel().clearSelection();
		disableButtonsIfNoRowSelected();
		enableChangeListener();
	}
	
	public void disableButtonsIfNoRowSelected() {
		boolean disable = false;
		if(dataTable.getSelectionModel().getSelectedIndex() == -1) disable = true;
		boutonDetail.setDisable(disable);
		boutonEdit.setDisable(disable);
		boutonDelete.setDisable(disable);
	}
	
	public void display() {
		showActionPane(dataTable.getSelectionModel().getSelectedItem(), false);
		parent.getLabelFilAriane().setText(parent.getLabelFilAriane().getText() + " > Detail");
	}
	
	public void insert() {
		showActionPane(null, false);
		parent.getLabelFilAriane().setText(parent.getLabelFilAriane().getText() + " > Creation");
	}
	
	public void edit() {
		showActionPane(dataTable.getSelectionModel().getSelectedItem(), true);
		parent.getLabelFilAriane().setText(parent.getLabelFilAriane().getText() + " > Modification");
	}
	
	private void showActionPane(Object objet, boolean bool) {
		((UIManagement) UIManagement.getUIManagement(dataModel)).setParent(parent);
		parent.getMainPane().setCenter(UIManagement.getUIManagement(dataModel).getActionPane(objet, bool));
	}

	public void delete() {
		if(dataTable.getSelectionModel().getSelectedIndex() == -1) return;
		Alert alert = new Alert(AlertType.CONFIRMATION, "Confirmer la suppression ?", ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> confirmation = alert.showAndWait();
		if(confirmation.get() == ButtonType.YES) {
			UIManagement.getUIManagement(dataModel).delete(dataTable.getSelectionModel().getSelectedItem());
			labelNombreRes.setText(String.valueOf(datas.size()));
			if((pageCourante >= nombreDePage) && (datas.size()%spinnerNombreLigne.getValue() == 0)) pageCourante = nombreDePage;;
			refresh(pageCourante);
		}
	}	
	
	public void showResearchPane() {
		panelRecherche.setCenter(UIManagement.getUIManagement(dataModel).getResearchPane());
	}
	
	public void boutonRechercheClick() {
		lancerRecherche(((UIManagement) UIManagement.getUIManagement(dataModel)).getResearch().getResearchParameters());
	}
	
	public void lancerRecherche(Object parameter) {
		loadDatas(UIManagement.getUIManagement(dataModel).research(parameter));
		refresh(1);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		choicebPageChangeList = (listePage, oldValue, newValue) -> choicebPageChange(newValue);
		enableChangeListener();
	}
	
	private void enableChangeListener() {
		choicebPage.valueProperty().addListener(choicebPageChangeList);
	}
	
	private void disableChangeListener() {
		choicebPage.valueProperty().removeListener(choicebPageChangeList);
	}
}
