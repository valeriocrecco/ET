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
import logic.util.CloseResources;
import logic.util.NotifSingletonClass;

public class HomeControllerView {

	@FXML
    private Button btnLogout;

    @FXML
    private ImageView btnSettings;

    @FXML
    private Label lblUsername;
    
    @FXML
    private Label lblHomeUsername;
  
    @FXML
    private Label lblSearchUser;

    @FXML
    private ImageView ivAirplane;
    
    @FXML
    private Button btnJoin;

    @FXML
    private Button btnManageTravel;

    @FXML
    private Button btnPlan;
    
    @FXML
    private ImageView ivNotifyBell;
    
    @FXML
    private Label lblUploadPhotos;
    
    private Image imgSettings = new Image(getClass().getResourceAsStream("/logic/views/images/settingsButtonBasic.png"));
    private Image imgSettingsOver = new Image("/logic/views/images/settingsButtonOver.png");
    private Image imgBell = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBell.png"));
    private Image imgBellNotif = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBellActive.png"));
    private Image imgBellOver = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBellSelected.png"));
   
    private UserBean userBean;
    private int pageNumber = 1;
    
    private AtomicBoolean closeThread = new AtomicBoolean(false);
    private int notifStatus = 0;
    
    private static final String COLOR_ENTERED = "-fx-background-color: #d16002";
    private static final String COLOR_EXITED = "-fx-background-color: #f9ab51";
    private static final String TEXT_COLOR_EXITED = "#ffffff";
    private static final String TEXT_COLOR_ENTERED = "#f9ab51";
    
    @FXML
    void manageMyTravels(ActionEvent event) {
    	
    	Stage stage;
    	Parent root;
		
    	this.closeThreads();
    	
    	try {
    	
    		stage = (Stage) btnManageTravel.getScene().getWindow();
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/ManageView.fxml"));
    		root = (Parent) loader.load();
    		
    		ManageTravelControllerView mtController = loader.getController();
    		mtController.setUser(userBean);
    		mtController.setTableView();
    		mtController.setTableViewGRTravels();
    		mtController.startThread();
    		
    		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    		
    		Scene scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    		stage.setOnCloseRequest(we -> {
    			mtController.closeThreads();
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
    void joinToATravel(ActionEvent event) {
    		
    	Stage stage;
    	Parent root;
    	
    	this.closeThreads();
		
    	try {
    		
    		stage = (Stage) btnJoin.getScene().getWindow();
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/JoinView.fxml"));
    		root = (Parent) loader.load();
    		
    		JoinControllerView jpController = loader.getController();
    		jpController.setUser(userBean);
    		jpController.setTableView();
    		jpController.startThread();
    		
    		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    		
    		Scene scene = new Scene(root,  gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
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
    void settings(MouseEvent event) {
    	
    	Stage stage;
    	Parent root;
    	
    	this.closeThreads();
    	
    	try {
    		stage = (Stage) btnSettings.getScene().getWindow();
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/SettingsView.fxml"));
    		root = (Parent) loader.load();
    		
    		SettingsControllerView spController = loader.getController();
    		spController.setUser(userBean);
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
    void planATravel(ActionEvent event) {
    	
    	Stage stage;
    	Parent root;
    	
    	this.closeThreads();
    	
    	try {
    		stage = (Stage) btnPlan.getScene().getWindow();
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/PlanView.fxml"));
    		root = (Parent) loader.load();
    		
    		PlanControllerView planControllerView = loader.getController();
    		planControllerView.setUser(userBean);
    		planControllerView.startThread();
    		
    		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	
    		Scene scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    		stage.setOnCloseRequest(we -> {
    			planControllerView.closeThreads();
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
    void notification(MouseEvent event) {
    	
    	Stage stage;
    	Parent root;
    	
    	this.closeThreads();
    	
    	try {
    		stage = (Stage) lblUsername.getScene().getWindow();
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/NotifyView.fxml"));
    		root = (Parent) loader.load();
    		
    		NotifyControllerView npcController = loader.getController();
    		npcController.setUser(userBean);
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
    void userProfile(MouseEvent event) {
    	
    	Stage stage;
    	Parent root;
    	
    	this.closeThreads();
    	
    	try {
    		stage = (Stage) lblUsername.getScene().getWindow();
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/ProfileView.fxml"));
    		root = (Parent) loader.load();
    		
    		ProfileControllerView pcController = loader.getController();
    		pcController.setUser(userBean);
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
    void searchUser(MouseEvent event) {
    	
    	Stage stage;
    	Parent root;
    	
    	this.closeThreads();
    	
    	try {
    		stage = (Stage) btnPlan.getScene().getWindow();

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
    void onMouseSearchEntered(MouseEvent event) {
    	lblSearchUser.setTextFill(Color.web(TEXT_COLOR_ENTERED));
    }

    @FXML
    void onMouseSearchExited(MouseEvent event) {
    	lblSearchUser.setTextFill(Color.web(TEXT_COLOR_EXITED));
    }

    @FXML
    void onMouseJoinEntered(MouseEvent event) {
    	btnJoin.setStyle(COLOR_ENTERED); 
    }
    
    @FXML
    void onMouseJoinExited(MouseEvent event) {
    	btnJoin.setStyle(COLOR_EXITED);
    }
    
    @FXML
    void onMousePlanEntered(MouseEvent event) {
    	btnPlan.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMousePlanExited(MouseEvent event) {
    	btnPlan.setStyle(COLOR_EXITED);
    }
    
    @FXML
    void onMouseManageEntered(MouseEvent event) {
    	btnManageTravel.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseManageExited(MouseEvent event) {
    	btnManageTravel.setStyle(COLOR_EXITED);
    }

    @FXML
    void onMouseLogOutEntered(MouseEvent event) {
    	btnLogout.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseLogOutExited(MouseEvent event) {
    	btnLogout.setStyle(COLOR_EXITED);
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

    public void setUser(UserBean userBean) {
    	this.userBean = userBean;
    	lblUsername.setText(userBean.getUsername());
    	lblHomeUsername.setText(userBean.getUsername());
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
