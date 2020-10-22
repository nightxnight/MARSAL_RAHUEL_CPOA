package vue.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePage extends Application {

	@Override
	public void start(Stage primaryStage) {
		//TODO accueil
		//TODO recentrer les formulaires pour qu'ils soient niquel
		//TODO fil d'ariane
		//TODO changement de la persistance
		//TODO css + bouton pour changer le theme
		//TODO xml de config (sauvegarde la derniere persistance choisis et du theme
		//TODO bouton dans les clients permettant d'afficher ses commandes
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/Accueil.fxml"));
			Scene scene = new Scene(root,900,700);
			primaryStage.setTitle("Gestion de la boutique");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
