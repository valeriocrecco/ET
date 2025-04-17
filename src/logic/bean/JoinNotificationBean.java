package logic.bean;

public class JoinNotificationBean {
	
	private int idJoin;
	private PublicTravelBean viaggioGruppoBeanJoin;
	private String msgJoin;
	private String dateJoin;
	private String senderJoin;
	
	public JoinNotificationBean() {}

	public JoinNotificationBean(int id, String sender, PublicTravelBean viaggioGruppoBean, String msg, String date) {
		this.viaggioGruppoBeanJoin = viaggioGruppoBean;
		this.msgJoin = msg;
		this.dateJoin = date;
		this.idJoin = id;
		this.senderJoin = sender;
	}

	public String getMsgJoin() {
		return msgJoin;
	}

	public void setMsgJoin(String msg) {
		this.msgJoin = msg;
	}

	public String getDateJoin() {
		return dateJoin;
	}

	public void setDateJoin(String date) {
		this.dateJoin = date;
	}

	public int getIdJoin() {
		return idJoin;
	}

	public void setIdJoin(int id) {
		this.idJoin = id;
	}

	public String getSenderJoin() {
		return senderJoin;
	}

	public void setSenderJoin(String sender) {
		this.senderJoin = sender;
	}

	public PublicTravelBean getViaggioGruppoBeanJoin() {
		return viaggioGruppoBeanJoin;
	}

	public void setViaggioGruppoBeanJoin(PublicTravelBean viaggioGruppoBean) {
		this.viaggioGruppoBeanJoin = viaggioGruppoBean;
	}
	
}
