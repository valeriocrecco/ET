package logic.decorator;

public abstract class Decorator implements GeneralFilter {
	
	private GeneralFilter generalFilter;
	
	protected Decorator(GeneralFilter generalFilter) {
		this.generalFilter = generalFilter;
	}
	
	@Override
	public String getFilters() {
		return this.generalFilter.getFilters();
	}
	
}
