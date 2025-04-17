package logic.queries;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import logic.model.User;
import logic.model.PrivateTravel;
import logic.model.PublicTravel;

public class CRUDQueries {
	
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	
	private CRUDQueries() {
		
	}

	public static int inserisciNuovoUtente(Statement stmt, User u) throws SQLException {		
		String insertStatement = String.format("INSERT INTO `Users` (`Cognome`, `Nome`, `Username`, `Password`, `email`) VALUES('%s' , '%s', '%s', '%s', '%s')", u.getSecondName(), u.getFirstName(), u.getUsername(), u.getPassword(), u.getEmail());
		return stmt.executeUpdate(insertStatement);
	}
	
	public static int modifPassword(Statement stmt, String usr, String newPass) throws SQLException {
		String updateStatement = String.format("UPDATE  Users set Password='%s' WHERE Username = '%s'", newPass, usr);
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int modifEmail(Statement stmt, String usr, String newEmail) throws SQLException {
		String updateStatement = String.format("UPDATE  Users set email='%s' WHERE Username = '%s'", newEmail, usr);
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int modifUsername(Statement stmt, String usr, String nu) throws SQLException {
		
		String updateStatement = String.format("UPDATE  Users set Username = '%s' WHERE Username = '%s'", nu, usr);
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int modifyPhoto(Connection conn, String usr, File file, String filename) throws SQLException {
		String query = "update users set Photo = ?, Filename = ? WHERE Username = ?";
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		
		try (
			FileInputStream fis = new FileInputStream(file);){	
			preparedStmt.setBinaryStream(1, fis);
			preparedStmt.setString(2, filename);
			preparedStmt.setString(3, usr);
			return preparedStmt.executeUpdate();
		} catch (IOException e) {
			return 0;
		} finally {
			preparedStmt.close();
		}
	}
	
	public static int addTravelPhoto(Connection conn, int idViaggio, File file, String filename) throws SQLException {
		
		String query = "INSERT INTO `Foto` (`Viaggi_idViaggio`, `Foto`, `Filename`) VALUES(?, ?, ?)";
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		
		try (
			FileInputStream fis = new FileInputStream(file);){			
		    preparedStmt.setInt(1, idViaggio);
			preparedStmt.setBinaryStream(2, fis);
			preparedStmt.setString(3, filename);
			return preparedStmt.executeUpdate();
		} catch (IOException e1) {
			return 0;
		} finally {
			preparedStmt.close();
		}		
		       
	}
	
	public static int addTravelGroupPhoto(Connection conn, int idViaggioGruppo, File file, String filename) throws SQLException {
		
		String query = "INSERT INTO `fotovg` (`Viaggi_gruppo_idViaggioG`, `Foto`, `Filename`) VALUES(?, ?, ?)";
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		
		try (
			FileInputStream fis = new FileInputStream(file);){
			preparedStmt.setInt(1, idViaggioGruppo);
			preparedStmt.setBinaryStream(2, fis);
			preparedStmt.setString(3, filename);
			return preparedStmt.executeUpdate();
		} catch (IOException e1) {
			return 0;
		} finally {
			preparedStmt.close();
		}
		    
	}
	
	public static int modifTravelUsername(Statement stmt, String nu, String usr) throws SQLException {
		String updateStatement = String.format("UPDATE  Viaggi set Creatore = '%s'  WHERE Creatore = '%s'", nu, usr);
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int modifTravelGRUsername(Statement stmt, String nu, String usr) throws SQLException {
		String updateStatement = String.format("UPDATE  Viaggi_gruppo set Creatore = '%s'  WHERE Creatore = '%s'", nu, usr);
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int modifFollowerUsername(Statement stmt, String nu, String usr) throws SQLException {
		String updateStatement = String.format("UPDATE Follow set Seguito = '%s'  WHERE Seguito = '%s'", nu, usr);
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int modifReplySenderUsername(Statement stmt, String nu, String usr) throws SQLException {
		String updateStatement = String.format("UPDATE repliesnotif set Sender = '%s'  WHERE Sender = '%s'", nu, usr);
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int modifReplyReceiverUsername(Statement stmt, String nu, String usr) throws SQLException {
		String updateStatement = String.format("UPDATE repliesnotif set Receiver = '%s'  WHERE Receiver = '%s'", nu, usr);
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int modifFollowedUsername(Statement stmt, String nu, String usr) throws SQLException {
		String updateStatement = String.format("UPDATE  Follow set Seguace = '%s'  WHERE Seguace = '%s'", nu, usr);
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int modifyFollowNotifSender(Statement stmt, String nu, String usr) throws SQLException {
		String updateStatement = String.format("UPDATE follownotif set Sender = '%s'  WHERE Sender = '%s'", nu, usr);
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int modifyJoinSender(Statement stmt, String nu, String usr) throws SQLException {
		String updateStatement = String.format("UPDATE jointravel set Sender = '%s'  WHERE Sender = '%s'", nu, usr);
        return stmt.executeUpdate(updateStatement);
	}
	
	
	public static int modifyFollowNotifReceiver(Statement stmt, String nu, String usr) throws SQLException {
		String updateStatement = String.format("UPDATE follownotif set Receiver = '%s'  WHERE Receiver = '%s'", nu, usr);
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int modifParticipantUsername(Statement stmt, String nu, String usr) throws SQLException {
		String updateStatement = String.format("UPDATE Partecipante set Partecipante = '%s'  WHERE Partecipante = '%s'", nu, usr);
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int saveTravel(Statement stmt, PrivateTravel v) throws SQLException {
		String updateStatement = String.format("INSERT INTO `Viaggi` (`Creatore`, `Destinazione`, `Descrizione`, `NomeViaggio`, `DataV`, `DataFineV`, `HotelName`, `HotelLink`, `Breakfast`, `Stars`, `NumRooms`, `Price`, `NumTrav`) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%d')", v.getCreator().getUsername(), v.getDestination().getDestinationName(), v.getDescription(), v.getTravelName(), v.getStartDate(), v.getEndDate(), v.getHotelInfo().getHotelName(), v.getHotelInfo().getHotelLink(), v.getHotelInfo().getBreakfast(), v.getHotelInfo().getStars(), v.getHotelInfo().getNumRooms(), v.getHotelInfo().getPrice(), v.getNumMaxUt());
		return stmt.executeUpdate(updateStatement);
	}
	
	public static int saveGroupTravel(Statement stmt, PublicTravel vgr) throws SQLException {
		String updateStatement = String.format("INSERT INTO `Viaggi_gruppo` (`Creatore`, `NumMaxUt`, `DataV`, `DataFineV`, `Descrizione`, `HotelName`, `HotelLink`, `Destinazione`, `NomeViaggio`, `Breakfast`, `Stars`, `NumRooms`, `Price`) VALUES ('%s', '%d', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", vgr.getCreator().getUsername(), vgr.getNumMaxUt(), vgr.getStartDate(), vgr.getEndDate(), vgr.getDescription(), vgr.getHotelInfo().getHotelName(), vgr.getHotelInfo().getHotelLink(), vgr.getDestination().getDestinationName(), vgr.getTravelName(), vgr.getHotelInfo().getBreakfast(), vgr.getHotelInfo().getStars(), vgr.getHotelInfo().getNumRooms(), vgr.getHotelInfo().getPrice());
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int bookTravel(Statement stmt, int idV) throws SQLException {
		String updateStatement = String.format("UPDATE  Viaggi set Prenotato = '1' WHERE idV = '%d'", idV);
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int bookGroupTravel(Statement stmt, int idV) throws SQLException {
		String updateStatement = String.format("UPDATE Viaggi_gruppo set Prenotato = '1' WHERE idV = '%d'", idV);
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int bookAndSaveTravel(Statement stmt, PrivateTravel v) throws SQLException {
		String updateStatement = String.format("INSERT INTO `Viaggi` (`Creatore`, `Destinazione`, `Descrizione`, `NomeViaggio`, `DataV`, `DataFineV`, `HotelName`, `HotelLink`, `Breakfast`, `Stars`, `NumRooms`, `Price`, `Prenotato`, `NumTrav`) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '1', '%d')", v.getCreator().getUsername(), v.getDestination().getDestinationName(), v.getDescription(), v.getTravelName(), v.getStartDate(), v.getEndDate(), v.getHotelInfo().getHotelName(), v.getHotelInfo().getHotelLink(), v.getHotelInfo().getBreakfast(), v.getHotelInfo().getStars(), v.getHotelInfo().getNumRooms(), v.getHotelInfo().getPrice(), v.getNumMaxUt());
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int bookAndSaveGroupTravel(Statement stmt, PublicTravel vgr) throws SQLException {
		String updateStatement = String.format("INSERT INTO `Viaggi_gruppo` (`Creatore`, `NumMaxUt`, `DataV`, `DataFineV`, `Descrizione`, `HotelName`, `HotelLink`, `Destinazione`, `NomeViaggio`, `Breakfast`, `Stars`, `NumRooms`, `Price`, `Prenotato`) VALUES ('%s', '%d', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%d', '%d', '%s', '1')", vgr.getCreator().getUsername(), vgr.getNumMaxUt(), vgr.getStartDate(), vgr.getEndDate(), vgr.getDescription(), vgr.getHotelInfo().getHotelName(), vgr.getHotelInfo().getHotelLink(), vgr.getDestination().getDestinationName(), vgr.getTravelName(), vgr.getHotelInfo().getBreakfast(), vgr.getHotelInfo().getStars(), vgr.getHotelInfo().getNumRooms(), vgr.getHotelInfo().getPrice());
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int addAcceptedFollow(Statement stmt, String f1, String f2) throws SQLException {	
		String updateStatement = String.format("INSERT INTO `Follow` (`Seguito`, `Seguace`) VALUES ('%s', '%s')", f1, f2);
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int sendRequestFollowed(Statement stmt, String sender, String receiver, String message) throws SQLException {
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		Date today = new Date();
		String updateStatement = String.format("INSERT INTO `follownotif` (`Sender`, `Receiver`, `Date`, `Message`) VALUES ('%s', '%s', '%s', '%s')", sender, receiver, dateFormat.format(today), message);
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int addFollowed(Statement stmt, String follower, String followed) throws SQLException {
		String updateStatement = String.format("INSERT INTO `Follow` (`Seguito`, `Seguace`) VALUES ('%s', '%s')", followed, follower);
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int delFollower(Statement stmt, String f1, String f2) throws SQLException {
		String updateStatement = String.format("DELETE FROM `Follow` WHERE Seguito = '%s' and Seguace = '%s'", f2, f1);
        return stmt.executeUpdate(updateStatement);	
	}
	
	public static int joinGrTravel(Statement stmt, String username, int idViaggio, String message) throws SQLException {
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		Date today = new Date();
		String updateStatement = String.format("INSERT INTO `JoinTravel` (`Sender`, `idViaggioG`, `Message`, `Date`) VALUES ('%s', %d, '%s', '%s')", username, idViaggio, message, dateFormat.format(today));
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int acceptJoinGrTravel(Statement stmt, String username, int idViaggio) throws SQLException {
        String updateStatement = String.format("UPDATE `JoinTravel`, `Viaggi_gruppo`  SET JoinTravel.Accettato = 1, Viaggi_gruppo.PostiOccupati = Viaggi_gruppo.PostiOccupati+1 WHERE JoinTravel.idViaggioG = %d and Utente = '%s' and Viaggi_gruppo.idV = '%s'", idViaggio, username, idViaggio);
        return stmt.executeUpdate(updateStatement);  
	}
	
	public static int deleteTravel(Statement stmt, int idV) throws SQLException {
		String updateStatement = String.format("DELETE FROM `Viaggi` WHERE idV = %d", idV);
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int deleteTravelGr(Statement stmt, int idV) throws SQLException {
		String updateStatement = String.format("DELETE FROM `Viaggi_gruppo` WHERE idV = %d", idV);
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int setFollowNotifRead(Statement stmt, String seg, String u) throws SQLException {
		String updateStatement = String.format("UPDATE  Follow SET Letto = 1 WHERE Seguace = '%s' and Seguito = '%s'", seg, u);
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int setJoinNotifRead(Statement stmt, String user, int idV) throws SQLException {
		String updateStatement = String.format("UPDATE jointravel SET Letto = 1 WHERE Utente = '%s' and idViaggioG = '%s'", user, idV);
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int setReplyNotifRead(Statement stmt, int idNotif) throws SQLException {
		
		String updateStatement = String.format("UPDATE RepliesNotif SET Letto = 1 WHERE idRepliesNotif = '%d'", idNotif);
        return stmt.executeUpdate(updateStatement);
	}
	
	public static int sendNotificationReply(Statement stmt, String sender, String receiver, String msg) throws SQLException{
		String insertStatement;
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		Date today = new Date();
		insertStatement = String.format("INSERT INTO RepliesNotif (`Sender`, `Receiver`, `Message`, `Date`) VALUES ('%s', '%s', '%s', '%s')", sender, receiver, msg, dateFormat.format(today));
		return stmt.executeUpdate(insertStatement);
	}
	
	public static int insertOneParticipant(Statement stmt, int idV, String username) throws SQLException {
		String insertStatement;
		insertStatement = String.format("INSERT INTO Partecipante (`Partecipante`, `Viaggi_gruppo_idV`) VALUES ('%s', '%d')", username, idV);
		return stmt.executeUpdate(insertStatement);
	}
	 
	public static int decreaseAvailableSlots(Statement stmt, int idV, int slots) throws SQLException {
		String insertStatement;
		insertStatement = String.format("UPDATE Viaggi_gruppo SET PostiOccupati = '%d' WHERE Viaggi_gruppo.idV = '%d'", slots, idV);
		return stmt.executeUpdate(insertStatement);
	}
	
	public static int deleteJoinNotification(Statement stmt, int idNotif) throws SQLException {
		String updateStatement = String.format("DELETE FROM `jointravel` WHERE id = '%d'", idNotif);
        return stmt.executeUpdate(updateStatement);	
	}
	
	public static int deleteFollowNotification(Statement stmt, int idNotif) throws SQLException {
		String updateStatement = String.format("DELETE FROM `follownotif` WHERE id = '%d'", idNotif);
        return stmt.executeUpdate(updateStatement);	
	}
	
	public static int deleteReplyNotification(Statement stmt, int idNotif) throws SQLException {
		String updateStatement = String.format("DELETE FROM `repliesnotif` WHERE id = '%d'", idNotif);
        return stmt.executeUpdate(updateStatement);	
	}
	
}
