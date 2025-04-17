package logic.decorator;



public class ContinentDecorator extends Decorator{

	private String continentName;
	
	public ContinentDecorator(GeneralFilter filter){
		super(filter);
		this.continentName = "";
    }
		
	protected String applyFilterContinent(String input){
		return input + "-" + this.continentName;
	}
	
	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}
	
	@Override
	public String getFilters() {
		String preliminaryResults = super.getFilters();
		preliminaryResults = this.applyFilterContinent(preliminaryResults);
		return preliminaryResults;
	}

}
