package com.sellit.sellit.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sellit.sellit.Dao.UserDao;
import com.sellit.sellit.entities.User;
import com.sellit.sellit.helper.Message;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDao userDao;
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "SelliT");
//		System.out.println(userDao.getUserByUserName("kunal@gmail.com"));
		return "home";
	}
	
	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About");
		return "about";
	}

	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Signup");
		model.addAttribute("user", new User());
		return "signup";
	}

	@PostMapping("/register_user")
	public String registerUser(@ModelAttribute("user") User user, @RequestParam(value="agreement", defaultValue="false") boolean agreement, Model model, HttpSession session) {
		try {
			if(!agreement)  {
				System.out.println("Not Signed User Agreemet");
				throw new Exception("Terms and Condition not Accepted !!");
			}
			user.setRole("ROLE_USER");
			user.setName(user.getName().toUpperCase());
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setIspremium(false);
			User res= this.userDao.save(user);
			model.addAttribute("user", res);
			System.out.println(res);
			
			session.setAttribute("message", new Message("Successfully Registred", "alert-success"));
		
		}catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something Whent Wrong !! "+e.getMessage(), "alert-danger"));
			return "signup";
		}
		return "login";
	}
	
	@GetMapping("/login")
	public String clogin(Model model) {
		model.addAttribute("title", "Login");
		return "login";
	}
	
}
