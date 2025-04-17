package logic.controllergraphics;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.bean.UserBean;
import logic.util.CloseResources;

public class WelcomeControllerView{
	
	@FXML
    private Button btnMainMenu;
	
	@FXML
	public Label lblUsername;
	
	private UserBean userBean;
	
    @FXML
    public void showMainMenu(ActionEvent event) {
    	
    	Stage stage;
    	Parent root;
    	
    	try {
    		
    		stage = (Stage) btnMainMenu.getScene().getWindow();

    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/HomeView.fxml"));
    		root = (Parent) loader.load();
    		
    		HomeControllerView cpcController = loader.getController();
    		cpcController.setUser(this.userBean);
    		cpcController.startThread();
    		
    		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    		Scene scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();        	
    		stage.setOnCloseRequest(we -> {
    			cpcController.closeThreads();
    			CloseResources closeResources = new CloseResources();
    			closeResources.closeApplication();
	            stage.close();
	            System.exit(0);
	    	});
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    }

	@FXML
    void onMouseMainEntered(MouseEvent event) {
    	btnMainMenu.setStyle("-fx-background-color: #d16002");
    }

    @FXML
    void onMouseMainExited(MouseEvent event) {
    	btnMainMenu.setStyle("-fx-background-color: #f9ab51");
    }
    
    public void setUser(UserBean userBean) {
    	this.userBean = userBean;
    	lblUsername.setText(userBean.getUsername());
    }
    
}    
