package vue.application.custom.controls.tableview;

import controleur.MainControleur;
import dao.DAOFactory;
import entities.Produit;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProduitTableView extends TableView<Produit>{
	
	public ProduitTableView(MainControleur mainControleur) {
		TableColumn<Produit, Integer> idCol = new TableColumn<Produit, Integer>("numero");	
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Produit, String> nameCol = new TableColumn<Produit, String>("nom");		
		nameCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
		TableColumn<Produit, String> descCol = new TableColumn<Produit, String>("description");
		descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		TableColumn<Produit, Double> priceCol = new TableColumn<Produit, Double>("prix");
		descCol.setPrefWidth(200);
		priceCol.setCellValueFactory(new PropertyValueFactory<>("tarif"));
		TableColumn<Produit, String> visuelCol = new TableColumn<Produit, String>("visuel");
		visuelCol.setCellValueFactory(new PropertyValueFactory<>("visuel"));
		TableColumn<Produit, String> libelleCategCol = new TableColumn<Produit, String>("categorie");
		libelleCategCol.setCellValueFactory(Produit -> {
            SimpleObjectProperty<String> property = new SimpleObjectProperty<String>();
            String libelleCateg = ""; 
            try {
            	libelleCateg = DAOFactory.getDAOFactory(mainControleur.getPersistance()).getCategorieDAO().getById(Produit.getValue().getIdCategorie()).getTitre();
            } catch(IllegalArgumentException iae) {
            	libelleCateg = "Introuvable";
            }
            property.setValue(libelleCateg);
            return property;
        });
		TableColumn<Produit, Integer> venteCol = new TableColumn<Produit, Integer>("ventes");
		venteCol.setCellValueFactory(Produit -> {
			SimpleObjectProperty<Integer> property = new SimpleObjectProperty<Integer>();
			int nbVente = (int) DAOFactory.getDAOFactory(mainControleur.getPersistance()).getLigneCommandeDAO().getAll()
									.stream().filter(LigneCommande -> LigneCommande.getIdProduit() == Produit.getValue().getId()).count();
			property.setValue(nbVente);
			return property;
		});
		
		idCol.setSortType(TableColumn.SortType.DESCENDING);

		this.getColumns().clear();
		this.getColumns().add(idCol);
		this.getColumns().add(nameCol);
		this.getColumns().add(descCol);
		this.getColumns().add(priceCol);
		this.getColumns().add(visuelCol);
		this.getColumns().add(libelleCategCol);
		this.getColumns().add(venteCol);
		
		for(int i = 0; i < this.getColumns().size(); i++) {
			this.getColumns().get(i).setSortable(false);
		}
		
		this.getSelectionModel().clearSelection();
	}
}
