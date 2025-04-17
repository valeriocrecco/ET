package logic.controllergraphics;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.controllers.NotifyController;
import logic.bean.FollowNotificationBean;
import logic.bean.JoinNotificationBean;
import logic.bean.ReplyNotificationBean;
import logic.bean.UserBean;
import logic.bean.PrivateTravelBean;
import logic.bean.PublicTravelBean;
import logic.exceptions.SeatsNotAvailableException;
import logic.exceptions.SystemException;
import logic.util.CloseResources;

public class NotifyControllerView implements Initializable {

    @FXML
    private TableView<JoinNotificationBean> tvNotifyJoin;

    @FXML
    private TableColumn<JoinNotificationBean, String> tcDateJoin;

    @FXML
    private TableColumn<JoinNotificationBean, String> tcDescJoin;
    
    @FXML
    private TableView<FollowNotificationBean> tvNotifyFollow;

    @FXML
    private TableColumn<FollowNotificationBean, String> tcDateFollow;

    @FXML
    private TableColumn<FollowNotificationBean, String> tcDescFollow;
    
    @FXML
    private TableView<ReplyNotificationBean> tvNotifyReply;

    @FXML
    private TableColumn<ReplyNotificationBean, String> tcDateReply;

    @FXML
    private TableColumn<ReplyNotificationBean, String> tcDescReply;
    
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
    private Label lblSearchPeople;

    @FXML
    private Label lblUploadPhotos;

    @FXML
    private ImageView btnBack;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnAccept;

    @FXML
    private Button btnDecline;

    @FXML
    private Label lblJoinNotifications;

    @FXML
    private Label lblFollowNotifications;

    @FXML
    private RadioButton rbJoinNotifications;

    @FXML
    private RadioButton rbFollowNotifications;

    @FXML
    private RadioButton rbReplyNotifications;

    @FXML
    private Label lblReplyNotifications;

    @FXML
    private Label lblNJoin;

    @FXML
    private Label lblNReply;

    @FXML
    private Label lblNFollow;

    @FXML
    private Button btnAcceptFollow;

    @FXML
    private Button btnDeclineFollow;
        
    private Image imgSettings = new Image(getClass().getResourceAsStream("/logic/views/images/settingsButtonBasic.png"));
    private Image imgSettingsOver = new Image("/logic/views/images/settingsButtonOver.png");
    private Image imgBack = new Image(getClass().getResourceAsStream("/logic/views/images/back.png"));
    private Image imgBackOver = new Image(getClass().getResourceAsStream("/logic/views/images/back-over.png"));
    private Image imgHome = new Image(getClass().getResourceAsStream("/logic/views/images/home.png"));
    private Image imgHomeOver = new Image(getClass().getResourceAsStream("/logic/views/images/homeSelected.png"));
    
    private UserBean userBean;
    private PrivateTravelBean vgBean;
    private PublicTravelBean vgrBean;
    private int pageNumber = 8;
    private int kindTravel;
    private int previousPage;
    
    private static final String COLOR_ENTERED = "-fx-background-color: #d16002";
    private static final String COLOR_EXITED = "-fx-background-color: #f9ab51";
    private static final String TEXT_COLOR_ENTERED = "#f9ab51";
    private static final String TEXT_COLOR_EXITED = "#ffffff";
	
	@Override
    public void initialize(URL location ,ResourceBundle resources) {
		tcDateJoin.setCellValueFactory(new PropertyValueFactory<>("dateJoin"));
		tcDescJoin.setCellValueFactory(new PropertyValueFactory<>("msgJoin"));
		
		tcDateFollow.setCellValueFactory(new PropertyValueFactory<>("dateFollow"));
		tcDescFollow.setCellValueFactory(new PropertyValueFactory<>("msgFollow"));
		
		tcDateReply.setCellValueFactory(new PropertyValueFactory<>("dateReply"));
		tcDescReply.setCellValueFactory(new PropertyValueFactory<>("msgReply"));
		
		ToggleGroup tgKindNotification = new ToggleGroup();
		rbJoinNotifications.setToggleGroup(tgKindNotification);
		rbFollowNotifications.setToggleGroup(tgKindNotification);
		rbReplyNotifications.setToggleGroup(tgKindNotification);
		rbJoinNotifications.setSelected(true);
	}
	
