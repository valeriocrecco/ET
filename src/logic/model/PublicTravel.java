package logic.model;

public class PublicTravel extends PrivateTravel {
	
	private Integer availableSeats;
	
	public PublicTravel() {
		super();
		availableSeats = 0;
	}

	public Integer getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(Integer availableSeats) {
		this.availableSeats = availableSeats;
	}
	
}
