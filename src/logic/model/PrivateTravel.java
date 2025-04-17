package logic.model;

public class PrivateTravel {
	
	private Integer idTravel;
	private User creator;
	private Destination destination;
	private String startDate;
	private String endDate;
	private String description;
	private Integer numMaxUt;
	private String travelName;
	private Hotel hotelInfo;
	
	public PrivateTravel() {
		idTravel = 0;
		creator = new User();
		destination = new Destination();
		startDate = "";
		endDate = "";
		description = "";
		numMaxUt = 0;
		travelName = "";
		hotelInfo = new Hotel();
	}
	
	public Integer getIdTravel() {
		return idTravel;
	}

	public void setIdTravel(Integer idTravel) {
		this.idTravel = idTravel;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTravelName() {
		return travelName;
	}

	public void setTravelName(String travelName) {
		this.travelName = travelName;
	}
	
	public Integer getNumMaxUt() {
		return numMaxUt;
	}

	public void setNumMaxUt(Integer numMaxUt) {
		this.numMaxUt = numMaxUt;
	}
	
	public Hotel getHotelInfo() {
		return hotelInfo;
	}

	public void setHotelInfo(Hotel hotelInfo) {
		this.hotelInfo = hotelInfo;
	}
	
}
