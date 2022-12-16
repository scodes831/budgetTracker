package budgetTracker;

public interface SubMenu {
	
	public abstract void show(Household household, Budget budget, Menu parentMenu, Menu mainMenu);
	public abstract String showOptions();
	public abstract void processSelection(Household household, Budget budget, Menu parentMenu, Menu mainMenu, String selection);

}
