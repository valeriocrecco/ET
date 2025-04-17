package logic.controllers;

import java.util.List;
import logic.bean.FollowNotificationBean;
import logic.bean.JoinNotificationBean;
import logic.bean.ReplyNotificationBean;
import logic.dao.UserDao;
import logic.dao.FollowNotificationDao;
import logic.dao.JoinNotificationDao;
import logic.dao.PublicTravelDao;
import logic.dao.ReplyNotificationDao;
import logic.exceptions.SeatsNotAvailableException;
import logic.exceptions.SystemException;
import logic.model.ReplyNotification;
import logic.model.User;
import logic.util.NotifSingletonClass;

public class NotifyController {

	public List<JoinNotificationBean> retrieveJoinNotifications(String username) {
		return NotifSingletonClass.getNotifSingletonInstance().getJoinNotifList(username);
	}
	
	public List<FollowNotificationBean> retrieveFollowNotifications(String username) {
		return NotifSingletonClass.getNotifSingletonInstance().getFollowNotifList(username);
	}
	
	public List<ReplyNotificationBean> retrieveReplyNotifications(String username) {
		return NotifSingletonClass.getNotifSingletonInstance().getReplyNotifList(username);
	}

	public void acceptJoinNotification(String username, JoinNotificationBean joinNotificationBean) throws SystemException, SeatsNotAvailableException {
		
		int idTravel = Integer.parseInt(joinNotificationBean.getViaggioGruppoBeanJoin().getIdTravelBean());
		int idNotif = joinNotificationBean.getIdJoin();
		String travelname = joinNotificationBean.getViaggioGruppoBeanJoin().getTravelNameBean();
		String participant = joinNotificationBean.getSenderJoin();
		
		/* Controllo se i posti sono ancora disponibili */
		int slots = PublicTravelDao.retrieveTravelSlots(idTravel);
		int slotsNotAvailable = PublicTravelDao.retrieveNotAvailableSeats(idTravel);
		if(slots == 0) {
			/* Invio notifica di rifiuto/risposta */
			User sender = new User();
			sender.setUsername(username);
			User receiver = new User();
			receiver.setUsername(participant);
			String msg = username + " ha rifiutato la tua richiesta di join al viaggio " + travelname;
			ReplyNotification replyNotification = new ReplyNotification(sender, receiver, msg);
			ReplyNotificationDao.sendReplyNotification(replyNotification);
			
			/* Cancellazione notifica di join dal database */
			JoinNotificationDao.deleteJoinNotification(idNotif);
			NotifSingletonClass.getNotifSingletonInstance().removeJoinNotification(joinNotificationBean);
			throw new SeatsNotAvailableException("Seats not available, the request has been declined automatically!");
		}
		else {
			/* Aggiunta di un nuovo partecipante, update dei posti disponibili e invio notifica al nuovo partecipante */
			PublicTravelDao.insertOneParticipant(idTravel, participant);
			PublicTravelDao.decreaseAvailableSlots(idTravel, slotsNotAvailable+1);
			
			/* Invio notifica di conferma/risposta */
			User sender = new User();
			sender.setUsername(username);
			User receiver = new User();
			receiver.setUsername(participant);
			String msg = username + " ha accettato la tua richiesta e ti ha aggiunto al viaggio " + travelname + " :)";
			ReplyNotification replyNotification = new ReplyNotification(sender, receiver, msg);
			ReplyNotificationDao.sendReplyNotification(replyNotification);
			
			/* Cancellazione notifica di join dal database */
			JoinNotificationDao.deleteJoinNotification(idNotif);
			NotifSingletonClass.getNotifSingletonInstance().removeJoinNotification(joinNotificationBean);
		}
	}
	
	public void acceptFollowNotification(String followed, FollowNotificationBean followNotificationBean) throws SystemException {
		
		int idNotif = followNotificationBean.getIdFollow();
		String follower = followNotificationBean.getSenderFollow();
		
		User userFollower = new User();
		userFollower.setUsername(followed);
		User userFollowed = new User();
		userFollowed.setUsername(follower);
		
		/* Segui anche tu il follower */
		UserDao.addFollowed(userFollower, userFollowed);
		
		/* Invio notifica di conferma/risposta */
		String msg = followed + " ha accettato di seguirti :)";
		ReplyNotification replyNotification = new ReplyNotification(userFollower, userFollowed, msg);
		ReplyNotificationDao.sendReplyNotification(replyNotification);
		
		/* Cancellazione notifica di join dal database */
		FollowNotificationDao.deleteFollowNotification(idNotif);
		NotifSingletonClass.getNotifSingletonInstance().removeFollowNotification(followNotificationBean);
		
	}
	
	public void declineJoinNotification(String username, JoinNotificationBean joinNotificationBean) throws SystemException {
		
		int idNotif = joinNotificationBean.getIdJoin();
		String travelname = joinNotificationBean.getViaggioGruppoBeanJoin().getTravelNameBean();
		String participant = joinNotificationBean.getSenderJoin();
		
		/* Invio notifica di conferma/risposta */
		User sender = new User();
		sender.setUsername(username);
		User receiver = new User();
		receiver.setUsername(participant);
		String msg = username + " ha rifiutato la tua richiesta per il viaggio: " + travelname + " :(";
		ReplyNotification replyNotification = new ReplyNotification(sender, receiver, msg);
		ReplyNotificationDao.sendReplyNotification(replyNotification);
		
		/* Cancellazione notifica di join dal database */
		JoinNotificationDao.deleteJoinNotification(idNotif);
		NotifSingletonClass.getNotifSingletonInstance().removeJoinNotification(joinNotificationBean);
	}
	
	public void declineFollowNotification(String username, FollowNotificationBean followNotificationBean) throws SystemException {
		
		int idNotif = followNotificationBean.getIdFollow();
		String follower = followNotificationBean.getSenderFollow();
		
		/* Invio notifica di conferma/risposta */
		User userFollower = new User();
		userFollower.setUsername(follower);
		User sender = new User();
		sender.setUsername(username);
		String msg = username + " ha rifiutato di seguirti :(";
		ReplyNotification replyNotification = new ReplyNotification(sender, userFollower, msg);
		ReplyNotificationDao.sendReplyNotification(replyNotification);
		
		/* Cancellazione notifica di join dal database */
		FollowNotificationDao.deleteFollowNotification(idNotif);
		NotifSingletonClass.getNotifSingletonInstance().removeFollowNotification(followNotificationBean);
	}
	
	public void deleteNotification(ReplyNotificationBean replyNotificationBean) throws SystemException {
		int idNotif = replyNotificationBean.getIdReply();
		ReplyNotificationDao.deleteReplyNotification(idNotif);
		NotifSingletonClass.getNotifSingletonInstance().removeReplyNotification(replyNotificationBean);
	}
	
}
