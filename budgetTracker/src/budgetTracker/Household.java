package budgetTracker;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Formatter;

public class Household {

	private ArrayList<Budget> budgets = new ArrayList<Budget>();
	private ArrayList<FamilyMember> householdMembers = new ArrayList<FamilyMember>();
	private ArrayList<Purchase> purchasesList = new ArrayList<Purchase>();

	private double income;

	public void addFamilyMembers(Connection connection, UsersTable usersTable) {
		int numCurrentFamilyMembers = getHouseholdMembers().size();
		System.out.println("You currently have " + numCurrentFamilyMembers
				+ " family members set up for your household.\nHow many family members do you want to add?");
		Scanner in = new Scanner(System.in);
		int userInputNum = in.nextInt();
		if (userInputNum == 0) {
			System.out.println("No new family members have been added.");
		} else {
			do {
				System.out.println("Family Member #" + (getHouseholdMembers().size() + 1) + " name: ");
				String name = in.next();
				System.out.println("Family Member #" + (getHouseholdMembers().size() + 1) + " monthly income: ");
				double salary = in.nextDouble();
				FamilyMember familyMember = new FamilyMember(name, salary);
				householdMembers.add(familyMember);
				usersTable.insertUsersRow(connection, name, new BigDecimal(salary));
				System.out.println("You have added " + familyMember.getName() + " who has a monthly income of $"
						+ familyMember.getSalary() + "\n");
			} while (getHouseholdMembers().size() != (numCurrentFamilyMembers + userInputNum));
		}
	}

	public Formatter displayFamilyMembers() {
		Formatter table = new Formatter();
		table.format("%15s %15s\n", "Name", "Income");
		for (FamilyMember familyMember : getHouseholdMembers()) {
			table.format("%15s %15s\n", familyMember.getName(), familyMember.getSalary());
		}
		System.out.println(table);
		return table;
	}

	public void editFamilyMembers(Household household, Connection connection, UsersTable usersTable) {
		displayFamilyMembers();
		System.out.println("Enter the name of the family member you want to edit:");
		Scanner in = new Scanner(System.in);
		String editName = in.next();
		for (FamilyMember familyMember : getHouseholdMembers()) {
			if (familyMember.getName().toLowerCase().equals(editName.toLowerCase())) {
				System.out.println("Name is currently set up as: " + familyMember.getName()
						+ " with a salary of $" + familyMember.getSalary() + ". Enter new name:");
				String oldName = familyMember.getName();
				String newName = in.next();
				familyMember.setName(newName);
				Purchase.updatePurchasedBy(household, editName, newName);
				System.out.println("Enter new salary: ");
				double newSalary = in.nextDouble();
				familyMember.setSalary(newSalary);
				usersTable.updateUser(connection, newName, oldName, new BigDecimal(newSalary));
				System.out.println("Name has been updated to " + familyMember.getName());
				System.out.println("Salary for " + familyMember.getName() + " has been updated to $"
							+ familyMember.getSalary());
			}
		}
	}

	public boolean checkFamilyMember(Household household, String purchasedBy) {
		for (FamilyMember familyMember : household.getHouseholdMembers()) {
			if (familyMember.getName().toLowerCase().equals(purchasedBy.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	public static int[] generateBudgetName() {
		Scanner in = new Scanner(System.in);
		int[] budgetName = new int[2];
		budgetName[0] = PromptUserInput.promptUserBudgetMonth(in);
		budgetName[1] = PromptUserInput.promptUserBudgetYear(in);
		return budgetName;
	}

	public void addPurchase(String category, BigDecimal amount, String purchasedBy, LocalDate datePurchased, Connection connection, PurchasesTable purchasesTable) {
		Purchase purchase = new Purchase(category, amount, purchasedBy, datePurchased);
		System.out.println("Added purchase of $" + amount + " spent on " + category + " by " + purchasedBy + " on "
				+ datePurchased);
		purchasesList.add(purchase);
		purchasesTable.insertPurchasesRow(connection, datePurchased, category, purchasedBy, amount);
		int purchaseNum = purchasesList.size() - 1;
	}
	
	public void calculateCategorySpend(Budget budget) { 
		BigDecimal housingTotal = new BigDecimal(0);
		BigDecimal utilitiesTotal = new BigDecimal(0);
		BigDecimal healthTotal = new BigDecimal(0);
		BigDecimal carTotal = new BigDecimal(0);
		BigDecimal groceryTotal = new BigDecimal(0);
		BigDecimal diningTotal = new BigDecimal(0);
		BigDecimal funTotal = new BigDecimal(0);
		BigDecimal miscTotal = new BigDecimal(0);

		for (Purchase purchase : purchasesList) {
			LocalDate purchaseDate = purchase.getDatePurchased();
			int purchaseMonth = purchaseDate.getMonthValue();
			int purchaseYear = purchaseDate.getYear();
			if (budget.getBudgetMonth() == purchaseMonth && budget.getBudgetYear() == purchaseYear) {
				switch (purchase.getCategory()) {
				case "housing":
					housingTotal = housingTotal.add(purchase.getAmount());
					break;
				case "utilities":
					utilitiesTotal = utilitiesTotal.add(purchase.getAmount());
					break;
				case "health":
					healthTotal = healthTotal.add(purchase.getAmount());
					break;
				case "car":
					carTotal = carTotal.add(purchase.getAmount());
					break;
				case "grocery":
					groceryTotal = groceryTotal.add(purchase.getAmount());
					break;
				case "dining":
					diningTotal = diningTotal.add(purchase.getAmount());
					break;
				case "fun":
					funTotal = funTotal.add(purchase.getAmount());
					break;
				case "miscellaneous":
					miscTotal = miscTotal.add(purchase.getAmount());
					break;
				}
			}
		}
		
		budget.setHousingSpend(housingTotal);
		budget.setUtilitiesSpend(utilitiesTotal);
		budget.setHealthSpend(healthTotal);
		budget.setCarSpend(carTotal);
		budget.setGrocerySpend(groceryTotal);
		budget.setDiningSpend(diningTotal);
		budget.setFunSpend(funTotal);
		budget.setMiscSpend(miscTotal);
		
	}

	public void calculateHouseholdIncome(Household household) {
		double totalHouseholdIncome = 0.0;
		for (FamilyMember member : household.getHouseholdMembers()) {
			totalHouseholdIncome += member.getSalary();
		}
		household.setIncome(totalHouseholdIncome);
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
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

	public ArrayList<Budget> getBudgets() {
		return budgets;
	}

	public void setBudgets(ArrayList<Budget> budgets) {
		this.budgets = budgets;
	}
}
