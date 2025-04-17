package logic.controllergraphics;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.controllers.PlanController;
import logic.exceptions.DatesException;
import logic.exceptions.DestinationSyntaxException;
import logic.exceptions.NumRoomsSyntaxException;
import logic.exceptions.NumTravSyntaxException;
import logic.exceptions.SystemException;
import logic.exceptions.TravRoomException;
import logic.exceptions.TravelNameSyntaxException;
import logic.bean.DestinationBean;
import logic.bean.HotelBean;
import logic.bean.UserBean;
import logic.bean.PrivateTravelBean;
import logic.bean.PublicTravelBean;
import logic.util.CloseResources;
import logic.util.NotifSingletonClass;

public class PlanControllerView implements Initializable {
	
	@FXML
    private Button btnLogout;
	
	@FXML
	private Button btnNext;
    
    @FXML
    private Label lblUsername;
    
    @FXML
    private Label lblUploadPhotos;
    
    @FXML
    private ChoiceBox<String> cbPrice;
    
    @FXML
    private CheckBox cbBreakfast;
    
    @FXML
    private ImageView btnHome;
    
    @FXML
    private TextField tfTrav;
    
    @FXML
    private ImageView ivNotifyBell;
    
    @FXML
    private TextField tfRooms;
    
    @FXML
    private TextField tfDest;
    
    @FXML
    private ImageView btnSettings;
    
    @FXML
    private ImageView ivBack;
    
    @FXML
    private ImageView ivw1;
    
    @FXML
    private ImageView ivw2;
    
    @FXML
    private ImageView ivw3;
    
    @FXML
    private ImageView ivw4;
    
    @FXML
    private ImageView ivw5;
    
    @FXML
    private ImageView btnSearchFollow;

    @FXML
    private Label lblSearchUsers;
    
    @FXML
    private Label lblDestError;

    @FXML
    private Label lblTravError;

    @FXML
    private Label lblRoomError;
    
    @FXML
    private Label lblTravelNameError;

    @FXML
    private Label lblDatesError;
    
    @FXML
    private ImageView ivAirplane;
    
    @FXML
    private TextField tfTravelName;

    @FXML
    private DatePicker dpStart;

    @FXML
    private DatePicker dpEnd;
    
    @FXML
    private RadioButton radioPrivateTravel;

    @FXML
    private RadioButton radioPublicTravel;

    @FXML
    private RadioButton radioSea;

    @FXML
    private RadioButton radioMountain;

    @FXML
    private CheckBox cbArt;

    @FXML
    private CheckBox cbYoung;

    @FXML
    private ImageView btnNextResult;

    @FXML
    private ImageView btnPreviousResult;

    @FXML
    private ChoiceBox<String> cebContinent;

    @FXML
    private Button btnSearch;

    @FXML
    private Label lblResult;
    
    @FXML
    private Label lblFiltersError;
    
    private Image imgSettings = new Image(getClass().getResourceAsStream("/logic/views/images/settingsButtonBasic.png"));
    private Image imgSettingsOver = new Image("/logic/views/images/settingsButtonOver.png");
    private Image imgBack = new Image(getClass().getResourceAsStream("/logic/views/images/back.png"));
    private Image imgBackOver = new Image(getClass().getResourceAsStream("/logic/views/images/back-over.png"));
    private Image imgHome = new Image(getClass().getResourceAsStream("/logic/views/images/home.png"));
    private Image imgHomeOver = new Image(getClass().getResourceAsStream("/logic/views/images/homeSelected.png"));
    private Image imgBell = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBell.png"));
    private Image imgBellOver = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBellSelected.png"));
    private Image imgBellNotif = new Image(getClass().getResourceAsStream("/logic/views/images/notifyBellActive.png"));
    private Image goldStar = new Image(getClass().getResourceAsStream("/logic/views/images/gold-star.png"));
    private Image whiteStar = new Image(getClass().getResourceAsStream("/logic/views/images/white-star.png")); 
    
    private UserBean userBean;
    private PrivateTravelBean vgBean;
    private PublicTravelBean vgrBean;
    private int pageNumber = 3;
    
