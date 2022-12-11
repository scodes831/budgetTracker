package budgetTracker;

public class Purchase {
		
	String category;
	double amount;
	String purchasedBy;
	
	Purchase(String category, double amount, String purchasedBy) {
		this.category = category;
		this.amount = amount;
		this.purchasedBy = purchasedBy;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPurchasedBy() {
		return purchasedBy;
	}

	public void setPurchasedBy(String purchasedBy) {
		this.purchasedBy = purchasedBy;
	}
}
