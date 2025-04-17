package logic.bean;

import logic.exceptions.DescriptionSyntaxException;
import logic.exceptions.DestinationSyntaxException;
import logic.exceptions.NumTravSyntaxException;
import logic.exceptions.TravelNameSyntaxException;

public class PrivateTravelBean {
	
	private String idTravelBean;
	private String creatorBean;
	private String destinationBean;
	private String startDateBean;
	private String endDateBean;
	private String numTravelersBean;
	private String descriptionBean;
	private String travelNameBean;
	private HotelBean hotelInfoBean;
	
	public PrivateTravelBean() {
		idTravelBean = "";
		creatorBean = "";
		destinationBean = "";
		startDateBean = "";
		endDateBean = "";
		descriptionBean = "";
		travelNameBean = "";
		hotelInfoBean = new HotelBean();
	}
	
	public String getIdTravelBean() {
		return idTravelBean;
	}

	public void setIdTravelBean(String idTravel) {
		this.idTravelBean = idTravel;
	}

	public String getCreatorBean() {
		return creatorBean;
	}

	public void setCreatorBean(String creator) {
		this.creatorBean = creator;
	}

	public String getDestinationBean() {
		return destinationBean;
	}

	public void setDestinationBean(String destination) {
		this.destinationBean = destination;
	}
	
	public String getStartDateBean() {
		return startDateBean;
	}

	public void setStartDateBean(String startDate) {
		this.startDateBean = startDate;
	}

	public String getEndDateBean() {
		return endDateBean;
	}

	public void setEndDateBean(String endDate) {
		this.endDateBean = endDate;
	}

	public String getDescriptionBean() {
		return descriptionBean;
	}

	public void setDescriptionBean(String description) {
		this.descriptionBean = description;
	}
	
	public void setAndValidateDescription(String description) throws DescriptionSyntaxException {
		description = this.validateDescription(description);
		this.descriptionBean = description;
	}
	
	public String getTravelNameBean() {
		return travelNameBean;
	}

	public void setTravelNameBean(String travelName) {
		this.travelNameBean = travelName;
	}
	
	public HotelBean getHotelInfoBean() {
		return hotelInfoBean;
	}

	public void setHotelInfoBean(HotelBean hotelInfo) {
		this.hotelInfoBean = hotelInfo;
	}
	
	public String getNumTravelersBean() {
		return numTravelersBean;
	}

	public void setNumTravelersBean(String numMaxUt) {
		this.numTravelersBean = numMaxUt;
	}
	
	public void setAndValidateDestination(String destination) throws DestinationSyntaxException {
		destination = this.validateDestination(destination);
		this.destinationBean = destination;
	}
	
	public void setAndValidateTravelName(String travelName) throws TravelNameSyntaxException {
		travelName = this.validateTravelName(travelName);
		this.travelNameBean = travelName;
	}
	
	public void setAndValidateNumTravelers(String numTrav) throws NumTravSyntaxException {
		this.validateNumTrav(numTrav);
		this.numTravelersBean = numTrav;
	}
	
	private String validateDescription(String description) throws DescriptionSyntaxException {
		if(description.length() <= 10 || description.length() >= 400)
			throw new DescriptionSyntaxException("Description syntax error: minimum 10 characters and maximum 400 characters");
		description = this.checkApostrophe(description);
		return description;
	}
	
	private String validateDestination(String destination) throws DestinationSyntaxException {
		
		if(destination.length() == 0) 
			throw new DestinationSyntaxException("Please, insert destination to continue...");
		
		if(!(destination.length() <= 45 && this.validateFormatDestination(destination))) 
			throw new DestinationSyntaxException("Destination name syntax error:\n - insert only characters, \' or blank");
		
		destination = this.checkApostrophe(destination);
		return destination;
	}
	
	public void validateNumTrav(String numTrav) throws NumTravSyntaxException {
		
		if(numTrav.length() == 0)
			throw new NumTravSyntaxException("Please, insert number of travellers to continue...");
		
		for(int i =0; i<numTrav.length(); i++){
            char c = numTrav.charAt(i);
            if(!Character.isDigit(c))
            	throw new NumTravSyntaxException("Number of travellers syntax error:\n - insert only digits!");

        }
		
	}
	
	//metodo che verifica il nome del viaggio
	private String validateTravelName(String travelName) throws TravelNameSyntaxException {
		if(travelName.length() == 0) 
			throw new TravelNameSyntaxException("Please, insert travel name to continue...");
		
		if(!(travelName.length() <= 45 && this.validateStringWithApostrophe(travelName))) 
			throw new TravelNameSyntaxException("Travel name syntax error:\n - insert only characters, digits, \' or blank");
		
		travelName = this.checkApostrophe(travelName);
		return travelName;
	}
	
	private boolean validateFormatDestination(String str) {
		str = str.toLowerCase();
	    char[] charArray = str.toCharArray();
	    for (int i = 0; i < charArray.length; i++) {
		    char ch = charArray[i];
		    if (!((ch >= 'a' && ch <= 'z') || ch == 39 || ch == ' ' )) {
			    return false;
		    }
	    }
	    return true;
	}
	
	private boolean validateStringWithApostrophe(String str) {
	   str = str.toLowerCase();
	   char[] charArray = str.toCharArray();
	   for (int i = 0; i < charArray.length; i++) {
		   char ch = charArray[i];
		   if (!((ch >= 'a' && ch <= 'z') || ch == 39 || ch == ' ' || (ch >= 48 && ch <= 57 ))) {
			   return false;
		   }
	   }
	   return true;
	}
	
	private String checkApostrophe(String str) {
		String result = "";
		for(int i =0; i<str.length(); i++){
            char c = str.charAt(i);
            result = result.concat(String.valueOf(c));
            if(c == 39) {
               result = result.concat(String.valueOf(c));
            }
        }
		return result;
	}
	
}
