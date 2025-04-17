package logic.controllergraphics;

import java.awt.Desktop;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.controllers.JoinController;
import logic.bean.UserBean;
import logic.bean.PublicTravelBean;
import logic.exceptions.DuplicateRequestException;
import logic.exceptions.SystemException;
import logic.util.CloseResources;
import logic.util.NotifSingletonClass;

public class InfoPublicTravelControllerView {

    @FXML
    private Label lblUsernameTravel;

    @FXML
    private Label lblDest;

    @FXML
    private Label lblData;

    @FXML
    private Label lblPosti;

    @FXML
    private Button btnSend;

    @FXML
    private ImageView ivLoading;

    @FXML
    private Label lblRequest;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblStars;

    @FXML
    private Label lblTravellers;

    @FXML
    private Label lblRooms;

    @FXML
    private Label lblBreakfast;

    @FXML
    private Label lblHotelName;

    @FXML
    private TextArea txtDescription;

    @FXML
    private Button btnLogout;

    @FXML
    private ImageView btnSettings;

    @FXML
    private Label lblUsername;

    @FXML
    private ImageView btnHome;
    
    @FXML
    private Label lblDataEnd;
   
    @FXML
    private ImageView btnBack;
    
    @FXML
    private ImageView btnSearchFollow;

    @FXML
    private Label lblSearchPeople;
    
    @FXML
    private Label lblUploadPhotos;
    
    @FXML
    private ImageView ivNotifyBell;

    private Image imgSettings = new Image(getClass().getResourceAsStream("/logic/views/images/settingsButtonBasic.png"));
    private Image imgSettingsOver = new Image("/logic/views/images/settingsButtonOver.png");
    private Image imgBack = new Image(getClass().getResourceAsStream("/logic/views/images/back.png"));
    private Image imgBackOver = new Image(getClass().getResourceAsStream("/logic/views/images/back-over.png"));
    private Image imgHome = new Image(getClass().getResourceAsStream("/logic/views/images/home.png"));
    private Image imgHomeOver = new Image(getClass().getResourceAsStream("/logic/views/images/homeSelected.png"));
    private Image imgBell = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBell.png"));
    private Image imgBellOver = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBellSelected.png"));
    private Image imgBellNotif = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBellActive.png"));
    private Image imageLoading = new Image(getClass().getResourceAsStream("/logic/views/images/loading.png"));
    
    private UserBean userBean;
    private PublicTravelBean vgr;
    private int pageNumber = 9; 
    
    private AtomicBoolean closeThread = new AtomicBoolean(false);
    private int notifStatus = 0;
    
    private static final String COLOR_ENTERED = "#ffffff";
    private static final String COLOR_EXITED = "#f9ab51";
    
