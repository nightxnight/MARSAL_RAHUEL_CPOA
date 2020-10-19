package controleur;

import java.util.ArrayList;
import java.util.Optional;

import javafx.fxml.FXML;
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

public class ManagementControleur {
	
	@FXML
	private Label labelNombreRes;
	@FXML
	private TableView<Object> dataTable;
	@FXML
	private Button boutonPagePrec;
	@FXML
	private ChoiceBox<Integer> choicebPage;
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
	
	private BorderPane parentPane;
	private Entities dataModel;
	private ArrayList<Object> datas;
	
	private int nombreDePage;
	private int pageCourante;
	
	public void setParentPane(BorderPane parentPane) {
		this.parentPane = parentPane;
	}
	
	public void render(Entities entities) {
		spinnerNombreLigne.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 40, 10));
		this.dataModel = entities;
		setModel(dataModel);
		loadDatas(UIManagement.getUIManagement(dataModel).getDatas());
		pageCourante = 1;
		setPaginationParameters();
		showDatas(1);
		updatePageControls(1);
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
		pageCourante = (pageCourante == 1) ? 1 : nombreDePage * pageCourante / ancienNombrePage;
		showDatas(pageCourante);
		updatePageControls(pageCourante);
	}
	
	private void setPaginationParameters() {
		int nombreResultat = datas.size();
		int nombreDeLigneParPage = spinnerNombreLigne.getValue();
		nombreDePage = nombreResultat / nombreDeLigneParPage;
		if((nombreResultat % nombreDeLigneParPage != 0)) nombreDePage += 1;
		choicebPage.getItems().clear();
		for(int i = 1; i <= nombreDePage; i++) {
			choicebPage.getItems().add(i);
		}
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
	
	public void choicebPage() {
		pageCourante = choicebPage.getValue();
		updatePageControls(pageCourante);
		showDatas(pageCourante);
	}
	
	private void updatePageControls(int numeroPage) {
		boutonPagePrec.setDisable(numeroPage == 1);
		boutonPageSuiv.setDisable(numeroPage == nombreDePage);
		choicebPage.getSelectionModel().select(numeroPage-1); //Index start at 0
		dataTable.getSelectionModel().clearSelection();
		disableButtonsIfNoRowSelected();
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
	}
	
	public void insert() {
		showActionPane(null, false);
	}
	
	public void edit() {
		showActionPane(dataTable.getSelectionModel().getSelectedItem(), true);
	}
	
	private void showActionPane(Object objet, boolean bool) {
		parentPane.setCenter(UIManagement.getUIManagement(dataModel).getActionPane(objet, bool));
	}

	public void delete() {
		if(dataTable.getSelectionModel().getSelectedIndex() == -1) return;
		Alert alert = new Alert(AlertType.CONFIRMATION, "Confirmer la suppression ?", ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> confirmation = alert.showAndWait();
		if(confirmation.get() == ButtonType.YES) {
			UIManagement.getUIManagement(dataModel).delete(dataTable.getSelectionModel().getSelectedItem());
			showDatas(pageCourante);
		}
	}	
}
