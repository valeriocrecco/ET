package logic.decorator;

public class YoungDecorator extends Decorator{
	
	private String young = "-YOUNG";
	
	public YoungDecorator(GeneralFilter generalFilter) {
		super(generalFilter);
	}
	
	protected String applyFilterYoung(String input){
		return input + this.young;
	}
	
	@Override
	public String getFilters() {
		String preliminaryResults = super.getFilters();
		preliminaryResults = this.applyFilterYoung(preliminaryResults);
		return preliminaryResults;
	}
    
}
