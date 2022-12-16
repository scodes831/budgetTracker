package budgetTracker;

import java.time.LocalDate;
import java.time.Month;
import java.util.Scanner;

public class PromptUserInput {

	public static double promptUserHousingBudget(Scanner in) {
		System.out.println("Enter budget for housing: ");
		double housingBudget = in.nextDouble();
		return housingBudget;
	}

	public static double promptUserUtilitiesBudget(Scanner in) {
		System.out.println("Enter budget for utilities: ");
		double utilitiesBudget = in.nextDouble();
		return utilitiesBudget;
	}

	public static double promptUserHealthBudget(Scanner in) {
		System.out.println("Enter budget for health: ");
		double healthBudget = in.nextDouble();
		return healthBudget;
	}

	public static double promptUserCarBudget(Scanner in) {
		System.out.println("Enter budget for car: ");
		double carBudget = in.nextDouble();
		return carBudget;
	}

	public static double promptUserGroceryBudget(Scanner in) {
		System.out.println("Enter budget for grocery: ");
		double groceryBudget = in.nextDouble();
		return groceryBudget;
	}

	public static double promptUserDiningBudget(Scanner in) {
		System.out.println("Enter budget for dining: ");
		double diningBudget = in.nextDouble();
		return diningBudget;
	}

	public static double promptUserFunBudget(Scanner in) {
		System.out.println("Enter budget for fun: ");
		double funBudget = in.nextDouble();
		return funBudget;
	}

	public static double promptUserMiscBudget(Scanner in) {
		System.out.println("Enter budget for miscellaneous: ");
		double miscBudget = in.nextDouble();
		return miscBudget;
	}

	public static String promptUserCategoryInput(Household household) {
		System.out.println(
				"Please choose a category:\na - Housing\nb - Utilities\nc - Health\nd - Car\ne - Groceries\nf - Dining\ng - Fun\nh - Miscellaneous");
		Scanner in = new Scanner(System.in);
		String categorySel = in.next();

		switch (categorySel) {
		case "a":
			return "housing";
		case "b":
			return "utilities";
		case "c":
			return "health";
		case "d":
			return "car";
		case "e":
			return "grocery";
		case "f":
			return "dining";
		case "g":
			return "fun";
		case "h":
			return "miscellaneous";
		default:
			System.out.println("Please enter a valid option a-h");
		}
		return null;
	}

	public static LocalDate promptUserDateInput(Household household, Budget budget) {
		Scanner in = new Scanner(System.in);
		boolean inputNeeded = true;
		System.out.println("When was the purchase made? Please enter the date in MM-DD-YYYY format.");
		while (inputNeeded) {
			try {
				String dateInput = in.next();
				String dateRegex = "(\\d{2}-\\d{2}-\\d{4})";
				if (!dateInput.toString().matches(dateRegex)) {
					System.out.println("Invalid date format. You must use MM-DD-YYYY format");
					PurchaseMenu.addPurchaseMenu(household, budget);
				} else {
					String[] dateInputArr = new String[3];
					dateInputArr = dateInput.toString().split("-");
					LocalDate datePurchased = LocalDate.of(Integer.parseInt(dateInputArr[2]),
							Month.of(Integer.parseInt(dateInputArr[0])), Integer.parseInt(dateInputArr[1]));
					return datePurchased;
				}
			} catch (Exception e) {
				System.out.println("Invalid date format entered. You must use MM-DD-YYYY format");
			}
		}
		return null;
	}

	public static String promptUserNameInput(Household household, Budget budget) {
		System.out.println("Who made the purchase?");
		Scanner in = new Scanner(System.in);
		String purchasedBy = in.next();
		boolean isFamilyMemberPresent = household.checkFamilyMember(household, purchasedBy);
		if (isFamilyMemberPresent) {
			return purchasedBy;
		} else {
			System.out.println("You entered " + purchasedBy
					+ " who is not set up in your household.\nHow would you like to proceed?\n1 - Add A Purchase\n2 - Add New Family Member");
			int selection = in.nextInt();
			switch (selection) {
			case 1:
				PurchaseMenu.addPurchaseMenu(household, budget);
				break;
			case 2:
				household.addFamilyMembers();
				break;
			}
		}
		return null;
	}

	public static double promptUserAmountInput(Household household) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter amount:");
		double amount = in.nextDouble();
		return amount;
	}
}
