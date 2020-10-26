package vue.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePage extends Application {

	@Override
	public void start(Stage primaryStage) {	
		//TODO css
		//TODO eviter les doublons -> a mettre dans la methode create, trigger en mysql puis verif en liste memoire -> catch illegalArgument Exception ("x similaire")
		
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
