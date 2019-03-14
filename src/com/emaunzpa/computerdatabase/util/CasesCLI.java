package com.emaunzpa.computerdatabase.util;

public enum CasesCLI {

	LIST_COMPUTERS(1),
	LIST_COMPANIES(2),
	SHOW_COMPUTER_DETAILS(3),
	CREATE_COMPUTER(4),
	UPDATE_COMPUTER(5),
	DELETE_COMPUTER(6),
	EXIT(7);
	
	private int choice;
	
	CasesCLI(int choice) {
		this.choice = choice;
	}

	public int getChoice() {
		return choice;
	}
	
}