    private AtomicBoolean closeThread = new AtomicBoolean(false);
    private int notifStatus = 0;
    
    private static final String COLOR_ENTERED = "-fx-background-color: #d16002";
    private static final String COLOR_EXITED = "-fx-background-color: #f9ab51";
    private static final String TEXT_COLOR_ENTERED = "#f9ab51";
    private static final String TEXT_COLOR_EXITED = "#ffffff";
    private static final String INCLUDED = "Included";
    private static final String NOT_INCLUDED = "Not included";
    private static final String NOT_DEFINED = "Not defined";
  
	private int stars = 3;
    
    private List<DestinationBean> destinationBeans;
    private int destIndex = 0;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	ToggleGroup kindLocation;
        ToggleGroup kindTravel;
    	ObservableList<String> continentList = FXCollections.observableArrayList(NOT_DEFINED, "Africa", "America","Asia", "Europe", "Oceania");
        cebContinent.setItems(continentList);
    	cebContinent.setValue(NOT_DEFINED); // default value
    	
    	ObservableList<String> priceList = FXCollections.observableArrayList(NOT_DEFINED,"Less than € 50", "€ 50 - € 100", "€ 100 - € 150", "€ 150 - € 225", "More than € 225");
    	cbPrice.setItems(priceList);
    	cbPrice.setValue(NOT_DEFINED); // default value
    	
    	kindLocation = new ToggleGroup();
    	this.radioSea.setToggleGroup(kindLocation);
    	this.radioMountain.setToggleGroup(kindLocation);
    	
