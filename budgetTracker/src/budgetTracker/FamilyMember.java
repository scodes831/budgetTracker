package budgetTracker;

import java.util.ArrayList;
import java.util.List;

public class FamilyMember {
	private static List<FamilyMember> householdMembers = new ArrayList<FamilyMember>();
	private String name;
	private double salary;
	
	public FamilyMember(String name, double salary) {
		this.name = name;
		this.salary = salary;
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
	public static List<FamilyMember> getHouseholdMembers() {
		return householdMembers;
	}
	public static void setHouseholdMembers(List<FamilyMember> householdMembers) {
		FamilyMember.householdMembers = householdMembers;
	}
}
