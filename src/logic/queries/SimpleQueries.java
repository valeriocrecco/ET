package logic.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import logic.model.User;


public class SimpleQueries {
	
	private static final String DATAV_LESS = " DataV <= '";
	private static final String DATAV_GREATER = " DataV > '";
	private static final String OR_PARTECIPANT = "' OR Partecipante = '";
	private static final String AND_SQL = "') AND";
	private static final String AND = "' AND";
	private static final String SELECT_VCREATOR = "SELECT * FROM Viaggi WHERE Creatore = '";
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final String BOOKED = "' and Prenotato = 1;";
	private static final String SEATS_AND_DATE = "' and NumMaxUt > PostiOccupati and DataV > '";
	private static final String YOUTH = "' and Youth = '";
	private SimpleQueries() {
		
	}
	
	public static ResultSet controlUniqueUsername(Statement stmt, String username) throws SQLException {
		String sql = "SELECT Username FROM Users WHERE Username = '" + username + "';";
        return stmt.executeQuery(sql);
	}
	
	public static ResultSet controlUniqueRequest(Statement stmt, int idV, String username) throws SQLException{
		String sql = String.format("SELECT Partecipante FROM `partecipante` WHERE Partecipante = '%s' and Viaggi_gruppo_idV = '%d' UNION ALL SELECT Sender FROM `jointravel` WHERE Sender = '%s' and idViaggioG = '%d'", username, idV, username, idV);
        return stmt.executeQuery(sql);
	}

