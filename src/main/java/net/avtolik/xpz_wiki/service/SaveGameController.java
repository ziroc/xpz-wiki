package net.avtolik.xpz_wiki.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SaveGameController {


	@Autowired
	WikiDao wd;
	
	@GetMapping("/savegame")
	public String showSave(Model model) {

		
		model.addAttribute("saveGameLoaded", false);
		
		return "savegame";
	}
	
	@PostMapping("/uploadSave")
	public String uploadSaveGame(MultipartFile file, RedirectAttributes redirectAttributes, HttpSession session) {
		
		System.out.println("file is " + file.getOriginalFilename() + ", size: " + file.getSize());
		
	    redirectAttributes.addFlashAttribute("message",
	        "You successfully uploaded " + file.getOriginalFilename() + "!");
		
	    
	    return "redirect:/savegame";

	}
	
	
	
}
