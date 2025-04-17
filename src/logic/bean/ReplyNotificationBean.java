package logic.bean;

public class ReplyNotificationBean {
	
	private int idReply;
	private String msgReply;
	private String dateReply;
	private String senderReply;
	
	public ReplyNotificationBean() {
		
	}
	
	public ReplyNotificationBean(int id, String sender, String msg, String date) {
		this.msgReply = msg;
		this.dateReply = date;
		this.idReply = id;
		this.senderReply = sender;
	}

	public String getMsgReply() {
		return msgReply;
	}

	public void setMsgReply(String msg) {
		this.msgReply = msg;
	}

	public String getDateReply() {
		return dateReply;
	}

	public void setDateReply(String date) {
		this.dateReply = date;
	}

	public int getIdReply() {
		return idReply;
	}

	public void setIdReply(int id) {
		this.idReply = id;
	}

	public String getSenderReply() {
		return senderReply;
	}

	public void setSenderReply(String sender) {
		this.senderReply = sender;
	}

}
