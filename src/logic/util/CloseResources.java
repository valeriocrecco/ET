package logic.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import logic.dao.UserDao;
import logic.exceptions.SystemException;

public class CloseResources {
	
	private static final String SYSTEM_ERROR = "System error!";
	
	public void closeApplication() {
		this.closeDBConnection();
		NotifSingletonClass.getNotifSingletonInstance().resetNotifications();
	}
	
    private void closeDBConnection() {
    	try {
			DBConnector.getDBConnectorInstance().releaseConnection();
		} catch (SystemException e) {
			Logger logger = Logger.getLogger(UserDao.class.getName());
        	logger.log(Level.WARNING, SYSTEM_ERROR);
		}
    }
	
}
