package logic.controllergraphics;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.controllers.JoinController;
import logic.exceptions.SystemException;
import logic.bean.UserBean;
import logic.bean.PublicTravelBean;
import logic.util.CloseResources;
import logic.util.NotifSingletonClass;

public class JoinControllerView implements Initializable{
	
	@FXML
    private Button btnLogout;

    @FXML
    private ImageView btnSettings;

    @FXML
    private Label lblUsername;

    @FXML
    private ImageView btnBack;
    
    @FXML
    private ImageView btnHome;

    @FXML
    private TableView<PublicTravelBean> tvJoins;

    @FXML
    private TableColumn<PublicTravelBean, String> tcID;

    @FXML
    private TableColumn<PublicTravelBean, String> tcUsername;

    @FXML
    private TableColumn<PublicTravelBean, String> tcDest;

    @FXML
    private TableColumn<PublicTravelBean, String> tcDataPartenza;
    
    @FXML
    private TableColumn<PublicTravelBean, String> tcNumMax;

    @FXML
    private TableColumn<PublicTravelBean, String> tcPostiDisponiibli;

    @FXML
    private ImageView ivNotifyBell;
    
    @FXML
    private Button btnViewInfos;
    
    @FXML
    private ImageView btnSearchFollow;

    @FXML
    private Label lblSearchPeople;
    
    @FXML
    private Label lblErrorView;
     
    @FXML 
    private Label lblUploadPhotos;
    
    @FXML
    private RadioButton radioSea;

    @FXML
    private ToggleGroup kind;

    @FXML
    private RadioButton radioMountain;

    @FXML
    private CheckBox cbArt;

    @FXML
    private CheckBox cbYoung;

    @FXML
    private ChoiceBox<String> cebContinent;
    
    @FXML
    private Button btnSearch;
    
    @FXML
    private Button btnReset;
 
    private Image imgSettings = new Image(getClass().getResourceAsStream("/logic/views/images/settingsButtonBasic.png"));
    private Image imgSettingsOver = new Image(getClass().getResourceAsStream("/logic/views/images/settingsButtonOver.png"));
    private Image imgBack = new Image(getClass().getResourceAsStream("/logic/views/images/back.png"));
    private Image imgBackOver = new Image(getClass().getResourceAsStream("/logic/views/images/back-over.png"));
    private Image imgHome = new Image(getClass().getResourceAsStream("/logic/views/images/home.png"));
    private Image imgHomeOver = new Image(getClass().getResourceAsStream("/logic/views/images/homeSelected.png"));
    private Image imgBell = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBell.png"));
    private Image imgBellOver = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBellSelected.png"));
    private Image imgBellNotif = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBellActive.png"));
    
    private UserBean userBean;
    private int pageNumber = 2;
    
    private AtomicBoolean closeThread = new AtomicBoolean(false);
    private int notifStatus = 0;
    
    private static final String COLOR_ONE = "-fx-background-color: #f9ab51";
    private static final String COLOR_TWO = "-fx-background-color: #d16002";
    private static final String TEXT_COLOR_EXITED = "#ffffff";
    private static final String TEXT_COLOR_ENTERED = "#f9ab51";
    private static final String NOT_DEFINED = "Not defined";
    
    ObservableList<String> continentList = FXCollections.observableArrayList(NOT_DEFINED, "Africa", "America","Asia", "Europe", "Oceania");
    
