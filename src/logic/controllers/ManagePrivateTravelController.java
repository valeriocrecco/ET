package logic.controllers;

import java.util.ArrayList;
import java.util.List;

import logic.bean.HotelBean;
import logic.bean.PrivateTravelBean;
import logic.dao.PrivateTravelDao;
import logic.exceptions.BookTravelException;
import logic.exceptions.DeleteTravelException;
import logic.exceptions.SystemException;
import logic.model.PrivateTravel;

public class ManagePrivateTravelController {
	
	public List<PrivateTravelBean> loadMyUpcomingT(String username){
		List<PrivateTravel> travels = new ArrayList<>();
		List<PrivateTravelBean> travelsBean = new ArrayList<>();
		
		try {
			travels = PrivateTravelDao.retrieveMySavedTravels(username);
			for(PrivateTravel vg : travels) {
				
				HotelBean hotelBean = new HotelBean();
				hotelBean.setBreakfast(vg.getHotelInfo().getBreakfast());
                hotelBean.setHotelLink(vg.getHotelInfo().getHotelLink());
                hotelBean.setHotelName(vg.getHotelInfo().getHotelName());
                hotelBean.setNumRooms(String.valueOf(vg.getHotelInfo().getNumRooms()));
                hotelBean.setPrice(vg.getHotelInfo().getPrice());
                hotelBean.setStars(String.valueOf(vg.getHotelInfo().getStars()));
				
                PrivateTravelBean viaggioBean = new PrivateTravelBean();
                viaggioBean.setCreatorBean(vg.getCreator().getUsername());
                viaggioBean.setDestinationBean(vg.getDestination().getDestinationName());
                viaggioBean.setDescriptionBean(vg.getDescription());
                viaggioBean.setStartDateBean(vg.getStartDate());
                viaggioBean.setEndDateBean(vg.getEndDate());
                viaggioBean.setHotelInfoBean(hotelBean);
                viaggioBean.setTravelNameBean(vg.getTravelName());
                viaggioBean.setIdTravelBean(String.valueOf(vg.getIdTravel()));
				travelsBean.add(viaggioBean);
			}
			return travelsBean;
		} catch (Exception e) {
			return travelsBean;
		}
	}
	
	public void bookTravel(String idV) throws BookTravelException, SystemException {
		PrivateTravelDao.bookTravel(Integer.valueOf(idV));
	}
	
	public void deleteTravel(String idV) throws DeleteTravelException, SystemException {
		PrivateTravelDao.deleteTravel(Integer.valueOf(idV));
	}
	
	
}
