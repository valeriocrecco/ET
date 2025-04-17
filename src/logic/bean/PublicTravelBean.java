package logic.bean;

public class PublicTravelBean extends PrivateTravelBean {
	
	private String availableSeats;
	
	public PublicTravelBean() {
		super();
		availableSeats = "";
	}

	public String getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(String availableSeats) {
		this.availableSeats = availableSeats;
	}		
		
}
