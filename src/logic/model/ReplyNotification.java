package logic.model;

public class ReplyNotification {
	
	private int idReply;
	private User receiverReply;
	private String msgReply;
	private String dateReply;
	private User senderReply;
	
	public ReplyNotification() {
		this.idReply = 0;
		this.receiverReply = null;
		this.senderReply = null;
		this.msgReply = "";
		this.dateReply = "";
	}
	
	public ReplyNotification(User sender, User receiver, String msg) {
		this.senderReply = sender;
		this.receiverReply = receiver;
		this.msgReply = msg;
	}
		
	public void setMsgReply(String msg) {
		this.msgReply = msg;
	}
	
	public String getMsgReply() {
		return this.msgReply;
	}
	
	public int getIdReply() {
		return idReply;
	}

	public void setIdReply(int id) {
		this.idReply = id;
	}
	
	public void setDateReply(String date) {
		this.dateReply = date;
	}

	public String getDateReply() {
		return this.dateReply;
	}

	public User getSenderReply() {
		return senderReply;
	}

	public void setSenderReply(User sender) {
		this.senderReply = sender;
	}

	public User getReceiverReply() {
		return receiverReply;
	}

	public void setReceiverReply(User receiver) {
		this.receiverReply = receiver;
	}
		
}