    @FXML
    void back(MouseEvent event) {
    	
	    Stage stage;
	    Parent root;
	    
	    this.closeThreads();
	 
		try {
		 	
			stage = (Stage) btnBack.getScene().getWindow();
		 	
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/JoinView.fxml"));
			root = (Parent) loader.load();
			
			JoinControllerView jpController = loader.getController();
			jpController.setUser(this.userBean);
			jpController.setTableView();
			jpController.startThread();
			
			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			
			Scene scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
			stage.setOnCloseRequest(we -> {
				jpController.closeThreads();
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
    	lblUploadPhotos.setTextFill(Color.web(COLOR_ENTERED));
    }
    
    @FXML
    void onMouseUploadPhotosExited(MouseEvent event) {
    	lblUploadPhotos.setTextFill(Color.web(COLOR_EXITED));
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
    		npcController.setPrevPage(pageNumber);
    		npcController.setPublicTravelInfo(this.vgr);
    		
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
    void onMouseHotelNameClicked(MouseEvent event) {
    	openLinkHotel(vgr.getHotelInfoBean().getHotelLink());
    }
    
    private void openLinkHotel(String link) {
		try {
			Desktop.getDesktop().browse(new URL(link).toURI());
		} catch (IOException|URISyntaxException e) {
			e.printStackTrace();
		} 
	}
    
    @FXML
    void onMouseHotelNameEntered(MouseEvent event) {
    	lblHotelName.setTextFill(Color.web("#153bc2"));
    }

    @FXML
    void onMouseHotelNameExited(MouseEvent event) {
    	lblHotelName.setTextFill(Color.web(COLOR_EXITED));
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
    void onMouseSendEntered(MouseEvent event) {
    	btnSend.setStyle("-fx-background-color: #d16002");
    }

    @FXML
    void onMouseSendExited(MouseEvent event) {
    	btnSend.setStyle("-fx-background-color: #f9ab51");
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
    void onMouseUsernameEntered(MouseEvent event) {
    	lblUsername.setTextFill(Color.web(COLOR_EXITED));
    }

    @FXML
    void onMouseUsernameExited(MouseEvent event) {
    	lblUsername.setTextFill(Color.web(COLOR_EXITED));
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
    
    @FXML
    void onMouseBackEntered(MouseEvent event) {
    	btnBack.setImage(imgBackOver);
    }

    @FXML
    void onMouseBackExited(MouseEvent event) {
    	btnBack.setImage(imgBack);
    }
    
    @FXML
    void sendRequest(ActionEvent event) {	
    	JoinController joinPageController = new JoinController();
    	try {
			joinPageController.sendJoinRequest(vgr, lblUsername.getText());
			btnSend.setDisable(true);
    		lblRequest.setText("Request correctly sent");
    		lblRequest.setVisible(true);
    		lblRequest.setTextFill(Color.web("#00FF00"));
    		ivLoading.setVisible(true);
        	ivLoading.setImage(imageLoading);
		} catch (DuplicateRequestException e) {
			lblRequest.setText("Request already sent!");
    		lblRequest.setVisible(true);
    		btnSend.setDisable(true);
		} catch (SystemException e) {
			showAlertError(e.getMessage());
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
    void settings(MouseEvent event) {
    	
    	Stage stage;
	    Parent root;
	    
	    this.closeThreads();
	    
	    try {
	    		
	    	stage = (Stage) btnSettings.getScene().getWindow();
	    		
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/SettingsView.fxml"));
	    	root = (Parent) loader.load();
	    		
	    	SettingsControllerView spController = loader.getController();
	    	spController.setUser(this.userBean);
	    	spController.setPrevPage(pageNumber);
	    	spController.setPublicTravelInfo(this.vgr);
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
    void showUserPage(MouseEvent event) {
    	
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
    		pcController.setPrevPage(pageNumber);
    		pcController.setPublicTravelInfo(this.vgr);
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
        	
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    @FXML
    void onMouseSearchFollowEntered(MouseEvent event) {
    	lblSearchPeople.setTextFill(Color.web(COLOR_EXITED));
    }

    @FXML
    void onMouseSearchFollowExited(MouseEvent event) {
    	lblSearchPeople.setTextFill(Color.web(COLOR_EXITED));
    }

    @FXML
    void searchUser(MouseEvent event) {
    	
    	Stage stage;
    	Parent root;
    	
    	this.closeThreads();
    	
    	try {
    		stage = (Stage) btnSearchFollow.getScene().getWindow();
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/SearchFollowView.fxml"));
    		root = (Parent) loader.load();
    		
    		SearchFollowControllerView sfpController = loader.getController();
    		sfpController.setUser(this.userBean);
    		sfpController.setPrevPage(pageNumber);
    		sfpController.setPublicTravelInfo(this.vgr);
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
    
    public void setTravelInfo(PublicTravelBean vgr) {
    	this.vgr = vgr;
    	lblUsernameTravel.setText(vgr.getCreatorBean());
    	lblDest.setText(vgr.getDestinationBean());
    	lblData.setText(vgr.getStartDateBean());
    	lblDataEnd.setText(vgr.getEndDateBean());
    	lblPosti.setText(vgr.getAvailableSeats());
    	lblHotelName.setText(vgr.getHotelInfoBean().getHotelName());
    	lblStars.setText(vgr.getHotelInfoBean().getStars());
    	lblRooms.setText(vgr.getHotelInfoBean().getNumRooms());
    	lblBreakfast.setText(vgr.getHotelInfoBean().getBreakfast());
    	String price = vgr.getHotelInfoBean().getPrice();
    	price = price.replace("euro", "€");
    	lblPrice.setText(price);
    	lblTravellers.setText(vgr.getNumTravelersBean());
    	txtDescription.setText(vgr.getDescriptionBean());
    }
    
    public void setUser(UserBean user) {
    	this.userBean = user;
    	lblUsername.setText(user.getUsername());
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
