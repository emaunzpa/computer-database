package com.emaunzpa.util;

public enum CasesSorted {

	BY_NAME("byName"),
	BY_NAME_REVERSE("byNameReverse"),
	BY_COMPANY("byCompany"),
	BY_COMPANY_REVERSE("byCompanyReverse"),
	BY_INTRODUCED("byIntroduced"),
	BY_INTRODUCED_REVERSE("byIntroducedReverse"),
	BY_DISCONTINUED("byDiscontinued"),
	BY_DISCONTINUED_REVERSE("byDiscontinuedReverse");
	
	private String sortType;
	
	private CasesSorted(String sortType) {
		this.sortType = sortType;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	
}
