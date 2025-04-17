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
import logic.bean.UserBean;
import logic.dao.UserDao;
import logic.dao.FollowNotificationDao;
import logic.dao.JoinNotificationDao;
import logic.dao.PrivateTravelDao;
import logic.dao.PublicTravelDao;
import logic.dao.ReplyNotificationDao;
import logic.exceptions.DefaultPhotoException;
import logic.exceptions.DuplicateUsernameException;
import logic.exceptions.EmailException;
import logic.exceptions.PasswordException;
import logic.exceptions.SystemException;
import logic.exceptions.UsernameException;
import logic.model.PrivateTravel;
import logic.model.PublicTravel;
import logic.model.User;

public class ProfileController {
	
	private static final String DB_IMAGES = "DBImages";
	private static final String IMAGES = "Images";
	private static final String WEB_CONTENT = "WebContent";
	private static final String USR_DIR = "user.dir";
	
	private List<String> setSavePaths(List<String> filenames){
		List<String> savePaths = new ArrayList<>();
		String currentDirectoryProject = System.getProperty(USR_DIR);
    	String savePath = "";
		for(String filename : filenames) {
			savePath = currentDirectoryProject + File.separator + WEB_CONTENT + File.separator + IMAGES + File.separator + DB_IMAGES + File.separator +  filename;
			savePaths.add(savePath);
		}
		return savePaths;
	}
	
	public List<String> retrieveTravelPhotos(String idViaggio) throws SystemException {
		List<String> filenames = PrivateTravelDao.retrieveTravelPhoto(Integer.valueOf(idViaggio));
		return setSavePaths(filenames);	
	}
	
	public List<String> retrieveTravelGroupPhotos(String idViaggioGruppo) throws SystemException {
		List<String> filenames = PublicTravelDao.retrieveTravelGroupPhoto(Integer.valueOf(idViaggioGruppo));
		return setSavePaths(filenames);	
	}
	
	
	private List<PrivateTravelBean> setPrivateTravelsInfo(List<PrivateTravel> travels){
		
		List<PrivateTravelBean> travelsBean = new ArrayList<>();
		
		for(PrivateTravel vg : travels) {
			PrivateTravelBean vgBean = new PrivateTravelBean();
            vgBean.getHotelInfoBean().setBreakfast(vg.getHotelInfo().getBreakfast());
            vgBean.getHotelInfoBean().setHotelLink(vg.getHotelInfo().getHotelLink());
            vgBean.getHotelInfoBean().setHotelName(vg.getHotelInfo().getHotelName());
            vgBean.getHotelInfoBean().setNumRooms(String.valueOf(vg.getHotelInfo().getNumRooms()));
            vgBean.getHotelInfoBean().setPrice(vg.getHotelInfo().getPrice());
            vgBean.getHotelInfoBean().setStars(String.valueOf(vg.getHotelInfo().getStars()));
            
            vgBean.setCreatorBean(vg.getCreator().getUsername());
            vgBean.setDestinationBean(vg.getDestination().getDestinationName());
            vgBean.setDescriptionBean(vg.getDescription());
            vgBean.setStartDateBean(vg.getStartDate());
            vgBean.setEndDateBean(vg.getEndDate());
            vgBean.setTravelNameBean(vg.getTravelName());
            vgBean.setIdTravelBean(String.valueOf(vg.getIdTravel()));
            vgBean.setNumTravelersBean(String.valueOf(vg.getNumMaxUt()));
            
            travelsBean.add(vgBean);
		}
		
		return travelsBean;
	}
	
	private List<PublicTravelBean> setPublicTravelInfo(List<PublicTravel> travels){
		
		List<PublicTravelBean> travelsBean = new ArrayList<>();
		
		for(PublicTravel vg : travels) {
			PublicTravelBean vgrBean = new PublicTravelBean();
			vgrBean.getHotelInfoBean().setBreakfast(vg.getHotelInfo().getBreakfast());
			vgrBean.getHotelInfoBean().setHotelLink(vg.getHotelInfo().getHotelLink());
			vgrBean.getHotelInfoBean().setHotelName(vg.getHotelInfo().getHotelName());
			vgrBean.getHotelInfoBean().setNumRooms(String.valueOf(vg.getHotelInfo().getNumRooms()));
			vgrBean.getHotelInfoBean().setPrice(vg.getHotelInfo().getPrice());
			vgrBean.getHotelInfoBean().setStars(String.valueOf(vg.getHotelInfo().getStars()));
            
            vgrBean.setCreatorBean(vg.getCreator().getUsername());
            vgrBean.setDestinationBean(vg.getDestination().getDestinationName());
            vgrBean.setDescriptionBean(vg.getDescription());
            vgrBean.setStartDateBean(vg.getStartDate());
            vgrBean.setEndDateBean(vg.getEndDate());
            vgrBean.setAvailableSeats(String.valueOf(vg.getAvailableSeats()));
            vgrBean.setNumTravelersBean(String.valueOf(vg.getNumMaxUt()));
            vgrBean.setIdTravelBean(String.valueOf(vg.getIdTravel()));
            vgrBean.setTravelNameBean(vg.getTravelName());
			travelsBean.add(vgrBean);
		}
		
		return travelsBean;
	}
	
