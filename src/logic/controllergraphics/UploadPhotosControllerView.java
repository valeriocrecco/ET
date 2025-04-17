package logic.controllergraphics;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import logic.bean.UserBean;
import logic.bean.PrivateTravelBean;
import logic.bean.PublicTravelBean;
import logic.controllers.UploadPhotosController;
import logic.controllers.ProfileController;
import logic.exceptions.SystemException;

public class UploadPhotosControllerView implements Initializable {

    @FXML
    private TableView<PrivateTravelBean> tvPrivateTravels;

    @FXML
    private TableColumn<PrivateTravelBean, String> tcTravelName;

    @FXML
    private TableColumn<PrivateTravelBean, String> tcDestination;

    @FXML
    private TableView<PublicTravelBean> tvPublicTravels;

    @FXML
    private TableColumn<PublicTravelBean, String> tcTravelGrName;

    @FXML
    private TableColumn<PublicTravelBean, String> tcDestinationGr;

    @FXML
    private Button btnAddPhotosGr;

    @FXML
    private Button btnAddPhotos;
    
    private UserBean userBean;
    
	private static final String COLOR_ENTERED = "-fx-background-color: #d16002";
	private static final String COLOR_EXITED = "-fx-background-color: #f9ab51";
    private static final String DEST = "destinationBean";
    private static final String TRAVEL = "travelNameBean";
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	//Destination, Hotel
		tcDestination.setCellValueFactory(new PropertyValueFactory<>(DEST));
		tcTravelName.setCellValueFactory(new PropertyValueFactory<>(TRAVEL));
		
		//Destination, Hotel
    	tcDestinationGr.setCellValueFactory(new PropertyValueFactory<>(DEST));
		tcTravelGrName.setCellValueFactory(new PropertyValueFactory<>(TRAVEL));
	}
    
    @FXML
    void privateTravelSelected(MouseEvent event) {
    	btnAddPhotosGr.setVisible(false);
    	btnAddPhotos.setVisible(true);
    }

    @FXML
    void publicTravelSelected(MouseEvent event) {
    	btnAddPhotos.setVisible(false);
    	btnAddPhotosGr.setVisible(true);
    }
    
    @FXML
    void addPhotos(MouseEvent event) {
    	PrivateTravelBean v = tvPrivateTravels.getSelectionModel().getSelectedItem();	
    	if(v != null) {
    		ArrayList<String> filenames = new ArrayList<>();
    		FileChooser fc = new FileChooser();
    		fc.setTitle("Add Photos");
    		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png", "*.gif"));
    		List<File> files = fc.showOpenMultipleDialog(btnAddPhotos.getScene().getWindow());
    		if (files != null) {
    			for(File file : files) {
    				filenames.add(file.toURI().toString());
    			}	
    			try {
    				UploadPhotosController addTravelPhotosController = new UploadPhotosController();
    				addTravelPhotosController.addTravelPhotos(v.getIdTravelBean(), files, this.userBean.getUsername());
    				showAlertInformation("Photos correctly uploaded!");
    			} catch (SystemException e) {
    				showAlertError("Error on photo upload!");
    			}	
    		}
    	}
    	else {
    		showAlertInformation("Please, select a private travel!");
    	}
    }

    @FXML
    void addPhotosGr(MouseEvent event) {
    	PublicTravelBean vgr = tvPublicTravels.getSelectionModel().getSelectedItem();
    	if(vgr != null) {
    		ArrayList<String> filenames = new ArrayList<>();
    		FileChooser fc = new FileChooser();
    		fc.setTitle("Add Photos");
    		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png", "*.gif"));
    		List<File> files = fc.showOpenMultipleDialog(btnAddPhotosGr.getScene().getWindow());
    		if (files != null) {
    			for(File file : files) {
    				filenames.add(file.toURI().toString());
    			}	
    			try {
    				UploadPhotosController addTravelPhotosController = new UploadPhotosController();
    				addTravelPhotosController.addTravelGroupPhotos(vgr.getIdTravelBean(), files, this.userBean.getUsername());
    				showAlertInformation("Photos correctly uploaded!");
    			} catch (SystemException e) {
    				showAlertError("Error on photo upload!");
    			}	
    		}
    	}
    	else {
    		showAlertInformation("Please, select a public travel!");
    	}
    }
    
    public void setTableView() {
		try {
			ProfileController profileController = new ProfileController();
			ObservableList<PrivateTravelBean> travels;
			travels = FXCollections.observableList(profileController.loadMyOldT(userBean.getUsername()));
			tvPrivateTravels.setItems(travels);
		} catch (SystemException e) {
			showAlertError(e.getMessage());
		}
    }   
    
    public void setTableViewGRTravels() {
		try {
			ProfileController profileController = new ProfileController();
			ObservableList<PublicTravelBean> travels;
			travels = FXCollections.observableArrayList(profileController.loadMyOldTGR(this.userBean.getUsername()));
			tvPublicTravels.setItems(travels);
		} catch (SystemException e) {
			showAlertError(e.getMessage());
		}
    }  
    
    public void setUserBean(UserBean userBean) {
    	this.userBean = userBean;
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
    void onMouseAddPhotosEntered(MouseEvent event) {
    	btnAddPhotos.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseAddPhotosExited(MouseEvent event) {
    	btnAddPhotos.setStyle(COLOR_EXITED);
    }

    @FXML
    void onMouseAddPhotosGrEntered(MouseEvent event) {
    	btnAddPhotosGr.setStyle(COLOR_ENTERED);
    }

    @FXML
    void onMouseAddPhotosGrExited(MouseEvent event) {
    	btnAddPhotosGr.setStyle(COLOR_EXITED);
    }
    
}