    	kindTravel = new ToggleGroup();
    	this.radioPrivateTravel.setToggleGroup(kindTravel);
    	this.radioPublicTravel.setToggleGroup(kindTravel);		
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
    	lblUploadPhotos.setTextFill(Color.web(TEXT_COLOR_ENTERED));
    }
    
    @FXML
    void onMouseUploadPhotosExited(MouseEvent event) {
    	lblUploadPhotos.setTextFill(Color.web(TEXT_COLOR_EXITED));
    }
    
    private void validationPublicTravelSuccess(PublicTravelBean viaggioGruppoBean) {
    	
    	Stage stage;
    	Parent root;
    	
    	this.closeThreads();
    	
    	try {
			stage = (Stage) ivBack.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/SearchHotelView.fxml"));
			root = (Parent) loader.load();
			
			SearchHotelsControllerView shController = loader.getController();
			shController.setUser(this.userBean);
			shController.setKindTravel(0);
			shController.setPublicTravelInfo(viaggioGruppoBean);
			shController.startThread();
			
			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			
			Scene scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
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
		} catch (IOException e) {
			e.printStackTrace();
		} 
    }
    
    private void validationPrivateTravelSuccess(PrivateTravelBean viaggioBean) {
    	
    	Stage stage;
    	Parent root;
    	
    	this.closeThreads();
    	
    	try {
			stage = (Stage) ivBack.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/SearchHotelView.fxml"));
			root = (Parent) loader.load();
			
			SearchHotelsControllerView shController = loader.getController();
			shController.setUser(this.userBean);
			shController.setKindTravel(1);
			shController.setPrivateTravelInfo(viaggioBean);
			shController.startThread();
			
			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			
			Scene scene = new Scene(root, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
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
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
    }

    @FXML
    void next(ActionEvent event) {
    	
		lblTravelNameError.setVisible(false);
		lblDatesError.setVisible(false);
		lblDestError.setVisible(false);
		lblTravError.setVisible(false);
		lblRoomError.setVisible(false);
		
		if(radioPublicTravel.isSelected()) {
			this.radioPublicTravelSelected();
		}
		else {
			this.radioPrivateTravelSelected();			
		}
    }
    
    private void radioPublicTravelSelected() { 	
    	PublicTravelBean viaggioGruppoBean = new PublicTravelBean();
		viaggioGruppoBean.setCreatorBean(this.userBean.getUsername());
		String price = cbPrice.getSelectionModel().getSelectedItem();
		price = price.replace("€", "euro");
		viaggioGruppoBean.getHotelInfoBean().setPrice(price);
		viaggioGruppoBean.getHotelInfoBean().setStars(String.valueOf(stars));
		if(cbBreakfast.isSelected()) {
			viaggioGruppoBean.getHotelInfoBean().setBreakfast(INCLUDED);
		}
		else {
			viaggioGruppoBean.getHotelInfoBean().setBreakfast(NOT_INCLUDED);
		}
		
		try {
			PlanController planController = new PlanController();
			viaggioGruppoBean.setAndValidateTravelName(tfTravelName.getText());
			if(dpStart.getValue() != null && dpEnd.getValue() != null) {
				viaggioGruppoBean.setStartDateBean(dpStart.getValue().toString());
				viaggioGruppoBean.setEndDateBean(dpEnd.getValue().toString());
			}
			planController.validateDates(viaggioGruppoBean.getStartDateBean(), viaggioGruppoBean.getEndDateBean());
			viaggioGruppoBean.setAndValidateDestination(tfDest.getText());
			viaggioGruppoBean.setAndValidateNumTravelers(tfTrav.getText());
			viaggioGruppoBean.getHotelInfoBean().setAndValidateNumRooms(tfRooms.getText());
			planController.validateTravelersAndRooms(viaggioGruppoBean.getNumTravelersBean(), viaggioGruppoBean.getHotelInfoBean().getNumRooms());
			
			this.validationPublicTravelSuccess(viaggioGruppoBean);
		} catch (TravelNameSyntaxException|DestinationSyntaxException|NumTravSyntaxException|NumRoomsSyntaxException|DatesException|TravRoomException e) {
			this.showAlertError(e.getMessage());
		}
    }
    
    private void radioPrivateTravelSelected() {
    	PrivateTravelBean viaggioBean = new PrivateTravelBean();
		viaggioBean.setCreatorBean(this.userBean.getUsername());
		String price = cbPrice.getSelectionModel().getSelectedItem();
		price = price.replace("€", "euro");
		viaggioBean.getHotelInfoBean().setPrice(price);
		viaggioBean.getHotelInfoBean().setStars(String.valueOf(stars));
		if(cbBreakfast.isSelected()) {
			viaggioBean.getHotelInfoBean().setBreakfast(INCLUDED);
		}
		else {
			viaggioBean.getHotelInfoBean().setBreakfast(NOT_INCLUDED);
		}
		
		try {
			PlanController planController = new PlanController();
			viaggioBean.setAndValidateTravelName(tfTravelName.getText());
			if(dpStart.getValue() != null && dpEnd.getValue() != null) {
				viaggioBean.setStartDateBean(dpStart.getValue().toString());
				viaggioBean.setEndDateBean(dpEnd.getValue().toString());
			}
			planController.validateDates(viaggioBean.getStartDateBean(), viaggioBean.getEndDateBean());
			viaggioBean.setAndValidateDestination(tfDest.getText());
			viaggioBean.setAndValidateNumTravelers(tfTrav.getText());
			viaggioBean.getHotelInfoBean().setAndValidateNumRooms(tfRooms.getText());
			planController.validateTravelersAndRooms(viaggioBean.getNumTravelersBean(), viaggioBean.getHotelInfoBean().getNumRooms());
			
			this.validationPrivateTravelSuccess(viaggioBean);
		} catch (TravelNameSyntaxException|DestinationSyntaxException|NumTravSyntaxException|NumRoomsSyntaxException|DatesException|TravRoomException e) {
			this.showAlertError(e.getMessage());
		}
		
    }
    
    private void handlerNextPageInfoTravel() {
    	if(radioPublicTravel.isSelected()) {
    		this.handlerNextPageInfoPublicTravel();
    	}
    	else {
    		this.handlerNextPageInfoPrivateTravel();
    	}
    }

    private void handlerNextPageInfoPrivateTravel() {
    	HotelBean hotelBean = new HotelBean();
    	if(cbBreakfast.isSelected()) {
			hotelBean.setBreakfast(INCLUDED);
		}
		else {
			hotelBean.setBreakfast(NOT_INCLUDED);
		}
		hotelBean.setNumRooms(tfRooms.getText());
		hotelBean.setPrice(cbPrice.getSelectionModel().getSelectedItem());
		hotelBean.setStars(String.valueOf(stars));
		
		this.vgBean = new PrivateTravelBean();
		this.vgBean.setTravelNameBean(tfTravelName.getText());
		this.vgBean.setDestinationBean(tfDest.getText());
		this.vgBean.setCreatorBean(lblUsername.getText());
		if(dpStart.getValue() != null) {
			this.vgBean.setStartDateBean(dpStart.getValue().toString());
		}
		if(dpEnd.getValue() != null) {
			this.vgBean.setEndDateBean(dpEnd.getValue().toString());
		}
		this.vgBean.setHotelInfoBean(hotelBean);
		this.vgBean.setNumTravelersBean(tfTrav.getText());
    }
    
    private void handlerNextPageInfoPublicTravel() {
    	HotelBean hotelBean = new HotelBean();
    	if(cbBreakfast.isSelected()) {
			hotelBean.setBreakfast(INCLUDED);
		}
		else {
			hotelBean.setBreakfast(NOT_INCLUDED);
		}
		hotelBean.setNumRooms(tfRooms.getText());
		hotelBean.setPrice(cbPrice.getSelectionModel().getSelectedItem());
		hotelBean.setStars(String.valueOf(stars));
		
		this.vgrBean = new PublicTravelBean();
		this.vgrBean.setTravelNameBean(tfTravelName.getText());
		this.vgrBean.setDestinationBean(tfDest.getText());
		this.vgrBean.setCreatorBean(lblUsername.getText());
		
		if(dpStart.getValue() != null) {
			this.vgrBean.setStartDateBean(dpStart.getValue().toString());
		}
		
		if(dpEnd.getValue() != null) {
			this.vgrBean.setEndDateBean(dpEnd.getValue().toString());
		}
		this.vgrBean.setHotelInfoBean(hotelBean);
		this.vgrBean.setNumTravelersBean(tfTrav.getText());
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
    		if(radioPublicTravel.isSelected()) {
    			    			
    			handlerNextPageInfoTravel();
    			
    			spController.setKindTravel(0);
    			spController.setPublicTravelInfo(this.vgrBean);
    		}
    		else {

    			handlerNextPageInfoTravel();
    			
    			//spController.setTravellers() aggiungere metodo in settingsPage per settare il numero di viaggiatori
    			spController.setKindTravel(1);
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
    		
    		if(radioPublicTravel.isSelected()) {
    		
    			handlerNextPageInfoTravel();
    			
    			npcController.setKindTravel(0);
    			npcController.setPublicTravelInfo(this.vgrBean);
    		}
    		else {
    		
    			handlerNextPageInfoTravel();
    			
    			//spController.setTravellers() aggiungere metodo in settingsPage per settare il numero di viaggiatori
    			npcController.setKindTravel(1);
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
    void home(MouseEvent event) {
    	this.goHome();
    }
    
    @FXML
    void back(MouseEvent event) {
    	this.goHome();
    }
    
    private void goHome() {
    	
    	 Stage stage;
		 Parent root;
		 
		 this.closeThreads();
		 
		 try {
			 	
			stage = (Stage) ivBack.getScene().getWindow();
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/views/HomeView.fxml"));
			root = (Parent) loader.load();
			
			HomeControllerView cpController = loader.getController();
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
    		if(radioPublicTravel.isSelected()) {
    	
    			handlerNextPageInfoTravel();
    			
    			pcController.setKindTravel(0);
    			pcController.setPublicTravelInfo(this.vgrBean);
    		}
    		else {
    			
    			handlerNextPageInfoTravel();
    			
    			//spController.setTravellers() aggiungere metodo in settingsPage per settare il numero di viaggiatori
    			pcController.setKindTravel(1);
    			pcController.setPrivateTravelInfo(this.vgBean);
    		}
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
    void nextResult(MouseEvent event) {
    	
    	lblDestError.setVisible(false);
    	
    	destIndex++;
    	
    	if(destIndex == destinationBeans.size()-1) {
    		btnNextResult.setVisible(false);
    		btnPreviousResult.setVisible(true);
    		lblResult.setText(destinationBeans.get(destIndex).getDestinationName());
    		tfDest.setText(destinationBeans.get(destIndex).getDestinationName());
    	}
    	else{
    		btnNextResult.setVisible(true);
			btnPreviousResult.setVisible(true);
			lblResult.setText(destinationBeans.get(destIndex).getDestinationName());
			tfDest.setText(destinationBeans.get(destIndex).getDestinationName());
    	}
    }
    
    @FXML
    void previousResult(MouseEvent event) {
    	
    	lblDestError.setVisible(false);   
    	
    	destIndex--;
    	
    	if(destIndex == 0) {
    		btnPreviousResult.setVisible(false);
    		btnNextResult.setVisible(true);
    		lblResult.setText(destinationBeans.get(destIndex).getDestinationName());
    		tfDest.setText(destinationBeans.get(destIndex).getDestinationName());
    	}
    	else {
    		btnPreviousResult.setVisible(true);
    		btnNextResult.setVisible(true);	
    		lblResult.setText(destinationBeans.get(destIndex).getDestinationName());
    		tfDest.setText(destinationBeans.get(destIndex).getDestinationName());
    	}    	
    }
    
    @FXML
    void search(ActionEvent event) {
    	
    	lblDestError.setVisible(false);
    	lblFiltersError.setVisible(false);
    	
    	List<DestinationBean> dests;
  
		try {
			/* 1 - Controllo se ho impostato almeno un filtro */
	    	if(radioSea.isSelected() || radioMountain.isSelected() || cbArt.isSelected() || cbYoung.isSelected() || !cebContinent.getSelectionModel().getSelectedItem().equalsIgnoreCase(NOT_DEFINED)) {
	        	PlanController planController = new PlanController();
	        	DestinationBean destinationBean = new DestinationBean();
	        	destinationBean.setSea(radioSea.isSelected());
	        	destinationBean.setMountain(radioMountain.isSelected());
	        	destinationBean.setArt(cbArt.isSelected());
	        	destinationBean.setYoung(cbYoung.isSelected());
	        	destinationBean.setContinent(cebContinent.getSelectionModel().getSelectedItem());
				dests = planController.findSpecialDestination(destinationBean);
				destinationBeans = dests;
	    		destIndex = 0;
	    		lblResult.setText(dests.get(0).getDestinationName());
	    		tfDest.setText(dests.get(0).getDestinationName());
	        	
	        	if(destinationBeans.size() < 2) {
	        		btnNextResult.setVisible(false);
	        		btnPreviousResult.setVisible(false);
	        	}
	        	else {
	        		btnNextResult.setVisible(true);
	        		btnPreviousResult.setVisible(false);
	        	}
	    	}
	    	/* 2 - Se almeno un filtro è stato impostato allora chiama controller applicativo */
	    	else {
	    		lblFiltersError.setVisible(true);
	    	}
		} catch (SystemException e) {
			showAlertError(e.getMessage());
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
    void searchUsers(MouseEvent event) {
		
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
    		
    		if(radioPublicTravel.isSelected()) {
    			
    			handlerNextPageInfoTravel();
    			
    			sfpController.setKindTravel(0);
    			sfpController.setPublicTravelInfo(this.vgrBean);
    		}
    		else {
    			
    			handlerNextPageInfoTravel();
    			
    			//spController.setTravellers() aggiungere metodo in settingsPage per settare il numero di viaggiatori
    			sfpController.setKindTravel(1);
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
    
    @FXML
	void onMouseSearchUsersEntered(MouseEvent event) {
		lblSearchUsers.setTextFill(Color.web(TEXT_COLOR_ENTERED));
	}

	@FXML
	void onMouseSearchUsersExited(MouseEvent event) {
		lblSearchUsers.setTextFill(Color.web(TEXT_COLOR_EXITED));
	}
    
    public void setImageStars(String s) {
    	
    	switch(Integer.valueOf(s)) {
    		case 1:
    			ivw1.setImage(goldStar);    
    	    	ivw2.setImage(whiteStar);
    	    	ivw3.setImage(whiteStar);
    	    	ivw4.setImage(whiteStar);    
    	    	ivw5.setImage(whiteStar);
    	    	stars = 1;
    			break;
    		case 2:
    			ivw1.setImage(goldStar);    
    	    	ivw2.setImage(goldStar);
    	    	ivw3.setImage(whiteStar);
    	    	ivw4.setImage(whiteStar);    
    	    	ivw5.setImage(whiteStar);
    	    	stars = 2;
    			break;
    		case 3:
    			ivw1.setImage(goldStar);    
    	    	ivw2.setImage(goldStar);
    	    	ivw3.setImage(goldStar);
    	    	ivw4.setImage(whiteStar);    
    	    	ivw5.setImage(whiteStar);
    	    	stars = 3;
    			break;
    		case 4:
    			ivw1.setImage(goldStar);    
    	    	ivw2.setImage(goldStar);
    	    	ivw3.setImage(goldStar);
    	    	ivw4.setImage(goldStar);    
    	    	ivw5.setImage(whiteStar);
    	    	stars = 4;
    			break;
    		case 5:
    			ivw1.setImage(goldStar);    
    	    	ivw2.setImage(goldStar);
    	    	ivw3.setImage(goldStar);
    	    	ivw4.setImage(goldStar);    
    	    	ivw5.setImage(goldStar);
    	    	stars = 5;
    			break;
    		
    		default:
    			break;
    	}
    }
    
    @FXML
    void onMouseStarEntered1(MouseEvent event) {
		ivw1.setImage(goldStar);    
    	ivw2.setImage(whiteStar);
    	ivw3.setImage(whiteStar);
    	ivw4.setImage(whiteStar);    
    	ivw5.setImage(whiteStar);
    	stars = 1;
    }

    @FXML
    void onMouseStarEntered2(MouseEvent event) {
    	ivw1.setImage(goldStar);    
    	ivw2.setImage(goldStar);
    	ivw3.setImage(whiteStar);
    	ivw4.setImage(whiteStar);    
    	ivw5.setImage(whiteStar);
    	stars = 2;
    }

    @FXML
    void onMouseStarEntered3(MouseEvent event) {
    	ivw1.setImage(goldStar);    
    	ivw2.setImage(goldStar);
    	ivw3.setImage(goldStar);
    	ivw4.setImage(whiteStar);    
    	ivw5.setImage(whiteStar);
    	stars = 3;
    }

    @FXML
    void onMouseStarEntered4(MouseEvent event) {
    	ivw1.setImage(goldStar);    
    	ivw2.setImage(goldStar);
    	ivw3.setImage(goldStar);
    	ivw4.setImage(goldStar);    
    	ivw5.setImage(whiteStar);
    	stars = 4;
    }

    @FXML
    void onMouseStarEntered5(MouseEvent event) {
    	ivw1.setImage(goldStar);    
    	ivw2.setImage(goldStar);
    	ivw3.setImage(goldStar);
    	ivw4.setImage(goldStar);    
    	ivw5.setImage(goldStar);
    	stars = 5;
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
    void onMouseNextEntered(MouseEvent event) {
    	btnNext.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseNextExited(MouseEvent event) {
    	btnNext.setStyle(COLOR_EXITED);
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
    void onMouseTravelNameClicked(MouseEvent event) {
    	lblTravelNameError.setVisible(false);
    }
    
    @FXML
    void onMouseDestClicked(MouseEvent event) {
    	lblDestError.setVisible(false);
    }

    @FXML
    void onMouseFromClicked(MouseEvent event) {
    	lblDatesError.setVisible(false);
    }
    
    @FXML
    void onMouseRoomsClicked(MouseEvent event) {
    	lblRoomError.setVisible(false);
    }
    
    @FXML
    void onMouseToClicked(MouseEvent event) {
    	lblDatesError.setVisible(false);
    }

    @FXML
    void onMouseTravClicked(MouseEvent event) {
    	lblTravError.setVisible(false);
    }
    
    @FXML
    void onMouseSearchEntered(MouseEvent event) {
    	btnSearch.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseSearchExited(MouseEvent event) {
    	btnSearch.setStyle(COLOR_EXITED);
    }
    
    public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}
    
    public void setDestErrorLabel(String s) {
    	lblDestError.setText(s);
    }
    
    public void setTravErrorLabel(String s) {
    	lblTravError.setText(s);
    }
    
    public void setRoomsErrorLabel(String s) {
    	lblRoomError.setText(s);
    }
    
    public void setDestField(String s) {
    	tfDest.setText(s);
    }
    
    public void setTravField(String s) {
    	tfTrav.setText(s);
    }
    
    public void setRoomsField(String s) {
    	tfRooms.setText(s);
    }
	
    public void setDestination(String s) {
    	tfDest.setText(s);
    }
    
    public void setNumTrav(String s) {
    	tfTrav.setText(s);
    }
    
    public void setNumRooms(String s) {
    	tfRooms.setText(s);
    }
    
    public void setNameTravel(String s) {
    	tfTravelName.setText(s);
    }
    
    public void setPrice(String p) {
    	cbPrice.setValue(p);
    }

	public void setNumRooms(int numRooms) {
		tfRooms.setText(String.valueOf(numRooms));
	}

	public void setNumTravellers(int numTravellers) {
		tfTrav.setText(String.valueOf(numTravellers));
	}
	
	public void setKindTravel(int kindTravel) {
		if(kindTravel == 0) {
			radioPublicTravel.setSelected(true);
			radioPrivateTravel.setSelected(false);
		}
		else {
			radioPublicTravel.setSelected(false);
			radioPrivateTravel.setSelected(true);
		}
	}
	
	private boolean checkBreakfastPublic() {
		return this.vgrBean.getHotelInfoBean().getBreakfast().equalsIgnoreCase(INCLUDED);
	}
	
	private boolean checkBreakfastPrivate() {
		return this.vgBean.getHotelInfoBean().getBreakfast().equalsIgnoreCase(INCLUDED);
	}
	
	public void setPublicTravelInfo(PublicTravelBean vgrBean) {
		boolean ret;
		this.vgrBean = vgrBean;
		tfTravelName.setText(this.vgrBean.getTravelNameBean());
		
		ret = checkBreakfastPublic();
		cbBreakfast.setSelected(ret);
		tfDest.setText(this.vgrBean.getDestinationBean());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if(!this.vgrBean.getStartDateBean().equalsIgnoreCase("")) {
			dpStart.setValue(LocalDate.parse(this.vgrBean.getStartDateBean(), formatter));
		}
		if(!this.vgrBean.getEndDateBean().equalsIgnoreCase("")) {
			dpEnd.setValue(LocalDate.parse(this.vgrBean.getEndDateBean(), formatter));
		}
		cbPrice.setValue(this.vgrBean.getHotelInfoBean().getPrice());
		tfRooms.setText(this.vgrBean.getHotelInfoBean().getNumRooms());
		setImageStars(this.vgrBean.getHotelInfoBean().getStars());
		tfTrav.setText(this.vgrBean.getNumTravelersBean());
	}
	
	public void setPrivateTravelInfo(PrivateTravelBean vgBean) {
		boolean ret;
		this.vgBean = vgBean;
		tfTravelName.setText(this.vgBean.getTravelNameBean());
		
		ret = checkBreakfastPrivate();
		cbBreakfast.setSelected(ret);
		
		tfDest.setText(this.vgBean.getDestinationBean());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if(!this.vgBean.getStartDateBean().equalsIgnoreCase("")) {
			dpStart.setValue(LocalDate.parse(this.vgBean.getStartDateBean(), formatter));
		}
		if(!this.vgBean.getEndDateBean().equalsIgnoreCase("")) {
			dpEnd.setValue(LocalDate.parse(this.vgBean.getEndDateBean(), formatter));
		}
		String price = this.vgBean.getHotelInfoBean().getPrice();
		price = price.replace("euro", "€");
		cbPrice.setValue(price);
		tfRooms.setText(this.vgBean.getHotelInfoBean().getNumRooms());
		tfTrav.setText(this.vgBean.getNumTravelersBean());
		setImageStars(this.vgBean.getHotelInfoBean().getStars());
	}

	public void setFiltersLabelError(String s) {
    	lblFiltersError.setText(s);
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

