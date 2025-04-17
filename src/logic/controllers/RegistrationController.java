package logic.controllers;

import logic.bean.UserBean;
import logic.dao.UserDao;
import logic.exceptions.DuplicateUsernameException;
import logic.exceptions.SystemException;
import logic.model.User;

public class RegistrationController {
	
	public void signUp(UserBean userBean) throws DuplicateUsernameException, SystemException {
		UserDao.usernameChecker(userBean.getUsername());
		User user = new User(userBean.getFirstName(), userBean.getSecondName(), userBean.getUsername(), userBean.getEmail(), userBean.getPassword());
    	UserDao.registerUsr(user);
	}	   
    
}
