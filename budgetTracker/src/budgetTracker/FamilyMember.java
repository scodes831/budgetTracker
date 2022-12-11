package budgetTracker;

import java.util.ArrayList;
import java.util.List;

public class FamilyMember {
	
	private ArrayList<Purchase> memberPurchases = new ArrayList<Purchase>();
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
	
	public ArrayList<Purchase> getMemberPurchases() {
		return memberPurchases;
	}
	public void setPurchases(ArrayList<Purchase> memberPurchases) {
		this.memberPurchases = memberPurchases;
	}
}
