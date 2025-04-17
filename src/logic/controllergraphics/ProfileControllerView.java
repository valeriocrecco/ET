package logic.controllergraphics;

import java.awt.Desktop;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
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
import logic.bean.UserBean;
import logic.bean.PrivateTravelBean;
import logic.bean.PublicTravelBean;
import logic.controllers.ProfileController;
import logic.exceptions.DefaultPhotoException;
import logic.exceptions.SystemException;
import logic.util.CloseResources;
import logic.util.NotifSingletonClass;

public class ProfileControllerView implements Initializable {

    @FXML
    private TableView<PrivateTravelBean> tvUserTravels;

    @FXML
    private TableColumn<PrivateTravelBean, String> tcDest;
    
    @FXML
    private TableColumn<PrivateTravelBean, String> tcTraveName;
    
    @FXML
    private TableView<PublicTravelBean> tvUserTravelsGr;

    @FXML
    private TableColumn<PublicTravelBean, String> tcTraveNameGr;

    @FXML
    private TableColumn<PublicTravelBean, String> tcDestGr;
    
    @FXML
    private TableView<PrivateTravelBean> tvUserBookedPrivateTravels;

    @FXML
    private TableColumn<PrivateTravelBean, String> tcPrivateTravelName;

    @FXML
    private TableColumn<PrivateTravelBean, String> tcDestPrivate;

    @FXML
    private TableColumn<PrivateTravelBean, String> tcFromPrivate;

    @FXML
    private TableColumn<PrivateTravelBean, String> tcToPrivate;

    @FXML
    private TableView<PublicTravelBean> tvUserBookedPublicTravels;

    @FXML
    private TableColumn<PublicTravelBean, String> tcPublicTravelName;

    @FXML
    private TableColumn<PublicTravelBean, String> tcDestPublic;

    @FXML
    private TableColumn<PublicTravelBean, String> tcFromPublic;

    @FXML
    private TableColumn<PublicTravelBean, String> tcToPublic;

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
    private ImageView btnSearchFollow;

    @FXML
    private Label lblSearchPeople;

    @FXML
    private ImageView btnBack;

    @FXML
    private Label lblPrivateTravels;

    @FXML
    private Label lblProfileUsername;

    @FXML
    private Label lblProfileEmail;

    @FXML
    private ImageView ivUserPhoto;

    @FXML
    private Button btnProfile;

    @FXML
    private Button btnFollowers;

    @FXML
    private Label lblPublicTravels;

    @FXML
    private RadioButton rbPastTravel;

    @FXML
    private RadioButton rbNextTravel;

    @FXML
    private Button btnSeePhotos;

    @FXML
    private Button btnViewHotel;

    @FXML
    private Button btnDetail;

    @FXML
    private Button btnSeePhotosGr;

    @FXML
    private Button btnViewHotelGr;

    @FXML
    private Button btnDetailGr;
	
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
    private PrivateTravelBean vgBean;
    private PublicTravelBean vgrBean;    
    
    private int pageNumber = 6;
    private int kindTravel;
    private int previousPage;
    
    private AtomicBoolean closeThread = new AtomicBoolean(false);
    private int notifStatus = 0;
    
	private TilePane tilePane = new TilePane();
	private int nRows = 3;  /* number of row for tile pane */
    private int nCols = 3;  /* number of columns for tile pane */
    private static final double ELEMENT_SIZE = 200;
    private static final double GAP = ELEMENT_SIZE / 20; 
    private static final double PROFILE_IMAGE_SIZE = 150;
	
	private static final String COLOR_ENTERED = "-fx-background-color: #d16002";
	private static final String COLOR_EXITED = "-fx-background-color: #f9ab51";
	private static final String TRAVEL = "travelNameBean";
	private static final String DEST = "destinationBean";
	private static final String PRIVATE = "Please, select a private travel!";
	private static final String PUBLIC = "Please, select a public travel!";
	
