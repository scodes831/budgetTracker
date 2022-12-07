package budgetTracker;

import java.util.Scanner;

public class Budget {

	public static void addPurchase(FamilyMember purchasedBy, String date, String category, double amount) {

	}

	public static void main(String[] args) {
		// create household budget object
		Budget budget = new Budget();
		Household household = new Household();
		
		// ask user to input family members
		System.out.println("Please add family members to your household. Enter X when finished.\nHow many family members do you want to add?");
		Scanner in = new Scanner(System.in);
		int userInputNum = in.nextInt();
		int numFamilyMembers = 0;FamilyMember.getHouseholdMembers().size();
		do {
			System.out.println("Family Member #" + (numFamilyMembers+1) + " name: ");
			String name = in.next();
			System.out.println("Family Member #" + (numFamilyMembers+1) + " monthly income: ");
			double salary = in.nextDouble();
			FamilyMember familyMember = new FamilyMember(name, salary);
			FamilyMember.getHouseholdMembers().add(familyMember);
			numFamilyMembers = FamilyMember.getHouseholdMembers().size();
			System.out.println("You have added " + familyMember.getName() + " who has a monthly income of $" + familyMember.getSalary());
		} while (userInputNum != numFamilyMembers);
		// present options of check budget and add a purchase
		// check budget
		// display all categories with budget amount and remaining amount
		// provide option to add a purchase
		// add a purchase
		// ask user for purchase date, category, and amount
		// ask user if they want to add another purchase
		// if yes, repeat
		// if no, display all categories with budget amount and remaining amount
	}
}
