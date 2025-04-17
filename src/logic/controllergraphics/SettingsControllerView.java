package logic.controllergraphics;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import logic.util.CloseResources;
import logic.util.NotifSingletonClass;

public class SettingsControllerView {
	
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
    private Button btnInfo;

    @FXML
    private ImageView btnBack;

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
    
    private int kindTravel; 
    private int pageNumber = 7; 
    private int previousPage; 
    
    private AtomicBoolean closeThread = new AtomicBoolean(false);
    private int notifStatus = 0;
    
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
	    	switch(this.previousPage) {
	       	 
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
	     	 		stage = (Stage) btnLogout.getScene().getWindow();
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
   		    		p2Controller.setKindTravel(this.kindTravel);
 		    		if(this.kindTravel == 0) {
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
   		    		shController.setKindTravel(this.kindTravel);
   		    		if(this.kindTravel == 0) {
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
    void showUserPage(MouseEvent event) {
    	
    	Stage stage;
    	Parent root;
    	
    	this.closeThreads();
    	
    	try {
    	
    		stage = (Stage) lblSearchPeople.getScene().getWindow();
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/ProfileView.fxml"));
    		root = (Parent) loader.load();
    		
    		ProfileControllerView pcController = loader.getController();
    		pcController.setUser(this.userBean);
    		pcController.setPrevPage(this.pageNumber);
    		pcController.setTableView();
    		pcController.setTableViewGRTravels();
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
    void personalInfo(ActionEvent event) {
    	
    	Stage stage;
    	Parent root;
    	
    	this.closeThreads();
    	
    	try {
    	
    		stage = (Stage) btnInfo.getScene().getWindow();
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/UserInfoView.fxml"));
    		root = (Parent) loader.load();
    		
    		UserInfoControllerView upsController = loader.getController();
    		upsController.setUser(this.userBean);
    		upsController.setPrevPage(pageNumber);
    		upsController.setUserPhotoProfile();
    		upsController.startThread();
    		
    		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    		Scene scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    		stage.setOnCloseRequest(we -> {
    			upsController.closeThreads();
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
    void onMouseLogoutEntered(MouseEvent event) {
    	btnLogout.setStyle("-fx-background-color: #d16002");
    }

    @FXML
    void onMouseLogoutExited(MouseEvent event) {
    	btnLogout.setStyle("-fx-background-color: #f9ab51");
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
    void onMouseSearchFollowEntered(MouseEvent event) {
    	lblSearchPeople.setTextFill(Color.web(TEXT_COLOR_ENTERED));
    }

    @FXML
    void onMouseSearchFollowExited(MouseEvent event) {
    	lblSearchPeople.setTextFill(Color.web(TEXT_COLOR_EXITED));
    }
    
    @FXML
    void onMouseInfoEntered(MouseEvent event) {
    	btnInfo.setStyle("-fx-background-color: #d16002");
    }

    @FXML
    void onMouseInfoExited(MouseEvent event) {
    	btnInfo.setStyle("-fx-background-color: #f9ab51");
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
