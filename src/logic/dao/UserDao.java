package logic.dao;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.exceptions.DefaultPhotoException;
import logic.exceptions.DuplicateUsernameException;
import logic.exceptions.EmailException;
import logic.exceptions.PasswordException;
import logic.exceptions.SystemException;
import logic.exceptions.UsernameException;
import logic.model.FollowNotification;
import logic.model.User;
import logic.queries.CRUDQueries;
import logic.queries.SimpleQueries;
import logic.util.DBConnector;

public class UserDao {
	
	private static final String SYSTEM_ERROR = "System error!";
	private static final String PHOTO = "Photo";
	private static final String ERROR = "Unexpected application error occurred... retry!";
	private static final String EMAIL = "email";
	private static final String FILENAME = "Filename";
	
	private UserDao() {
		
	}
	
    public static void usernameChecker(String username) throws SystemException, DuplicateUsernameException {

    	Statement stmt = null;
    	ResultSet rs;
    	
		try {
			// creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = SimpleQueries.controlUniqueUsername(stmt, username);
            
            if(rs.first()) {
            	throw new DuplicateUsernameException("Username already exists");
            }
    
        } catch (SQLException e) {
        	throw new SystemException(ERROR);
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
    
    public static void registerUsr(User user) throws SystemException {

    	Statement stmt = null;

    	try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.inserisciNuovoUtente(stmt, user);
        } catch (SQLException e) {
        	throw new SystemException(ERROR);
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
    
    public static void modifPsw(String username, String password) throws PasswordException, SystemException {
    
    	Statement stmt = null;
    	  	
    	try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.modifPassword(stmt, username, password);
            
        } catch (SQLException e) {
			throw new PasswordException("Change password error! Please retry");
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
    
    public static void modifEmail(String username, String email) throws EmailException, SystemException {
    	
    	Statement stmt = null;
    	    	
    	try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.modifEmail(stmt, username, email);
            
        } catch (SQLException e) {
			throw new EmailException("Change email error! Please retry");
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
    
    public static void modifUsrn(String usr, String newUsername) throws UsernameException, SystemException {

    	Statement stmt = null;
    	    	
    	try {
    		// creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            stmt.execute("SET FOREIGN_KEY_CHECKS=0");
            CRUDQueries.modifUsername(stmt, usr, newUsername);
            
            stmt.execute("SET FOREIGN_KEY_CHECKS=1");
            
        } catch (SQLException e) {
			throw new UsernameException("Error in inserting new username... pleae retry!");
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
    
    public static void modifyPhoto(String username, File file, String filename) throws DefaultPhotoException, SystemException  {

    	Statement stmt = null;

    	try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.modifyPhoto(DBConnector.getDBConnectorInstance().getConnection(), username, file, filename);
    	} catch (SQLException e) {
			throw new DefaultPhotoException("Error in changing user photo... please retry!");
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
    
    public static String retrieveUserPhoto(String username) throws DefaultPhotoException, SystemException {
    	
    	Statement stmt = null;

    	ResultSet rs;

        String filename = "";
    	
    	try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = SimpleQueries.retrieveUserPhoto(stmt, username);
            
            rs.first();
            
            if(rs.getBinaryStream(PHOTO) == null) {
            	return filename;
            }
            
            filename = rs.getString(FILENAME);
            rs.close();
            return filename;
        } catch (SQLException e) {
        	throw new DefaultPhotoException("Error in retrieve photo profile");
		}
    	finally {
        	try {
                if (stmt != null)
                    stmt.close();        
            } catch (SQLException se) {
            	Logger logger = Logger.getLogger(UserDao.class.getName());
            	logger.log(Level.WARNING, SYSTEM_ERROR);
            }
        }
    }
    
    public static User logUsr(String username) throws SystemException {

		Statement stmt = null;
    	ResultSet rs;
    	
		User usr = null;

		try {
           			
			// creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = SimpleQueries.loginChecker(stmt, username);
            
            if(!rs.first()) {
            	return usr;
            }
                        
        	usr = new User();
        	usr.setUsername(rs.getString("Username"));
        	usr.setPassword(rs.getString("Password"));
        	
        	return usr;
        	
        } catch (SQLException e) {
			throw new SystemException(SYSTEM_ERROR);
		} 
		finally {
        	try {
                if (stmt != null)
                    stmt.close();       
            } catch (SQLException se) {
            	Logger logger = Logger.getLogger(UserDao.class.getName());
            	logger.log(Level.WARNING, SYSTEM_ERROR);
            }
        }
				
    }
    
    public static String getUserEmail(String username) throws SystemException {
    	    	
    	Statement stmt = null;
    	
        String strEmail = "";
		
        try {                	
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = SimpleQueries.selectUserEmail(stmt, username);

            if (!rs.first()){ // rs empty
            	return strEmail;
            }
            
            // riposizionamento del cursore
            rs.first();
            
            strEmail = rs.getString(EMAIL);
            
            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
            
        } catch (SQLException e) {
			throw new SystemException(ERROR);
		} finally {
        	try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se) {
            	Logger logger = Logger.getLogger(UserDao.class.getName());
            	logger.log(Level.WARNING, SYSTEM_ERROR);
            }
        }
    
        return strEmail;
    }  
    
    public static void modifyTravelInfo(String newUsername, String username) throws SystemException {
    	    	
    	Statement stmt = null;
    	    	
    	try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.modifTravelUsername(stmt, newUsername, username);

        } catch (SQLException e) {
        	throw new SystemException(ERROR);
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
    
    public static void modifyTravelGRInfo(String newUsername, String username) throws SystemException{
    	    	
    	Statement stmt = null;
    	    	
    	try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.modifTravelGRUsername(stmt, newUsername, username);
            
        } catch (SQLException e) {
        	throw new SystemException(ERROR);
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
    
    public static void sendRequestFollow(FollowNotification followNotification) throws SystemException {

    	Statement stmt = null;
    	    	
    	try {	    			    		
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.sendRequestFollowed(stmt, followNotification.getSenderFollow().getUsername(), followNotification.getReceiverFollow().getUsername(), followNotification.getMsgFollow());
        } catch (SQLException e) {
			throw new SystemException("Follow request failed... retry!");
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
    
    public static void addFollowed(User follower, User followed) throws SystemException {

    	Statement stmt = null;
    	
		try {	    			    		
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.addFollowed(stmt, follower.getUsername(), followed.getUsername());
        } catch (SQLException e) {
			throw new SystemException("Add followed error... retry!");
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
    
    public static List<User> retrieveFollowersByUsername(String u) throws SystemException {
    	    	
   	 	Statement stmt = null;
        
        List<User> followersList = new ArrayList<>();
        
        try {            	
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = SimpleQueries.getFollowersInfos(stmt, u);

            if (!rs.first()){ // rs empty
            	return followersList;
            }
            
            // riposizionamento del cursore
            rs.first();
            
            do {
                String username = rs.getString("Seguito");
                String email = rs.getString(EMAIL);
                String photo = "";
                if(rs.getString(FILENAME) != null) {
                	photo = rs.getString(FILENAME);
                }
                User user = new User();
                user.setEmail(email);
                user.setPhoto(photo);
                user.setUsername(username);
                followersList.add(user);
            } while(rs.next());
            
            rs.close();
            return followersList;
        } catch (SQLException e) {
			throw new SystemException(SYSTEM_ERROR);
		} finally {
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();           
            } catch (SQLException se) {
            	Logger logger = Logger.getLogger(UserDao.class.getName());
            	logger.log(Level.WARNING, SYSTEM_ERROR);
            }
        }
        
    }
    
    public static List<User> retrieveFollowersSearched(String user, String userSearched) throws SystemException {
    	
    	List<User> users = new ArrayList<>();
    	
   	 	Statement stmt = null;
           
        try {
                	
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = SimpleQueries.retrieveFollowersSearched(stmt, user);
                        
            if (!rs.first()){ // rs empty
            	return users;
            }
            
            // riposizionamento del cursore
            rs.first();
            
            do {
                String username = rs.getString("Username");
                String email = rs.getString(EMAIL);
                String photo = "";
                if(rs.getString(FILENAME) != null) {
                	photo = rs.getString(FILENAME);
                }
              
                User usr = new User();
                usr.setEmail(email);
                usr.setUsername(username);
                usr.setPhoto(photo);
                
                users = UserDao.addUser(userSearched, usr, users);
            } while(rs.next());
           
            rs.close();
            return users;
            
        } catch (SQLException e) {
			return users;
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
    
    private static List<User> addUser(String userSearched, User usr, List<User> users) {
    	
        int limitCycle = 0;
    	boolean find = false;
        
    	if(userSearched.length() >= usr.getUsername().length()) {
        	limitCycle = usr.getUsername().length();
        }
        else {
        	limitCycle = userSearched.length();
        }
        for(int j = 0; j < limitCycle; j++) {
    		if(Character.toLowerCase(userSearched.charAt(j)) != Character.toLowerCase(usr.getUsername().charAt(j))) {
        		find = false;
        		break;
        	}	
    		find = true;
        }       
        
        if(find) {
        	users.add(usr);
        }
        
        return users;
    }
       
    public static boolean checkFollow(User follower, User followed) throws SystemException {
    	
    	Statement stmt = null;
    	
    	try {    		
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = SimpleQueries.retrieveFollower(stmt, follower, followed);
            
            if (!rs.first()){ // rs empty
            	return true;
            }
            
            rs.close();
            return false;
            
        } catch (SQLException e) {
        	throw new SystemException(ERROR);
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
    
    
    public static void modifyParticipantUsername(String newUsername, String username) throws SystemException {
    	
    	Statement stmt = null;
    	    	
    	try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.modifParticipantUsername(stmt, newUsername, username);

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
