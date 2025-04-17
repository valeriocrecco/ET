package logic.decorator;

public class Filter implements GeneralFilter {
	
	private String flt;
	
	public Filter(String flt) {
		this.setFlt(flt);
	}
	
	public void setFlt(String flt) {
		this.flt = flt;
	}
	
	public String getFlt() {
		return this.flt;
	}
	
	@Override
	public String getFilters() {
		return this.flt;
	}
	
}
