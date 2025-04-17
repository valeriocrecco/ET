package logic.model;

public class FollowNotification {
	
	private int idFollow;
	private String msgFollow;
	private String dateFollow;
	private User senderFollow;
	private User receiverFollow;
	
	public FollowNotification() {}

	public FollowNotification(User sender, User receiver, String msg) {
		this.senderFollow = sender;
		this.receiverFollow = receiver;
		this.msgFollow = msg;
	}
	
	public void setMsgFollow(String u) {
		this.msgFollow = u;
	}
	
	public String getMsgFollow() {
		return this.msgFollow;
	}
	
	public void setDateFollow(String d) {
		this.dateFollow = d;
	}
	
	public String getDateFollow() {
		return this.dateFollow;
	}

	public int getIdFollow() {
		return idFollow;
	}

	public void setIdFollow(int id) {
		this.idFollow = id;
	}

	public User getSenderFollow() {
		return senderFollow;
	}

	public void setSenderFollow(User sender) {
		this.senderFollow = sender;
	}

	public User getReceiverFollow() {
		return receiverFollow;
	}

	public void setReceiverFollow(User receiver) {
		this.receiverFollow = receiver;
	}
	
}

