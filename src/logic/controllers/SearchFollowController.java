package logic.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import logic.bean.UserBean;
import logic.dao.UserDao;
import logic.exceptions.AddFollowerException;
import logic.exceptions.FollowRequestException;
import logic.exceptions.SystemException;
import logic.model.FollowNotification;
import logic.model.User;

public class SearchFollowController {
	
	public List<UserBean> loadUsers(String follower, String followed) throws SystemException {
		List<User> users;
		List<UserBean> usersBean = new ArrayList<>();
		String filename = "";
		String currentDirectoryProject = System.getProperty("user.dir");

		users = UserDao.retrieveFollowersSearched(follower, followed);
		for(User u : users) {
			UserBean userBean = new UserBean();
			userBean.setUsername(u.getUsername());
			userBean.setEmail(u.getEmail());
			filename = u.getPhoto();
			if(filename.equals("")) 
				userBean.setPhoto(filename);
			else {
				filename = currentDirectoryProject.concat(File.separator).concat("WebContent").concat(File.separator).concat("Images").concat(File.separator).concat("DBImages").concat(File.separator).concat(filename);
				userBean.setPhoto(filename);
			}
			usersBean.add(userBean);
		}
		return usersBean;  
		
	}
	
	public void sendFollowRequest(String follower, String followed) throws SystemException, FollowRequestException, AddFollowerException {
		User userFollower = new User();
		userFollower.setUsername(follower);
		User userFollowed = new User();
		userFollowed.setUsername(followed);
		
		/* Verifico che l'utente che si vuole seguire non è l'utente stesso */
		if(userFollower.getUsername().equals(userFollowed.getUsername())) 
			throw new FollowRequestException("You can not follow yourself!"); 
		
		/* Verifica che l'utente da seguire non si segua già */
		List<User> followeds = UserDao.retrieveFollowersByUsername(userFollower.getUsername());
		for(User fl : followeds) {
			if(fl.getUsername().equals(userFollowed.getUsername()))
				throw new FollowRequestException("User already followed!");
		}

		/* Verifica che l'utente non mi segua */
		if(UserDao.checkFollow(userFollower, userFollowed)) { 
			FollowNotification followNotification = new FollowNotification();
			followNotification.setSenderFollow(userFollower);
			followNotification.setReceiverFollow(userFollowed);
			followNotification.setMsgFollow(follower + " ha iniziato a seguitirti, vuoi seguirlo?");
			UserDao.sendRequestFollow(followNotification);
			UserDao.addFollowed(userFollower, userFollowed);
		}
		else {
			/* Lancio eccezione per comunicare che l'utente mi segue, l'utente può aggiungerlo accettando la notifica ricevuta */
			throw new AddFollowerException("The selected user already follows you...\nGo to the notify center to follow her/him!");
		}
	}
}
