package com.emaunzpa.dao;

import com.emaunzpa.model.User;

public interface UserDAO {

	User findUserByUsername(String username);
	
}
