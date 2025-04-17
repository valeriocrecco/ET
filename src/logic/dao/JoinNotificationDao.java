package logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.exceptions.SystemException;
import logic.model.JoinNotification;
import logic.model.PublicTravel;
import logic.model.User;
import logic.queries.CRUDQueries;
import logic.queries.SimpleQueries;
import logic.util.DBConnector;

public class JoinNotificationDao {
	
	private static final String SYSTEM_ERROR = "Unexpected error occured!";
	private static final String SENDER = "Sender";
	private static final String MESSAGE = "Message";
	
	private JoinNotificationDao() {
		
	}
	
	public static List<JoinNotification> retrieveJoinNotifications(String username) throws SystemException {
    	
    	Statement stmt = null;
    	ResultSet rs = null;
    	
    	List<JoinNotification> joinNotifications = new ArrayList<>();
    	
    	try {
			stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = SimpleQueries.retrieveJoinNotification(stmt, username);		
	 
			if (!rs.first()) { // rs empty
            	return joinNotifications;
            }
	    	
			do {
				User sender = new User();
				sender.setUsername(rs.getString(SENDER));
				PublicTravel viaggioGruppo = new PublicTravel();
				viaggioGruppo.setIdTravel(rs.getInt("idV"));
				viaggioGruppo.setTravelName(rs.getString("NomeViaggio"));
				
				JoinNotification joinNotification = new JoinNotification();
				joinNotification.setIdJoin(rs.getInt("id"));
				joinNotification.setSenderJoin(sender);
				joinNotification.setTravelJoin(viaggioGruppo);
				joinNotification.setMsgJoin(rs.getString(MESSAGE));
				joinNotification.setDateJoin(rs.getString("Date"));
				
				joinNotifications.add(joinNotification);
			} while(rs.next());
			
			return joinNotifications;
		} catch (SQLException e) {
			throw new SystemException(SYSTEM_ERROR);
        } finally {
        	try {
                if (stmt != null)
                    stmt.close();
        	} catch (SQLException se) {
        		Logger logger = Logger.getLogger(JoinNotificationDao.class.getName());
            	logger.log(Level.WARNING, SYSTEM_ERROR);
            }
        }	
    }
	
	public static void deleteJoinNotification(int idNotif) throws SystemException {
    	Statement stmt = null;
        try {	 
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.deleteJoinNotification(stmt, idNotif);
        } catch (SQLException se) {
        	throw new SystemException(SYSTEM_ERROR);
        } finally {
       	 	try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se) {
            	Logger logger = Logger.getLogger(JoinNotificationDao.class.getName());
            	logger.log(Level.WARNING, SYSTEM_ERROR);
            }
        }
	}
	
	public static void modifyJoinSender(String newUsername, String username) throws SystemException {
    	
    	Statement stmt = null;
    	    	
    	try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.modifyJoinSender(stmt, newUsername, username);

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
