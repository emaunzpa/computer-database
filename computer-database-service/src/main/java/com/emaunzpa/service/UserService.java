package com.emaunzpa.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.emaunzpa.db.UserDriver;
import com.emaunzpa.model.Authorities;
import com.emaunzpa.model.User;

public class UserService implements UserDetailsService {
	
	private UserDriver userDriver;
	
	public UserService() {}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userDriver.findUserByUsername(username);
	    UserBuilder builder = null;
	    if (user != null) {
	      
	      builder = org.springframework.security.core.userdetails.User.withUsername(username);
	      builder.disabled(!user.isEnabled());
	      builder.password(user.getPassword());
	      String[] authorities = user.getAuthorities()
	          .stream().map(a -> a.getAuthority()).toArray(String[]::new);

	      builder.authorities(authorities);
	    } else {
	      throw new UsernameNotFoundException("User not found.");
	    }

	    return builder.build();
	    
	}
	
	public void addUser(User user, String role) {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		Authorities newAuthorities = new Authorities(role, user);
		Set<Authorities> authorities = new HashSet<Authorities>();
		authorities.add(newAuthorities);
		user.setAuthorities(authorities);
		userDriver.addUser(user, newAuthorities);
	}

	public UserDriver getUserDriver() {
		return userDriver;
	}

	public void setUserDriver(UserDriver userDriver) {
		this.userDriver = userDriver;
	}

}
