package logic.controllergraphics;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import logic.bean.PrivateTravelBean;

public class PrivateTravelDetailView {
	
	@FXML
    private Label lblUsernameTravel;

    @FXML
    private Label lblDest;

    @FXML
    private Label lblData;

    @FXML
    private Label lblPosti;

    @FXML
    private Label lblDataEnd;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblStars;

    @FXML
    private Label lblTravellers;

    @FXML
    private Label lblRooms;

    @FXML
    private Label lblBreakfast;

    @FXML
    private Label lblHotelName;

    @FXML
    private TextArea txtDescription;
    
    private PrivateTravelBean vg;
    
    @FXML
    void onMouseHotelNameClicked(MouseEvent event) {
    	openLinkHotel(vg.getHotelInfoBean().getHotelLink());
    }

    @FXML
    void onMouseHotelNameEntered(MouseEvent event) {
    	lblHotelName.setTextFill(Color.web("#153bc2"));
    }

    @FXML
    void onMouseHotelNameExited(MouseEvent event) {
    	lblHotelName.setTextFill(Color.web("#f9ab51"));
    }
    
    private void openLinkHotel(String link) {
		try {
			Desktop.getDesktop().browse(new URL(link).toURI());
		} catch (IOException|URISyntaxException e) {
			e.printStackTrace();
		} 
	}
    
    public void setTravelInfo(PrivateTravelBean vg) {
    	this.vg = vg;
    	lblUsernameTravel.setText(vg.getCreatorBean());
    	lblDest.setText(vg.getDestinationBean());
    	lblData.setText(vg.getStartDateBean());
    	lblDataEnd.setText(vg.getEndDateBean());
    	lblHotelName.setText(vg.getHotelInfoBean().getHotelName());
    	lblStars.setText(vg.getHotelInfoBean().getStars());
    	lblRooms.setText(vg.getHotelInfoBean().getNumRooms());
    	lblBreakfast.setText(vg.getHotelInfoBean().getBreakfast());
    	String price = vg.getHotelInfoBean().getPrice();
    	price = price.replace("euro", "€");
    	lblPrice.setText(price);
    	txtDescription.setText(vg.getDescriptionBean());
    }

}
