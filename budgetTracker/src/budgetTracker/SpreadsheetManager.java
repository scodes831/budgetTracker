package budgetTracker;

import java.time.LocalDateTime;

public class SpreadsheetManager {
	
	public void createSpreadsheet() {
		generateFileName();
		
	}
	
	private String generateFileName() {
		return String.valueOf(LocalDateTime.now()).replaceAll("-", "_");
	}
	
}
