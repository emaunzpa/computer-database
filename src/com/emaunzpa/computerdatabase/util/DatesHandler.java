package com.emaunzpa.computerdatabase.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DatesHandler {

	private SimpleDateFormat sdf;
	
	/**
	 * Creator without parameter
	 */
	public DatesHandler() {
		this.sdf = new SimpleDateFormat("yyyy-MM-dd");
	}
	
	/**
	 * Convert a date in String format to an SQL date
	 * @param stringDate
	 * @return sqlDate
	 */
	public java.sql.Date convertStringDateToSqlDate(String stringDate){
		if (stringDate == null) {
			return null;
		}
		else if (stringDate.equals("")) {
			return null;
		}
		else {
			java.util.Date stringDateParsed = null;
			java.sql.Date sqlDate = null;
			try {
				stringDateParsed = sdf.parse(stringDate);
				sqlDate = new java.sql.Date(stringDateParsed.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
	    	return sqlDate;
		}
	}
	
	public String convertSqlDateToString(java.sql.Date sqlDate) {
		if (sqlDate == null) {
			return null;
		}
		else {
			return (sqlDate.toString());
		}
	}
	
}
