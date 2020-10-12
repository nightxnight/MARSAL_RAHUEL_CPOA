package vue.application;

import dao.Persistance;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePage extends Application {

	public static Persistance PERSISTANCE = Persistance.MYSQL;
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("AjoutProduit.fxml"));
			Scene scene = new Scene(root,400,400);
			primaryStage.setTitle("Ajout d'un produit");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
