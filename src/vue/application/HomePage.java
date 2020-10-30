package vue.application;

import controleur.MainControleur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class HomePage extends Application {

	@Override
	public void start(Stage primaryStage) {	
		//TODO css
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Accueil.fxml"));
			Parent root = loader.load();			
			Scene scene = new Scene(root,900,700);
			scene.getStylesheets().add(getClass().getResource("/css/themeSombre.css").toExternalForm());
			primaryStage.setTitle("Boutique de pulls moche");
			primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.centerOnScreen();
			
			MainControleur controler = loader.getController();
			controler.setScene(scene);
			controler.loadUserConfig();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
