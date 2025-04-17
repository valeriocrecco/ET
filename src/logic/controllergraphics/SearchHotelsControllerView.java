package logic.controllergraphics;

import java.awt.Desktop;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
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
import javafx.util.Duration;
import logic.bean.HotelBean;
import logic.bean.UserBean;
import logic.bean.PrivateTravelBean;
import logic.bean.PublicTravelBean;
import logic.controllers.PlanController;
import logic.util.CloseResources;
import logic.util.NotifSingletonClass;

public class SearchHotelsControllerView implements Initializable {
	
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
    private TableView<HotelBean> table;

    @FXML
    private TableColumn<HotelBean, String> colHotel;

    @FXML
    private Button btnSelectHotel;

    @FXML
    private Button btnSearch;

    @FXML
    private ImageView ivwLoading;

    @FXML
    private TextField fdDest;

    @FXML
    private TextField fdStars;

    @FXML
    private TextField fdPrice;

    @FXML
    private TextField fdBreakfast;

    @FXML
    private TextField fdStartDate;

    @FXML
    private TextField fdEndDate;

    @FXML
    private TextField fdTrav;

    @FXML
    private TextField fdRooms;

    @FXML
    private Button btnSeeMore;
        
    private Image imgSettings = new Image(getClass().getResourceAsStream("/logic/views/images/settingsButtonBasic.png"));
    private Image imgSettingsOver = new Image(getClass().getResourceAsStream("/logic/views/images/settingsButtonOver.png"));
    private Image imgBack = new Image(getClass().getResourceAsStream("/logic/views/images/back.png"));
    private Image imgBackOver = new Image(getClass().getResourceAsStream("/logic/views/images/back-over.png"));
    private Image imgHome = new Image(getClass().getResourceAsStream("/logic/views/images/home.png"));
    private Image imgHomeOver = new Image(getClass().getResourceAsStream("/logic/views/images/homeSelected.png"));
    private Image imgBell = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBell.png"));
    private Image imgBellOver = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBellSelected.png"));
    private Image imgBellNotif = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBellActive.png"));
    private Image imageLoading = new Image(getClass().getResourceAsStream("/logic/views/images/black-loading-icon.png"));    
    
    private AtomicBoolean flag = new AtomicBoolean(false);
    
    private UserBean userBean;
    private PrivateTravelBean vgBean;
    private PublicTravelBean vgrBean;
    
    private int pageNumber = 10;
    private int kindTravel;
    
    private AtomicBoolean closeThread = new AtomicBoolean(false);
    private int notifStatus = 0;
    
	private ObservableList<HotelBean> listHotels;
	
	private static final String COLOR_ENTERED = "-fx-background-color: #d16002";
	private static final String COLOR_EXITED = "-fx-background-color: #f9ab51";
	private static final String TEXT_COLOR_ENTERED = "#f9ab51";
	private static final String TEXT_COLOR_EXITED = "#ffffff";
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colHotel.setCellValueFactory(new PropertyValueFactory<>("hotelName"));
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
			FXMLLoader loaderHome = new FXMLLoader(getClass().getResource("/logic/views/HomeView.fxml"));
			root = (Parent) loaderHome.load();
			
			HomeControllerView cpController = loaderHome.getController();
			cpController.setUser(this.userBean);
			cpController.startThread();
			
	   		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	   		
	   		Scene scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    @FXML
    void onMouseBtnSelectHotelClicked(MouseEvent event) {
    	
    	Stage stage = new Stage();
    	stage.setTitle("Summary");
    	stage.initOwner(lblUsername.getScene().getWindow());
    	stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.UTILITY);
		
		HotelBean selectedHotel = null;
		
