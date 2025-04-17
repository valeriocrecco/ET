package logic.controllergraphics;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.bean.UserBean;
import logic.controllers.ProfileController;
import logic.exceptions.DefaultPhotoException;
import logic.exceptions.DuplicateUsernameException;
import logic.exceptions.EmailException;
import logic.exceptions.EmailSyntaxException;
import logic.exceptions.PasswordException;
import logic.exceptions.PasswordSyntaxException;
import logic.exceptions.SystemException;
import logic.exceptions.UsernameException;
import logic.exceptions.UsernameSyntaxException;
import logic.util.CloseResources;
import logic.util.NotifSingletonClass;

public class UserInfoControllerView {
	
	@FXML
    private Button btnLogout;

    @FXML
    private ImageView btnSettings;

    @FXML
    private Label lblUsername;

    @FXML
    private Label lblUploadPhotos;
    
    @FXML
    private ImageView ivNotifyBell;

    @FXML
    private ImageView btnHome;

    @FXML
    private Label lblSearchPeople;

    @FXML
    private ImageView btnBack;

    @FXML
    private ImageView ivUserPhoto;

    @FXML
    private Label lblUsernameProfile;

    @FXML
    private Label lblEmailProfile;

    @FXML
    private Label lblModifUsername;

    @FXML
    private Label lblModifEmail;

    @FXML
    private Label lblModifPassword;

    @FXML
    private PasswordField lblPasswordProfile;

    @FXML
    private Label lblModifPhoto;
    
    private Image imgSettings = new Image(getClass().getResourceAsStream("/logic/views/images/settingsButtonBasic.png"));
    private Image imgSettingsOver = new Image("/logic/views/images/settingsButtonOver.png");
    private Image imgBack = new Image(getClass().getResourceAsStream("/logic/views/images/back.png"));
    private Image imgBackOver = new Image(getClass().getResourceAsStream("/logic/views/images/back-over.png"));
    private Image imgHome = new Image(getClass().getResourceAsStream("/logic/views/images/home.png"));
    private Image imgHomeOver = new Image(getClass().getResourceAsStream("/logic/views/images/homeSelected.png"));
    private Image imgBell = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBell.png"));
    private Image imgBellOver = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBellSelected.png"));
    private Image imgBellNotif = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBellActive.png"));
    
    private UserBean userBean;
    private int pageNumber = 12;
    private int previousPage;
    
    private AtomicBoolean closeThread = new AtomicBoolean(false);
    private int notifStatus = 0;
    
    private static final double PROFILE_IMAGE_SIZE = 180;
    private static final String ARE_YOU_SURE = "Are you sure?";
    private static final String CONFIRMATION = "Confirmation dialogue";
    
    private static final String TEXT_COLOR_ENTERED = "#f9ab51";
    private static final String TEXT_COLOR_EXITED = "#ffffff";
    
    @FXML
    void uploadPhotos(MouseEvent event) {
		Stage stage = new Stage();
    	stage.setTitle("Upload photos");
    	stage.initOwner(lblUsername.getScene().getWindow());
    	stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.UTILITY);
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/logic/views/UploadPhotosView.fxml"));
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		UploadPhotosControllerView addTravelPhotosControllerView = loader.getController();
		addTravelPhotosControllerView.setUserBean(userBean);
		addTravelPhotosControllerView.setTableView();
		addTravelPhotosControllerView.setTableViewGRTravels();
		
