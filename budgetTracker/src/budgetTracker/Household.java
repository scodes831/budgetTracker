package budgetTracker;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Household {
	private ArrayList<FamilyMember> householdMembers = new ArrayList<FamilyMember>();
	private ArrayList<Purchase> purchasesList = new ArrayList<Purchase>();

	private double income;
	private double expenses;

	private double housingBudget;
	private double housingSpend;

	private double groceryBudget;
	private double grocerySpend;

	private double diningBudget;
	private double diningSpend;

	private double carBudget;
	private double carSpend;

	private double utilitiesBudget;
	private double utilitiesSpend;

	private double healthBudget;
	private double healthSpend;

	private double funBudget;
	private double funSpend;

	private double miscBudget;
	private double miscSpend;

	public void addFamilyMembers() {
		System.out.println("Welcome to Budget Tracker! Follow the prompts to get started on your budget.");
		System.out.println("You currently have " + Household.getHouseholdMembers().size() + " family members set up for your household.\nHow many family members do you want to add?");
		Scanner in = new Scanner(System.in);
		int userInputNum = in.nextInt();
		int numFamilyMembers = 0;
		Household.getHouseholdMembers().size();
		do {
			System.out.println("Family Member #" + (numFamilyMembers + 1) + " name: ");
			String name = in.next();
			System.out.println("Family Member #" + (numFamilyMembers + 1) + " monthly income: ");
			double salary = in.nextDouble();
			FamilyMember familyMember = new FamilyMember(name, salary);
			Household.getHouseholdMembers().add(familyMember);
			numFamilyMembers = Household.getHouseholdMembers().size();
			System.out.println("You have added " + familyMember.getName() + " who has a monthly income of $"
					+ familyMember.getSalary());
		} while (userInputNum != numFamilyMembers);
	}

	public void addPurchase(String category, double amount, String purchasedBy) {
		Purchase purchase = new Purchase(category, amount, purchasedBy);
		System.out.println("Your purchase is: " + category + amount);
		purchasesList.add(purchase);
		int purchaseNum = purchasesList.size()-1;
		totalPurchases(purchaseNum);
		ArrayList<FamilyMember> members = Household.getHouseholdMembers();
		for (FamilyMember member : members) {
			if (purchasedBy == member.getName()) {
				member.getMemberPurchases().add(purchase);
			}
		}
	}

	public void totalPurchases(int purchaseNum) {
		double categoryPurchase = 0.0;
		Purchase purchase = purchasesList.get(purchaseNum);
		if (purchase.getCategory() == "housing") {
			categoryPurchase = purchase.getAmount() + getHousingSpend();
			setHousingSpend(categoryPurchase);
		} else if (purchase.getCategory() == "utilities") {
			categoryPurchase = purchase.getAmount() + getUtilitiesSpend();
			setUtilitiesSpend(categoryPurchase);
		} else if (purchase.getCategory() == "health") {
			categoryPurchase = purchase.getAmount() + getHealthSpend();
			setHealthSpend(categoryPurchase);
		} else if (purchase.getCategory() == "car") {
			categoryPurchase = purchase.getAmount() + getCarSpend();
			setCarSpend(categoryPurchase);
		} else if (purchase.getCategory() == "grocery") {
			categoryPurchase = purchase.getAmount() + getGrocerySpend();
			setGrocerySpend(categoryPurchase);
		} else if (purchase.getCategory() == "dining") {
			categoryPurchase = purchase.getAmount() + getDiningSpend();
			setDiningSpend(categoryPurchase);
		} else if (purchase.getCategory() == "fun") {
			categoryPurchase = purchase.getAmount() + getFunSpend();
			setFunSpend(categoryPurchase);
		} else if (purchase.getCategory() == "miscellaneous") {
			categoryPurchase = purchase.getAmount() + getMiscSpend();
			setMiscSpend(categoryPurchase);
		}
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public double getExpenses() {
		return expenses;
	}

	public void setExpenses(double expenses) {
		this.expenses = expenses;
	}

	public double getHousingBudget() {
		return housingBudget;
	}

	public void setHousingBudget(double housingBudget) {
		this.housingBudget = housingBudget;
	}

	public double getHousingSpend() {
		return housingSpend;
	}

	public void setHousingSpend(double housingSpend) {
		this.housingSpend = housingSpend;
	}

	public double getGroceryBudget() {
		return groceryBudget;
	}

	public void setGroceryBudget(double groceryBudget) {
		this.groceryBudget = groceryBudget;
	}

	public double getGrocerySpend() {
		return grocerySpend;
	}

	public void setGrocerySpend(double grocerySpend) {
		this.grocerySpend = grocerySpend;
	}

	public double getDiningBudget() {
		return diningBudget;
	}

	public void setDiningBudget(double diningBudget) {
		this.diningBudget = diningBudget;
	}

	public double getDiningSpend() {
		return diningSpend;
	}

	public void setDiningSpend(double diningSpend) {
		this.diningSpend = diningSpend;
	}

	public double getCarBudget() {
		return carBudget;
	}

	public void setCarBudget(double carBudget) {
		this.carBudget = carBudget;
	}

	public double getCarSpend() {
		return carSpend;
	}

	public void setCarSpend(double carSpend) {
		this.carSpend = carSpend;
	}

	public double getUtilitiesBudget() {
		return utilitiesBudget;
	}

	public void setUtilitiesBudget(double utilitiesBudget) {
		this.utilitiesBudget = utilitiesBudget;
	}

	public double getUtilitiesSpend() {
		return utilitiesSpend;
	}

	public void setUtilitiesSpend(double utilitiesSpend) {
		this.utilitiesSpend = utilitiesSpend;
	}

	public double getHealthBudget() {
		return healthBudget;
	}

	public void setHealthBudget(double healthBudget) {
		this.healthBudget = healthBudget;
	}

	public double getHealthSpend() {
		return healthSpend;
	}

	public void setHealthSpend(double healthSpend) {
		this.healthSpend = healthSpend;
	}

	public double getFunBudget() {
		return funBudget;
	}

	public void setFunBudget(double funBudget) {
		this.funBudget = funBudget;
	}

	public double getFunSpend() {
		return funSpend;
	}

	public void setFunSpend(double funSpend) {
		this.funSpend = funSpend;
	}

	public double getMiscBudget() {
		return miscBudget;
	}

	public void setMiscBudget(double miscBudget) {
		this.miscBudget = miscBudget;
	}

	public double getMiscSpend() {
		return miscSpend;
	}

	public void setMiscSpend(double miscSpend) {
		this.miscSpend = miscSpend;
	}

	public ArrayList<FamilyMember> getHouseholdMembers() {
		return this.householdMembers;
	}

	public void setHouseholdMembers(ArrayList<FamilyMember> householdMembers) {
		this.householdMembers = householdMembers;
	}

	public ArrayList<Purchase> getPurchasesList() {
		return purchasesList;
	}

	public void setPurchasesList(ArrayList<Purchase> purchasesList) {
		this.purchasesList = purchasesList;
	}
}
