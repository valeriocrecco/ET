package logic.bean;

public class DestinationBean {
	
	private String destinationName;
	private boolean sea;
	private boolean mountain;
	private boolean art;
	private boolean young;
	private String continent;
	
	public DestinationBean() {
		this.destinationName = "";
		this.sea = false;
		this.mountain = false;
		this.art = false;
		this.young = false;
		this.continent = "";
	}

	public boolean getMountain() {
		return mountain;
	}

	public void setMountain(boolean mountain) {
		this.mountain = mountain;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public boolean getSea() {
		return sea;
	}

	public boolean getArt() {
		return art;
	}

	public boolean getYoung() {
		return young;
	}

	public String getContinent() {
		return continent;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public void setSea(boolean sea) {
		this.sea = sea;
	}

	public void setArt(boolean art) {
		this.art = art;
	}

	public void setYoung(boolean young) {
		this.young = young;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}
}