	public List<PrivateTravelBean> loadMyOldT(String username) throws SystemException {
		List<PrivateTravel> travels;
		travels = PrivateTravelDao.retrieveTravels(username);
		return setPrivateTravelsInfo(travels);
	}
	
	public List<PublicTravelBean> loadMyOldTGR(String username) throws SystemException {
		List<PublicTravel> travels;
		travels = PublicTravelDao.retrieveGroupTravels(username);
		return setPublicTravelInfo(travels);
	}
	
	public List<PrivateTravelBean> loadMyUpcomingT(String username) throws SystemException {
		List<PrivateTravel> travels;
		travels = PrivateTravelDao.retrieveNextBookedTravels(username);
		return setPrivateTravelsInfo(travels);
	}
	
	public List<PublicTravelBean> loadMyUpcomingTGR(String username) throws SystemException {
		
		List<PublicTravel> travels;
		travels = PublicTravelDao.retreiveMyNextBookedGrTravells(username);
		return setPublicTravelInfo(travels);
	}
	
	public String retrieveUserPhoto(String username) throws DefaultPhotoException, SystemException {
		String filename = "";
		filename = UserDao.retrieveUserPhoto(username);
		String currentDirectoryProject = System.getProperty(USR_DIR);
		if(filename.equals("")) 
			return filename;
		else
			return currentDirectoryProject + File.separator + WEB_CONTENT + File.separator + IMAGES + File.separator + DB_IMAGES + File.separator +  filename;
	}
	
	public void changeEmail(String username, String email) throws EmailException, SystemException {
		UserDao.modifEmail(username, email);
	}
	
	public void changePassword(String username, String newPassword) throws PasswordException, SystemException {
		UserDao.modifPsw(username, newPassword);
	}
	
	// metodo per inserire il nuovo username nei viaggi private
	private void updateTravelInfo(String newUsr, String usr) throws SystemException {
		UserDao.modifyTravelInfo(newUsr, usr);
	}
	
	// metodo per inserire il nuovo username nei viaggi public
	private void updateTravelGrInfo(String newUsr, String usr) throws SystemException{
		UserDao.modifyTravelGRInfo(newUsr, usr);
	}
	
	private void updateParticipant(String newUsr, String usr) throws SystemException{
		UserDao.modifyParticipantUsername(newUsr, usr);
	}
	
	private void updateReplySenderNotif(String newUsr, String usr) throws SystemException{
		ReplyNotificationDao.modifyReplySenderUsername(newUsr, usr);
	}
	
	private void updateReplyReceiverNotif(String newUsr, String usr) throws SystemException{
		ReplyNotificationDao.modifyReplyReceiverUsername(newUsr, usr);
	}
	
	private void updateFollowerUsernameNotif(String newUsr, String usr) throws SystemException{
		FollowNotificationDao.modifyFollowerUsername(newUsr, usr);
	}
	
	private void updateFollowedUsernameNotif(String newUsr, String usr) throws SystemException{
		FollowNotificationDao.modifyFollowedUsername(newUsr, usr);
	}
	
	private void updateFollowNotifSender(String newUsr, String usr) throws SystemException{
		FollowNotificationDao.modifyFollowNotifSender(newUsr, usr);
	}
	
	private void updateFollowNotifReceiver(String newUsr, String usr) throws SystemException{
		FollowNotificationDao.modifyFollowNotifReceiver(newUsr, usr);
	}
	
	private void updateJoinTravelSender(String newUsr, String usr) throws SystemException{
		JoinNotificationDao.modifyJoinSender(newUsr, usr);
	}
	
	public void changeUsername(String username, String newUsername) throws SystemException, UsernameException, DuplicateUsernameException {
		try {
			UserDao.usernameChecker(newUsername);
			UserDao.modifUsrn(username, newUsername);
			this.updateTravelInfo(newUsername, username);
			this.updateTravelGrInfo(newUsername, username);
			this.updateParticipant(newUsername, username);
			this.updateReplySenderNotif(newUsername, username);
			this.updateReplyReceiverNotif(newUsername, username);
			this.updateFollowerUsernameNotif(newUsername, username);
			this.updateFollowedUsernameNotif(newUsername, username);
			this.updateFollowNotifSender(newUsername, username);
			this.updateFollowNotifReceiver(newUsername, username);
			this.updateJoinTravelSender(newUsername, username);
		} catch (DuplicateUsernameException due) {
			throw due;
		} catch (UsernameException ue) {
			throw ue;
		} catch (SystemException sye) {
			throw sye;
		}	
	}
	
