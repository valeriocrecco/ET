package logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.exceptions.SystemException;
import logic.model.Destination;
import logic.queries.SimpleQueries;
import logic.util.DBConnector;

public class DestinationDao {
	
	private static final String SYSTEM_ERROR = "Unexpected application error occurred... retry!";
	
	private DestinationDao() {
		
	}
	
	public static List<Destination> findSpecialTravel(Destination destination) throws SystemException {
		
		Statement stmt = null;
        List<Destination> dests = new ArrayList<>();
               
        try {
            stmt = DBConnector.getDBConnectorInstance().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = SimpleQueries.findSpecialDestination(stmt, destination.getLocation(), destination.getArt(), destination.getYoung(), destination.getContinent());

            if (!rs.first()){ // rs empty
            	return dests;
            }
            
            // riposizionamento del cursore
            rs.first();
            do{
                // lettura delle colonne "by name"
            	Destination dest = new Destination();
                dest.setDestinationName(rs.getString("DestinationName"));
                dests.add(dest);

            } while(rs.next());
            
            rs.close();
            return dests;
        } catch (SQLException e) {
        	throw new SystemException(SYSTEM_ERROR);
		} finally {
        	try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se) {
            	Logger logger = Logger.getLogger(PublicTravelDao.class.getName());
            	logger.log(Level.WARNING, SYSTEM_ERROR);
            }
        }
	}
}