		stage.setScene(new Scene(loader.getRoot()));
		stage.setResizable(false);
		stage.showAndWait();
    }
    
    @FXML
    void onMouseUploadPhotosEntered(MouseEvent event) {
    	lblUploadPhotos.setTextFill(Color.web(TEXT_COLOR_ENTERED));
    }
    
    @FXML
    void onMouseUploadPhotosExited(MouseEvent event) {
    	lblUploadPhotos.setTextFill(Color.web(TEXT_COLOR_EXITED));
    }
    
    @FXML
    void back(MouseEvent event) {
    		
    	Stage stage;
    	Parent root;
    	Scene scene;
    	FXMLLoader loader;
    	GraphicsDevice gd;
		
    	this.closeThreads();
    	
		try {
	    	switch(previousPage) {
	    	
	    		case 6:
    	    		stage = (Stage) btnBack.getScene().getWindow();
    	    		loader = new FXMLLoader(getClass().getResource("/logic/views/ProfileView.fxml"));
    	    		root = (Parent) loader.load();
    	    		
    	    		ProfileControllerView pController = loader.getController();
    	    		pController.setUser(this.userBean);
    	    		pController.setTableView();
    	    		pController.setTableViewGRTravels();
    	    		pController.setUserPhotoProfile();
    	    		pController.startThread();
    	    		
    	    		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    	    		
    	    		scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
    	    		stage.setScene(scene);
    	    		stage.setResizable(false);
    	    		stage.show();
    	    		stage.setOnCloseRequest(we -> {
    	    			pController.closeThreads();
    	    			CloseResources closeResources = new CloseResources();
    					closeResources.closeApplication();
						stage.close();
    		            System.exit(0);
    		    	});
	    	   
	    			break;
	    			
	    		case 7:
	    			stage = (Stage) btnBack.getScene().getWindow();
    	    		loader = new FXMLLoader(getClass().getResource("/logic/views/SettingsView.fxml"));
    	    		root = (Parent) loader.load();
    	    		
    	    		SettingsControllerView spcController = loader.getController();
    	    		spcController.setUser(this.userBean);
    	    		spcController.startThread();
    	    		
    	    		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    	    		
    	    		scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
    	    		stage.setScene(scene);
    	    		stage.setResizable(false);
    	    		stage.show();
    	    		stage.setOnCloseRequest(we -> {
    	    			spcController.closeThreads();
    	    			CloseResources closeResources = new CloseResources();
    					closeResources.closeApplication();
    					stage.close();
    		            System.exit(0);
    		    	});

	    			break;
	    		
	    		default:
	    			break;
	    	}   
	    } catch (IOException e){
			e.printStackTrace();
		}		
    }

    @FXML
    void home(MouseEvent event) {
    	
    	Stage stage;
    	Parent root;
    	
    	this.closeThreads();
    	
    	try {
    	
    		stage = (Stage) btnHome.getScene().getWindow();
    		
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
    		
    	} catch (IOException e){
    		e.printStackTrace();
    	}
    }

    @FXML
    void logout(ActionEvent event) {
    	
    	Stage stage;
    	Parent root;
    	
    	this.closeThreads();
		    	
    	try {
    	
    		stage = (Stage) btnLogout.getScene().getWindow();
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/RegistrationView.fxml"));
    		root = (Parent) loader.load();
    		
    		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			
    		Scene scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();		
    		stage.setOnCloseRequest(we -> {
    			CloseResources closeResources = new CloseResources();
    			closeResources.closeApplication();
	            stage.close();
	            System.exit(0);
	    	});
    		
    	} catch (IOException e){
    		e.printStackTrace();
    	}
    }
    
    @FXML
    void notifications(MouseEvent event) {
    	
    	Stage stage;
    	Parent root;
    	
    	this.closeThreads();
    	
    	try {
    		
    		stage = (Stage) lblUsername.getScene().getWindow();
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/NotifyView.fxml"));
    		root = (Parent) loader.load();
    		
    		NotifyControllerView npcController = loader.getController();
    		npcController.setUser(this.userBean);
    		npcController.setTableViewNotify();
    		npcController.setPrevPage(this.pageNumber);
    		
    		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			
    		Scene scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();        	
    		stage.setOnCloseRequest(we -> {
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
    void onMouseLogoutEntered(MouseEvent event) {
    	btnLogout.setStyle("-fx-background-color: #d16002");
    }

    @FXML
    void onMouseLogoutExited(MouseEvent event) {
    	btnLogout.setStyle("-fx-background-color: #f9ab51");
    }

    @FXML
    void onMouseSettingsEntered(MouseEvent event) {
    	btnSettings.setImage(imgSettingsOver);
    }

    @FXML
    void onMouseSettingsExited(MouseEvent event) {
    	btnSettings.setImage(imgSettings);
    }
    
    @FXML
    void settings(MouseEvent event) {
    	
    	Stage stage;
    	Parent root;
    	
    	this.closeThreads();
    	
    	try {
    		
    		stage = (Stage) lblUsername.getScene().getWindow();
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/SettingsView.fxml"));
    		root = (Parent) loader.load();
    		
    		SettingsControllerView spController = loader.getController();
    		spController.setUser(this.userBean);
    		spController.setPrevPage(this.pageNumber);  	
    		spController.startThread();
    		
    		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			
    		Scene scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    		stage.setOnCloseRequest(we -> {
    			spController.closeThreads();
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
    void showUser(MouseEvent event) {
    	
    	Stage stage;
    	Parent root;
		
    	this.closeThreads();
    	
    	try {
    		
    		stage = (Stage) lblUsername.getScene().getWindow();
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/ProfileView.fxml"));
    		root = (Parent) loader.load();
    		
    		ProfileControllerView pcController = loader.getController();
    		pcController.setUser(this.userBean);
    		pcController.setTableView();
    		pcController.setTableViewGRTravels();
    		pcController.setPrevPage(this.pageNumber);
    		pcController.setUserPhotoProfile();		
    		pcController.startThread();
    		
    		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			
    		Scene scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    		stage.setOnCloseRequest(we -> {
    			pcController.closeThreads();
    			CloseResources closeResources = new CloseResources();
				closeResources.closeApplication();
				stage.close();
	            System.exit(0);
	    	});
        	
    	}catch (IOException e) {
    		e.printStackTrace();
    	}
    }

    @FXML
    void modifyEmail(MouseEvent event) {
    	String previousEmail;
    	TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Modify email");
		dialog.setHeaderText(null);
		dialog.setContentText("Please enter your new email");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			 /* Controllo input dell'utente */
			 previousEmail = this.userBean.getEmail();
			 try {
				 this.userBean.setAndValidateEmail(result.get());
				
	 			 Alert alert = new Alert(AlertType.CONFIRMATION);
	 			 alert.setTitle(CONFIRMATION);
	 			 alert.setHeaderText("Change email");
	 			 alert.setContentText(ARE_YOU_SURE);
	 			 Optional<ButtonType> resultButton = alert.showAndWait();		 
	 			 if(resultButton.isPresent() && resultButton.get() == ButtonType.OK) {
	 				 ProfileController profileController = new ProfileController();
					 profileController.changeEmail(this.userBean.getUsername(), this.userBean.getEmail());
					 lblEmailProfile.setText(this.userBean.getEmail()); 
	 			 }
			 } catch (EmailSyntaxException|EmailException|SystemException e) {
				 this.userBean.setEmail(previousEmail);
				 showAlertError(e.getMessage());
			 }
		 }
    }

    @FXML
    void modifyPassword(MouseEvent event) {
    	String previousPassword;
    	TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Modify password");
		dialog.setHeaderText(null);
		dialog.setContentText("Please enter your new password");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			previousPassword = this.userBean.getPassword();
			try {
				 this.userBean.setAndValidatePassword(result.get());
				
				 Alert alert = new Alert(AlertType.CONFIRMATION);
	   			 alert.setTitle(CONFIRMATION);
	   			 alert.setHeaderText("Change password");
	   			 alert.setContentText(ARE_YOU_SURE);
	   			 Optional<ButtonType> resultButton = alert.showAndWait();
	   			 if(resultButton.isPresent() && resultButton.get() == ButtonType.OK) {
	   				 ProfileController profileController = new ProfileController();
					 profileController.changePassword(this.userBean.getUsername(), this.userBean.getPassword());
					 lblPasswordProfile.setText(result.get());
	   			 }
			} catch (PasswordSyntaxException|PasswordException|SystemException e) {
				this.userBean.setPassword(previousPassword);
				this.showAlertError(e.getMessage());
			}
		}
    }

    @FXML
    void modifyUsername(MouseEvent event) {
    	String previousUsername;
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Modify username");
		dialog.setHeaderText(null);
		dialog.setContentText("Please enter your new username");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			/* Controllo input dell'utente */
			previousUsername = userBean.getUsername();
			try {
				this.userBean.setAndValidateUsername(result.get());

				Alert alert = new Alert(AlertType.CONFIRMATION);
   			 	alert.setTitle(CONFIRMATION);
   			 	alert.setHeaderText("Change username");
   			 	alert.setContentText(ARE_YOU_SURE);
   			 	Optional<ButtonType> resultButton = alert.showAndWait();
   			 	
   			 	if(resultButton.isPresent() && resultButton.get() == ButtonType.OK) {
					ProfileController profileController = new ProfileController();
					profileController.changeUsername(previousUsername, this.userBean.getUsername());
					lblUsername.setText(result.get());
    				lblUsernameProfile.setText(result.get());
   			 	}
   			 	
			} catch (UsernameSyntaxException|SystemException|UsernameException|DuplicateUsernameException e) {
				this.userBean.setUsername(previousUsername);
				this.showAlertError(e.getMessage());
			}
		}
    }
    
    @FXML
    void modifyPhoto(MouseEvent event) {
    	FileChooser fc = new FileChooser();
		fc.setTitle("Add photo");
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png", "*.gif"));
		
		File file = fc.showOpenDialog(lblModifPhoto.getScene().getWindow());
		
		if (file != null) {
			Image image = new Image(file.toURI().toString());
			/* Memorizzare l'immagine sul DB */
			ProfileController profileController = new ProfileController();
			try {
				profileController.changePhoto(lblUsername.getText(), file);
				ivUserPhoto.setImage(image);
				ivUserPhoto.setFitHeight(PROFILE_IMAGE_SIZE);
				ivUserPhoto.setFitWidth(PROFILE_IMAGE_SIZE);
				ivUserPhoto.setSmooth(true);
				ivUserPhoto.setCache(true);
			} catch (DefaultPhotoException|SystemException e) {
				this.showAlertError(e.getMessage());
			} 			
		}
    }
    
    private void showAlertError(String cause) {
    	Alert alertPhoto = new Alert(AlertType.ERROR);
		alertPhoto.setHeaderText(cause);
		alertPhoto.setTitle("Error");
		alertPhoto.setResizable(false);
		alertPhoto.showAndWait();
    }
    
    @FXML
    void onMouseUsernameEntered(MouseEvent event) {
    	lblUsername.setTextFill(Color.web(TEXT_COLOR_ENTERED));
    }

    @FXML
    void onMouseUsernameExited(MouseEvent event) {
    	lblUsername.setTextFill(Color.web(TEXT_COLOR_EXITED));
    }
    
    @FXML
    void onMouseBackEntered(MouseEvent event) {
    	btnBack.setImage(imgBackOver);
    }
    
    @FXML
    void onMouseBackExited(MouseEvent event) {
    	btnBack.setImage(imgBack);
    }

    @FXML
    void onMouseHomeEntered(MouseEvent event) {
    	btnHome.setImage(imgHomeOver);
    }

    @FXML
    void onMouseHomeExited(MouseEvent event) {
    	btnHome.setImage(imgHome);
    }

    @FXML
    void onMouseNotiEntered(MouseEvent event) {
    	ivNotifyBell.setImage(imgBellOver);
    }

    @FXML
    void onMouseNotiExited(MouseEvent event) {
    	if(notifStatus == 0) {
    	    ivNotifyBell.setImage(imgBell);
    	}
    	else {
    	    ivNotifyBell.setImage(imgBellNotif);
    	}
    }
	
	public void setPrevPage(int prevPage) {
		this.previousPage = prevPage;
	}
	
	@FXML
    void onMouseSearchFollowEntered(MouseEvent event) {
		lblSearchPeople.setTextFill(Color.web(TEXT_COLOR_ENTERED));
    }

    @FXML
    void onMouseSearchFollowExited(MouseEvent event) {
		lblSearchPeople.setTextFill(Color.web(TEXT_COLOR_EXITED));
    }

    @FXML
    void searchUser(MouseEvent event) {
    	
    	Stage stage;
    	Parent root;
    	
    	this.closeThreads();
    	
    	try {
    		
    		stage = (Stage) lblSearchPeople.getScene().getWindow();

    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/SearchFollowView.fxml"));
    		root = (Parent) loader.load();
    		
    		SearchFollowControllerView sfpController = loader.getController();
    		
    		/* Avvio dei thread nel nuovo controller */
    		sfpController.setUser(this.userBean);
    		sfpController.setPrevPage(this.pageNumber);
    		sfpController.startThread();
    		
    		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			
    		Scene scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    		stage.setOnCloseRequest(we -> {
    			sfpController.closeThreads();
    			CloseResources closeResources = new CloseResources();
				closeResources.closeApplication();
				stage.close();
	            System.exit(0);
	    	});
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
	
    public void setUserPhotoProfile() {
    	
    	ProfileController profileController	= new ProfileController();
    	try {
    		String filename = "";
    		filename = profileController.retrieveUserPhoto(lblUsername.getText());		
			if(!filename.equals("")) {
				File tmpFile = new File(filename);
		    	Image photoUser = new Image(tmpFile.toURI().toString());
	    		ivUserPhoto.setImage(photoUser);
	    		ivUserPhoto.setFitHeight(PROFILE_IMAGE_SIZE);
	    		ivUserPhoto.setFitWidth(PROFILE_IMAGE_SIZE);
	    		ivUserPhoto.setSmooth(true);
	    		ivUserPhoto.setCache(true);
			}		
			else {
				Image photoUser = new Image(getClass().getResourceAsStream("/logic/views/images/userButton.png"));
			    ivUserPhoto.setImage(photoUser);
	    		ivUserPhoto.setFitHeight(PROFILE_IMAGE_SIZE);
	    		ivUserPhoto.setFitWidth(PROFILE_IMAGE_SIZE);
	    		ivUserPhoto.setSmooth(true);
	    		ivUserPhoto.setCache(true);
			}
		} catch (DefaultPhotoException e) {
			Image photoUser = new Image(getClass().getResourceAsStream("/logic/views/images/userButton.png"));
		    ivUserPhoto.setImage(photoUser);
    		ivUserPhoto.setFitHeight(PROFILE_IMAGE_SIZE);
    		ivUserPhoto.setFitWidth(PROFILE_IMAGE_SIZE);
    		ivUserPhoto.setSmooth(true);
    		ivUserPhoto.setCache(true);
		} catch (SystemException e) {
			Image photoUser = new Image(getClass().getResourceAsStream("/userButton.png"));
		    ivUserPhoto.setImage(photoUser);
    		ivUserPhoto.setFitHeight(PROFILE_IMAGE_SIZE);
    		ivUserPhoto.setFitWidth(PROFILE_IMAGE_SIZE);
    		ivUserPhoto.setSmooth(true);
    		ivUserPhoto.setCache(true);
    		showAlertError(e.getMessage());
		}
	}
	
    public void setUser(UserBean user) {
    	this.userBean = user;
    	lblUsername.setText(user.getUsername());
    	lblEmailProfile.setText(user.getEmail());
    	lblPasswordProfile.setText(user.getPassword());
    	lblUsernameProfile.setText(user.getUsername());
    }
        
    public void startThread() {
    	
    	Thread threadUpdateNotifyImage = new Thread(() ->
		{
			while(!closeThread.get()) {
				if (NotifSingletonClass.getNotifSingletonInstance().getNotifications(userBean.getUsername())) {
					ivNotifyBell.setImage(imgBell);
					notifStatus = 0;
				}
				else {
					ivNotifyBell.setImage(imgBellNotif);
					notifStatus = 1;
				}
				try {
					Thread.sleep(5000);
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
					return;
				}		
			}	
		});   
    	
    	threadUpdateNotifyImage.start();
    }
    
    public void closeThreads() {
    	this.closeThread.set(true);
    }
	
}
