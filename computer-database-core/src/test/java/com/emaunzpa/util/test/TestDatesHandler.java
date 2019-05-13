package com.emaunzpa.util.test;

import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.Test;

import com.emaunzpa.util.DatesHandler;

public class TestDatesHandler {
	
	private DatesHandler dh = new DatesHandler();

	@Test
	public void convertSqlDateToString() {
		Date sqlDate = Date.valueOf("1996-05-03");
		Date sqlDateNull = null;
		assertEquals(dh.convertSqlDateToString(sqlDate), "1996-05-03");
		assertEquals(dh.convertSqlDateToString(sqlDateNull), null);
	}
	
	@Test
	public void convertStringDateToSql() {
		String validStr = "1996-05-03";
		String invalidStr = "1996/05/03";
		assertEquals(dh.convertStringDateToSqlDate(validStr), Date.valueOf("1996-05-03"));
		assertEquals(dh.convertStringDateToSqlDate(invalidStr), null);
	}

}
