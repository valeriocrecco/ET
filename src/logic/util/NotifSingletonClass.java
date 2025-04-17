package logic.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.bean.FollowNotificationBean;
import logic.bean.JoinNotificationBean;
import logic.bean.PublicTravelBean;
import logic.bean.ReplyNotificationBean;
import logic.dao.FollowNotificationDao;
import logic.dao.JoinNotificationDao;
import logic.dao.ReplyNotificationDao;
import logic.exceptions.SystemException;
import logic.model.FollowNotification;
import logic.model.JoinNotification;
import logic.model.ReplyNotification;

/* In qualsiasi punto dell'applicazione potremmo ricevere una notifica, per questo utilizzo una classe
 * Singleton per avere un'unica lista condivisa di notifiche, raggiungibile da qualsiasi punto */
public class NotifSingletonClass {
	
	private static final String SYSTEM_ERROR = "Unexpected application error occurred... retry!";
	
	private static NotifSingletonClass notifSingletonInstance = null;
	private List<JoinNotificationBean> joinNotifList = null;
	private List<FollowNotificationBean> followNotifList = null;
	private List<ReplyNotificationBean> replyNotifList = null;
	
	/* Protected per permettere alle classi "figlie" di bypassare il Singleton e creare più istanze */
	protected NotifSingletonClass() {
		this.joinNotifList = new ArrayList<>();
		this.followNotifList = new ArrayList<>();
		this.replyNotifList = new ArrayList<>();
	}
	
	public static synchronized NotifSingletonClass getNotifSingletonInstance() {
		if(notifSingletonInstance == null) {
			notifSingletonInstance = new NotifSingletonClass();
		}
		return notifSingletonInstance;
	}
	
	private JoinNotificationBean joinToJoinBeanNotification(JoinNotification joinNotification) {
		// JoiNotification to JoinNotificationBean
		PublicTravelBean viaggioGruppoBean = new PublicTravelBean();
		viaggioGruppoBean.setIdTravelBean(String.valueOf(joinNotification.getTravelJoin().getIdTravel()));
		viaggioGruppoBean.setTravelNameBean(joinNotification.getTravelJoin().getTravelName());
		return new JoinNotificationBean(joinNotification.getIdJoin(), joinNotification.getSenderJoin().getUsername(), viaggioGruppoBean, joinNotification.getMsgJoin(), joinNotification.getDateJoin());
	}
	
	public List<JoinNotificationBean> getJoinNotifList(String username) {
		List<JoinNotification> joinNotifications = new ArrayList<>();
		this.joinNotifList.clear();
		try {
			joinNotifications = JoinNotificationDao.retrieveJoinNotifications(username);
			
			for(JoinNotification joinNotification : joinNotifications) {
				JoinNotificationBean joinNotificationBean = this.joinToJoinBeanNotification(joinNotification);
				this.joinNotifList.add(joinNotificationBean);
			}
		} catch (SystemException e) {
			Logger logger = Logger.getLogger(NotifSingletonClass.class.getName());
        	logger.log(Level.WARNING, SYSTEM_ERROR);
		}	
		return this.joinNotifList;
	}

	private FollowNotificationBean followToFollowBeanNotification(FollowNotification followNotification) {
		// FollowNotification to FollowNotificationBean
		return new FollowNotificationBean(followNotification.getIdFollow(), followNotification.getSenderFollow().getUsername(), followNotification.getMsgFollow(), followNotification.getDateFollow());
	}
	
	public List<FollowNotificationBean> getFollowNotifList(String username) {
		List<FollowNotification> followNotifications = new ArrayList<>();
		this.followNotifList.clear();
		try {
			followNotifications = FollowNotificationDao.retrieveFollowNotifications(username);
			for(FollowNotification followNotification : followNotifications) {
				FollowNotificationBean followNotificationBean = this.followToFollowBeanNotification(followNotification);
				this.followNotifList.add(followNotificationBean);
			}
		} catch (SystemException e) {
			Logger logger = Logger.getLogger(NotifSingletonClass.class.getName());
        	logger.log(Level.WARNING, SYSTEM_ERROR);
		}
		return this.followNotifList;
	}

	private ReplyNotificationBean replyToReplyBeanNotification(ReplyNotification replyNotification) {
		// ReplyNotification to ReplyNotificationBean
		return new ReplyNotificationBean(replyNotification.getIdReply(), replyNotification.getSenderReply().getUsername(), replyNotification.getMsgReply(), replyNotification.getDateReply());
	}
	
	public List<ReplyNotificationBean> getReplyNotifList(String username) {
		List<ReplyNotification> replyNotifications = new ArrayList<>();
		this.replyNotifList.clear();
		try {
			replyNotifications = ReplyNotificationDao.retrieveReplyNotifications(username);
			for(ReplyNotification replyNotification : replyNotifications) {
				ReplyNotificationBean replyNotificationBean = this.replyToReplyBeanNotification(replyNotification);
				this.replyNotifList.add(replyNotificationBean);
			}
		} catch (SystemException e) {
			Logger logger = Logger.getLogger(NotifSingletonClass.class.getName());
        	logger.log(Level.WARNING, SYSTEM_ERROR);
		}
		return this.replyNotifList;
	}
	
	public boolean getNotifications(String username) {
		return (this.getJoinNotifList(username).isEmpty() && this.getFollowNotifList(username).isEmpty() && this.getReplyNotifList(username).isEmpty());
	}
	
	public void removeJoinNotification(JoinNotificationBean joinNotificationBean) {
		this.joinNotifList.remove(joinNotificationBean);
	}
	
	public void removeFollowNotification(FollowNotificationBean followNotificationBean) {
		this.followNotifList.remove(followNotificationBean);
	}
	
	public void removeReplyNotification(ReplyNotificationBean replyNotificationBean) {
		this.replyNotifList.remove(replyNotificationBean);
	}
	
	protected void resetNotifications() {
		this.joinNotifList.clear();
		this.followNotifList.clear();
		this.replyNotifList.clear();
	}
	
}


