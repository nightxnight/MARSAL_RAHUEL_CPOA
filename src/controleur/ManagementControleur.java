package controleur;

import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.Binding;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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
	private Button boutonCreer;
	@FXML
	private Button boutonDetail;
	@FXML
	private Button boutonEdit;
	@FXML
	private Button boutonDelete;
	
	private BorderPane parentPane;
	private Entities dataModel;
	
	public void setParentPane(BorderPane parentPane) {
		this.parentPane = parentPane;
	}
	
	public void setModel(Entities entities) {
		this.dataModel = entities;
		
		dataTable.getColumns().clear();
		dataTable.getColumns().setAll(UIManagement.getUIManagement(dataModel).getTableModel().getColumns());
		
		dataTable.getItems().addAll(UIManagement.getUIManagement(dataModel).getDatas());
		
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
		
	}
	
	
	
}
