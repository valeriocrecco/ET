package logic.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import logic.bean.HotelBean;
import logic.bean.PrivateTravelBean;
import logic.bean.PublicTravelBean;
import logic.dao.PrivateTravelDao;
import logic.dao.PublicTravelDao;
import logic.exceptions.SystemException;
import logic.model.PrivateTravel;
import logic.model.PublicTravel;

public class UploadPhotosController {
	
	private static final String SYSTEM_ERROR = "Unexpected error, retry!";
	
	public void addTravelPhotos(String idViaggio, List<File> files, String username) throws SystemException {
		
		String currentDirectoryProject = System.getProperty("user.dir");
		String filename = "";
		String savePath = "";
	
    	for(File file : files) {
    		filename = String.valueOf(idViaggio).concat("_").concat(username).concat("_").concat(file.getName());
    		savePath = currentDirectoryProject + File.separator + "WebContent" + File.separator + "Images" + File.separator + "DBImages" + File.separator +  filename;
    		PrivateTravelDao.addTravelPhoto(Integer.valueOf(idViaggio), file, filename);
    		File travelPhoto = new File(savePath);
    		try {
				Files.copy(file.toPath(), travelPhoto.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				throw new SystemException(SYSTEM_ERROR);
			}
    	}
	} 
	
	public void addTravelGroupPhotos(String idViaggioGruppo, List<File> files, String username) throws SystemException {
		
		String currentDirectoryProject = System.getProperty("user.dir");
		String filename = "";
		String savePath = "";
	
    	for(File file : files) {
    		filename = String.valueOf(idViaggioGruppo).concat("_").concat(username).concat("_").concat(file.getName());
    		savePath = currentDirectoryProject + File.separator + "WebContent" + File.separator + "Images" + File.separator + "DBImages" + File.separator +  filename;
    		PublicTravelDao.addTravelGroupPhoto(Integer.valueOf(idViaggioGruppo), file, filename);
    		File travelGroupPhoto = new File(savePath);
    		try {
				Files.copy(file.toPath(), travelGroupPhoto.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				throw new SystemException(SYSTEM_ERROR);
			}
    	}
		
	}
	
	public List<PrivateTravelBean> loadMyOldT(String username) throws SystemException {
		
		List<PrivateTravel> travels;
		List<PrivateTravelBean> travelsBean = new ArrayList<>();
		
		travels = PrivateTravelDao.retrieveTravels(username);
		for(PrivateTravel vg : travels) {
			
			HotelBean hotelBean = new HotelBean();                
            hotelBean.setHotelLink(vg.getHotelInfo().getHotelLink());
       	 
            PrivateTravelBean vgBean = new PrivateTravelBean();
            vgBean.setIdTravelBean(String.valueOf(vg.getIdTravel()));
            vgBean.setDestinationBean(vg.getDestination().getDestinationName());
            vgBean.setTravelNameBean(vg.getTravelName());
            vgBean.setHotelInfoBean(hotelBean);
            travelsBean.add(vgBean);
		}
		
		return travelsBean;
	}
	
	public List<PublicTravelBean> loadMyOldTGR(String username) throws SystemException {
		List<PublicTravel> travels;
		List<PublicTravelBean> travelsBean = new ArrayList<>();
		
		travels = PublicTravelDao.retrieveGroupTravels(username);
		for(PublicTravel vg : travels) {
			
            HotelBean hotelBean = new HotelBean();
            hotelBean.setHotelLink(vg.getHotelInfo().getHotelLink());
            
            PublicTravelBean vgrBean = new PublicTravelBean();
            vgrBean.setIdTravelBean(String.valueOf(vg.getIdTravel()));
            vgrBean.setDestinationBean(vg.getDestination().getDestinationName());
            vgrBean.setTravelNameBean(vg.getTravelName());
            vgrBean.setHotelInfoBean(hotelBean);
            
            travelsBean.add(vgrBean);
		}
		
		return travelsBean;
	}
	
}
