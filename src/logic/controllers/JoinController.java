package logic.controllers;

import java.util.ArrayList;
import java.util.List;
import logic.bean.PublicTravelBean;
import logic.dao.PublicTravelDao;
import logic.exceptions.DuplicateRequestException;
import logic.exceptions.SystemException;
import logic.model.JoinNotification;
import logic.model.User;
import logic.model.PublicTravel;

public class JoinController {
	
	public List<PublicTravelBean> allTravels(String username) throws SystemException {
		List<PublicTravel> travels = PublicTravelDao.retreiveJoinableGrTravels(username);
		return this.setPublicTravelBean(travels);
	}

	public List<PublicTravelBean> filterTravels(String username, boolean sea, boolean mountain, boolean art, boolean young, String continent) throws SystemException {
		
		int location = 2;
		
		/* Se mare selezionato */
		if(sea) {
			location = 0;
		}
		/* Se montagna selezionata */
		else if(mountain) {
			location = 1;
		}
		/* Converti art/young */
		int artVal = (Boolean.TRUE.equals(art)) ? 1 : 0;
	    int youngVal = (Boolean.TRUE.equals(young)) ? 1 : 0;
		
		List<PublicTravel> travels = PublicTravelDao.retreiveSpecialJoinableGrTravels(username, location, artVal, youngVal, continent);
		
		return this.setPublicTravelBean(travels);
	}
	
	private List<PublicTravelBean> setPublicTravelBean(List<PublicTravel> travels) {
		
		List<PublicTravelBean> publicTravelBeans = new ArrayList<>();
		
		for(PublicTravel vg : travels) {
            
            PublicTravelBean viaggioGruppoBean = new PublicTravelBean();
            viaggioGruppoBean.getHotelInfoBean().setBreakfast(vg.getHotelInfo().getBreakfast());
            viaggioGruppoBean.getHotelInfoBean().setHotelLink(vg.getHotelInfo().getHotelLink());
            viaggioGruppoBean.getHotelInfoBean().setHotelName(vg.getHotelInfo().getHotelName());
            viaggioGruppoBean.getHotelInfoBean().setNumRooms(String.valueOf(vg.getHotelInfo().getNumRooms()));
            viaggioGruppoBean.getHotelInfoBean().setPrice(vg.getHotelInfo().getPrice());
            viaggioGruppoBean.getHotelInfoBean().setStars(String.valueOf(vg.getHotelInfo().getStars()));
            viaggioGruppoBean.setCreatorBean(vg.getCreator().getUsername());
            viaggioGruppoBean.setDestinationBean(vg.getDestination().getDestinationName());
            viaggioGruppoBean.setDescriptionBean(vg.getDescription());
            viaggioGruppoBean.setStartDateBean(vg.getStartDate());
            viaggioGruppoBean.setEndDateBean(vg.getEndDate());
            viaggioGruppoBean.setAvailableSeats(String.valueOf(vg.getAvailableSeats()));
            viaggioGruppoBean.setNumTravelersBean(String.valueOf(vg.getNumMaxUt()));
            viaggioGruppoBean.setIdTravelBean(String.valueOf(vg.getIdTravel()));
            viaggioGruppoBean.setTravelNameBean(vg.getTravelName());
            
            publicTravelBeans.add(viaggioGruppoBean);
		}
		
		return publicTravelBeans;
	}
	
	public void sendJoinRequest(PublicTravelBean viaggioGruppoBean, String username) throws SystemException, DuplicateRequestException {
		
		int idTravel = Integer.parseInt(viaggioGruppoBean.getIdTravelBean());
		String travelName = viaggioGruppoBean.getTravelNameBean();
		User sender = new User();
		sender.setUsername(username);
		PublicTravel viaggioGruppo = new PublicTravel();
		viaggioGruppo.setIdTravel(idTravel);
		JoinNotification joinNotification = new JoinNotification();
		joinNotification.setSenderJoin(sender);
		joinNotification.setTravelJoin(viaggioGruppo);
		joinNotification.setMsgJoin(sender.getUsername() + " ha richiesto di unirsi al viaggio: " + travelName + " con destinazione " + viaggioGruppoBean.getDestinationBean());

		/* Controllo che non esista già la mia richiesta */
		PublicTravelDao.checkRequests(idTravel, username);
		PublicTravelDao.joinVGruppo(joinNotification);
	}
	
}
