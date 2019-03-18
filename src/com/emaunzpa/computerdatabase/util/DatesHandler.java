package com.emaunzpa.computerdatabase.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DatesHandler {

	private SimpleDateFormat sdf;
	
	/**
	 * Creator without parameter
	 */
	public DatesHandler() {
		this.sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * Convert a date in String format to an SQL date
	 * @param stringDate
	 * @return sqlDate
	 */
	public java.sql.Date convertStringDateToSqlDate(String stringDate){
		java.util.Date stringDateParsed = null;
		java.sql.Date sqlDate = null;
		try {
			stringDateParsed = sdf.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (stringDateParsed != null) {
			sqlDate = new java.sql.Date(stringDateParsed.getTime());
		}
    	return sqlDate;
	}
	
	public String convertSqlDateToString(java.sql.Date sqlDate) {
		return (sqlDate.toString() + " 00:00:00");
	}
	
}
