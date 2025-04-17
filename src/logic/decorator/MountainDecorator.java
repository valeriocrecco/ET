package logic.decorator;

public class MountainDecorator extends Decorator {

	private String mountain = "-MOUNTAIN";
	
	public MountainDecorator(GeneralFilter generalFilter) {
		super(generalFilter);
	}
	
	protected String applyFilterMountain(String input){
		return input + this.mountain;
	}
	
	@Override
	public String getFilters() {
		String preliminaryResults = super.getFilters();
		preliminaryResults = this.applyFilterMountain(preliminaryResults);
		return preliminaryResults;
	}
    
}