	@Override
    public void initialize(URL location ,ResourceBundle resources) {
		//Destination, Hotel
		tcDest.setCellValueFactory(new PropertyValueFactory<>(DEST));
		tcTraveName.setCellValueFactory(new PropertyValueFactory<>(TRAVEL));
		
		//Destination, Hotel
    	tcDestGr.setCellValueFactory(new PropertyValueFactory<>(DEST));
		tcTraveNameGr.setCellValueFactory(new PropertyValueFactory<>(TRAVEL));
		
		tcDestPrivate.setCellValueFactory(new PropertyValueFactory<>(DEST));
		tcPrivateTravelName.setCellValueFactory(new PropertyValueFactory<>(TRAVEL));
		tcFromPrivate.setCellValueFactory(new PropertyValueFactory<>("startDateBean"));
		tcToPrivate.setCellValueFactory(new PropertyValueFactory<>("endDateBean"));
		
    	tcDestPublic.setCellValueFactory(new PropertyValueFactory<>(DEST));
		tcPublicTravelName.setCellValueFactory(new PropertyValueFactory<>(TRAVEL));
		tcFromPublic.setCellValueFactory(new PropertyValueFactory<>("startDateBean"));
		tcToPublic.setCellValueFactory(new PropertyValueFactory<>("endDateBean"));
		
		ToggleGroup tgKindTravel = new ToggleGroup();
		rbNextTravel.setToggleGroup(tgKindTravel);
		rbPastTravel.setToggleGroup(tgKindTravel);
		rbPastTravel.setSelected(true);
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
    	lblUploadPhotos.setTextFill(Color.web("#f9ab51"));
    }
    
