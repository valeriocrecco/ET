package logic;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.*;
import logic.util.DBConnector;
import javafx.scene.*;
import javafx.scene.image.Image;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// Creo una connessione al db locale
		DBConnector.getDBConnectorInstance().getConnection();
		
		Image icon = new Image("https://i.ibb.co/6bCr1dq/logoIcon.png");
		
		try {
			Stage stage = primaryStage;
			stage.setTitle("Easy Travel");
			stage.getIcons().add(icon);
			Parent root = FXMLLoader.load(getClass().getResource("/logic/views/RegistrationView.fxml"));
			
			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			
			Scene scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
			stage.setMaximized(true);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setOnCloseRequest(we -> {
	            stage.close();
	            System.exit(0);
	    	});
			stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
