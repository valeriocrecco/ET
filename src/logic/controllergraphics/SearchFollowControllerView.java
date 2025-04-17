package logic.controllergraphics;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.bean.UserBean;
import logic.bean.PrivateTravelBean;
import logic.bean.PublicTravelBean;
import logic.controllers.SearchFollowController;
import logic.exceptions.AddFollowerException;
import logic.exceptions.FollowRequestException;
import logic.exceptions.SystemException;
import logic.util.CloseResources;
import logic.util.NotifSingletonClass;

public class SearchFollowControllerView implements Initializable {
	
    @FXML
    private TableView<UserBean> tvUsers;

    @FXML
    private TableColumn<UserBean, String> tcUsername;
    
    @FXML
    private ImageView btnSettings;

    @FXML
    private Label lblUsername;

    @FXML
    private Label lblUploadPhotos;
    
    @FXML
    private ImageView btnHome;

    @FXML
    private Button btnLogout;

    @FXML
    private ImageView ivNotifyBell;

    @FXML
    private ImageView btnSearchFollow;

    @FXML
    private Label lblSearchUser;

    @FXML
    private TextField fdUserName;
    
    @FXML
    private Button btnSearch;

    @FXML
    private ImageView ivBack;

    @FXML
    private Label lblErrorUsername;

    @FXML
    private Label lblReqOk;

    @FXML
    private Label lblReqKo;

    @FXML
    private Button btnAdd;
    
    private Image imgSettings = new Image(getClass().getResourceAsStream("/logic/views/images/settingsButtonBasic.png"));
    private Image imgSettingsOver = new Image("/logic/views/images/settingsButtonOver.png");
    private Image imgBell = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBell.png"));
    private Image imgBellNotif = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBellActive.png"));
    private Image imgBellOver = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBellSelected.png"));
    private Image imgBack = new Image(getClass().getResourceAsStream("/logic/views/images/back.png"));
    private Image imgBackOver = new Image(getClass().getResourceAsStream("/logic/views/images/back-over.png"));
    private Image imgHome = new Image(getClass().getResourceAsStream("/logic/views/images/home.png"));
    private Image imgHomeOver = new Image(getClass().getResourceAsStream("/logic/views/images/homeSelected.png"));

    private UserBean userBean;
    private PrivateTravelBean vgBean;
    private PublicTravelBean vgrBean;
    
    private int pageNumber = 5;
    private int previousPage;
    private int kindTravel;
    
    private AtomicBoolean closeThread = new AtomicBoolean(false);
    private int notifStatus = 0;
    
    private static final String COLOR_ENTERED = "-fx-background-color: #d16002";
	private static final String COLOR_EXITED = "-fx-background-color: #f9ab51";
	
    @Override
    public void initialize(URL location, ResourceBundle resources) {
		tcUsername.setCellValueFactory(new PropertyValueFactory<>("username"));	
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

	   	 		case 9:
	 		    	stage = (Stage) ivBack.getScene().getWindow();
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
			    	stage = (Stage) ivBack.getScene().getWindow();
					 	
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
		   	 		stage = (Stage) ivBack.getScene().getWindow();
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
			 	
	   	 		case 4:
		   	 		stage = (Stage) ivBack.getScene().getWindow();				 	
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
	   	 			
	   	 		case 8:
			 		stage = (Stage) ivBack.getScene().getWindow();
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

			 	case 6:
			    	stage = (Stage) ivBack.getScene().getWindow();				 	
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
			 		
			 	case 12:
			    	stage = (Stage) ivBack.getScene().getWindow();				 	
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
			    	stage = (Stage) ivBack.getScene().getWindow();			 	
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
			 	
			 	case 7:
		    		stage = (Stage) ivBack.getScene().getWindow();
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
			 	
			 	case 11:
			    	stage = (Stage) lblSearchUser.getScene().getWindow();		    		
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
			 		stage = (Stage) ivBack.getScene().getWindow();
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

    @FXML
    void onMouseSearchEntered(MouseEvent event) {
    	btnSearch.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseSearchExited(MouseEvent event) {
    	btnSearch.setStyle(COLOR_EXITED);
    }
    
    @FXML
    void searchFollower(ActionEvent event) {    	
    	
		try {
		   	lblErrorUsername.setVisible(false);
		   	if(fdUserName.getText() != null) {
		   		SearchFollowController searchFollowController = new SearchFollowController(); 
				ObservableList<UserBean>users = FXCollections.observableList(searchFollowController.loadUsers(lblUsername.getText(), fdUserName.getText()));
				tvUsers.setItems(users);	
		   	}
		   	else {
	    		lblErrorUsername.setVisible(true);
	    		lblErrorUsername.setText("Please insert the name of the user to search");
	    	}
		} catch (SystemException e) {
			showAlertError(e.getMessage());
		}
    	
    }
    
    @FXML
    void addFollower(ActionEvent event) {
    	lblReqOk.setVisible(false);
    	lblReqKo.setVisible(false);
    	UserBean followed = tvUsers.getSelectionModel().getSelectedItem();
    	if(followed != null) {
    		try {
    			SearchFollowController searchFollowController = new SearchFollowController();
    			searchFollowController.sendFollowRequest(this.userBean.getUsername(), followed.getUsername());
    			lblReqOk.setVisible(true);
    		} catch (FollowRequestException e) {
    			lblReqOk.setVisible(false);
        		lblReqKo.setVisible(true);
    		} catch (SystemException|AddFollowerException e) {
    			this.showAlertError(e.getMessage());
    		}   							
    	}
    	else {
    		this.showAlertError("Please, select a user to follow!");
    	}
    }

    @FXML
    void onMouseAddEntered(MouseEvent event) {
    	btnAdd.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseAddExited(MouseEvent event) {
    	btnAdd.setStyle(COLOR_EXITED);
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
    void userProfile(MouseEvent event) {
    	
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
    void onMouseBellEntered(MouseEvent event) {
    	ivNotifyBell.setImage(imgBellOver);
    }

    @FXML
    void onMouseBellExited(MouseEvent event) {
    	if(notifStatus == 0) {
    	    ivNotifyBell.setImage(imgBell);
    	}
    	else {
    	    ivNotifyBell.setImage(imgBellNotif);
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
    void onMouseSearchFollowerEntered(MouseEvent event) {
    	btnLogout.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseSearchFollowerExited(MouseEvent event) {
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
    void onMouseUsernameEntered(MouseEvent event) {
    	lblUsername.setTextFill(Color.web("#f9ab51"));
    }

    @FXML
    void onMouseUsernameExited(MouseEvent event) {
    	lblUsername.setTextFill(Color.web("#ffffff"));
    }
    
    @FXML
    void onMouseBackEntered(MouseEvent event) {
    	ivBack.setImage(imgBackOver);
    }

    @FXML
    void onMouseBackExited(MouseEvent event) {
    	ivBack.setImage(imgBack);
    }
    
    @FXML
    void onMouseHomeEntered(MouseEvent event) {
    	btnHome.setImage(imgHomeOver);
    }

    @FXML
    void onMouseHomeExited(MouseEvent event) {
    	btnHome.setImage(imgHome);
    }
    
    private void showAlertError(String cause) {
    	Alert alertPhoto = new Alert(AlertType.ERROR);
		alertPhoto.setHeaderText(cause);
		alertPhoto.setTitle("Error");
		alertPhoto.setResizable(false);
		alertPhoto.showAndWait();
    }
       
    public void setPrevPage(int prevPage) {
    	this.previousPage = prevPage;
    }
    
    public void setUser(UserBean user) {
    	this.userBean = user;
    	lblUsername.setText(user.getUsername());
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
