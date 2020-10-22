package vue.application.management.entities;

import java.util.ArrayList;

import controleur.entities.ClientManagementControleur;
import dao.DAOFactory;
import entities.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import vue.application.custom.controls.ClientTableView;
import vue.application.management.Management;
import vue.application.management.UIManagement;

public class ClientUIManagement extends UIManagement implements Management<Client>{
	
	private static ClientUIManagement instance;
    private TableView<Client> table;

    private ClientUIManagement() {}

    public static ClientUIManagement getInstance() {
        if(instance==null) instance = new ClientUIManagement();
        return instance;
    }

    @Override
    public TableView<Client> getTableModel() {
        if(table == null) table = new ClientTableView();
        return table;
    }

    @Override
    public ArrayList<Client> getDatas() {
        return DAOFactory.getDAOFactory(parent.getPersistance()).getClientDAO().getAll();
    }

    @Override
    public ArrayList<Client> research(Client clientRecherche) {
        return DAOFactory.getDAOFactory(parent.getPersistance()).getClientDAO().research(clientRecherche);
    }

    @Override
    public Pane getActionPane(Client objet, boolean bool) {
        Pane actionPane = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/management/entities/clientForm.fxml"));
            actionPane = loader.load();
            ClientManagementControleur controler = loader.getController();
            controler.setParent(parent);
            controler.setFormMode(objet, bool);
           } catch (Exception e) {
               e.printStackTrace();
           }
        return actionPane;
    }
    
	@Override
	public Pane getResearchPane() {
		Pane researchPane = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/management/entities/research/PanelRechercheClient.fxml"));
			researchPane = loader.load();
			researchControler = loader.getController();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return researchPane;
	}

    @Override
    public boolean delete(Client objet) {
        return DAOFactory.getDAOFactory(parent.getPersistance()).getClientDAO().delete(objet);
    }
}