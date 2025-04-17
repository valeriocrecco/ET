package logic.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import logic.bean.DestinationBean;
import logic.bean.HotelBean;
import logic.bean.PrivateTravelBean;
import logic.bean.PublicTravelBean;
import logic.dao.DestinationDao;
import logic.dao.PrivateTravelDao;
import logic.dao.PublicTravelDao;
import logic.decorator.ArtDecorator;
import logic.decorator.ContinentDecorator;
import logic.decorator.Filter;
import logic.decorator.GeneralFilter;
import logic.decorator.MountainDecorator;
import logic.decorator.SeaDecorator;
import logic.decorator.YoungDecorator;
import logic.exceptions.BookGroupTravelException;
import logic.exceptions.BookTravelException;
import logic.exceptions.DatesException;
import logic.exceptions.SaveTravelException;
import logic.exceptions.SystemException;
import logic.exceptions.TravRoomException;
import logic.model.Destination;
import logic.model.Hotel;
import logic.model.PrivateTravel;
import logic.model.PublicTravel;

public class PlanController {
	
	public List<HotelBean> searchHotels(String destination, String startDate, String endDate, String numTravellers, HotelBean hotelBean) {
		
		List<HotelBean> listHotelsBean = new ArrayList<>();
		List<Hotel> listHotels;
		
		listHotels = this.getHotel(destination, startDate, endDate, Integer.valueOf(numTravellers), hotelBean);
		for(Hotel hotel : listHotels) {
			HotelBean addHotelBean = new HotelBean();
			addHotelBean.setHotelName(hotel.getHotelName());
			addHotelBean.setHotelLink(hotel.getHotelLink());
			listHotelsBean.add(addHotelBean);
		}
		
		return listHotelsBean;
	}
	
