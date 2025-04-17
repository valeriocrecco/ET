package logic.bean;

public class FollowNotificationBean {

	private int idFollow;
	private String msgFollow;
	private String dateFollow;
	private String senderFollow;
	
	public FollowNotificationBean() {}
	
	public FollowNotificationBean(int id, String sender, String msg, String date) {
		this.idFollow = id;
		this.msgFollow = msg;
		this.dateFollow = date;
		this.senderFollow = sender;
	}

	public String getMsgFollow() {
		return msgFollow;
	}

	public void setMsgFollow(String msg) {
		this.msgFollow = msg;
	}

	public String getDateFollow() {
		return dateFollow;
	}

	public void setDateFollow(String date) {
		this.dateFollow = date;
	}

	public int getIdFollow() {
		return idFollow;
	}

	public void setIdFollow(int id) {
		this.idFollow = id;
	}

	public String getSenderFollow() {
		return senderFollow;
	}

	public void setSenderFollow(String sender) {
		this.senderFollow = sender;
	}
	
}
