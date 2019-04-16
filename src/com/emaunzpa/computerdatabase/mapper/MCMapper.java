package com.emaunzpa.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;

import com.emaunzpa.computerdatabase.model.Computer;
import com.emaunzpa.computerdatabase.util.DatesHandler;

public class MCMapper implements RowMapper<Optional<Computer>> {
	
	private DatesHandler dh = new DatesHandler();

	@Override
	public Optional<Computer> mapRow(ResultSet rs, int rowNum) throws SQLException {
		Optional<Computer> computer = Optional.of(new Computer.ComputerBuilder()
				.withId(rs.getInt("id"))
				.withName(rs.getString("name"))
				.withIntroducedDate(dh.convertStringDateToSqlDate(rs.getString("introduced")))
				.withDiscontinuedDate(dh.convertStringDateToSqlDate(rs.getString("discontinued")))
				.withManufacturerId(rs.getInt("company_id"))
				.withManufacturerName(rs.getString("company_name"))
				.build());

		return computer;
	}
		
}
