package budgetTracker;

import java.util.ArrayList;

public abstract class Menu {
	
	private static ArrayList<String> activeMenuList = new ArrayList<String>();
	
	public abstract void show(Household household, Budget budget, Menu mainMenu);
	public abstract int showOptions();
	public abstract void processSelection(Household household, Budget budget, Menu mainMenu, int selection);
	
	public static void getNextMenu() {
		int length = Menu.getActiveMenuList().size();
		String currentMenu = Menu.getActiveMenuList().get(length-1);
		switch (currentMenu) {
		case "main":
			
		}
	}

	public static ArrayList<String> getActiveMenuList() {
		return activeMenuList;
	}

	public static void setActiveMenuList(ArrayList<String> activeMenuList) {
		Menu.activeMenuList = activeMenuList;
	}
}