	public void changePhoto(String username, File file) throws DefaultPhotoException, SystemException {
		String currentDirectoryProject = System.getProperty(USR_DIR);
    	String filename = username.concat("_").concat(file.getName());
    	String savePath = currentDirectoryProject + File.separator + WEB_CONTENT + File.separator + IMAGES + File.separator + DB_IMAGES + File.separator +  filename;
    	UserDao.modifyPhoto(username, file, filename);
    	File photoUser = new File(savePath);
    	try {
			Files.copy(file.toPath(), photoUser.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException("Unexpected error occured, retry!");
		}
	}
	
	public List<UserBean> retrieveFollowers(String username) throws SystemException {
		
		List<User> followers;
		List<UserBean> followersBean = new ArrayList<>();
		String filename = "";
		String currentDirectoryProject = System.getProperty(USR_DIR);
		
		followers = UserDao.retrieveFollowersByUsername(username);
		for(User follower : followers) {
			UserBean userBean = new UserBean();
            userBean.setUsername(follower.getUsername());
            userBean.setEmail(follower.getEmail());
            
            filename = follower.getPhoto();
            if(filename.equals("")) {
            	userBean.setPhoto(filename);
            }
			else {
				filename = currentDirectoryProject.concat(File.separator).concat(WEB_CONTENT).concat(File.separator).concat(IMAGES).concat(File.separator).concat(DB_IMAGES).concat(File.separator).concat(filename);
				userBean.setPhoto(filename);
			}

            followersBean.add(userBean);
		}
		return followersBean;
	}
	
	public List<PrivateTravelBean> retrieveFollowerPrivateTravels(String username) throws SystemException {
		List<PrivateTravel> travels = PrivateTravelDao.retrieveFollowerTravelsPrivate(username);
		List<PrivateTravelBean> travelsBean = new ArrayList<>();
		
		for(PrivateTravel vg : travels) {
			travelsBean.add(viaggioToViaggioBean(vg));			
		}
		
		return travelsBean;
	}
	
	public List<PublicTravelBean> retrieveFollowerPublicTravels(String username) throws SystemException {
		List<PublicTravelBean> travelsBean = new ArrayList<>();
		List<PublicTravel> travels = PublicTravelDao.retrieveFollowerTravelsPublic(username);
		
		for(PublicTravel vgr : travels) {
			travelsBean.add(viaggioGruppoToViaggioGruppoBean(vgr));			
		}
		
		return travelsBean;
	}
	
	private PrivateTravelBean viaggioToViaggioBean(PrivateTravel vg) {
		HotelBean hotelBean = new HotelBean();
		hotelBean.setBreakfast(vg.getHotelInfo().getBreakfast());
		hotelBean.setHotelLink(vg.getHotelInfo().getHotelLink());
		hotelBean.setHotelName(vg.getHotelInfo().getHotelName());
		hotelBean.setNumRooms(String.valueOf(vg.getHotelInfo().getNumRooms()));
		hotelBean.setPrice(vg.getHotelInfo().getPrice());
		hotelBean.setStars(String.valueOf(vg.getHotelInfo().getStars()));
		
		PrivateTravelBean vgBean = new PrivateTravelBean();
		vgBean.setCreatorBean(vg.getCreator().getUsername());
		vgBean.setDescriptionBean(vg.getDescription());
		vgBean.setDestinationBean(vg.getDestination().getDestinationName());
		vgBean.setStartDateBean(vg.getStartDate());
		vgBean.setEndDateBean(vg.getEndDate());
		vgBean.setHotelInfoBean(hotelBean);
		vgBean.setTravelNameBean(vg.getTravelName());
		vgBean.setIdTravelBean(String.valueOf(vg.getIdTravel()));
		
		return vgBean;
	}
	
	private PublicTravelBean viaggioGruppoToViaggioGruppoBean(PublicTravel vgr) {
		HotelBean hotelBean = new HotelBean();
		hotelBean.setBreakfast(vgr.getHotelInfo().getBreakfast());
		hotelBean.setHotelLink(vgr.getHotelInfo().getHotelLink());
		hotelBean.setHotelName(vgr.getHotelInfo().getHotelName());
		hotelBean.setNumRooms(String.valueOf(vgr.getHotelInfo().getNumRooms()));
		hotelBean.setPrice(vgr.getHotelInfo().getPrice());
		hotelBean.setStars(String.valueOf(vgr.getHotelInfo().getStars()));
		
		PublicTravelBean vgrBean = new PublicTravelBean();
		vgrBean.setCreatorBean(vgr.getCreator().getUsername());
		vgrBean.setDescriptionBean(vgr.getDescription());
		vgrBean.setDestinationBean(vgr.getDestination().getDestinationName());
		vgrBean.setStartDateBean(vgr.getStartDate());
		vgrBean.setEndDateBean(vgr.getEndDate());
		vgrBean.setHotelInfoBean(hotelBean);
		vgrBean.setTravelNameBean(vgr.getTravelName());
		vgrBean.setNumTravelersBean(String.valueOf(vgr.getNumMaxUt()));
		vgrBean.setIdTravelBean(String.valueOf(vgr.getIdTravel()));
		
		return vgrBean;
	}
	
}
