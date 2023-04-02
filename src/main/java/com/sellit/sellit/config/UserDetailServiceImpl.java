package com.sellit.sellit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sellit.sellit.Dao.UserDao;
import com.sellit.sellit.entities.User;

public class UserDetailServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.getUserByUserName(username);
		if(user == null) {
			throw new UsernameNotFoundException("Could not found user !!"); 
		}
		
		CustomeUserDetails cud= new CustomeUserDetails(user);
		return cud;
	}

}