    @FXML
    void onMouseUploadPhotosExited(MouseEvent event) {
    	lblUploadPhotos.setTextFill(Color.web("#ffffff"));
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
	    	
    	 	case 4:
	   	 		stage = (Stage) btnBack.getScene().getWindow();				 	
	    		loader = new FXMLLoader(getClass().getResource("/logic/views/ManageView.fxml"));
	    		root = (Parent) loader.load();
	    		
	    		ManageTravelControllerView mController = loader.getController();
	    		mController.setUser(this.userBean);
	    		mController.setTableView();
	    		mController.setTableViewGRTravels();
	    		mController.startThread();
	    		
	    		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	    		
	    		scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
	    		stage.setScene(scene);
	    		stage.setResizable(false);
	    		stage.show();
	    		stage.setOnCloseRequest(we -> {
	    			mController.closeThreads();
	    			CloseResources closeResources = new CloseResources();
					closeResources.closeApplication();
	    			stage.close();
		            System.exit(0);
		    	});	  
		    	
		 		break;
    	 
    	 	case 9:
	    		stage = (Stage) btnBack.getScene().getWindow();
	    		loader = new FXMLLoader(getClass().getResource("/logic/views/InfoPublicTravelView.fxml"));
	    		root = (Parent) loader.load();
	    		
	    		InfoPublicTravelControllerView itController = loader.getController();
	    		itController.setUser(this.userBean);
	    		itController.setTravelInfo(this.vgrBean);
	    		itController.startThread();
	    		
	    		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	    		
	    		scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
	    		stage.setScene(scene);
	    		stage.setResizable(false);
	    		stage.show();
	    		stage.setOnCloseRequest(we -> {
	    			itController.closeThreads();
	    			CloseResources closeResources = new CloseResources();
					closeResources.closeApplication();
					stage.close();
		            System.exit(0);
		    	});
  		    
	    		break;
  			
    	 	case 2:
	    		stage = (Stage) btnBack.getScene().getWindow();
	    		loader = new FXMLLoader(getClass().getResource("/logic/views/JoinView.fxml"));
	    		root = (Parent) loader.load();
	    		
	    		JoinControllerView jpController = loader.getController();
	    		jpController.setUser(this.userBean);
	    		jpController.setTableView();
	    		jpController.startThread();
	    		
	    		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	    		
	    		scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
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
		 		
		 		break;
		 	
		 	case 3:
	 			stage = (Stage) btnBack.getScene().getWindow();
	    		loader = new FXMLLoader(getClass().getResource("/logic/views/PlanView.fxml"));
	    		root = (Parent) loader.load();
	    		
	    		PlanControllerView p2Controller = loader.getController();
	    		p2Controller.setUser(this.userBean);
	    		p2Controller.setKindTravel(kindTravel);
	    		if(kindTravel == 0) {
	    			p2Controller.setPublicTravelInfo(this.vgrBean);
	    		}
	    		else {
	    			p2Controller.setPrivateTravelInfo(this.vgBean);
	    		}
	    		p2Controller.startThread();
	    		
	    		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	    		
	    		scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
	    		stage.setScene(scene);
	    		stage.setResizable(false);
	    		stage.show();
	    		stage.setOnCloseRequest(we -> {
	    			p2Controller.closeThreads();
	    			CloseResources closeResources = new CloseResources();
					closeResources.closeApplication();
					stage.close();
		            System.exit(0);
		    	});

		 		break;

		 	case 8:
		 		stage = (Stage) btnBack.getScene().getWindow();
	    		loader = new FXMLLoader(getClass().getResource("/logic/views/NotifyView.fxml"));
	    		root = (Parent) loader.load();
	    		
	    		NotifyControllerView npController = loader.getController();
	    		npController.setUser(this.userBean);
	    		npController.setTableViewNotify();
	    		
	    		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	    		
	    		scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
	    		stage.setScene(scene);
	    		stage.setResizable(false);
	    		stage.show();
	    		stage.setOnCloseRequest(we -> {
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
	    		
	    		SettingsControllerView spController = loader.getController();
	    		spController.setUser(this.userBean);
	    		spController.startThread();
	    		
	    		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	    		
	    		scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
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
		 		
		 		break;
		 		
		 	case 12:
		 		stage = (Stage) btnBack.getScene().getWindow();
	    		loader = new FXMLLoader(getClass().getResource("/logic/views/UserInfoView.fxml"));
	    		root = (Parent) loader.load();
	    		
	    		UserInfoControllerView uiController = loader.getController();
	    		uiController.setUser(this.userBean);
	    		uiController.setUserPhotoProfile();
	    		uiController.startThread();
	    		
	    		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	    		
	    		scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
	    		stage.setScene(scene);
	    		stage.setResizable(false);
	    		stage.show();
	    		stage.setOnCloseRequest(we -> {
	    			uiController.closeThreads();
	    			CloseResources closeResources = new CloseResources();
					closeResources.closeApplication();
					stage.close();
		            System.exit(0);
		    	});
		 		
		 		break;
		 		
		 	case 10:
		    	stage = (Stage) btnBack.getScene().getWindow();
	    		loader = new FXMLLoader(getClass().getResource("/logic/views/SearchHotelView.fxml"));
	    		root = (Parent) loader.load();
	    		
	    		SearchHotelsControllerView shController = loader.getController();
	    		shController.setUser(this.userBean);
	    		shController.setKindTravel(kindTravel);
	    		if(kindTravel == 0) {
	    			shController.setPublicTravelInfo(this.vgrBean);
	    		}
	    		else {
	    			shController.setPrivateTravelInfo(this.vgBean);
	    		}
	    		shController.startThread();
	    		
	    		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	    		
	    		scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
	    		stage.setScene(scene);
	    		stage.setResizable(false);
	    		stage.show();
	    		stage.setOnCloseRequest(we -> {
	    			shController.closeThreads();
	    			CloseResources closeResources = new CloseResources();
					closeResources.closeApplication();
					stage.close();
		            System.exit(0);
		    	});
		 		
		 		break;
		 		
		 	case 5:
		 		stage = (Stage) btnBack.getScene().getWindow();
	    		loader = new FXMLLoader(getClass().getResource("/logic/views/SearchFollowView.fxml"));
	    		root = (Parent) loader.load();
	    		
	    		SearchFollowControllerView sfpController = loader.getController();
	    		sfpController.setUser(this.userBean);
	    		sfpController.startThread();
	    		
	    		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	    		
	    		scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
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
		    	
   		 		break;

		 	case 11:
	    		stage = (Stage) lblSearchPeople.getScene().getWindow();
	    		loader = new FXMLLoader(getClass().getResource("/logic/views/FollowersView.fxml"));
	    		root = (Parent) loader.load();
	    		
				FollowersControllerView fpController = loader.getController();
	    		fpController.setUser(this.userBean);
	    		fpController.setTableFollowers();
	    		fpController.startThread();
	    		
	    		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	    		
	    		scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
	    		stage.setScene(scene);
	    		stage.setResizable(false);
	    		stage.show();
	    		stage.setOnCloseRequest(we -> {
	    			fpController.closeThreads();
	    			CloseResources closeResources = new CloseResources();
					closeResources.closeApplication();
					stage.close();
		            System.exit(0);
		    	});
		    	
		 		break;
		 		
		 	default: 
		 		stage = (Stage) btnBack.getScene().getWindow();
	    		loader = new FXMLLoader(getClass().getResource("/logic/views/HomeView.fxml"));
	    		root = (Parent) loader.load();
	    		
	    		HomeControllerView cpcController = loader.getController();
	    		cpcController.setUser(this.userBean);
	    		cpcController.startThread();
	    		
	    		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	    		
	    		scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
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
    		  
		 		break;
		 }
    	 
		} catch (IOException e) {
	    		e.printStackTrace();
	    }
    }
    
