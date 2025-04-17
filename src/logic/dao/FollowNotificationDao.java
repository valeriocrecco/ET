package logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.exceptions.SystemException;
import logic.model.FollowNotification;
import logic.model.User;
import logic.queries.CRUDQueries;
import logic.queries.SimpleQueries;
import logic.util.DBConnector;

public class FollowNotificationDao {

	private static final String SYSTEM_ERROR = "Unexpected error occured!";
	private static final String SENDER = "Sender";
	private static final String MESSAGE = "Message";
	
	private FollowNotificationDao() {
		
	}
	
	public static List<FollowNotification> retrieveFollowNotifications(String username) throws SystemException {
    	
    	Statement stmt = null;
    	ResultSet rs = null;
    	
    	List<FollowNotification> followNotifications = new ArrayList<>();
    	
    	try {
    		
			stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = SimpleQueries.retrieveFollowNotifications(stmt, username);		
	 
			if (!rs.first()) { // rs empty
            	return followNotifications;
            }
	    	
			do {
				User sender = new User();
				sender.setUsername(rs.getString(SENDER));
				User receiver = new User();
				receiver.setUsername(rs.getString("Receiver"));
				
				FollowNotification followNotification = new FollowNotification();
				followNotification.setIdFollow(rs.getInt("id"));
				followNotification.setMsgFollow(rs.getString(MESSAGE));
				followNotification.setSenderFollow(sender);
				followNotification.setReceiverFollow(receiver);
				followNotification.setDateFollow(rs.getString("Date"));
				
				followNotifications.add(followNotification);
			} while(rs.next());
			
			return followNotifications;
			
		} catch (SQLException e) {
			throw new SystemException(SYSTEM_ERROR);
        } finally {
        	try {
                if (stmt != null)
                    stmt.close();
        	} catch (SQLException se) {
        		Logger logger = Logger.getLogger(FollowNotificationDao.class.getName());
            	logger.log(Level.WARNING, SYSTEM_ERROR);
            }
        }	
    }  
	
	public static void deleteFollowNotification(int idNotif) throws SystemException {
    	Statement stmt = null;
        try {	 
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.deleteFollowNotification(stmt, idNotif);
        } catch (SQLException se) {
        	throw new SystemException(SYSTEM_ERROR);
        } finally {
       	 	try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se) {
            	Logger logger = Logger.getLogger(FollowNotificationDao.class.getName());
            	logger.log(Level.WARNING, SYSTEM_ERROR);
            }
        }
    }
	
	public static void modifyFollowerUsername(String newUsername, String username) throws SystemException {
    	
    	Statement stmt = null;
    	    	
    	try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.modifFollowerUsername(stmt, newUsername, username);

        } catch (SQLException e) {
        	throw new SystemException(SYSTEM_ERROR);
		} finally {
        	try {
                if (stmt != null)
                	stmt.close();            
            } catch (SQLException se) {
            	Logger logger = Logger.getLogger(UserDao.class.getName());
            	logger.log(Level.WARNING, SYSTEM_ERROR);
            }
        }
    	    	
    }
	
	public static void modifyFollowedUsername(String newUsername, String username) throws SystemException {
    	
    	Statement stmt = null;
    	    	
    	try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.modifFollowedUsername(stmt, newUsername, username);

        } catch (SQLException e) {
        	throw new SystemException(SYSTEM_ERROR);
		} finally {
        	try {
                if (stmt != null)
                	stmt.close();            
            } catch (SQLException se) {
            	Logger logger = Logger.getLogger(UserDao.class.getName());
            	logger.log(Level.WARNING, SYSTEM_ERROR);
            }
        }
    	    	
    }
	
	public static void modifyFollowNotifSender(String newUsername, String username) throws SystemException {
    	
    	Statement stmt = null;
    	    	
    	try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.modifyFollowNotifSender(stmt, newUsername, username);

        } catch (SQLException e) {
        	throw new SystemException(SYSTEM_ERROR);
		} finally {
        	try {
                if (stmt != null)
                	stmt.close();            
            } catch (SQLException se) {
            	Logger logger = Logger.getLogger(UserDao.class.getName());
            	logger.log(Level.WARNING, SYSTEM_ERROR);
            }
        }
    	    	
    }
	
	public static void modifyFollowNotifReceiver(String newUsername, String username) throws SystemException {
    	
    	Statement stmt = null;
    	    	
    	try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.modifyFollowNotifReceiver(stmt, newUsername, username);

        } catch (SQLException e) {
        	throw new SystemException(SYSTEM_ERROR);
		} finally {
        	try {
                if (stmt != null)
                	stmt.close();            
            } catch (SQLException se) {
            	Logger logger = Logger.getLogger(UserDao.class.getName());
            	logger.log(Level.WARNING, SYSTEM_ERROR);
            }
        }
    	    	
    }
	
}
