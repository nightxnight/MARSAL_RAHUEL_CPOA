package vue.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePage extends Application {

	@Override
	public void start(Stage primaryStage) {
		//FIXME Gestion de l'affichage de categorie, client, produit supprime dans les ManagementControleur
	
		//TODO Trier les resultats
		//TODO css + bouton pour changer le theme
		//TODO xml de config (sauvegarde la derniere persistance choisis et du theme
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/Accueil.fxml"));
			Scene scene = new Scene(root,900,700);
			primaryStage.setTitle("Boutique de pulls moche");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
