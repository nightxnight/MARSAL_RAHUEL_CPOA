package vue.application;

import dao.Persistance;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePage extends Application {

	public static Persistance PERSISTANCE = Persistance.LISTEMEMOIRE;
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/Accueil.fxml"));
			Scene scene = new Scene(root,800,600);
			primaryStage.setTitle("Gestion de la boutique");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
