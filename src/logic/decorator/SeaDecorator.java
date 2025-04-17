package logic.decorator;

public class SeaDecorator extends Decorator {

	private String sea = "-SEA";
	
	public SeaDecorator(GeneralFilter generalFilter) {
		super(generalFilter);
	}

	public String applyFilterSea(String input) {
		return input + sea;
	}
	
	@Override
	public String getFilters() {
		String preliminaryResults = super.getFilters();
		preliminaryResults = this.applyFilterSea(preliminaryResults);
		return preliminaryResults;
	}
	
}