		try {
			
	    	if (table.getSelectionModel().getSelectedItem() != null) {
	    		
	    		selectedHotel = table.getSelectionModel().getSelectedItem();
	    		
	        	if(this.kindTravel == 1) {
	        		//CREATE A PRIVATE TRAVEL
	        		//status == 1 --> CREATE A PRIVATE TRAVEL
	        		        		       		
	        		this.vgBean.getHotelInfoBean().setHotelName(selectedHotel.getHotelName());
	            	this.vgBean.getHotelInfoBean().setHotelLink(selectedHotel.getHotelLink());
	            	
	        		FXMLLoader loader = new FXMLLoader();
	        		loader.setLocation(getClass().getResource("/logic/views/SummaryPrivateTravelView.fxml"));
	        		loader.load();
	        		
	        		SummaryPrivateTravelControllerView summaryPrivateTravelControllerView = loader.getController();
					summaryPrivateTravelControllerView.setPrivateTravelInfo(this.vgBean);
					
					stage.setScene(new Scene(loader.getRoot()));
					stage.setResizable(false);
					stage.showAndWait();
					
					this.goHome();	
	        	}
	        	else {
	        		//CREATE A PUBLIC TRAVEL
	        		//status == 0 --> CREATE A PUBLIC TRAVEL
	        	
	        		this.vgrBean.getHotelInfoBean().setHotelName(selectedHotel.getHotelName());
	        		this.vgrBean.getHotelInfoBean().setHotelLink(selectedHotel.getHotelLink());
	        		    		
	        		FXMLLoader loader = new FXMLLoader();
	        		loader.setLocation(getClass().getResource("/logic/views/SummaryPublicTravelView.fxml"));
	        		
						loader.load();
					
	        		SummaryPublicTravelControllerView summaryPublicTravelControllerView = loader.getController();
					summaryPublicTravelControllerView.setPublicTravelInfo(this.vgrBean);
					
					stage.setScene(new Scene(loader.getRoot()));
					stage.setResizable(false);
					stage.showAndWait();
					
					this.goHome();
	        	}	
	        }
	    	else {
	    		this.showAlertError("Please select an hotel!");
	    	}    
    	
		} catch (IOException e) {
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
    		npcController.setUser(this.userBean);
    		npcController.setTableViewNotify();
    		npcController.setPrevPage(this.pageNumber);
    		npcController.setKindTravel(this.kindTravel);
    		
    		if(this.kindTravel == 0) {
    			npcController.setPublicTravelInfo(this.vgrBean);
    		}
    		else {
    			npcController.setPrivateTravelInfo(this.vgBean);
    		}
    		
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
    void onMouseBtnSelectHotelEntered(MouseEvent event) {
    	btnSelectHotel.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseBtnSelectHotelExited(MouseEvent event) {
    	btnSelectHotel.setStyle(COLOR_EXITED);
    }
    
    @FXML
    void onMouseSearchClicked(MouseEvent event) {
    	
    	btnSearch.setDisable(true);
		ivwLoading.setVisible(true);
    	ivwLoading.setImage(imageLoading);
		
    	PlanController planController = new PlanController();
    	RotateTransition rt = new RotateTransition(Duration.millis(1000), ivwLoading);
    	rt.setByAngle(360);
    	rt.setCycleCount(Animation.INDEFINITE);
    	rt.setInterpolator(Interpolator.LINEAR);
    	rt.play();
    	
    	// Thread che effettua la ricerca degli hotel
    	Thread threadSearch = new Thread(() ->
		{
			HotelBean hotelBean = new HotelBean();
			hotelBean.setBreakfast(fdBreakfast.getText());
			hotelBean.setNumRooms(fdRooms.getText());
			hotelBean.setPrice(fdPrice.getText());
			hotelBean.setStars(fdStars.getText());
			listHotels = FXCollections.observableArrayList(planController.searchHotels(fdDest.getText(), fdStartDate.getText(), fdEndDate.getText(), fdTrav.getText(), hotelBean));
			flag.set(true);
		});
    	
    	Thread threadUpdateTable = new Thread(() ->
		{
			while(!flag.get()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			
			int numLinks = listHotels.size(); // Numero di hotel trovati
			String[] linksHotel;
			linksHotel = new String[numLinks];
			
			for (int i=0; i<numLinks; i++) {
				linksHotel[i] = listHotels.get(i).getHotelLink();
			}
			
			table.setItems(listHotels);			
			rt.pause();
			ivwLoading.setVisible(false);
			btnSelectHotel.setVisible(true);
			btnSeeMore.setVisible(true);
		});
    	
    	threadSearch.start(); 	   // Avvia il thread di ricerca
    	threadUpdateTable.start(); // Avvia il thread che aggiorna la tableview
    }
	
    @FXML
    void seeMoreHotel(MouseEvent event) {
    	HotelBean selectedHotel = null;
    	selectedHotel = table.getSelectionModel().getSelectedItem();
	    if (selectedHotel != null) {
	    	this.openLinkHotel(selectedHotel.getHotelLink());
	    }
	    else {
	    	this.showAlertError("Please, select an hotel!");
	    }
    }
    
    private void showAlertError(String message) {
    	Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText(message);
		alert.setTitle("Error");
		alert.setResizable(false);
		alert.showAndWait();
    }
    
    @FXML
    void onMouseBtnSeeMoreEntered(MouseEvent event) {
    	btnSeeMore.setStyle(COLOR_ENTERED);
    }
    
    @FXML
    void onMouseBtnSeeMoreExited(MouseEvent event) {
    	btnSeeMore.setStyle(COLOR_EXITED);
    }
    
    private void openLinkHotel(String link) {
		try {
			Desktop.getDesktop().browse(new URL(link).toURI());
		} catch (IOException|URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@FXML
    void back(MouseEvent event) {
    	
    	 Stage stage;
		 Parent root;
		 
		 this.closeThreads();
		 
		 try {
    		stage = (Stage) btnBack.getScene().getWindow();
		 	
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/PlanView.fxml"));
    		root = (Parent) loader.load();
    		
    		PlanControllerView p2cController = loader.getController();
    		p2cController.setUser(this.userBean);

    		if(this.kindTravel == 0) {
    			p2cController.setPublicTravelInfo(this.vgrBean);
    			p2cController.setKindTravel(this.kindTravel);
    		}
    		else {
    			p2cController.setPrivateTravelInfo(this.vgBean);
    			p2cController.setTravField(fdTrav.getText());
    			p2cController.setKindTravel(this.kindTravel);
    		}
    		
    		p2cController.startThread();
    		
    		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    		
    		Scene scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    		stage.setOnCloseRequest(we -> {
    			p2cController.closeThreads();
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
    		spController.setKindTravel(this.kindTravel);
    		if(this.kindTravel == 0) {
    			spController.setPublicTravelInfo(this.vgrBean);
    		}
    		
    		if(this.kindTravel == 1) {
    			spController.setPrivateTravelInfo(this.vgBean);
    		}
    		
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
	void settingsButtonEntered(MouseEvent event) {
		btnSettings.setImage(imgSettingsOver);
	}
	
	@FXML
	void settingsButtonExited(MouseEvent event) {
		btnSettings.setImage(imgSettings);
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
	 void onMouseSearchEntered(MouseEvent event) {
	    btnSearch.setStyle(COLOR_ENTERED);
	 }

	 @FXML
	 void onMouseSearchExited(MouseEvent event) {
	    btnSearch.setStyle(COLOR_EXITED);
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
    		pcController.setPrevPage(this.pageNumber);
    		pcController.setKindTravel(this.kindTravel);
    		if(this.kindTravel == 0) {
    			pcController.setPublicTravelInfo(this.vgrBean);
    		}
    		
    		if(this.kindTravel == 1) {
    			pcController.setPrivateTravelInfo(this.vgBean);
    		}
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
	 void home(MouseEvent event) {
		 
	    Stage stage;
    	Parent root;
    	
    	this.closeThreads();
    	
    	try {
    	
    		stage = (Stage) btnLogout.getScene().getWindow();
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/HomeView.fxml"));
    		root = (Parent) loader.load();
    		
    		HomeControllerView cpcChoiceController = loader.getController();
    		cpcChoiceController.setUser(this.userBean);
    		cpcChoiceController.startThread();
    		
    		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    		
    		Scene scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
    		stage.setScene(scene);
    		stage.setResizable(false);
    		stage.show();
    		stage.setOnCloseRequest(we -> {
    			cpcChoiceController.closeThreads();
    			CloseResources closeResources = new CloseResources();
				closeResources.closeApplication();
				stage.close();
	            System.exit(0);
	    	});
    	} catch (IOException e){
    		e.printStackTrace();
    	}
	 }
    
	public void setDestination(String dest) {
		fdDest.setText(dest);
	}
	
	public void setStars(String stars) {
		fdStars.setText(stars);
	}
	
	public void setPrice(String price) {
		fdPrice.setText(price);
	}
	
	public void setBreakfast(String breakfast) {
		if (breakfast.equalsIgnoreCase("true"))
			fdBreakfast.setText("Included");
		else
			fdBreakfast.setText("Not included");
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
    void onMouseSettingsEntered(MouseEvent event) {
    	btnSettings.setImage(imgSettingsOver);
    }

    @FXML
    void onMouseSettingsExited(MouseEvent event) {
    	btnSettings.setImage(imgSettings);
    }

	public void setStartDate(String startDate) {
		fdStartDate.setText(startDate);
	}
	
	public void setEndDate(String endDate) {
		fdEndDate.setText(endDate);
	}
	
	public void setNumRooms(String numRooms) {
		fdRooms.setText(numRooms);
	}
	
	public void setKindTravel(int kindTravel) {
		this.kindTravel = kindTravel;
	}
	
	public void setPublicTravelInfo(PublicTravelBean vgr) {
		this.vgrBean = vgr;
		fdBreakfast.setText(vgr.getHotelInfoBean().getBreakfast());
		fdDest.setText(vgr.getDestinationBean());
		fdStartDate.setText(vgr.getStartDateBean());
		fdEndDate.setText(vgr.getEndDateBean());
		String price = vgr.getHotelInfoBean().getPrice();
		price = price.replace("euro", "€");
		fdPrice.setText(price);
		fdRooms.setText(vgr.getHotelInfoBean().getNumRooms());
		fdStars.setText(vgr.getHotelInfoBean().getStars());
		fdTrav.setText(vgr.getNumTravelersBean());
	}
	
	public void setPrivateTravelInfo(PrivateTravelBean vg) {
		this.vgBean = vg;
		fdBreakfast.setText(vg.getHotelInfoBean().getBreakfast());
		fdDest.setText(vg.getDestinationBean());
		fdStartDate.setText(vg.getStartDateBean());
		fdEndDate.setText(vg.getEndDateBean());
		String price = vg.getHotelInfoBean().getPrice();
		price = price.replace("euro", "€");
		fdPrice.setText(price);
		fdRooms.setText(vg.getHotelInfoBean().getNumRooms());
		fdStars.setText(vg.getHotelInfoBean().getStars());
		fdTrav.setText(vg.getNumTravelersBean());
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
    		sfpController.setPrevPage(this.pageNumber);
    		sfpController.setKindTravel(this.kindTravel);
    		if(this.kindTravel == 0) {
    			sfpController.setPublicTravelInfo(this.vgrBean);
    		}
    		
    		if(this.kindTravel == 1) {
    			sfpController.setPrivateTravelInfo(this.vgBean);
    		}
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