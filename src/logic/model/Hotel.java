package logic.model;

public class Hotel {
	
	private String hotelName;
	private int stars;
	private String price;
	private String breakfast;
	private int numRooms;
	private String hotelLink;
	
	public Hotel() {
		this.hotelName = "";
		this.stars = 3;
		this.price = "";
		this.breakfast = "";
		this.numRooms = 0;
		this.hotelLink = "";
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
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
	
	public int getNumRooms() {
		return numRooms;
	}

	public void setNumRooms(int numRooms) {
		this.numRooms = numRooms;
	}

	public String getHotelLink() {
		return hotelLink;
	}

	public void setHotelLink(String hotelLink) {
		this.hotelLink = hotelLink;
	}
	
}
