package logic.dao;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.exceptions.BookTravelException;
import logic.exceptions.DeleteTravelException;
import logic.exceptions.SaveTravelException;
import logic.exceptions.SystemException;
import logic.model.PrivateTravel;
import logic.queries.CRUDQueries;
import logic.queries.SimpleQueries;
import logic.util.DBConnector;

public class PrivateTravelDao {
	
	private static final String SYSTEM_ERROR = "System error!";
	private static final String DESTINAZIONE = "Destinazione";
	private static final String HOTEL_LINK = "HotelLink";
	private static final String NOME_VIAGGIO = "NomeViaggio";
	private static final String BREAKFAST = "Breakfast";
	private static final String CREATOR = "Creatore";
	private static final String DATA_END = "DataFineV";
	private static final String DATA_V = "DataV";
	private static final String DESCRIPTION = "Descrizione";
	private static final String HOTEL_NAME = "HotelName";
	private static final String NUM_MAX_UT = "NumTrav";
	private static final String NUM_ROOMS = "NumRooms";
	private static final String PRICE = "Price";
	private static final String STARS = "Stars";
	
	private PrivateTravelDao() {
		
	}
	
	public static void addTravelPhoto(int idViaggio, File file, String filename) throws SystemException {
        
    	Statement stmt = null;
    	    	
    	try {    	
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.addTravelPhoto(DBConnector.getDBConnectorInstance().getConnection(), idViaggio, file, filename);  
        } catch (SQLException e) {
			throw new SystemException(SYSTEM_ERROR);
		} finally {
        	try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se) {
            	Logger logger = Logger.getLogger(PrivateTravelDao.class.getName());
            	logger.log(Level.WARNING, SYSTEM_ERROR);
            }
        }    	    	
    }
	
	public static List<String> retrieveTravelPhoto(int idViaggio) throws SystemException {
        
    	Statement stmt = null;
    	ResultSet rs;
    	
    	List<String> filenamesPhotos = new ArrayList<>();
    	
    	try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = SimpleQueries.retrieveTravelPhoto(stmt, idViaggio);
            
            if (!rs.first()) { // rs empty
            	return filenamesPhotos;
            }
            
            rs.first();
            
            String filenamePhoto = "";
            do {
            	filenamePhoto = rs.getString("Filename");
            	filenamesPhotos.add(filenamePhoto);
            } while(rs.next());
            
            return filenamesPhotos;
        } catch (SQLException e) {
			throw new SystemException(SYSTEM_ERROR);
		} finally {
        	try {
                if (stmt != null)
                    stmt.close();           
            } catch (SQLException se) {
            	Logger logger = Logger.getLogger(PrivateTravelDao.class.getName());
            	logger.log(Level.WARNING, SYSTEM_ERROR);
            }
        }
    }
	
    public static void saveTravel(PrivateTravel v) throws SaveTravelException, SystemException  {
    	
    	Statement stmt = null;

    	try {    		
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.saveTravel(stmt, v);
   
        } catch (SQLException e) {
        	throw new SaveTravelException("Error in saving the travel... retry!");
		} finally {
        	try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se) {
            	Logger logger = Logger.getLogger(PrivateTravelDao.class.getName());
            	logger.log(Level.WARNING, SYSTEM_ERROR);
            }
        }   	
    }
    
    public static void bookTravel(int idV) throws BookTravelException, SystemException {
    	
    	Statement stmt = null;
    	
    	try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.bookTravel(stmt, idV);
    	} catch (SQLException se) {
    		throw new BookTravelException("Book travel error"); 
        } finally {
        	try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se) {
            	Logger logger = Logger.getLogger(PrivateTravelDao.class.getName());
            	logger.log(Level.WARNING, SYSTEM_ERROR);
            }
        }
    }
    
    public static void bookAndSaveTravel(PrivateTravel v) throws BookTravelException, SystemException {
    	
    	Statement stmt = null;

    	try {
    		   		
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.bookAndSaveTravel(stmt, v);
   
        } catch (SQLException e) {
        	e.printStackTrace();
        	throw new BookTravelException("Book travel error"); 
        } finally {
        	try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se) {
            	Logger logger = Logger.getLogger(PrivateTravelDao.class.getName());
            	logger.log(Level.WARNING, SYSTEM_ERROR);
            }
        }    	
    }
    
    public static void deleteTravel(int idV) throws DeleteTravelException, SystemException {
    	
    	Statement stmt = null;
               
        try {              	 
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.deleteTravel(stmt, idV);
        } catch (SQLException se) {
        	throw new DeleteTravelException("Delete travel error");
        } finally {
       	 	try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se) {
                Logger logger = Logger.getLogger(PrivateTravelDao.class.getName());
            	logger.log(Level.WARNING, SYSTEM_ERROR);
            }
        }
       
    }
    
    private static List<PrivateTravel> setPrivateTravelInfoFromResultSet(ResultSet rs) throws SQLException{
    	List<PrivateTravel> listOfTravells = new ArrayList<>();
    	
			rs.first();
			do{
				PrivateTravel vg = new PrivateTravel();
	            
				vg.getHotelInfo().setBreakfast(rs.getString(BREAKFAST));
				vg.getHotelInfo().setHotelLink(rs.getString(HOTEL_LINK));
				vg.getHotelInfo().setHotelName(rs.getString(HOTEL_NAME));
				vg.getHotelInfo().setNumRooms(rs.getInt(NUM_ROOMS));
				vg.getHotelInfo().setPrice(rs.getString(PRICE));
				vg.getHotelInfo().setStars(rs.getInt(STARS));
	            
	            vg.getCreator().setUsername(rs.getString(CREATOR));
	            vg.getDestination().setDestinationName(rs.getString(DESTINAZIONE));
	            vg.setDescription(rs.getString(DESCRIPTION));
	            vg.setStartDate(rs.getString(DATA_V));
	            vg.setEndDate(rs.getString(DATA_END));
	            vg.setTravelName(rs.getString(NOME_VIAGGIO));
	            vg.setIdTravel(rs.getInt("idV"));
	            vg.setNumMaxUt(rs.getInt(NUM_MAX_UT));
	            
	            listOfTravells.add(vg);

	        } while(rs.next());
		
        
        return listOfTravells;
    }
    
   
    public static List<PrivateTravel> retrieveTravels(String username) throws SystemException {
    	
    	 Statement stmt = null;
         List<PrivateTravel> listOfTravells = new ArrayList<>();
                  
         try {
             // creazione ed esecuzione della query
             stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = SimpleQueries.selectAllTravelsDone(stmt, username);

             if (!rs.first()){ // rs empty
             	return listOfTravells;
             }
             
             listOfTravells = setPrivateTravelInfoFromResultSet(rs);
             // riposizionamento del cursore
             
             // STEP 5.1: Clean-up dell'ambiente
             rs.close();
             return listOfTravells;
         } catch (SQLException e) {
			throw new SystemException(SYSTEM_ERROR);
         } finally {
        	 try {
                 if (stmt != null)
                     stmt.close();
             } catch (SQLException se) {
                 Logger logger = Logger.getLogger(PrivateTravelDao.class.getName());
            	 logger.log(Level.WARNING, SYSTEM_ERROR);
             }
        }
    }
    
    public static List<PrivateTravel> retrieveMySavedTravels(String username) throws SystemException {
    	
   	 	Statement stmt = null;
        
        List<PrivateTravel> listOfSavedTravells = new ArrayList<>();
                
        try {
       	 
        	DBConnector.getDBConnectorInstance().getConnection();
       	 
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = SimpleQueries.selectUpcomingTravels(stmt, username);

            if (!rs.first()){ // rs empty
            	return listOfSavedTravells;
            }
            
            listOfSavedTravells = setPrivateTravelInfoFromResultSet(rs);
            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
            return listOfSavedTravells;
        } catch (SQLException e) {
			throw new SystemException(SYSTEM_ERROR);
		} finally {
       	 	try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se) {
                Logger logger = Logger.getLogger(PrivateTravelDao.class.getName());
            	logger.log(Level.WARNING, SYSTEM_ERROR);
            }
        }
    }
    
    public static List<PrivateTravel> retrieveNextBookedTravels(String username) throws SystemException {
    	
   	 	Statement stmt = null;
        
        List<PrivateTravel> listOfNextBookedTravells = new ArrayList<>();
        
        try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = SimpleQueries.selectUpcomingBookedTravels(stmt, username);

            if (!rs.first()){ // rs empty
            	return listOfNextBookedTravells;
            }
            
            listOfNextBookedTravells = setPrivateTravelInfoFromResultSet(rs);
            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
            return listOfNextBookedTravells;
         } catch (SQLException e) {
			throw new SystemException(SYSTEM_ERROR);
		 } finally {
       	 try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se) {
                Logger logger = Logger.getLogger(PrivateTravelDao.class.getName());
            	logger.log(Level.WARNING, SYSTEM_ERROR);
            }
        }
    }
    
    public static List<PrivateTravel> retrieveFollowerTravelsPrivate(String usrn) throws SystemException {
		
		Statement stmt = null;
        List<PrivateTravel> followerTravels = new ArrayList<>();
        
        try {      	 
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            ResultSet rs = SimpleQueries.selectAllTravelsDone(stmt, usrn);
            
            if (!rs.first()){ // rs empty
            	return followerTravels;
            }
            
            // riposizionamento del cursore
            rs.first();
            
            do {
            	PrivateTravel vg = new PrivateTravel();
                vg.getHotelInfo().setHotelLink(rs.getString(HOTEL_LINK));
                vg.setIdTravel(rs.getInt("idV"));
                vg.getDestination().setDestinationName(rs.getString(DESTINAZIONE));
                vg.setTravelName(rs.getString(NOME_VIAGGIO));
                
                followerTravels.add(vg);

            } while(rs.next());
            
            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
            return followerTravels;
        } catch (SQLException e) {
			throw new SystemException(SYSTEM_ERROR);
		} finally {
       	 	try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se) {
                Logger logger = Logger.getLogger(PrivateTravelDao.class.getName());
            	logger.log(Level.WARNING, SYSTEM_ERROR);
            }
        }
	}
    
}
