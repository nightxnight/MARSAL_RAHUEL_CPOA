package vue.application;

import controleur.MainControleur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {	
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Application.fxml"));
			Parent root = loader.load();			
			Scene scene = new Scene(root);
			
			primaryStage.setTitle("Boutique de pulls moche");
			primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));
			primaryStage.setMinWidth(1200);
			primaryStage.setMinHeight(900);
			primaryStage.setMaximized(true);
			primaryStage.centerOnScreen();
			
			primaryStage.setScene(scene);
			MainControleur controler = loader.getController();
			controler.setScene(scene);
			controler.loadUserConfig();
			
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
