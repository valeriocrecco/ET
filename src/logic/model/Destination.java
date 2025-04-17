package logic.model;

public class Destination {
	
	private String destinationName;
	private int location;
	private int art;
	private int young;
	private String continent;
	
	public Destination() {
		this.destinationName = "";
		this.location = 2;
		this.art = 0;
		this.young = 0;
		this.continent = "";
	}

	public String getDestinationName() {
		return destinationName;
	}

	public int getLocation() {
		return location;
	}

	public int getArt() {
		return art;
	}

	public int getYoung() {
		return young;
	}

	public String getContinent() {
		return continent;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public void setArt(int art) {
		this.art = art;
	}

	public void setYoung(int young) {
		this.young = young;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}
}
