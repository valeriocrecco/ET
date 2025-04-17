package logic.controllers;

import logic.bean.UserBean;
import logic.dao.UserDao;
import logic.exceptions.LoginException;
import logic.exceptions.SystemException;
import logic.model.User;

public class LoginController {
	
	public void login(UserBean userBean) throws SystemException, LoginException {
		
		String username = userBean.getUsername();
		String password = userBean.getPassword();
		
		User user;
		user = UserDao.logUsr(username);
		if(user == null || !user.getPassword().equals(password)) 
			throw new LoginException("Wrong username or password, please retry!");	
		
		String email = UserDao.getUserEmail(username);
		userBean.setEmail(email);
	}
	
}
