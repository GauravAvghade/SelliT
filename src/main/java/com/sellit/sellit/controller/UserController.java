package com.sellit.sellit.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sellit.sellit.Dao.UserDao;
import com.sellit.sellit.entities.User;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {
		String username = principal.getName();
		User user = userDao.getUserByUserName(username); 
		model.addAttribute("user", user);
		return "normal/user_dashboard";
	}
}
