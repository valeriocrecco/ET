package logic.controllergraphics;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import logic.controllers.ManagePublicTravelController;
import logic.controllers.ManagePrivateTravelController;
import logic.bean.UserBean;
import logic.bean.PrivateTravelBean;
import logic.bean.PublicTravelBean;
import logic.exceptions.BookGroupTravelException;
import logic.exceptions.BookTravelException;
import logic.exceptions.DeleteGroupTravelException;
import logic.exceptions.DeleteTravelException;
import logic.exceptions.SystemException;
import logic.util.CloseResources;
import logic.util.NotifSingletonClass;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ManageTravelControllerView implements Initializable {
    
    @FXML
    private TableView<PrivateTravelBean> tvUserTravels;

    @FXML
    private TableColumn<PrivateTravelBean, String> tcId;

    @FXML
    private TableColumn<PrivateTravelBean, String> tcTravelName;

    @FXML
    private TableColumn<PrivateTravelBean, String> tcDest;

    @FXML
    private TableColumn<PrivateTravelBean, String> tcFrom;

    @FXML
    private TableColumn<PrivateTravelBean, String> tcTo;

    @FXML
    private TableColumn<PrivateTravelBean, String> tcDetail;
    
    @FXML
    private TableColumn<PrivateTravelBean, String> tcBook;

    @FXML
    private TableColumn<PrivateTravelBean, String> tcDelete;

    @FXML
    private TableView<PublicTravelBean> tvUserTravelsGR;

    @FXML
    private TableColumn<PublicTravelBean, String> tcIdGr;

    @FXML
    private TableColumn<PublicTravelBean, String> tcTravelNameGr;

    @FXML
    private TableColumn<PublicTravelBean, String> tcDestGr;

    @FXML
    private TableColumn<PublicTravelBean, String> tcFromGr;

    @FXML
    private TableColumn<PublicTravelBean, String> tcToGr;

    @FXML
    private TableColumn<PublicTravelBean, String> tcDetailGr;
    
    @FXML
    private TableColumn<PublicTravelBean, String> tcBookGr;

    @FXML
    private TableColumn<PublicTravelBean, String> tcDeleteGr;
   
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
    private Button btnViewInfo;

    @FXML
    private Button btnViewInfoGr;
    
    @FXML
    private ImageView btnBack;

    @FXML
    private Button btnBook;

    @FXML
    private Button btnBookGr;
    
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnDeleteGr;
    
    private Image imgSettings = new Image(getClass().getResourceAsStream("/logic/views/images/settingsButtonBasic.png"));
    private Image imgSettingsOver = new Image("/logic/views/images/settingsButtonOver.png");
    private Image imgBack = new Image(getClass().getResourceAsStream("/logic/views/images/back.png"));
    private Image imgBackOver = new Image(getClass().getResourceAsStream("/logic/views/images/back-over.png"));
    private Image imgHome = new Image(getClass().getResourceAsStream("/logic/views/images/home.png"));
    private Image imgHomeOver = new Image(getClass().getResourceAsStream("/logic/views/images/homeSelected.png"));
    private Image imgBell = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBell.png"));
    private Image imgBellOver = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBellSelected.png"));
    private Image imgBellNotif = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBellActive.png"));
    
    private static final String COLOR_ENTERED = "-fx-background-color: #d16002";
    private static final String COLOR_EXITED = "-fx-background-color: #f9ab51";
    private static final String TEXT_COLOR_ENTERED = "#f9ab51";
    private static final String TEXT_COLOR_EXITED = "#ffffff";
    
    private UserBean userBean;
    private int pageNumber = 4;
    
    private AtomicBoolean closeThread = new AtomicBoolean(false);
    private int notifStatus = 0;
   
    private ObservableList<PrivateTravelBean> travels;
    private ObservableList<PublicTravelBean> travelsGr;
    
    @Override
    public void initialize(URL location ,ResourceBundle resources) {
    	tcId.setCellValueFactory(new PropertyValueFactory<>("idTravelBean"));
		tcDest.setCellValueFactory(new PropertyValueFactory<>("destinationBean"));
		tcTravelName.setCellValueFactory(new PropertyValueFactory<>("travelNameBean"));
		tcFrom.setCellValueFactory(new PropertyValueFactory<>("startDateBean"));
		tcTo.setCellValueFactory(new PropertyValueFactory<>("endDateBean"));
		
    	tcIdGr.setCellValueFactory(new PropertyValueFactory<>("idTravelBean"));
		tcDestGr.setCellValueFactory(new PropertyValueFactory<>("destinationBean"));
		tcTravelNameGr.setCellValueFactory(new PropertyValueFactory<>("travelNameBean"));
		tcFromGr.setCellValueFactory(new PropertyValueFactory<>("startDateBean"));
		tcToGr.setCellValueFactory(new PropertyValueFactory<>("endDateBean"));
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
    		cpcController.setUser(userBean);
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
    void notify(MouseEvent event) {
    	
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
    	}catch (IOException e) {
    		e.printStackTrace();
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
    
    public void setTableView() {
    	ManagePrivateTravelController manageTravelController = new ManagePrivateTravelController();
		this.travels = FXCollections.observableList(manageTravelController.loadMyUpcomingT(this.userBean.getUsername()));	
		tvUserTravels.setItems(this.travels);
    }   
    
    public void setTableViewGRTravels() {
    	ManagePublicTravelController manageTravelController = new ManagePublicTravelController();
		this.travelsGr = FXCollections.observableList(manageTravelController.loadMyUpcomingTGR(this.userBean.getUsername()));
    	tvUserTravelsGR.setItems(this.travelsGr);
    }  
    
    @FXML
    void viewInfo(MouseEvent event) {
    	PrivateTravelBean selectedTravel;
    	selectedTravel = tvUserTravels.getSelectionModel().getSelectedItem();
    	
    	if(selectedTravel != null) {
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
			privateTravelDetailPageController.setTravelInfo(selectedTravel);
			
			stage.setScene(new Scene(loader.getRoot()));
			stage.setResizable(false);
			stage.showAndWait();
    	}
    	else {
    		this.showAlertError("Please, select a private travel to display info!");
    	}
    }

    @FXML
    void viewInfoGr(MouseEvent event) {
    	PublicTravelBean selectedTravel;
    	selectedTravel = tvUserTravelsGR.getSelectionModel().getSelectedItem();
    	
    	if(selectedTravel != null) {
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
			publicTravelDetailPageController.setTravelInfo(selectedTravel);
			
			stage.setScene(new Scene(loader.getRoot()));
			stage.setResizable(false);
			stage.showAndWait();
    	}
    	else {
    		this.showAlertError("Please, select a public travel to display info!");
    	}
    }
    
    @FXML
    void book(MouseEvent event) {
    	PrivateTravelBean selectedTravel;
    	selectedTravel = tvUserTravels.getSelectionModel().getSelectedItem();
    	
    	if(selectedTravel != null) {
    		try {
    			ManagePrivateTravelController manageTravelController = new ManagePrivateTravelController();
				manageTravelController.bookTravel(selectedTravel.getIdTravelBean());
				this.showAlertInformation("Travel booked successfully!\nYou can see it in your profile page.");
				this.travels.remove(selectedTravel);
				this.setTableView();
			} catch (BookTravelException e) {
				this.showAlertError("Booking error! please retry");
			} catch (SystemException e) {
				showAlertError(e.getMessage());
			}
    	}
    	else {
    		this.showAlertError("Please, select a private travel to book!");
    	}
    }

    @FXML
    void bookGr(MouseEvent event) {
    	PublicTravelBean selectedTravel;
    	selectedTravel = tvUserTravelsGR.getSelectionModel().getSelectedItem();
    	
    	if(selectedTravel != null) {
    		try {
    			ManagePublicTravelController manageTravelController = new ManagePublicTravelController();
				manageTravelController.bookTravelGr(selectedTravel.getIdTravelBean());
				this.showAlertInformation("Travel booked successfully!\nYou can see it in your profile page.");
				this.travelsGr.remove(selectedTravel);
				this.setTableViewGRTravels();
			} catch (BookGroupTravelException e) {
				this.showAlertError("Booking error! please retry");
			} catch (SystemException e) {
				showAlertError(e.getMessage());
			}
    	}
    	else {
    		this.showAlertError("Please, select a public travel to book!");
    	}
    }

    @FXML
    void delete(MouseEvent event) {
    	PrivateTravelBean selectedTravel;
    	selectedTravel = tvUserTravels.getSelectionModel().getSelectedItem();
    	
    	if(selectedTravel != null) {
    		try {
    			ManagePrivateTravelController manageTravelController = new ManagePrivateTravelController();
				manageTravelController.deleteTravel(selectedTravel.getIdTravelBean());
				this.showAlertInformation("Travel deleted successfully!");
				this.travels.remove(selectedTravel);
				this.setTableView();
			} catch (DeleteTravelException e) {
				this.showAlertError("Deleting error! please retry");
			} catch (SystemException e) {
				showAlertError(e.getMessage());
			}
    	}
    	else {
    		this.showAlertError("Please, select a private travel to delete!");
    	}
    }

    @FXML
    void deleteGr(MouseEvent event) {
    	PublicTravelBean selectedTravel;
    	selectedTravel = tvUserTravelsGR.getSelectionModel().getSelectedItem();
    	
    	if(selectedTravel != null) {
    		try {
    			ManagePublicTravelController manageTravelController = new ManagePublicTravelController();
				manageTravelController.deleteTravelGr(selectedTravel.getIdTravelBean());
				this.showAlertInformation("Travel deleted successfully!");
				this.travelsGr.remove(selectedTravel);
				this.setTableViewGRTravels();
			} catch (DeleteGroupTravelException e) {
				this.showAlertError("Deleting error! please retry");
			} catch (SystemException e) {
				showAlertError(e.getMessage());
			}
    	}
    	else {
    		this.showAlertError("Please, select a public travel to delete!");
    	}
    }
    
    private void showAlertInformation(String message) {
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(message);
		alert.setResizable(false);
		alert.showAndWait();
    }
    
    private void showAlertError(String message) {
    	Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText(message);
		alert.setTitle("Error");
		alert.setResizable(false);
		alert.showAndWait();
    }
    
    @FXML
    void onMouseBtnBookEntered(MouseEvent event) {
    	btnBook.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseBtnBookExited(MouseEvent event) {
    	btnBook.setStyle(COLOR_EXITED);
    }

    @FXML
    void onMouseBtnBookGrEntered(MouseEvent event) {
    	btnBookGr.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseBtnBookGrExited(MouseEvent event) {
    	btnBookGr.setStyle(COLOR_EXITED);
    }

    @FXML
    void onMouseBtnDeleteEntered(MouseEvent event) {
    	btnDelete.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseBtnDeleteExited(MouseEvent event) {
    	btnDelete.setStyle(COLOR_EXITED);
    }	

    @FXML
    void onMouseBtnDeleteGrEntered(MouseEvent event) {
    	btnDeleteGr.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseBtnDeleteGrExited(MouseEvent event) {
    	btnDeleteGr.setStyle(COLOR_EXITED);
    }

    @FXML
    void onMouseBtnViewInfoEntered(MouseEvent event) {
    	btnViewInfo.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseBtnViewInfoExited(MouseEvent event) {
    	btnViewInfo.setStyle(COLOR_EXITED);
    }

    @FXML
    void onMouseBtnViewInfoGrEntered(MouseEvent event) {
    	btnViewInfoGr.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseBtnViewInfoGrExited(MouseEvent event) {
    	btnViewInfoGr.setStyle(COLOR_EXITED);
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
    void showProfile(MouseEvent event) {
    	
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