	private List<Hotel> getHotel(String dest, String startDate, String endDate, Integer numTravellers, HotelBean hotelBean) {
		
		String urlPrefix = "https://www.expedia.it/Hotel-Search?";
		String urlPeoplePrefix = "adults=";
		String urlDestPrefix = "&destination=";
		String urlRoomsPrefix = "&rooms=";
		String urlStarsPrefix = "&star=";
		String urlPricePrefix = "&price=";
		String urlBreakfastPrefix = "&amenities=";
		String urlStartDate = "&startDate=";
		String urlEndDate = "&endDate=";
		
		List<Hotel> hotels = new ArrayList<>();
		String url = "";
		
		/* Formatta correttamente la destinazione da cercare */
		dest = modifyDest(dest);
		
		/* Aggiungi numero di persone */
		url = addTravellers(url, String.valueOf(numTravellers), urlPrefix, urlPeoplePrefix);
		/* Aggiungi destinazione */
		url = addDest(url, dest, urlDestPrefix);
		/* Aggiungi data di partenza */
		url = addStartDate(url, startDate, urlStartDate);
		/* Aggiungi data di ritorno */
		url = addEndDate(url, endDate, urlEndDate);
		/* Aggiungi numero di stanze */
		url = addRooms(url, String.valueOf(Integer.valueOf(hotelBean.getNumRooms())), urlRoomsPrefix);
		
		/* Aggiungi le stelle */
		switch (Integer.valueOf(hotelBean.getStars())) {
		
			case 1:
				url = addStars(url,"10", urlStarsPrefix);
				break;
					
			case 2:
				url = addStars(url,"20", urlStarsPrefix);
				break;
					
			case 3:
				url = addStars(url,"30", urlStarsPrefix);
				break;
				
			case 4:
				url = addStars(url,"40", urlStarsPrefix);
				break;
				
			case 5:
				url = addStars(url,"50", urlStarsPrefix);
				break;
				
			default:
				break;
		}
		
		/* Aggiungi prezzo */
		switch (setPrice(hotelBean.getPrice())) {
		
			case 0:
				url = addPrice(url,"0", urlPricePrefix);
				break;
					
			case 1:
				url = addPrice(url, "1", urlPricePrefix);
				break;
					
			case 2:
				url = addPrice(url, "2", urlPricePrefix);
				break;
				
			case 3:
				url = addPrice(url, "3", urlPricePrefix);
				break;
				
			case 4:
				url = addPrice(url, "4", urlPricePrefix);
				break;
				
			default:
				break;
		}
		
		/* Aggiungi colazione */
		if (hotelBean.getBreakfast().equalsIgnoreCase("Included")) {
			url = addBreakfast(url, urlBreakfastPrefix);
		}
		
		try {
				
			Document doc = Jsoup.connect(url).userAgent("Chrome/80.0.3987.122").get();
				
			Elements links = doc.select("a");
				
			String attribute = ""; // Prende la classe del tag in questione per confrontarla con quella che si vuole ottenere
			String hotel = ""; // Nome dell'hotel ottenuto dal parse del "tag" html
			
			for (Element link:links)
			{
				attribute = link.attr("class");
				if (attribute.equalsIgnoreCase("listing__link uitk-card-link")) {
					hotel = parseLink(link.text());
					
					/* Aggiungi un nuovo oggetto HotelTest alla lista hotels */
					Hotel ht = new Hotel();
					ht.setHotelName(hotel);
					ht.setHotelLink(link.attr("abs:href"));
					
					hotels.add(ht);
				}
			}
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/* Restituisce al controller la lista di tutti gli hotel trovati */
		return hotels;   
	}
	
	public List<DestinationBean> findSpecialDestination(DestinationBean destinationBean) throws SystemException {
		
		GeneralFilter generalFilter = new Filter("");

		String continent = destinationBean.getContinent();
		boolean sea = destinationBean.getSea();
		boolean mountain = destinationBean.getMountain();
		boolean art = destinationBean.getArt();
		boolean young = destinationBean.getYoung();
		
		/* Controllo continente selezionato */
		if(!continent.equalsIgnoreCase("Not defined")) {
			ContinentDecorator continentDecorator = new ContinentDecorator(generalFilter);
			continentDecorator.setContinentName(continent);
			generalFilter = continentDecorator;
		}
		
		/* Se il mare è selezionato */
		if(sea) {
			generalFilter = new SeaDecorator(generalFilter);
		}
		/* Se montagna è selezionata */
		else if(mountain) {
			generalFilter = new MountainDecorator(generalFilter);
		}
		
		/* Se arte è selezionata */
		if(art) {
			generalFilter = new ArtDecorator(generalFilter);
		}
		
		/* Se young è selezionato */
		if(young) {
			generalFilter = new YoungDecorator(generalFilter);
		}
	    
		Destination destination = this.splitGeneralFilter(generalFilter);

		List<Destination> destinations;
		if(destination.getContinent().equalsIgnoreCase("")) {
			destination.setContinent("Not defined");
			destinations = DestinationDao.findSpecialTravel(destination);
		}
		else {
			destinations = DestinationDao.findSpecialTravel(destination);
		}
		
		List<DestinationBean> destinationBeans = new ArrayList<>();
		for(Destination d : destinations) {
			DestinationBean dBean = new DestinationBean();
			dBean.setDestinationName(d.getDestinationName());
			destinationBeans.add(dBean);
		}
	
		return destinationBeans;
	}
	
	private Destination splitGeneralFilter(GeneralFilter generalFilter) {
		String[] params;
		Destination destination = new Destination();
		params = generalFilter.getFilters().split("-");
		for(String str : params) {
			if(str.equalsIgnoreCase("SEA")) {
				destination.setLocation(0);
			}
			else if(str.equalsIgnoreCase("MOUNTAIN")) {
				destination.setLocation(1);
			}
			else if(str.equalsIgnoreCase("YOUNG")) {
				destination.setYoung(1);
			}
			else if(str.equalsIgnoreCase("ART")) {
				destination.setArt(1);
			}
			else if(str.equalsIgnoreCase("America") || str.equalsIgnoreCase("Asia") || str.equalsIgnoreCase("Europe") || str.equalsIgnoreCase("Africa") || str.equalsIgnoreCase("Oceania")) {
				destination.setContinent(str);
			}
		}
		
		return destination;
	}
	
	/* Parse del nome dell'hotel */
	private String parseLink(String s) {
		s = s.substring(25);
		String[] tokens = s.split(",");
		return tokens[0];
	}
	
	/* Formatta destinazione */
	private String modifyDest(String s) {
		s = s.substring(0,1).toUpperCase() + s.substring(1, s.length()).toLowerCase();
		return s;
	}
	
	private String addDest(String url, String destination, String urlDestPrefix) {
		url = url.concat(urlDestPrefix).concat(destination);
		return url;
	} 
	
	private String addStartDate(String url, String startDate, String urlStartDate) {
		url = url.concat(urlStartDate).concat(startDate);
		return url;
	}
	
	private String addEndDate(String url, String endDate, String urlEndDate) {
		url = url.concat(urlEndDate).concat(endDate);
		return url;
	} 

	private String addRooms(String url, String number, String urlRoomsPrefix) {
		url = url.concat(urlRoomsPrefix).concat(number);
		return url;
	} 
	
	private String addTravellers(String url, String number, String urlPrefix, String urlPeoplePrefix) {
		url = url.concat(urlPrefix).concat(urlPeoplePrefix).concat(number);
		return url;
	} 
	
	private String addStars(String url, String stars, String urlStarsPrefix) {
		url = url.concat(urlStarsPrefix).concat(stars);
		return url;
	}
	
	private String addPrice(String url, String price, String urlPricePrefix) {
		url = url.concat(urlPricePrefix).concat(price);
		return url;
	}
	
	private String addBreakfast(String url, String urlBreakfastPrefix) {
		url = url.concat(urlBreakfastPrefix).concat("FREE_BREAKFAST");
		return url;
	}
	
	private int setPrice(String price) {
		
		if (price.equalsIgnoreCase("Less than euro 50"))
			return 0;
		
		if (price.equalsIgnoreCase("euro 50 - euro 100"))
			return 1;
		
		if (price.equalsIgnoreCase("euro 100 - euro 150"))
			return 2;
		
		if (price.equalsIgnoreCase("euro 150 - euro 225"))
			return 3;
		
		if (price.equalsIgnoreCase("More than euro 225"))
			return 4;
		
		return -1;
	}
	
	private PrivateTravel viaggioBeanToViaggio(PrivateTravelBean vg) {
		PrivateTravel viaggio = new PrivateTravel();
		viaggio.getHotelInfo().setBreakfast(vg.getHotelInfoBean().getBreakfast());
		viaggio.getHotelInfo().setHotelLink(vg.getHotelInfoBean().getHotelLink());
		viaggio.getHotelInfo().setHotelName(vg.getHotelInfoBean().getHotelName());
		viaggio.getHotelInfo().setNumRooms(Integer.valueOf(vg.getHotelInfoBean().getNumRooms()));
		viaggio.getHotelInfo().setPrice(vg.getHotelInfoBean().getPrice());
		viaggio.getHotelInfo().setStars(Integer.valueOf(vg.getHotelInfoBean().getStars()));
		viaggio.getCreator().setUsername(vg.getCreatorBean());
		viaggio.setDescription(vg.getDescriptionBean());
		viaggio.getDestination().setDestinationName(vg.getDestinationBean());
		viaggio.setStartDate(vg.getStartDateBean());
		viaggio.setEndDate(vg.getEndDateBean());
		viaggio.setTravelName(vg.getTravelNameBean());
		viaggio.setNumMaxUt(Integer.parseInt(vg.getNumTravelersBean()));
		
		return viaggio;
	}
	
	private PublicTravel viaggioGruppoBeanToViaggioGruppo(PublicTravelBean vg) {
		PublicTravel viaggioGruppo = new PublicTravel();
		viaggioGruppo.getHotelInfo().setBreakfast(vg.getHotelInfoBean().getBreakfast());
		viaggioGruppo.getHotelInfo().setHotelLink(vg.getHotelInfoBean().getHotelLink());
		viaggioGruppo.getHotelInfo().setHotelName(vg.getHotelInfoBean().getHotelName());
		viaggioGruppo.getHotelInfo().setNumRooms(Integer.valueOf(vg.getHotelInfoBean().getNumRooms()));
		viaggioGruppo.getHotelInfo().setPrice(vg.getHotelInfoBean().getPrice());
		viaggioGruppo.getHotelInfo().setStars(Integer.valueOf(vg.getHotelInfoBean().getStars()));
		viaggioGruppo.getCreator().setUsername(vg.getCreatorBean());
		viaggioGruppo.setDescription(vg.getDescriptionBean());
		viaggioGruppo.getDestination().setDestinationName(vg.getDestinationBean());
		viaggioGruppo.setStartDate(vg.getStartDateBean());
		viaggioGruppo.setEndDate(vg.getEndDateBean());
		viaggioGruppo.setTravelName(vg.getTravelNameBean());
		viaggioGruppo.setNumMaxUt(Integer.valueOf(vg.getNumTravelersBean()));
		
		return viaggioGruppo;
	}
	
	public void validateDates(String startDate, String endDate) throws DatesException {
		
		if(startDate == null || endDate == null || startDate.equals("") || endDate.equals("")) {
			throw new DatesException("Dates not valid!");
		}
		
		String date1 = "";
		String date2 = "";
		
		date1 = date1.concat(startDate.substring(8, 10));
		date1 = date1.concat("/");
		date1 = date1.concat(startDate.substring(5, 7));
		date1 = date1.concat("/");
		date1 = date1.concat(startDate.substring(0, 4));
		
		date2 = date2.concat(endDate.substring(8, 10));
		date2 = date2.concat("/");
		date2 = date2.concat(endDate.substring(5, 7));
		date2 = date2.concat("/");
		date2 = date2.concat(endDate.substring(0, 4));
		
		Date dt1 = null;
		try {
			dt1 = new SimpleDateFormat("dd/MM/yyyy").parse(date1);
		} catch (ParseException e) {
			throw new DatesException("Start date not valid!");
		}  

		Date dt2 = null;
		try {
			dt2 = new SimpleDateFormat("dd/MM/yyyy").parse(date2);
		} catch (ParseException e) {
			throw new DatesException("End date not valid!");
		}  
		
		LocalDateTime localDateTime = LocalDateTime.now();
		Date dateNow = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

		int res;
		if(dt1 != null) {
			res = (dt1.compareTo(dt2));
			if(res > 0) {
				throw new DatesException("Start date must be before the End date!");
			}
			else if(res == 0) {
				throw new DatesException("Start date and End date must not be equal!");
			}
			else {
				res = dt1.compareTo(dateNow);
				if(res < 0) {
					throw new DatesException("Start date must be before the current date!");
				}
				else if(res == 0) {
					throw new DatesException("Start date must not be equal to the current date!");
				}
			}
		}
	}
	
	public void validateTravelersAndRooms(String numTravellers, String numRooms) throws TravRoomException {
		if(Integer.parseInt(numTravellers) < Integer.parseInt(numRooms))
			throw new TravRoomException("Number of rooms must be greater or equal than the number of travellers!");
	}

	public void saveTravel(PrivateTravelBean vg) throws SaveTravelException, SystemException, DatesException, TravRoomException {	
		this.validateDates(vg.getStartDateBean(), vg.getEndDateBean());
		this.validateTravelersAndRooms(vg.getNumTravelersBean(), vg.getHotelInfoBean().getNumRooms());
		PrivateTravelDao.saveTravel(viaggioBeanToViaggio(vg));
	}
	
	public void bookTravel(PrivateTravelBean vg) throws BookTravelException, SystemException, DatesException, TravRoomException {
		this.validateDates(vg.getStartDateBean(), vg.getEndDateBean());
		this.validateTravelersAndRooms(vg.getNumTravelersBean(), vg.getHotelInfoBean().getNumRooms());
		PrivateTravelDao.bookTravel(viaggioBeanToViaggio(vg).getIdTravel());
	}
	
	public void bookAndSaveTravel(PrivateTravelBean vg) throws BookTravelException, SystemException, DatesException, TravRoomException {
		this.validateDates(vg.getStartDateBean(), vg.getEndDateBean());
		this.validateTravelersAndRooms(vg.getNumTravelersBean(), vg.getHotelInfoBean().getNumRooms());
		PrivateTravelDao.bookAndSaveTravel(viaggioBeanToViaggio(vg));
	}
	
	public void saveGroupTravel(PublicTravelBean vgr) throws SaveTravelException, SystemException, DatesException, TravRoomException {
		this.validateDates(vgr.getStartDateBean(), vgr.getEndDateBean());
		this.validateTravelersAndRooms(vgr.getNumTravelersBean(), vgr.getHotelInfoBean().getNumRooms());
		PublicTravelDao.saveGroupTravel(viaggioGruppoBeanToViaggioGruppo(vgr));
	}
	
	public void bookGroupTravel(PublicTravelBean vgr) throws BookGroupTravelException, SystemException, DatesException, TravRoomException {
		this.validateDates(vgr.getStartDateBean(), vgr.getEndDateBean());
		this.validateTravelersAndRooms(vgr.getNumTravelersBean(), vgr.getHotelInfoBean().getNumRooms());
		PublicTravelDao.bookGroupTravel(viaggioGruppoBeanToViaggioGruppo(vgr).getIdTravel());
	}
	
	public void bookAndSaveGroupTravel(PublicTravelBean vgr) throws BookGroupTravelException, SystemException, DatesException, TravRoomException {
		this.validateDates(vgr.getStartDateBean(), vgr.getEndDateBean());
		this.validateTravelersAndRooms(vgr.getNumTravelersBean(), vgr.getHotelInfoBean().getNumRooms());
		PublicTravelDao.bookAndSaveGroupTravel(viaggioGruppoBeanToViaggioGruppo(vgr));
	}

}
