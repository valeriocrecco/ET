package logic.dao;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.exceptions.BookGroupTravelException;
import logic.exceptions.DeleteGroupTravelException;
import logic.exceptions.DuplicateRequestException;
import logic.exceptions.SaveTravelException;
import logic.exceptions.SystemException;
import logic.model.JoinNotification;
import logic.model.PublicTravel;
import logic.queries.CRUDQueries;
import logic.queries.SimpleQueries;
import logic.util.DBConnector;

public class PublicTravelDao {
    
	private static final String BREAKFAST = "Breakfast";
	private static final String CREATOR = "Creatore";
	private static final String DATA_END = "DataFineV";
	private static final String DATA_V = "DataV";
	private static final String DESCRIPTION = "Descrizione";
	private static final String DESTINATION = "Destinazione";
	private static final String HOTEL_LINK = "HotelLink";
	private static final String HOTEL_NAME = "HotelName";
	private static final String NUM_MAX_UT = "NumMaxUt";
	private static final String NUM_ROOMS = "NumRooms";
	private static final String POSTI_OCCUPATI = "PostiOccupati";
	private static final String PRICE = "Price";
	private static final String STARS = "Stars";
	private static final String NOME_VIAGGIO = "NomeViaggio";
	private static final String SYSTEM_ERROR = "Unexpected application error occurred... retry!";
	
	private PublicTravelDao() {
		
	}
	
	private static void printLogger() {
		Logger logger = Logger.getLogger(PublicTravelDao.class.getName());
    	logger.log(Level.WARNING, SYSTEM_ERROR);
	}
	
