package com.emaunzpa.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;

import com.emaunzpa.computerdatabase.model.Manufacturer;

public class ManufacturerMapper implements RowMapper<Manufacturer> {

	@Override
	public Manufacturer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Manufacturer manufacturer = new Manufacturer(rs.getInt("id"), rs.getString("name"));

		return manufacturer;
	}

}
