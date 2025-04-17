package logic.bean;

import logic.exceptions.NumRoomsSyntaxException;

public class HotelBean {

	private String hotelName;
	private String stars;
	private String price;
	private String breakfast;
	private String numRooms;
	private String hotelLink;
	
	public HotelBean() {
		this.hotelName = "";
		this.stars = "";
		this.price = "";
		this.breakfast = "";
		this.numRooms = "";
		this.hotelLink = "";
	}
	
	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getStars() {
		return stars;
	}

	public void setStars(String stars) {
		this.stars = stars;
	}
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getBreakfast() {
		return breakfast;
	}

	public void setBreakfast(String breakfast) {
		this.breakfast = breakfast;
	}
	
	public String getNumRooms() {
		return numRooms;
	}

	public void setNumRooms(String numRooms) {
		this.numRooms = numRooms;
	}

	public String getHotelLink() {
		return hotelLink;
	}

	public void setHotelLink(String hotelLink) {
		this.hotelLink = hotelLink;
	}
	
	public void setAndValidateNumRooms(String numRooms) throws NumRoomsSyntaxException {
		this.validateNumRooms(numRooms);
		this.numRooms = numRooms;
	}
	
	private void validateNumRooms(String numRooms) throws NumRoomsSyntaxException {
		
		if(numRooms.length() == 0)
			throw new NumRoomsSyntaxException("Please, insert number of rooms to continue...");
		
		for(int i =0; i<numRooms.length(); i++){
            char c = numRooms.charAt(i);
            if(!Character.isDigit(c))
            	throw new NumRoomsSyntaxException("Number of rooms syntax error:\n - insert only digits!");
        }
				
		if(Integer.parseInt(numRooms) <= 0 || Integer.parseInt(numRooms) > 50) 
			throw new NumRoomsSyntaxException("Number of rooms must be positive and between 1 and 50!");
	}

}
