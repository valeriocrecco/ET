package logic.controllergraphics;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.bean.UserBean;
import logic.controllers.RegistrationController;
import logic.exceptions.DuplicateUsernameException;
import logic.exceptions.EmailSyntaxException;
import logic.exceptions.FirstnameSyntaxException;
import logic.exceptions.PasswordSyntaxException;
import logic.exceptions.SurnameSyntaxException;
import logic.exceptions.SystemException;
import logic.exceptions.UsernameSyntaxException;
import logic.util.CloseResources;

public class RegistrationControllerView implements Initializable{
	
	@FXML
    private Button btnLogin;

    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfSurname;

    @FXML
    private TextField tfPassword;

    @FXML
    private Button btnSignUp;

    @FXML
    private Label lblUsernameError;

    @FXML
    private Label lblEmailError;

    @FXML
    private Label lblNameError;

    @FXML
    private Label lblSurnameError;

    @FXML
    private Label lblPasswordError;
   
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		lblEmailError.setVisible(false);
		lblNameError.setVisible(false);
		lblPasswordError.setVisible(false);
		lblSurnameError.setVisible(false);
		lblUsernameError.setVisible(false);
	} 
    
    @FXML
    public void signUp(ActionEvent event){
    	
    	Stage stage;
    	Parent root;
	
    	String username = tfUsername.getText();
    	String password = tfPassword.getText();
    	String email = tfEmail.getText();
    	String name = tfName.getText();
    	String surname = tfSurname.getText();
    	
    	try {
	    	UserBean userBean = new UserBean();
	    	userBean.setAndValidateUsername(username);
	    	userBean.setAndValidateEmail(email);
	    	userBean.setAndValidateFirstName(name);
	    	userBean.setAndValidateSecondName(surname);
	    	userBean.setAndValidatePassword(password);
	    	
			RegistrationController registrationController = new RegistrationController();
			registrationController.signUp(userBean);
    		
    		stage = (Stage) btnSignUp.getScene().getWindow();
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/WelcomeView.fxml"));
    		root = (Parent) loader.load();
    		
    		WelcomeControllerView welcomeControllerView = loader.getController();
    		welcomeControllerView.setUser(userBean);
    		
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
    		
		} catch (DuplicateUsernameException due) {
			this.showAlertError(due.getMessage());
    	} catch (FirstnameSyntaxException e) {
    		tfName.setText("");
			this.showAlertError(e.getMessage());
		} catch (SurnameSyntaxException e) {
			tfSurname.setText("");
			this.showAlertError(e.getMessage());
		} catch (UsernameSyntaxException e) {
			tfUsername.setText("");
			this.showAlertError(e.getMessage());
		} catch (EmailSyntaxException e) {
			tfEmail.setText("");
			this.showAlertError(e.getMessage());
		} catch (PasswordSyntaxException e) {
			tfPassword.setText("");
			this.showAlertError(e.getMessage());
		} catch (SystemException e) {
			this.showAlertError(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    @FXML
    void login(ActionEvent event) {
    	
    	Stage stage;
    	Parent root;
    	
    	try {
    		
    		stage = (Stage) btnLogin.getScene().getWindow();
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/LoginView.fxml"));
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
    void onMouseLoginEntered(MouseEvent event) {
    	btnLogin.setStyle("-fx-background-color: #d9d9d9");
    }

    @FXML
    void onMouseLoginExited(MouseEvent event) {
    	btnLogin.setStyle("-fx-background-color: #fff");
    }	

    @FXML
    void onMouseSignUpEntered(MouseEvent event) {
    	btnSignUp.setStyle("-fx-background-color: #d16002");
    }

    @FXML
    void onMouseSignUpExited(MouseEvent event) {
    	btnSignUp.setStyle("-fx-background-color: #f9ab51");
    }
    
    private void showAlertError(String cause) {
    	Alert alertPhoto = new Alert(AlertType.ERROR);
		alertPhoto.setHeaderText(cause);
		alertPhoto.setTitle("Error");
		alertPhoto.setResizable(false);
		alertPhoto.showAndWait();
    }
    
}
