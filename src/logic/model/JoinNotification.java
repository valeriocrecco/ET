package logic.model;

public class JoinNotification {
	
	private int idJoin;
	private User senderJoin;
	private User receiverJoin;
	private PublicTravel travelJoin;
	private String dateJoin;
	private String msgJoin;

	public JoinNotification() {}
	
	public JoinNotification(String username, String travelName, int idTravel, String date) {
		this.receiverJoin.setUsername(username);
		this.travelJoin.setIdTravel(idTravel);
		this.travelJoin.setTravelName(travelName);
		this.dateJoin = date;
	}	
	
	public User getReceiverJoin() {
		return receiverJoin;
	}

	public void setReceiverJoin(User receiver) {
		this.receiverJoin = receiver;
	}
	
	public void setTravelJoin(PublicTravel viaggioGruppo) {
		this.travelJoin = viaggioGruppo;
	}
	
	public PublicTravel getTravelJoin() {
		return this.travelJoin;
	}
	
	public void setMsgJoin(String msg) {
		this.msgJoin = msg;
	}
	
	public String getMsgJoin() {
		return this.msgJoin;
	}
	
	public void setDateJoin(String d) {
		this.dateJoin = d;
	}
	
	public String getDateJoin() {
		return this.dateJoin;
	}

	public int getIdJoin() {
		return idJoin;
	}

	public void setIdJoin(int id) {
		this.idJoin = id;
	}

	public User getSenderJoin() {
		return senderJoin;
	}

	public void setSenderJoin(User sender) {
		this.senderJoin = sender;
	}
	
}

