package net.avtolik.xpz_wiki.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

	@Autowired
	WikiDao wd;


	@GetMapping("/")
	public String showIndex(@RequestParam(name="search", required=false) String search,
			@RequestParam(name="reload", required=false) boolean reload, Model model, HttpSession session) {
		
		if(wd == null || !wd.isLoaded()) {
			model.addAttribute("loaded", false);
			return "index";
		}
		

		return "index";
	}
	
	
}
