package vue.application.management.entities;

import java.util.ArrayList;

import controleur.entities.ClientManagementControleur;
import dao.DAOFactory;
import dao.Persistance;
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
        return DAOFactory.getDAOFactory(Persistance.LISTEMEMOIRE).getClientDAO().getAll();
    }

    @Override
    public ArrayList<Client> research() {
        return DAOFactory.getDAOFactory(Persistance.LISTEMEMOIRE).getClientDAO().getAll();
    }

    @Override
    public Pane getActionPane(Client objet, boolean bool) {
        Pane actionPane = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/management/entities/clientForm.fxml"));
            actionPane = loader.load();
            ClientManagementControleur controller = loader.getController();
            controller.setFormMode(objet, bool);
           } catch (Exception e) {
               e.printStackTrace();
           }
        return actionPane;
    }
    
	@Override
	public Pane getResearchPane() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public boolean delete(Client objet) {
        return DAOFactory.getDAOFactory(Persistance.LISTEMEMOIRE).getClientDAO().delete(objet);

    }
}