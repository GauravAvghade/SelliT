package com.sellit.sellit.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sellit.sellit.Dao.PostsDao;
import com.sellit.sellit.Dao.UserDao;
import com.sellit.sellit.entities.User;
import com.sellit.sellit.helper.Message;
import com.sellit.sellit.entities.Posts;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PostsDao postsDao;
	
	@ModelAttribute
	public void addCommon(Model model, Principal principal) {
		String username = principal.getName();
		User user = userDao.getUserByUserName(username); 
		model.addAttribute("user", user);
	}
	
	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {
		model.addAttribute("title","Dashboard");
		model.addAttribute("list",this.postsDao.findAll());

		return "normal/user_dashboard";
	}
	
	@GetMapping("/addpost")
	public String addPost(Model model, Principal principal) {
		model.addAttribute("title","Add Post");
		model.addAttribute("posts", new Posts());
		return "normal/addpost";
	}
	
	@PostMapping("/process_post")
	public String process_post(@ModelAttribute("posts") Posts posts, HttpSession session, Model model) {
		try {
			User user = (User)model.getAttribute("user");
			if(!user.isIspremium()) {
				throw new Exception("Access deined!!");
			}
			posts.setUser(user);
			postsDao.save(posts);
			session.setAttribute("message", new Message("Post Added Successfully", "alert-success"));
		}
		catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new Message("Something Whent Wrong !! "+e.getMessage(), "alert-danger"));
			return "normal/addpost";
		}
		return "normal/addpost";
	}
	
	@GetMapping("/setting")
	public String setting(Model model, Principal principal) {
		model.addAttribute("title","Setting");
		return "normal/settings";
	}
	
	@PostMapping("/process_update")
	public String process_update(@RequestParam("ispremium") boolean ispremium, Model model, Principal principal, HttpSession session) {
		User user = (User) model.getAttribute("user");
		user.setIspremium(ispremium);
		userDao.save(user);
		return "normal/user_dashboard";
	}

}