    @FXML
    void seeFollowers(MouseEvent event) {
    	
    	Stage stage;
    	Parent root;
    	
    	this.closeThreads();
    
    	try {
    	
    		stage = (Stage) lblSearchPeople.getScene().getWindow();
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/FollowersView.fxml"));
    		root = (Parent) loader.load();
			
    		FollowersControllerView fpController = loader.getController();
    		fpController.setUser(this.userBean);
    		fpController.setTableFollowers();
    		fpController.startThread();
    		
    		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    		
    		Scene scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    		stage.setOnCloseRequest(we -> {
    			fpController.closeThreads();
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
    void modifyProfile(ActionEvent event) {
    	
    	Stage stage;
    	Parent root;
    	
    	this.closeThreads();
    	
    	try {
    	
    		stage = (Stage) btnProfile.getScene().getWindow();
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/UserInfoView.fxml"));
    		root = (Parent) loader.load();
    		
    		UserInfoControllerView upController = loader.getController();
    		upController.setUser(this.userBean);
    		upController.setPrevPage(pageNumber);
    		upController.setUserPhotoProfile();
    		upController.startThread();
    		
    		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	
    		Scene scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    		stage.setOnCloseRequest(we -> {
    			upController.closeThreads();
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
    
    public void setPrevPage(int prevPage) {
    	this.previousPage = prevPage;
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
    	ProfileController profileController = new ProfileController();
		ObservableList<PrivateTravelBean> travels;
		try {
			travels = FXCollections.observableList(profileController.loadMyOldT(userBean.getUsername()));
			tvUserTravels.setItems(travels);
		} catch (SystemException e) {
			showAlertError(e.getMessage());
		}
    }    

    @FXML
    void detail(MouseEvent event) {
    	if(tvUserBookedPrivateTravels.getSelectionModel().getSelectedItem() != null) {
    		Stage stage = new Stage();
        	stage.setTitle("Travel details");
        	stage.initOwner(lblUsername.getScene().getWindow());
        	stage.initModality(Modality.APPLICATION_MODAL);
    		stage.initStyle(StageStyle.UTILITY);
    		
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(getClass().getResource("/logic/views/PrivateTravelDetailView.fxml"));
    		try {
    			loader.load();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		
    		PrivateTravelDetailView privateTravelDetailPageController = loader.getController();
    		privateTravelDetailPageController.setTravelInfo(tvUserBookedPrivateTravels.getSelectionModel().getSelectedItem());
    		
    		stage.setScene(new Scene(loader.getRoot()));
    		stage.setResizable(false);
    		stage.showAndWait();
    	}
    	else {
    		showAlertInformation(PRIVATE);
    	}
    }

    @FXML
    void detailGr(MouseEvent event) {
    	if(tvUserBookedPublicTravels.getSelectionModel().getSelectedItem() != null) {
    		Stage stage = new Stage();
        	stage.setTitle("Travel details");
        	stage.initOwner(lblUsername.getScene().getWindow());
        	stage.initModality(Modality.APPLICATION_MODAL);
    		stage.initStyle(StageStyle.UTILITY);
    		
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(getClass().getResource("/logic/views/PublicTravelDetailView.fxml"));
    		try {
    			loader.load();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		
    		PublicTravelDetailView publicTravelDetailPageController = loader.getController();
    		publicTravelDetailPageController.setTravelInfo(tvUserBookedPublicTravels.getSelectionModel().getSelectedItem());
    		
    		stage.setScene(new Scene(loader.getRoot()));
    		stage.setResizable(false);
    		stage.showAndWait();
    	}
    	else {
    		showAlertInformation(PUBLIC);
    	}
    }
    
    @FXML
    void seePhotos(MouseEvent event) {
		PrivateTravelBean v = tvUserTravels.getSelectionModel().getSelectedItem();
		if(v != null) {
			try {
	    		ProfileController profileController = new ProfileController();
	    		List<String> filenames = profileController.retrieveTravelPhotos(v.getIdTravelBean());

				if(!filenames.isEmpty()) {

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
					stage.initOwner(btnSeePhotos.getScene().getWindow());
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.initStyle(StageStyle.UTILITY);
					stage.setScene(new Scene(scrollPane, 660, 660));
					stage.setResizable(false);
					stage.showAndWait();
				}
				else {
					showAlertInformation("No photos added for this travel yet!");
				}
			} catch (SystemException e) {
				showAlertError(e.getMessage());
			}
		}
		else {
			showAlertInformation(PRIVATE);
		}
    }
    
    @FXML
    void viewHotel(MouseEvent event) {
    	PrivateTravelBean v = tvUserTravels.getSelectionModel().getSelectedItem();
    	if(v != null) {
    		openLinkHotel(v.getHotelInfoBean().getHotelLink());
    	}
    	else {
    		showAlertInformation(PRIVATE);
    	}
    }
    
    @FXML
    void seePhotosGr(MouseEvent event) {
    	PublicTravelBean vgr = tvUserTravelsGr.getSelectionModel().getSelectedItem();
    	if(vgr != null) {
    		/* Recupero dell'immagine del profilo dal DB */
    		List<String> filenames = new ArrayList<>();
    		ProfileController profileController = new ProfileController();
    		try {
    			filenames = profileController.retrieveTravelGroupPhotos(vgr.getIdTravelBean());
    		    	
    			if (!filenames.isEmpty()) {

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
    				stage.initOwner(btnSeePhotosGr.getScene().getWindow());
    				stage.initModality(Modality.APPLICATION_MODAL);
    				stage.initStyle(StageStyle.UTILITY);
    				stage.setScene(new Scene(scrollPane, 660, 660));
    				stage.setResizable(false);
    				stage.showAndWait();
    			}
    			else {
    				showAlertInformation("No photos added for this travel yet!");
    			}
    		} catch (SystemException e) {
    			showAlertError("Photos not found!");
    		}
    	}
    	else {
    		showAlertInformation(PUBLIC);
    	}
    }

    @FXML
    void viewHotelGr(MouseEvent event) {
    	PublicTravelBean vgr = tvUserTravelsGr.getSelectionModel().getSelectedItem();
    	if(vgr != null) {
    		openLinkHotel(vgr.getHotelInfoBean().getHotelLink());
    	}
    	else {
    		showAlertInformation(PUBLIC);
    	}
    }
    
    @FXML
    void pastPrivateTravelSelected(MouseEvent event) {
    	btnViewHotelGr.setVisible(false);
    	btnSeePhotosGr.setVisible(false);
    	btnDetail.setVisible(false);
    	btnDetailGr.setVisible(false);
    	
    	btnViewHotel.setVisible(true);
    	btnSeePhotos.setVisible(true);
    }

    @FXML
    void pastPublicTravelSelected(MouseEvent event) {
    	btnViewHotel.setVisible(false);
    	btnSeePhotos.setVisible(false);
    	btnDetail.setVisible(false);
    	btnDetailGr.setVisible(false);
    	
    	btnViewHotelGr.setVisible(true);
    	btnSeePhotosGr.setVisible(true);
    }
    
    @FXML
    void nextPrivateTravelSelected(MouseEvent event) {
    	btnViewHotelGr.setVisible(false);
    	btnSeePhotosGr.setVisible(false);
    	btnViewHotel.setVisible(false);
    	btnSeePhotos.setVisible(false);
    	btnDetailGr.setVisible(false);
    	
    	btnDetail.setVisible(true);
    }

    @FXML
    void nextPublicTravelSelected(MouseEvent event) {
    	btnViewHotelGr.setVisible(false);
    	btnSeePhotosGr.setVisible(false);
    	btnViewHotel.setVisible(false);
    	btnSeePhotos.setVisible(false);
    	btnDetail.setVisible(false);
    	
    	btnDetailGr.setVisible(true);
    }

    @FXML
    void onMouseDetailEntered(MouseEvent event) {
    	btnDetail.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseDetailExited(MouseEvent event) {
    	btnDetail.setStyle(COLOR_EXITED);
    }

    @FXML
    void onMouseDetailGrEntered(MouseEvent event) {
    	btnDetailGr.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseDetailGrExited(MouseEvent event) {
    	btnDetailGr.setStyle(COLOR_EXITED);
    }

    @FXML
    void onMouseSeePhotosEntered(MouseEvent event) {
    	btnSeePhotos.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseSeePhotosExited(MouseEvent event) {
    	btnSeePhotos.setStyle(COLOR_EXITED);
    }

    @FXML
    void onMouseSeePhotosGrEntered(MouseEvent event) {
    	btnSeePhotosGr.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseSeePhotosGrExited(MouseEvent event) {
    	btnSeePhotosGr.setStyle(COLOR_EXITED);
    }

    @FXML
    void onMouseViewHotelEntered(MouseEvent event) {
    	btnViewHotel.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseViewHotelExited(MouseEvent event) {
    	btnViewHotel.setStyle(COLOR_EXITED);
    }

    @FXML
    void onMouseViewHotelGrEntered(MouseEvent event) {
    	btnViewHotelGr.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseViewHotelGrExited(MouseEvent event) {
    	btnViewHotelGr.setStyle(COLOR_EXITED);
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
		alertPhoto.setTitle("Information");
		alertPhoto.setResizable(false);
		alertPhoto.showAndWait();
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
    
    private void setBookedPrivateTravelsTables() {
    	ProfileController profileController = new ProfileController();
    	ObservableList<PrivateTravelBean> travels;
		try {
			travels = FXCollections.observableList(profileController.loadMyUpcomingT(userBean.getUsername()));
			tvUserBookedPrivateTravels.setItems(travels);
		} catch (SystemException e) {
			showAlertError(e.getMessage());
		}
    }
    
    private void setBookedPublicTravelsTables() {
    	ProfileController profileController = new ProfileController();
    	ObservableList<PublicTravelBean> travels;
		try {
			travels = FXCollections.observableList(profileController.loadMyUpcomingTGR(userBean.getUsername()));
			tvUserBookedPublicTravels.setItems(travels);
		} catch (SystemException e) {
			showAlertError(e.getMessage());
		}
    }
    
    @FXML
    void setTableNextTravels(MouseEvent event) {
    	
    	lblPrivateTravels.setText("Your next booked private travels");
    	lblPublicTravels.setText("Your next booked public travels");
    	
    	btnViewHotel.setVisible(false);
    	btnSeePhotos.setVisible(false);
    	btnViewHotelGr.setVisible(false);
    	btnSeePhotosGr.setVisible(false);
    	btnDetail.setVisible(false);
    	btnDetailGr.setVisible(false);
    	
    	tvUserTravels.setVisible(false);
    	tvUserBookedPrivateTravels.setVisible(true);
    	
    	tvUserTravelsGr.setVisible(false);
    	tvUserBookedPublicTravels.setVisible(true);
    	
    	setBookedPrivateTravelsTables(); 
    	setBookedPublicTravelsTables();
    }

    @FXML
    void setTablePastTravels(MouseEvent event) {
    	
    	lblPrivateTravels.setText("Your past private travels");
    	lblPublicTravels.setText("Your past public travels");
    	
    	btnViewHotel.setVisible(false);
    	btnSeePhotos.setVisible(false);
    	btnViewHotelGr.setVisible(false);
    	btnSeePhotosGr.setVisible(false);
    	btnDetail.setVisible(false);
    	btnDetailGr.setVisible(false);
    	
    	tvUserBookedPrivateTravels.setVisible(false);
    	tvUserTravels.setVisible(true);
    	
    	tvUserBookedPublicTravels.setVisible(false);
    	tvUserTravelsGr.setVisible(true);
    	
    	setTableView();
    	setTableViewGRTravels();
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
    
    private void openLinkHotel(String link) {
		
		try {
			Desktop.getDesktop().browse(new URL(link).toURI());
		} catch (IOException|URISyntaxException e) {
			e.printStackTrace();
		}
	}
    
    public void setTableViewGRTravels() {
    	ProfileController profileController = new ProfileController();
		ObservableList<PublicTravelBean> travels;
		try {
			travels = FXCollections.observableArrayList(profileController.loadMyOldTGR(this.userBean.getUsername()));
			tvUserTravelsGr.setItems(travels);
		} catch (SystemException e) {
			showAlertError(e.getMessage());
		}
    }   
    
    @FXML
    void onMouseLogoutEntered(MouseEvent event) {
    	btnLogout.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseLogoutExited(MouseEvent event) {
    	btnLogout.setStyle(COLOR_EXITED);
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
    void onMouseBackEntered(MouseEvent event) {
    	btnBack.setImage(imgBackOver);
    }

    @FXML
    void onMouseBackExited(MouseEvent event) {
    	btnBack.setImage(imgBack);
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
    	} catch (IOException e){
    		e.printStackTrace();
    	}
    }

    @FXML
    void onMouseProfileEntered(MouseEvent event) {
    	btnProfile.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseProfileExited(MouseEvent event) {
    	btnProfile.setStyle(COLOR_EXITED);
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
    void onMouseHomeEntered(MouseEvent event) {
    	btnHome.setImage(imgHomeOver);
    }

    @FXML
    void onMouseHomeExited(MouseEvent event) {
    	btnHome.setImage(imgHome);
    }
    
    @FXML
    void onMouseFollowersEntered(MouseEvent event) {
    	btnFollowers.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseFollowersExited(MouseEvent event) {
    	btnFollowers.setStyle(COLOR_EXITED);
    }
    
    @FXML
    void onMouseSearchFollowEntered(MouseEvent event) {
    	lblSearchPeople.setTextFill(Color.web("#f9ab51"));
    }

    @FXML
    void onMouseSearchFollowExited(MouseEvent event) {
    	lblSearchPeople.setTextFill(Color.web("#ffffff"));
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

    public void setUserPhotoProfile() {
		/* Recupero dell'immagine del profilo dal DB */
    	ProfileController profileController = new ProfileController();
		try {
			String filename = "";
			filename = profileController.retrieveUserPhoto(lblUsername.getText());
			if (!filename.equals("")) {
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
			showAlertError(e.getMessage());
		} 
	}    
    
    public void setUser(UserBean user) {
    	this.userBean = user;
    	lblUsername.setText(user.getUsername());
    	lblProfileUsername.setText(user.getUsername());
    	lblProfileEmail.setText(user.getEmail());
    }
    
    public void setPublicTravelInfo(PublicTravelBean vgr) {
		this.vgrBean = vgr;
	}
	
	public void setPrivateTravelInfo(PrivateTravelBean vg) {
		this.vgBean = vg;
	}
    
	public void setKindTravel(int kindTravel) {
		this.kindTravel = kindTravel;
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