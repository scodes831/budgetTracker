package budgetTracker;

public class FamilyMember {

	private String name;
	private double salary;

	public FamilyMember(String name, double salary) {
		this.name = name;
		this.salary = salary;
	}
	
	public static String capitalizeName(String name) {
		String firstL = name.substring(0,1).toUpperCase();
		String remainder = name.substring(1);
		return firstL + remainder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
}
