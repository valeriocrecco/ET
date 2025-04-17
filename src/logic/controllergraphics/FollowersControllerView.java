package logic.controllergraphics;

import java.awt.Desktop;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import logic.bean.UserBean;
import logic.controllers.ProfileController;
import logic.bean.PrivateTravelBean;
import logic.bean.PublicTravelBean;
import logic.exceptions.SystemException;
import logic.util.CloseResources;
import logic.util.NotifSingletonClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FollowersControllerView implements Initializable {

    @FXML
    private TableView<UserBean> tvFollowersList;

    @FXML
    private TableColumn<UserBean, String> tcUsername;
    
    @FXML
    private TableColumn<UserBean, String> tcEmail;

    @FXML
    private TableView<PrivateTravelBean> tvUserTravels;

    @FXML
    private TableColumn<PrivateTravelBean, String> tcTravelName;

    @FXML
    private TableColumn<PrivateTravelBean, String> tcDest; 
    
    @FXML
    private TableView<PublicTravelBean> tvUserTravelsGr;

    @FXML
    private TableColumn<PublicTravelBean, String> tcTravelGrName;

    @FXML
    private TableColumn<PublicTravelBean, String> tcDestGr; 
    
    @FXML
    private Button btnLogout;

    @FXML
    private ImageView btnSettings;

    @FXML
    private Label lblUsername;

    @FXML
    private ImageView ivNotifyBell;

    @FXML
    private ImageView btnHome;

    @FXML
    private ImageView btnSearchFollow;

    @FXML
    private Label lblSearchUsers;
    
    @FXML
    private Label lblPrivateTravels;
    
    @FXML
    private Label lblPublicTravels;
    
    @FXML
    private ImageView btnBack;

    @FXML
    private Button btnViewHotel;

    @FXML
    private Button btnViewPhotos;
    
    @FXML
    private Button btnViewHotelGr;

    @FXML
    private Button btnViewPhotosGr;
    
    @FXML
    private Label lblUploadPhotos;
    
    private Image imgSettings = new Image(getClass().getResourceAsStream("/logic/views/images/settingsButtonBasic.png"));
    private Image imgSettingsOver = new Image("/logic/views/images/settingsButtonOver.png");
    private Image imgBack = new Image(getClass().getResourceAsStream("/logic/views/images/back.png"));
    private Image imgBackOver = new Image(getClass().getResourceAsStream("/logic/views/images/back-over.png"));
    private Image imgHome = new Image(getClass().getResourceAsStream("/logic/views/images/home.png"));
    private Image imgHomeOver = new Image(getClass().getResourceAsStream("/logic/views/images/homeSelected.png"));
    private Image imgBell = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBell.png"));
    private Image imgBellOver = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBellSelected.png"));
    private Image imgBellNotif = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBellActive.png"));
    
    private static final String BACKGROUND_COLOR_ENTERED = "-fx-background-color: #d16002";
    private static final String BACKGROUND_COLOR_EXITED = "-fx-background-color: #f9ab51";
    private static final String TEXT_COLOR_ENTERED = "#f9ab51";
    private static final String TEXT_COLOR_EXITED = "#ffffff";
    
    private UserBean userBean;
    private int pageNumber = 11;
    
    private AtomicBoolean closeThread = new AtomicBoolean(false);
    private int notifStatus = 0;
    
    private TilePane tilePane = new TilePane();
	private int nRows = 3;  /* number of row for tile pane */
    private int nCols = 3;  /* number of columns for tile pane */
    private static final double ELEMENT_SIZE = 200;
    private static final double GAP = ELEMENT_SIZE / 20; 
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	/* Table user */
		tcUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
		tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		
		/* Private travel */
		tcTravelName.setCellValueFactory(new PropertyValueFactory<>("travelNameBean"));
		tcDest.setCellValueFactory(new PropertyValueFactory<>("destinationBean"));
		
		/* Public travel */
		tcTravelGrName.setCellValueFactory(new PropertyValueFactory<>("travelNameBean"));
		tcDestGr.setCellValueFactory(new PropertyValueFactory<>("destinationBean"));
	}
    
    @FXML
    void back(MouseEvent event) {
    	
    	Stage stage;
    	Parent root;
    	
    	this.closeThreads();
    	
    	try {
    		
    		stage = (Stage) lblUsername.getScene().getWindow();
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/ProfileView.fxml"));
    		root = (Parent) loader.load();
    		
    		ProfileControllerView profileControllerView;
    		profileControllerView = loader.getController();
    		profileControllerView.setUser(this.userBean);
    		profileControllerView.setTableView();
    		profileControllerView.setTableViewGRTravels();
    		profileControllerView.setUserPhotoProfile();
    		profileControllerView.startThread();

    		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			
    		Scene scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    		stage.setOnCloseRequest(we -> {
    			profileControllerView.closeThreads();
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
    		
    		HomeControllerView choicePageControllerView;
    		choicePageControllerView = loader.getController();
    		choicePageControllerView.setUser(this.userBean);
    		choicePageControllerView.startThread();
    		
    		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    		
    		Scene scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    		stage.setOnCloseRequest(we -> {
    			choicePageControllerView.closeThreads();
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
		
		UploadPhotosControllerView addTravelPhotosControllerView;
		addTravelPhotosControllerView = loader.getController();
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
    		
    		NotifyControllerView notifyControllerView;
    		notifyControllerView = loader.getController();
    		notifyControllerView.setUser(this.userBean);
    		notifyControllerView.setTableViewNotify();
    		notifyControllerView.setPrevPage(pageNumber);
    		
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
    void settings(MouseEvent event) {
    	
    	Stage stage;
    	Parent root;
    	
    	this.closeThreads();
    	
    	try {
    	
    		stage = (Stage) btnSettings.getScene().getWindow();
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/SettingsView.fxml"));
    		root = (Parent) loader.load();
    		
    		SettingsControllerView settingsPageControllerView;
    		settingsPageControllerView = loader.getController();
    		settingsPageControllerView.setUser(this.userBean);
    		settingsPageControllerView.setPrevPage(pageNumber);
    		settingsPageControllerView.startThread();
    		
    		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	
    		Scene scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    		stage.setOnCloseRequest(we -> {
    			settingsPageControllerView.closeThreads();
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
    void userProfile(MouseEvent event) {
    	
    	Stage stage;
    	Parent root;
    	
    	this.closeThreads();
    	
    	try {
    		
    		stage = (Stage) lblUsername.getScene().getWindow();
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/ProfileView.fxml"));
    		root = (Parent) loader.load();
    		
    		ProfileControllerView profileControllerView;
    		profileControllerView = loader.getController();
    		profileControllerView.setUser(this.userBean);
    		profileControllerView.setTableView();
    		profileControllerView.setTableViewGRTravels();
    		profileControllerView.setPrevPage(pageNumber);
    		profileControllerView.setUserPhotoProfile();
    		profileControllerView.startThread();
    		
    		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			
    		Scene scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    		stage.setOnCloseRequest(we -> {
    			profileControllerView.closeThreads();
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
    void searchUsers(MouseEvent event) {
    	
    	Stage stage;
    	Parent root;

    	this.closeThreads();
    	
    	try {
    		
    		stage = (Stage) btnViewHotel.getScene().getWindow();

    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/SearchFollowView.fxml"));
    		root = (Parent) loader.load();
    		
    		SearchFollowControllerView searchFollowControllerView;
    		searchFollowControllerView = loader.getController();
    		searchFollowControllerView.setUser(this.userBean);
    		searchFollowControllerView.setPrevPage(pageNumber);
    		searchFollowControllerView.startThread();
    		
    		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			
    		Scene scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    		stage.setOnCloseRequest(we -> {
    			searchFollowControllerView.closeThreads();
    			CloseResources closeResources = new CloseResources();
				closeResources.closeApplication();
	            stage.close();
	            System.exit(0);
	        });
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public void setTableFollowers() {
		try {
			ObservableList<UserBean> followers;
	    	ProfileController profileController = new ProfileController();
			followers = FXCollections.observableList(profileController.retrieveFollowers(userBean.getUsername()));
			tvFollowersList.setItems(followers);
		} catch (SystemException e) {
			showAlertError(e.getMessage());
		}
    }
    
    @FXML
    void onMouseViewHotelEntered(MouseEvent event) {
    	btnViewHotel.setStyle(BACKGROUND_COLOR_ENTERED);
    }

    @FXML
    void onMouseViewHotelExited(MouseEvent event) {
    	btnViewHotel.setStyle(BACKGROUND_COLOR_EXITED);
    }

    @FXML
    void onMouseViewPhotosEntered(MouseEvent event) {
    	btnViewPhotos.setStyle(BACKGROUND_COLOR_ENTERED);
    }

    @FXML
    void onMouseViewPhotosExited(MouseEvent event) {
    	btnViewPhotos.setStyle(BACKGROUND_COLOR_EXITED);
    }

    @FXML
    void onMouseViewHotelGrEntered(MouseEvent event) {
    	btnViewHotelGr.setStyle(BACKGROUND_COLOR_ENTERED);
    }

    @FXML
    void onMouseViewHotelGrExited(MouseEvent event) {
    	btnViewHotelGr.setStyle(BACKGROUND_COLOR_EXITED);
    }

    @FXML
    void onMouseViewPhotosGrEntered(MouseEvent event) {
    	btnViewPhotosGr.setStyle(BACKGROUND_COLOR_ENTERED);
    }

    @FXML
    void onMouseViewPhotosGrExited(MouseEvent event) {
    	btnViewPhotosGr.setStyle(BACKGROUND_COLOR_EXITED);
    }
    
    @FXML
    void userSelected(MouseEvent event) {
    	btnViewHotel.setVisible(false);
    	btnViewPhotos.setVisible(false);
    	btnViewPhotosGr.setVisible(false);
    	btnViewHotelGr.setVisible(false);
    	
    	UserBean userSelected = tvFollowersList.getSelectionModel().getSelectedItem();
    	if(userSelected != null) {
    		lblPrivateTravels.setText(userSelected.getUsername().concat("'s private travels"));
        	lblPublicTravels.setText(userSelected.getUsername().concat("'s public travels"));
        	this.setTableFollowerTravelsInfos(userSelected.getUsername());
    	}
    }
    
    @FXML
    void travelSelected(MouseEvent event) {
    	btnViewHotel.setVisible(true);
    	btnViewPhotos.setVisible(true);
    }
    
    @FXML
    void travelGrSelected(MouseEvent event) {
    	btnViewHotelGr.setVisible(true);
    	btnViewPhotosGr.setVisible(true);
    }
    
    @FXML
    void viewHotel(ActionEvent event) {
    	PrivateTravelBean v = tvUserTravels.getSelectionModel().getSelectedItem();
    	if(v != null) {
    		openLinkHotel(v.getHotelInfoBean().getHotelLink());
    	}
    	else {
    		showAlertError("Please select a private travel!");
    	}
    }

    @FXML
    void viewPhotos(ActionEvent event) {
    	
    	PrivateTravelBean v = tvUserTravels.getSelectionModel().getSelectedItem();
		
    	if(v != null) {
    		/* Recupero delle immagini del viaggio dal DB */
        	List<String> filenames;
        	
    		try {
    			ProfileController profileController = new ProfileController();
    			filenames = profileController.retrieveTravelPhotos(v.getIdTravelBean());
           		
    			if (!filenames.isEmpty()) {				
    				this.showPhoto(filenames);
    			}
    			else {
    				showAlertInformation("Photos not found!");							
    			}
    		} catch (SystemException e) {
    			showAlertError(e.getMessage());
    		}
    	}
    	else {
    		showAlertError("Please select a private travel!");
    	}
		
    }    
    
    @FXML
    void viewHotelGr(ActionEvent event) {
    	PublicTravelBean v = tvUserTravelsGr.getSelectionModel().getSelectedItem();
    	
    	if(v != null) {
    		openLinkHotel(v.getHotelInfoBean().getHotelLink());
    	}
		else {
			showAlertError("Please select a public travel!");
		}
    }

    @FXML
    void viewPhotosGr(ActionEvent event) {
    	
    	PublicTravelBean v = tvUserTravelsGr.getSelectionModel().getSelectedItem();
		
    	if(v != null) {
    		/* Recupero delle immagini del viaggio dal DB */
        	List<String> filenames;
        	
    		try {
    			ProfileController profileController = new ProfileController();
        		filenames = profileController.retrieveTravelGroupPhotos(v.getIdTravelBean());
    		
    			if (!filenames.isEmpty()) {				
    				this.showPhoto(filenames);
    			}
    			else {
    				showAlertInformation("Photos not found!");							
    			}
    		} catch (SystemException e) {
    			showAlertError(e.getMessage());
    		}
    	}
    	else {
    		showAlertError("Please select a public travel!");
    	}
		
    }    
    
    private void showAlertError(String cause) {
    	Alert alertPhoto = new Alert(AlertType.ERROR);
		alertPhoto.setHeaderText(cause);
		alertPhoto.setTitle("Error");
		alertPhoto.setResizable(false);
		alertPhoto.showAndWait();
    }
    
    private void showAlertInformation(String cause) {
    	Alert alertPhoto = new Alert(AlertType.INFORMATION);
		alertPhoto.setHeaderText(cause);
		alertPhoto.setTitle("Error");
		alertPhoto.setResizable(false);
		alertPhoto.showAndWait();
    }
    
    private void openLinkHotel(String link) {
		try {
			Desktop.getDesktop().browse(new URL(link).toURI());
		} catch (IOException|URISyntaxException e) {
			e.printStackTrace();
		} 
	}
    
    private void createView(List<String> filenames) {
    	
    	int count = 0;
    	
    	tilePane.getChildren().clear();
    	
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if (count >= filenames.size()) {
					break;
				}
				tilePane.getChildren().add(createPage(count, filenames));
				count++;
			}
		}
    }
    
    private VBox createPage(int index, List<String> filenames) {
		ImageView imageView = new ImageView();
		File tmpFile = new File(filenames.get(index));
		Image image = new Image(tmpFile.toURI().toString());
		imageView.setImage(image);
		imageView.setFitHeight(ELEMENT_SIZE);
		imageView.setFitWidth(ELEMENT_SIZE);
		imageView.setSmooth(true);
		imageView.setCache(true);
		
		VBox pageBox = new VBox();
		pageBox.getChildren().add(imageView);
		
		return pageBox;
	}
    
    public void setTableFollowerTravelsInfos(String username) {
    	ObservableList<PrivateTravelBean> travels;
    	ObservableList<PublicTravelBean> travelsGr;
		ProfileController profileController = new ProfileController();
		try {
			travels = FXCollections.observableArrayList(profileController.retrieveFollowerPrivateTravels(username));
			travelsGr = FXCollections.observableArrayList(profileController.retrieveFollowerPublicTravels(username));
			tvUserTravels.setItems(travels);
			tvUserTravelsGr.setItems(travelsGr);
		} catch (SystemException e) {
			showAlertError(e.getMessage());
		}
    }

    private void showPhoto(List<String> filenames) {
    	ScrollPane scrollPane = new ScrollPane();
		
		tilePane.setPrefRows(nRows);
		tilePane.setPrefColumns(nCols);
		tilePane.setHgap(GAP);
		tilePane.setVgap(GAP);
		
		nRows = (int) Math.ceil(filenames.size()/3.0);
		
		createView(filenames);
		
		scrollPane.setContent(tilePane);
		scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setPadding(new Insets(10));
		Stage stage = new Stage();
		stage.setTitle("Photos");
		stage.initOwner(btnViewPhotos.getScene().getWindow());
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.UTILITY);
		stage.setScene(new Scene(scrollPane, 660, 660));
		stage.setResizable(false);
		stage.showAndWait();
    }
    
    @FXML
    void onMouseLogoutEntered(MouseEvent event) {
    	btnLogout.setStyle(BACKGROUND_COLOR_ENTERED);
    }

    @FXML
    void onMouseLogoutExited(MouseEvent event) {
    	btnLogout.setStyle(BACKGROUND_COLOR_EXITED);
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

    @FXML
    void onMouseSearchFollowEntered(MouseEvent event) {
    	lblSearchUsers.setTextFill(Color.web(TEXT_COLOR_ENTERED));
    }

    @FXML
    void onMouseSearchFollowExited(MouseEvent event) {
    	lblSearchUsers.setTextFill(Color.web(TEXT_COLOR_EXITED));
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