	@FXML
    void home(MouseEvent event) {
		
		Stage stage;
    	Parent root;
    	
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

		 try {
		 
			 switch (previousPage) {
			 
			 	//case1:
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
   		    		if(kindTravel == 1) {
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
		    		if(kindTravel == 1) {
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
		 
		    		HomeControllerView cpController = loader.getController();
		    		cpController.setUser(this.userBean);
		    		cpController.startThread();
		    		
		    		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		    		
		    		scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
		    		stage.setScene(scene);
		    		stage.setResizable(false);
		    		stage.show();
		    		stage.setOnCloseRequest(we -> {
		    			cpController.closeThreads();
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
    void logout(ActionEvent event) {
    	
    	Stage stage;
    	Parent root;
    	
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
	    		}
    		);
    		
    	} catch (IOException e){
    		e.printStackTrace();
    	}
    }

    @FXML
    void searchUser(MouseEvent event) {
    	
    	Stage stage;
    	Parent root;
    	
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
    		
    	}catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    @FXML
    void settings(MouseEvent event) {
    	
    	Stage stage;
    	Parent root;
    	
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
    void onMouseLogoutEntered(MouseEvent event) {
    	btnLogout.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseLogoutExited(MouseEvent event) {
    	btnLogout.setStyle(COLOR_EXITED);
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
    void onMouseSettingsEntered(MouseEvent event) {
        btnSettings.setImage(imgSettingsOver);
    }
 
    @FXML
    void onMouseSettingsExited(MouseEvent event) {
        btnSettings.setImage(imgSettings);
    }

    @FXML
    void showUserPage(MouseEvent event) {
    	
    	Stage stage;
    	Parent root;
    	
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
    void joinNotificationSelected(MouseEvent event) {
		// Mostra pulsanti Accept e Decline
		btnAccept.setVisible(true);
		btnDecline.setVisible(true);
		btnDelete.setVisible(false);
		btnDeclineFollow.setVisible(false);
		btnAcceptFollow.setVisible(false);
    }
    
    @FXML
    void followNotificationSelected(MouseEvent event) {
		// Mostra pulsanti Accept e Decline
		btnAccept.setVisible(false);
		btnDecline.setVisible(false);
		btnDelete.setVisible(false);
		btnAcceptFollow.setVisible(true);
		btnDeclineFollow.setVisible(true);
    }
    
    @FXML
    void replyNotificationSelected(MouseEvent event) {
    	// Mostra pulsanti Accept e Decline
		btnAccept.setVisible(false);
		btnDecline.setVisible(false);
		btnDelete.setVisible(true);
		btnAcceptFollow.setVisible(false);
		btnDeclineFollow.setVisible(false);
    }
    
    @FXML
    void acceptJoinNotification(MouseEvent event) {
    	/* Notifica di join selezionata */
    	JoinNotificationBean joinNotificationBean = tvNotifyJoin.getSelectionModel().getSelectedItem();
    	
    	if(joinNotificationBean != null) {
    		NotifyController notifyController = new NotifyController();
    		try {
    			notifyController.acceptJoinNotification(this.userBean.getUsername(), joinNotificationBean);
    		} catch (SystemException e) {
    			this.showAlertError(e.getMessage());
    		} catch (SeatsNotAvailableException e) {
    			this.showAlertInformation(e.getMessage());
    		}
    		
    		/* Aggiornamento notifiche */
    		setTableViewNotify();	
    		
    		btnAccept.setVisible(false);
    		btnDecline.setVisible(false);
    		btnAcceptFollow.setVisible(false);
    		btnDeclineFollow.setVisible(false);
    		btnDelete.setVisible(false);
    	}
    	else {
    		this.showAlertError("Please select join notification to accept!");
    	}
    }
    
    @FXML
    void acceptFollowNotification(MouseEvent event) {
    	/* Notifica di follow selezionata */
    	FollowNotificationBean followNotificationBean = tvNotifyFollow.getSelectionModel().getSelectedItem();
    	
    	if(followNotificationBean != null) {
    		NotifyController notifyController = new NotifyController();
    		try {
    			notifyController.acceptFollowNotification(userBean.getUsername(), followNotificationBean);
    		} catch (SystemException e) {
    			this.showAlertError(e.getMessage());
    		}
    		
    		/* Aggiornamento notifiche */
    		setTableViewNotify();	
    		
    		btnAccept.setVisible(false);
    		btnDecline.setVisible(false);
    		btnAcceptFollow.setVisible(false);
    		btnDeclineFollow.setVisible(false);
    		btnDelete.setVisible(false);
    	}
    	else {
    		this.showAlertError("Please select one follow notification!");
    	}
    }

    @FXML
    void declineJoinNotification(MouseEvent event) {
    	/* Notifica di join selezionata */
    	JoinNotificationBean joinNotificationBean = tvNotifyJoin.getSelectionModel().getSelectedItem();
    	
    	if(joinNotificationBean != null) {
    		NotifyController notifyController = new NotifyController();
    		try {
    			notifyController.declineJoinNotification(userBean.getUsername(), joinNotificationBean);
    		} catch (SystemException e) {
    			this.showAlertError(e.getMessage());
    		}
    		
        	/* Aggiornamento notifiche */
    		setTableViewNotify();
    		
        	btnAccept.setVisible(false);
    		btnDecline.setVisible(false);
    		btnAcceptFollow.setVisible(false);
    		btnDeclineFollow.setVisible(false);
    		btnDelete.setVisible(false);
    	}
    	else {
    		this.showAlertError("Please select join notification to decline!");
    	}		
    }

    @FXML
    void declineFollowNotification(MouseEvent event) {
    	/* Notifica di follow selezionata */
    	FollowNotificationBean followNotificationBean = tvNotifyFollow.getSelectionModel().getSelectedItem();
    	
    	if(followNotificationBean != null) {
        	NotifyController notifyController = new NotifyController();
        	try {
    			notifyController.declineFollowNotification(userBean.getUsername(), followNotificationBean);
    		} catch (SystemException e) {
    			this.showAlertError(e.getMessage());
    		}
        	
        	/* Aggiornamento notifiche */
        	setTableViewNotify();
        	
        	btnAccept.setVisible(false);
    		btnDecline.setVisible(false);
    		btnAcceptFollow.setVisible(false);
    		btnDeclineFollow.setVisible(false);
    		btnDelete.setVisible(false);
    	}
    	else {
    		this.showAlertError("Please select follow notification to decline!");
    	}
    }

    @FXML
    void delete(MouseEvent event) {
    	/* Notifica da eliminare */
    	ReplyNotificationBean replyNotificationBean = tvNotifyReply.getSelectionModel().getSelectedItem();
    	
    	if(replyNotificationBean != null) {
    		NotifyController notifyController = new NotifyController();
        	try {
    			notifyController.deleteNotification(replyNotificationBean);
    		} catch (SystemException e) {
    			this.showAlertError(e.getMessage());
    		}
        	
        	/* Aggiornamento notifiche */
    		setTableViewNotify();
    		
    		btnAccept.setVisible(false);
    		btnDecline.setVisible(false);
    		btnAcceptFollow.setVisible(false);
    		btnDeclineFollow.setVisible(false);
    		btnDelete.setVisible(false);
    	}
    	else {
    		this.showAlertError("Please select reply notification to delete!");
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
		alertPhoto.setTitle("Information");
		alertPhoto.setResizable(false);
		alertPhoto.showAndWait();
    }
    
    @FXML
    void onMouseAcceptEntered(MouseEvent event) {
    	btnAccept.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseAcceptExited(MouseEvent event) {
    	btnAccept.setStyle(COLOR_EXITED);
    }
    
    @FXML
    void onMouseFollowEntered(MouseEvent event) {
    	btnAcceptFollow.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseFollowExited(MouseEvent event) {
    	btnAcceptFollow.setStyle(COLOR_EXITED);
    }
    
    @FXML
    void onMouseDeclineEntered(MouseEvent event) {
    	btnDecline.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseDeclineExited(MouseEvent event) {
    	btnDecline.setStyle(COLOR_EXITED);
    }

    @FXML
    void onMouseFollowDeclineEntered(MouseEvent event) {
    	btnDeclineFollow.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseFollowDeclineExited(MouseEvent event) {
    	btnDeclineFollow.setStyle(COLOR_EXITED);
    }
    
    @FXML
    void onMouseDeleteEntered(MouseEvent event) {
    	btnDelete.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseDeleteExited(MouseEvent event) {
    	btnDelete.setStyle(COLOR_EXITED);
    }    
    
    @FXML
    void setTableFollow(MouseEvent event) {
    	lblFollowNotifications.setVisible(true);
    	lblJoinNotifications.setVisible(false);
    	lblReplyNotifications.setVisible(false);
    	tvNotifyFollow.setVisible(true);
    	tvNotifyJoin.setVisible(false);
    	tvNotifyReply.setVisible(false);
    	btnAccept.setVisible(false);
		btnDecline.setVisible(false);
		btnDelete.setVisible(false);
		btnAcceptFollow.setVisible(false);
		btnDeclineFollow.setVisible(false);
    }

    @FXML
    void setTableJoin(MouseEvent event) {
    	lblFollowNotifications.setVisible(false);
    	lblJoinNotifications.setVisible(true);
    	lblReplyNotifications.setVisible(false);
    	tvNotifyFollow.setVisible(false);
    	tvNotifyJoin.setVisible(true);
    	tvNotifyReply.setVisible(false);
    	btnAccept.setVisible(false);
		btnDecline.setVisible(false);
		btnDelete.setVisible(false);
		btnAcceptFollow.setVisible(false);
		btnDeclineFollow.setVisible(false);
    }

    @FXML
    void setTableReply(MouseEvent event) {
    	lblFollowNotifications.setVisible(false);
    	lblJoinNotifications.setVisible(false);
    	lblReplyNotifications.setVisible(true);
    	tvNotifyFollow.setVisible(false);
    	tvNotifyJoin.setVisible(false);
    	tvNotifyReply.setVisible(true);
    	btnAccept.setVisible(false);
		btnDecline.setVisible(false);
		btnDelete.setVisible(false);
		btnAcceptFollow.setVisible(false);
		btnDeclineFollow.setVisible(false);
    }
    
    public void setTableViewNotify() {
    	this.setTableJoinNotifications();
    	this.setTableFollowNotifications();
    	this.setTableReplyNotifications();
    }
    
    private void setTableJoinNotifications() {
    	NotifyController notifyPageController = new NotifyController();
    	ObservableList<JoinNotificationBean> joinNotificationBeans;
		joinNotificationBeans = FXCollections.observableArrayList(notifyPageController.retrieveJoinNotifications(this.userBean.getUsername()));	
		tvNotifyJoin.setItems(joinNotificationBeans);
		int numJoinNotifications = joinNotificationBeans.size();
		lblNJoin.setText(String.valueOf(numJoinNotifications));
	}
    
    private void setTableFollowNotifications() {
    	NotifyController notifyPageController = new NotifyController();
    	ObservableList<FollowNotificationBean> followNotificationBeans;
		followNotificationBeans = FXCollections.observableArrayList(notifyPageController.retrieveFollowNotifications(this.userBean.getUsername()));
		tvNotifyFollow.setItems(followNotificationBeans);
		int numFollowNotifications = followNotificationBeans.size();
		lblNFollow.setText(String.valueOf(numFollowNotifications));
	}
    
    private void setTableReplyNotifications() {
    	NotifyController notifyPageController = new NotifyController();
    	ObservableList<ReplyNotificationBean> replyNotificationBeans;
		replyNotificationBeans = FXCollections.observableArrayList(notifyPageController.retrieveReplyNotifications(this.userBean.getUsername()));
		tvNotifyReply.setItems(replyNotificationBeans);
		int numReplyNotifications = replyNotificationBeans.size();
		lblNReply.setText(String.valueOf(numReplyNotifications));
	}
    
    public void setPrevPage(int prevPage) {
    	this.previousPage = prevPage;
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
    
    @FXML
    void onMouseSearchFollowEntered(MouseEvent event) {
    	lblSearchPeople.setTextFill(Color.web(TEXT_COLOR_ENTERED));
    }

    @FXML
    void onMouseSearchFollowExited(MouseEvent event) {
    	lblSearchPeople.setTextFill(Color.web(TEXT_COLOR_EXITED));
    }
    
    public void setUser(UserBean user) {
    	this.userBean = user;
    	lblUsername.setText(user.getUsername());
    }
        
}
