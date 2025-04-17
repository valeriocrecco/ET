package logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.exceptions.SystemException;
import logic.model.ReplyNotification;
import logic.model.User;
import logic.queries.CRUDQueries;
import logic.queries.SimpleQueries;
import logic.util.DBConnector;

public class ReplyNotificationDao {

	private static final String SYSTEM_ERROR = "Unexpected error occured!";
	private static final String SENDER = "Sender";
	private static final String MESSAGE = "Message";
	
	private ReplyNotificationDao() {
	
	}
	
	private static void printLogger() {
		Logger logger = Logger.getLogger(ReplyNotificationDao.class.getName());
    	logger.log(Level.WARNING, SYSTEM_ERROR);
	}
	
	public static List<ReplyNotification> retrieveReplyNotifications(String username) throws SystemException {
    	
    	Statement stmt = null;
    	ResultSet rs = null;
    	
    	List<ReplyNotification> replyNotifications = new ArrayList<>();
    	
    	try {
    		
			stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = SimpleQueries.retrieveReplyNotifications(stmt, username);		
	 
			if (!rs.first()) { // rs empty
            	return replyNotifications;
            }
	    	
			do {    	
				User sender = new User();
				sender.setUsername(rs.getString(SENDER));
				User receiver = new User();
				receiver.setUsername(rs.getString("Receiver"));
				
				ReplyNotification replyNotification = new ReplyNotification();
				replyNotification.setIdReply(rs.getInt("id"));
				replyNotification.setSenderReply(sender);
				replyNotification.setReceiverReply(receiver);
				replyNotification.setDateReply(rs.getString("Date"));
				replyNotification.setMsgReply(rs.getString(MESSAGE));
				
				replyNotifications.add(replyNotification);
			} while(rs.next());
			
			return replyNotifications;
			
		} catch (SQLException e) {
			throw new SystemException(SYSTEM_ERROR);
        } finally {
        	try {
                if (stmt != null)
                    stmt.close();
        	} catch (SQLException se) {
        		printLogger();
            }
        }
    }

	public static void sendReplyNotification(ReplyNotification replyNotification) throws SystemException {
    	Statement stmt = null;
    	try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.sendNotificationReply(stmt, replyNotification.getSenderReply().getUsername(), replyNotification.getReceiverReply().getUsername(), replyNotification.getMsgReply());
        } catch (SQLException e) {
        	throw new SystemException(SYSTEM_ERROR);
		} finally {
        	try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se) {
            	printLogger();
            }
        }  	
    }

	public static void deleteReplyNotification(int idNotif) throws SystemException {
    	Statement stmt = null;
        try {	 
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.deleteReplyNotification(stmt, idNotif);
        } catch (SQLException se) {
        	throw new SystemException(SYSTEM_ERROR);
        } finally {
       	 	try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se) {
            	printLogger();
            }
        }
    }
	
	public static void modifyReplySenderUsername(String newUsername, String username) throws SystemException {
    	
    	Statement stmt = null;
    	    	
    	try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.modifReplySenderUsername(stmt, newUsername, username);

        } catch (SQLException e) {
        	throw new SystemException("Un expected error occurred..!");
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
	
	public static void modifyReplyReceiverUsername(String newUsername, String username) throws SystemException {
    	
    	Statement stmt = null;
    	    	
    	try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.modifReplyReceiverUsername(stmt, newUsername, username);

        } catch (SQLException e) {
        	throw new SystemException("Un expected error occurred..!");
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
