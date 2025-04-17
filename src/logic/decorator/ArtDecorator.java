package logic.decorator;



public class ArtDecorator extends Decorator {
	
	private String art = "-ART";
	
	public ArtDecorator(GeneralFilter generalFilter) {
		super(generalFilter);
	}
	
	protected String applyFilterArt(String input){
		return input + this.art;
	}
	
	@Override
	public String getFilters() {
		String preliminaryResults = super.getFilters();
		preliminaryResults = this.applyFilterArt(preliminaryResults);
		return preliminaryResults;
	}
	
}