	public static ResultSet selectAllTravelsDone(Statement stmt, String username) throws SQLException {
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);		
		Date today = new Date();
		String sql = SELECT_VCREATOR + username + AND + DATAV_LESS + dateFormat.format(today) + BOOKED;
		return stmt.executeQuery(sql);
	}
	
	public static ResultSet selectUpcomingTravels(Statement stmt, String username) throws SQLException {
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);		
		Date today = new Date();
		String sql = SELECT_VCREATOR + username + AND + DATAV_GREATER + dateFormat.format(today) + "' and Prenotato = 0;";
		return stmt.executeQuery(sql);
	}
	
	public static ResultSet selectUpcomingBookedTravels(Statement stmt, String username) throws SQLException {
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);		
		Date today = new Date();
		String sql = SELECT_VCREATOR + username + AND + DATAV_GREATER + dateFormat.format(today) + BOOKED;
		return stmt.executeQuery(sql);
	}
	
	public static ResultSet selectAllGrTravelsProfile(Statement stmt, String username) throws SQLException {
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);		
		Date today = new Date();
		String sql = "SELECT distinct idV, Creatore, NumMaxUt, DataV, DataFineV, Destinazione, Descrizione, HotelName, HotelLink, PostiOccupati, NomeViaggio, Breakfast, Stars, NumRooms, Price FROM Viaggi_gruppo left join Partecipante on (Viaggi_gruppo.idV = Partecipante.Viaggi_gruppo_idV) WHERE (Creatore = '" + username + OR_PARTECIPANT + username + "')" + " AND" + DATAV_LESS + dateFormat.format(today) + "'" + " AND Prenotato = 1;"; 
		return stmt.executeQuery(sql);
	}
	
	public static ResultSet selectUpcomingGrTravels(Statement stmt, String username) throws SQLException {
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);		
		Date today = new Date();
		String sql = "SELECT distinct * FROM Viaggi_gruppo left join Partecipante on (Viaggi_gruppo.idV = Partecipante.Viaggi_gruppo_idV) WHERE (Creatore = '" + username + OR_PARTECIPANT + username + AND_SQL + DATAV_GREATER+ dateFormat.format(today) + "' and Prenotato = 0;";
		return stmt.executeQuery(sql);
	}
	
	public static ResultSet selectUpcomingBookedGrTravels(Statement stmt, String username) throws SQLException {
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);		
		Date today = new Date();
		String sql = "SELECT distinct idV, Creatore, NumMaxUt, DataV, DataFineV, Destinazione, Descrizione, HotelName, HotelLink, PostiOccupati, NomeViaggio, Breakfast, Stars, NumRooms, Price FROM Viaggi_gruppo left join Partecipante on (Viaggi_gruppo.idV = Partecipante.Viaggi_gruppo_idV) WHERE (Creatore = '" + username + OR_PARTECIPANT + username + AND_SQL + DATAV_GREATER+ dateFormat.format(today) + BOOKED;
		return stmt.executeQuery(sql);
	}
	
	public static ResultSet selectAllGrTravels(Statement stmt, String username) throws SQLException {
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);		
		Date today = new Date();
		String sql = "SELECT * FROM Viaggi_gruppo WHERE Creatore != '" + username + SEATS_AND_DATE + dateFormat.format(today) + BOOKED;
		return stmt.executeQuery(sql);
	}
	
	public static ResultSet getFollowersInfos(Statement stmt, String u) throws SQLException {
		String sql = "SELECT * FROM Users inner join Follow on (Users.Username = Follow.Seguito) WHERE Seguace = '" + u + "';";
        return stmt.executeQuery(sql);
	}
	
	public static ResultSet retrieveFollowersSearched(Statement stmt, String user) throws SQLException {
		String sql = "SELECT * FROM Users where Username != '" + user + "' order by Username;";
        return stmt.executeQuery(sql);
	}		
			
    public static ResultSet selectUserEmail(Statement stmt, String username) throws SQLException {
		String sql = "SELECT email FROM Users WHERE Username = '" + username + "';";
		return stmt.executeQuery(sql);
	}
    
    public static ResultSet findSpecialDestination(Statement stmt, int location, int art, int young, String cont) throws SQLException{
    	
    	ResultSet rs = null;
        
        if(cont.equals("Not defined")) {
        	String sql = "SELECT distinct DestinationName FROM destinations WHERE Sea = '" + location + "' and Art = '" + art + YOUTH + young + "';";
    		rs = stmt.executeQuery(sql);
        } else {
        	String sql = "SELECT distinct DestinationName FROM destinations WHERE Sea = '" + location + "' and Art = '" + art + YOUTH + young + "' and Continent = '" + cont + "';";
    		rs = stmt.executeQuery(sql);
        }

		return rs;
    }

    public static ResultSet retrieveJoinNotification(Statement stmt, String username) throws SQLException{
    	String sql = String.format("SELECT * FROM `Viaggi_Gruppo` inner join `jointravel` on (`Viaggi_Gruppo`.idV = `jointravel`.idViaggioG) WHERE Creatore = '%s';", username);
    	return stmt.executeQuery(sql);
    }
    
	public static ResultSet retrieveFollowNotifications(Statement stmt, String usern) throws SQLException {
		String sql = "SELECT * FROM follownotif WHERE `Receiver` = '"+ usern +"';";
		return stmt.executeQuery(sql);
	}
	
	public static ResultSet retrieveUserPhoto(Statement stmt, String usern) throws SQLException {	
		String sql = "SELECT Photo, Filename FROM users WHERE `Username` = '"+ usern +"';";
		return stmt.executeQuery(sql);
	}
	
	public static ResultSet retrieveTravelPhoto(Statement stmt, int idViaggio) throws SQLException {
		String sql = "SELECT Foto, Filename FROM Foto WHERE `Viaggi_idViaggio` = '"+ idViaggio +"';";
		return stmt.executeQuery(sql);
	}
	
	public static ResultSet retrieveTravelGroupPhoto(Statement stmt, int idViaggioGruppo) throws SQLException {
		String sql = "SELECT Foto, Filename FROM fotovg WHERE `Viaggi_gruppo_idViaggioG` = '"+ idViaggioGruppo +"';";
		return stmt.executeQuery(sql);
	}
	
	public static ResultSet retrieveReplyNotifications(Statement stmt, String username) throws SQLException{
    	String sql = String.format("SELECT * FROM `RepliesNotif` WHERE Receiver = '%s';", username);
    	return stmt.executeQuery(sql);
    }

    public static ResultSet selectTravelSlots(Statement stmt, int idV) throws SQLException{
    	String sql = String.format("SELECT NumMaxUt, PostiOccupati FROM `Viaggi_gruppo` WHERE idV = '%d';", idV);
    	return stmt.executeQuery(sql);
    }
    
    public static ResultSet selectNotAvailableTravelSlots(Statement stmt, int idV) throws SQLException{
    	String sql = String.format("SELECT PostiOccupati FROM `Viaggi_gruppo` WHERE idV = '%d';", idV);
    	return stmt.executeQuery(sql);
    }

    public static ResultSet loginChecker(Statement stmt, String username) throws SQLException {
	    String sql = String.format("SELECT Username, Password FROM `Users` WHERE Username = '%s';", username);
    	return stmt.executeQuery(sql); 
    }
    
    public static ResultSet retrieveFollower(Statement stmt, User follower, User followed) throws SQLException {
	    String sql = String.format("SELECT * FROM `Follow` WHERE Seguace = '%s' AND Seguito = '%s';", follower.getUsername(), followed.getUsername());
    	return stmt.executeQuery(sql); 
    }
    
    public static ResultSet retrieveSpecialTravels(Statement stmt, String username, int sea, int art, int young, String cont) throws SQLException {
    	DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);		
		Date today = new Date();    	
		ResultSet rs = null;
        if(cont.equals("Not defined")) {
    		String sql = "SELECT * FROM Viaggi_gruppo as V JOIN destinations as D ON V.Destinazione = D.DestinationName WHERE Creatore != '" + username + SEATS_AND_DATE + dateFormat.format(today) + "' and Prenotato = 1 and Sea = '" + sea + "' and Art ='" + art + YOUTH + young + "';";
    		rs = stmt.executeQuery(sql);
        } else {
    		String sql = "SELECT * FROM Viaggi_gruppo as V JOIN destinations as D ON V.Destinazione = D.DestinationName WHERE Creatore != '" + username + SEATS_AND_DATE + dateFormat.format(today) + "' and Prenotato = 1 and Sea = '" + sea + "' and Art ='" + art + YOUTH + young + "' and Continent = '" + cont + "';";
    		rs = stmt.executeQuery(sql);
        }
        return rs;
	}
    
}