    @Override
    public void initialize(URL location ,ResourceBundle resources) {
    	tcID.setCellValueFactory(new PropertyValueFactory<>("idTravelBean"));
    	tcUsername.setCellValueFactory(new PropertyValueFactory<>("creatorBean"));
		tcDest.setCellValueFactory(new PropertyValueFactory<>("destinationBean"));
		tcDataPartenza.setCellValueFactory(new PropertyValueFactory<>("startDateBean"));
		tcNumMax.setCellValueFactory(new PropertyValueFactory<>("numTravelersBean"));
		tcPostiDisponiibli.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));
		
		cebContinent.setItems(continentList);
    	cebContinent.setValue(NOT_DEFINED); // default value
	}
        
    @FXML
    void back(MouseEvent event) {
    	this.goHome();
    }
    
    @FXML
    void home(MouseEvent event) {
    	this.goHome();    	
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
    	lblUploadPhotos.setTextFill(Color.web(TEXT_COLOR_ENTERED));
    }
    
    @FXML
    void onMouseUploadPhotosExited(MouseEvent event) {
    	lblUploadPhotos.setTextFill(Color.web(TEXT_COLOR_EXITED));
    }
    
    private void goHome() {
    	
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
    void notifyPage(MouseEvent event) {
    	
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
    
    public void setTableView() {
    	JoinController joinPageController = new JoinController();
    	ObservableList<PublicTravelBean> travels;
		try {
			travels = FXCollections.observableList(joinPageController.allTravels(userBean.getUsername()));
			tvJoins.setItems(travels);
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
    
    private void setFiltredTableView(List<PublicTravelBean> travels) {
    	ObservableList<PublicTravelBean> travelsList = FXCollections.observableList(travels);
    	tvJoins.setItems(travelsList);
    }
	
    @FXML
    void onMouseLogoutEntered(MouseEvent event) {
    	btnLogout.setStyle(COLOR_TWO);
    }

    @FXML
    void onMouseLogoutExited(MouseEvent event) {
    	btnLogout.setStyle(COLOR_ONE);
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
    	} catch (IOException e){
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
    void viewTravel(ActionEvent event) {
    
    	if(tvJoins.getSelectionModel().getSelectedItem() != null) {
    		
    		Stage stage;
	    	Parent root;
	    	
	    	this.closeThreads();

	    	try {
	    		stage = (Stage) lblUsername.getScene().getWindow();
	    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/InfoPublicTravelView.fxml"));
	    		root = (Parent) loader.load();
	    		
	    		InfoPublicTravelControllerView itcControllerView = loader.getController();
	    		itcControllerView.setUser(this.userBean);
	    		itcControllerView.setTravelInfo(tvJoins.getSelectionModel().getSelectedItem());
	    		itcControllerView.startThread();
	    		
				GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
				
	    		Scene scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
	    		stage.setScene(scene);
	    		stage.setResizable(false);
	    		stage.show();
	    		stage.setOnCloseRequest(we -> {
	    			itcControllerView.closeThreads();
	    			CloseResources closeResources = new CloseResources();
	    			closeResources.closeApplication();
		            stage.close();
		            System.exit(0);
		    	});
	    	
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	}
    	}
    	else {
    		lblErrorView.setText(" You must select a travel!");
    	}
    	
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
    void onMouseViewEntered(MouseEvent event) {
    	btnViewInfos.setStyle(COLOR_TWO);
    }

    @FXML
    void onMouseViewExited(MouseEvent event) {
    	btnViewInfos.setStyle(COLOR_ONE);
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
    
    @FXML
    void onMouseSearchFollowEntered(MouseEvent event) {
    	lblSearchPeople.setTextFill(Color.web(TEXT_COLOR_ENTERED));
    }

    @FXML
    void onMouseSearchFollowExited(MouseEvent event) {
    	lblSearchPeople.setTextFill(Color.web(TEXT_COLOR_EXITED));
    }
   
    @FXML
    void onMouseSearchEntered(MouseEvent event) {
    	btnSearch.setStyle(COLOR_TWO);
    }

    @FXML
    void onMouseSearchExited(MouseEvent event) {
    	btnSearch.setStyle(COLOR_ONE);
    }
    
    @FXML
    void onMouseResetEntered(MouseEvent event) {
    	btnReset.setStyle("-fx-background-color: grey");
    }

    @FXML
    void onMouseResetExited(MouseEvent event) {
    	btnReset.setStyle("-fx-background-color: lightgrey");
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
    
    @FXML
    void resetTable(ActionEvent event) {
    	
    	radioSea.setSelected(false);
    	radioMountain.setSelected(false);
    	cbArt.setSelected(false);
    	cbYoung.setSelected(false);
    	cebContinent.setValue(NOT_DEFINED);
    	
    	setTableView();
    }

    @FXML
    void search(ActionEvent event) {
    	List<PublicTravelBean> travels;
    	JoinController joinPageController = new JoinController();
		try {
			travels = joinPageController.filterTravels(this.userBean.getUsername(), radioSea.isSelected(), radioMountain.isSelected(), cbArt.isSelected(), cbYoung.isSelected(), cebContinent.getSelectionModel().getSelectedItem());
			setFiltredTableView(travels);
		} catch (SystemException e) {
			showAlertError(e.getMessage());
		}   	
    }
    
    public void setUser(UserBean userBean) {
    	this.userBean = userBean;
    	lblUsername.setText(userBean.getUsername());
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
