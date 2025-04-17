package logic.controllergraphics;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.controllers.LoginController;
import logic.bean.UserBean;
import logic.exceptions.LoginException;
import logic.exceptions.PasswordSyntaxException;
import logic.exceptions.SystemException;
import logic.exceptions.UsernameSyntaxException;
import logic.util.CloseResources;

public class LoginControllerView {
	
	@FXML
    private ImageView btnBack;

    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfPassword;

    @FXML
    private Button btnLogin;
    
    @FXML
    private Label lblErrormsg;

    Image imgBack = new Image(getClass().getResourceAsStream("/logic/views/images/back.png"));
    Image imgBackOver = new Image(getClass().getResourceAsStream("/logic/views/images/back-over.png"));
    
    @FXML
    void back(MouseEvent event) {
    	
    	 Stage stage;
		 Parent root;
		 
		 try {
    		stage = (Stage) btnBack.getScene().getWindow();
		 	
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/RegistrationView.fxml"));
    		root = (Parent) loader.load();
    	
    		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    		
    		Scene scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.setOnCloseRequest(we -> {
				CloseResources closeResources = new CloseResources();
				closeResources.closeApplication();
	            stage.close();
	            System.exit(0);
	    	});
    		stage.show();	
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
		 
    }

    @FXML
    void login(ActionEvent event) {
    	try {
			UserBean userBean = new UserBean();
			userBean.setAndValidateUsername(tfUsername.getText());
			userBean.setAndValidatePassword(tfPassword.getText());
			LoginController loginController = new LoginController();
			loginController.login(userBean);
			this.loginUser(userBean);
		} catch (LoginException le) {
			this.setLabelError(le.getMessage());
		} catch (SystemException sye) {
			showAlertError(sye.getMessage());
		} catch (UsernameSyntaxException e) {
			tfUsername.setText("");
			this.showAlertError(e.getMessage());
		} catch (PasswordSyntaxException e) {
			tfPassword.setText("");
			this.showAlertError(e.getMessage());
		}		
    }
    
    @FXML
    void onMouseLoginEntered(MouseEvent event) {
    	btnLogin.setStyle("-fx-background-color: #d16002");
    }

    @FXML
    void onMouseLoginExited(MouseEvent event) {
    	btnLogin.setStyle("-fx-background-color: #f9ab51");
    }
    
    @FXML
    void onMouseBackEntered(MouseEvent event) {
    	btnBack.setImage(imgBackOver);
    }

    @FXML
    void onMouseBackExited(MouseEvent event) {
    	btnBack.setImage(imgBack);
    }
   
    private void loginUser(UserBean userBean) {
    	
    	Stage stage;
    	Parent root;
    	
    	try {
		 	
    		stage = (Stage) btnLogin.getScene().getWindow();
			 	
	   		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/HomeView.fxml"));
	   		root = (Parent) loader.load();
	    		
	   		HomeControllerView cpcController = loader.getController();		
	   		cpcController.setUser(userBean);
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
    
    private void showAlertError(String cause) {
    	Alert alertPhoto = new Alert(AlertType.ERROR);
		alertPhoto.setHeaderText(cause);
		alertPhoto.setTitle("Error");
		alertPhoto.setResizable(false);
		alertPhoto.showAndWait();
    }
    
    private void setLabelError(String str) {
    	lblErrormsg.setText(str);
    }
    
}