	public static void addTravelGroupPhoto(int idViaggioGruppo, File file, String filename) throws SystemException {
        
    	Statement stmt = null;
    	    	
    	try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.addTravelGroupPhoto(DBConnector.getDBConnectorInstance().getConnection(), idViaggioGruppo, file, filename);
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
	
	public static List<String> retrieveTravelGroupPhoto(int idViaggioGruppo) throws SystemException {
	      
    	Statement stmt = null;
    	ResultSet rs;

    	List<String> filenames = new ArrayList<>();
    	String filename = "";
    	try {
    		// creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = SimpleQueries.retrieveTravelGroupPhoto(stmt, idViaggioGruppo);
            
            if (!rs.first()) { // rs empty
            	return filenames;
            }
            
            rs.first();
            do {
                filename = rs.getString("Filename");
                filenames.add(filename);
            } while(rs.next());
            
            return filenames;
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

    public static void saveGroupTravel(PublicTravel vgr) throws SaveTravelException, SystemException {
    	
    	Statement stmt = null;

    	try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.saveGroupTravel(stmt, vgr);
        } catch (SQLException e) {
        	e.printStackTrace();
			throw new SaveTravelException("Error while saving the travel!");
		} finally {
        	try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se) {
            	printLogger();
            }
        }
    }
    
    public static void bookGroupTravel(int idV) throws BookGroupTravelException, SystemException {
    	
    	Statement stmt = null;

    	try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.bookGroupTravel(stmt, idV);
    	} catch (SQLException se) {
    		throw new BookGroupTravelException("Book group travel error");
        } finally {
        	try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se) {
            	printLogger();
            }
        }
    }
    
    public static void bookAndSaveGroupTravel(PublicTravel vgr) throws BookGroupTravelException, SystemException {
    	
    	Statement stmt = null;    	
    	
    	try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.bookAndSaveGroupTravel(stmt, vgr);
        } catch (SQLException e) {
        	throw new BookGroupTravelException("Book group travel error");
		} finally {
        	try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se) {
            	printLogger();
            }
        }
    }
    
    public static void deleteTravelGr(int idV) throws DeleteGroupTravelException, SystemException {
    	
    	Statement stmt = null;
        
        try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.deleteTravelGr(stmt, idV);
        } catch (SQLException se) {
        	throw new DeleteGroupTravelException("Delete group travel error");
        } finally {
       	 	try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se) {
                printLogger();
            }
        }
    }
    
    private static List<PublicTravel> setPublicTravelInfo(ResultSet rs) throws SQLException{
    	
    	List<PublicTravel> listOfGrTravells = new ArrayList<>();
        
    	// riposizionamento del cursore
        rs.first();
        
        do{
        	PublicTravel vgr = new PublicTravel();
            
        	vgr.getHotelInfo().setBreakfast(rs.getString(BREAKFAST));
        	vgr.getHotelInfo().setHotelLink(rs.getString(HOTEL_LINK));
        	vgr.getHotelInfo().setHotelName(rs.getString(HOTEL_NAME));
        	vgr.getHotelInfo().setNumRooms(rs.getInt(NUM_ROOMS));
        	vgr.getHotelInfo().setPrice(rs.getString(PRICE));
        	vgr.getHotelInfo().setStars(rs.getInt(STARS));
            
            vgr.getCreator().setUsername(rs.getString(CREATOR));
            vgr.getDestination().setDestinationName(rs.getString(DESTINATION));
            vgr.setDescription(rs.getString(DESCRIPTION));
            vgr.setStartDate(rs.getString(DATA_V));
            vgr.setEndDate(rs.getString(DATA_END));
            vgr.setAvailableSeats(rs.getInt(NUM_MAX_UT) - rs.getInt(POSTI_OCCUPATI));
            vgr.setNumMaxUt(rs.getInt(NUM_MAX_UT));
            vgr.setIdTravel(rs.getInt("idV"));
            vgr.setTravelName(rs.getString(NOME_VIAGGIO));
            
            listOfGrTravells.add(vgr);

        } while(rs.next());
        
        return listOfGrTravells;
    }
    
    public static List<PublicTravel> retrieveGroupTravels(String u) throws SystemException {
    	
   	 	Statement stmt = null;
            	
        List<PublicTravel> listOfGrTravells = new ArrayList<>();
        
        try {    	
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = SimpleQueries.selectAllGrTravelsProfile(stmt, u);

            if (!rs.first()){ // rs empty
            	return listOfGrTravells;
            }
            
            listOfGrTravells = setPublicTravelInfo(rs);
            
            rs.close();
            return listOfGrTravells;
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
    
    public static List<PublicTravel> retrieveMySavedGrTravels(String u) throws SystemException {
    	
   	 	Statement stmt = null;
            	
        List<PublicTravel> listOfSavedGrTravells = new ArrayList<>();
        
        try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = SimpleQueries.selectUpcomingGrTravels(stmt, u);

            if (!rs.first()){ // rs empty
            	return listOfSavedGrTravells;
            }
            
            listOfSavedGrTravells = setPublicTravelInfo(rs);
            rs.close();
            return listOfSavedGrTravells;
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
    
    public static List<PublicTravel> retreiveMyNextBookedGrTravells(String u) throws SystemException {
    	
   	 	Statement stmt = null;
    	
        List<PublicTravel> listOfNextBookedGrTravells = new ArrayList<>();
        
        try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = SimpleQueries.selectUpcomingBookedGrTravels(stmt, u);

            if (!rs.first()){ // rs empty
            	return listOfNextBookedGrTravells;
            }
            
            listOfNextBookedGrTravells = setPublicTravelInfo(rs);
            rs.close();
            return listOfNextBookedGrTravells;
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
    
    public static List<PublicTravel> retreiveJoinableGrTravels(String u) throws SystemException {
    	
   	 	Statement stmt = null;
    	
        List<PublicTravel> listOfJoinableGrTravells = new ArrayList<>();
        
        try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = SimpleQueries.selectAllGrTravels(stmt, u);

            if (!rs.first()){ // rs empty
            	return listOfJoinableGrTravells;
            }
            
            listOfJoinableGrTravells = setPublicTravelInfo(rs);
            rs.close();
            return listOfJoinableGrTravells;
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
    
    public static List<PublicTravel> retreiveSpecialJoinableGrTravels(String username, int sea, int art, int young, String continent) throws SystemException {
    	
   	 	Statement stmt = null;
    	
        List<PublicTravel> listOfSpecialGrTravells = new ArrayList<>();
        
        try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = SimpleQueries.retrieveSpecialTravels(stmt, username, sea, art, young, continent);

            if (!rs.first()){ // rs empty
            	return listOfSpecialGrTravells;
            }
            
            listOfSpecialGrTravells = setPublicTravelInfo(rs);
            rs.close();
            return listOfSpecialGrTravells;
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
       	
    public static void insertOneParticipant(int idV, String username) throws SystemException {
    	
    	Statement stmt = null;

        try {  
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.insertOneParticipant(stmt, idV, username);
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
    
    public static void decreaseAvailableSlots(int idTravel, int slots) throws SystemException {
    	Statement stmt = null;

        try {   
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.decreaseAvailableSlots(stmt, idTravel, slots);
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
    
    public static int retrieveTravelSlots(int idV) throws SystemException {
    	
       Statement stmt = null;
        
        try {   
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = SimpleQueries.selectTravelSlots(stmt, idV);
            
            int availableSlots = 0;
            
            if (!rs.first()){ // rs empty
            	return availableSlots;
            }
            
            // riposizionamento del cursore
            rs.first();
            
	       	availableSlots = (rs.getInt(NUM_MAX_UT) - rs.getInt(POSTI_OCCUPATI));
     
            rs.close();
            return availableSlots;
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
    
    public static int retrieveNotAvailableSeats(int idV) throws SystemException {
    	
        Statement stmt = null;
         
         try {   
             // creazione ed esecuzione della query
             stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = SimpleQueries.selectNotAvailableTravelSlots(stmt, idV);
             
             int notAvailableSlots = 0;
             
             if (!rs.first()){ // rs empty
             	return notAvailableSlots;
             }
             
             // riposizionamento del cursore
             rs.first();
             
 	       	notAvailableSlots = rs.getInt(POSTI_OCCUPATI);
      
             rs.close();
             return notAvailableSlots;
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
    
    public static void joinVGruppo(JoinNotification joinNotification) throws SystemException {
    	
    	Statement stmt = null;

    	try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.joinGrTravel(stmt, joinNotification.getSenderJoin().getUsername(), joinNotification.getTravelJoin().getIdTravel(), joinNotification.getMsgJoin());
        } catch (SQLException e) {
			throw new SystemException("Error in sending request... retry!");
		} finally {
			try {
	            if (stmt != null) {
					stmt.close();
	            }	
			} catch (SQLException e) {
				printLogger();
			}
        }
    }
    
    public static void acceptJoin(String usrn, int idV) throws SystemException {
    	
    	Statement stmt = null;    	
    	
    	try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            CRUDQueries.acceptJoinGrTravel(stmt, usrn, idV);       
        } catch (SQLException e) {
			throw new SystemException(SYSTEM_ERROR);
		} finally {
			try {
	            if (stmt != null) {
					stmt.close();
	            }
			} catch (SQLException e) {
		    	printLogger();
			}
        }
    	
    }
    
    public static void checkRequests(int idV, String username) throws DuplicateRequestException, SystemException {
    	
    	Statement stmt = null;
    	ResultSet rs;
    	    	
    	try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = SimpleQueries.controlUniqueRequest(stmt, idV, username);
    	    
            if(rs.first()) {
            	throw new DuplicateRequestException("Request already sent!");
            }
    	} catch (SQLException se) {
    		throw new SystemException(SYSTEM_ERROR);
        } finally {  	
        	try {
	            if (stmt != null) { 
					stmt.close();
	            }
        	} catch (SQLException e) {
            	printLogger();
			}
        }
    }
    
    public static List<PublicTravel> retrieveFollowerTravelsPublic(String usrn) throws SystemException  {
		
		Statement stmt = null;
		List<PublicTravel> travels = new ArrayList<>();
        
        try {
            // creazione ed esecuzione della query
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = SimpleQueries.selectAllGrTravelsProfile(stmt, usrn);
            
            if (!rs.first()){ // rs empty
            	return travels;
            }
            
            // riposizionamento del cursore
            rs.first();
            
            do {
            	PublicTravel vgr = new PublicTravel();
                
                vgr.getHotelInfo().setHotelLink(rs.getString(HOTEL_LINK));
                vgr.setIdTravel(rs.getInt("idV"));
                vgr.getDestination().setDestinationName(rs.getString(DESTINATION));
                vgr.setTravelName(rs.getString(NOME_VIAGGIO));
                
                travels.add(vgr);

            } while(rs.next());
            
            // STEP 5.1: Clean-up dell'ambiente
            rs.close();
            return travels;
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
    
}
