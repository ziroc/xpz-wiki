package net.avtolik.xpz_wiki.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.avtolik.xpz_wiki.model.saveFile.SaveGame;
import net.avtolik.xpz_wiki.model.saveFile.SaveGameJson;

@Controller
public class SaveGameController {


	@Autowired
	WikiDao wd;
	
	@Autowired
	SaveGameService sgService;
	
	@GetMapping("/savegame")
	public String showSave(Model model, HttpSession session) {
		SaveGame saveGame = (SaveGame) session.getAttribute("savegame");
		Boolean saveGameUpLoaded = session.getAttribute("saveGameUpLoaded") != null ? (boolean) session.getAttribute("saveGameUpLoaded") : false ;
		
		if(saveGame == null) {
			model.addAttribute("saveGameLoaded", false);
		}
		else {
			model.addAttribute("saveGameLoaded", true);
			model.addAttribute("saveGameResearch", saveGame.getCurrentResearch());
			model.addAttribute("funds", saveGame.getFunds().get(saveGame.getFunds().size()-1));
		}
		
		model.addAttribute("saveGameUpLoaded",  saveGameUpLoaded);
		return "savegame";
	}
	
	
	@PostMapping("/uploadSave")
	public String uploadSaveGame(MultipartFile file, RedirectAttributes redirectAttributes, Model model, HttpSession session) {
		
		System.out.println("file is " + file.getOriginalFilename() + ", size: " + file.getSize());
		
		sgService.processSaveGame(file, session);
		session.setAttribute("saveGameUpLoaded", true);
		
	    redirectAttributes.addFlashAttribute("message",
	        "Your save game was successfully uploaded " + file.getOriginalFilename() + ". Now it's being processed. Please refresh after a minute.");
	    redirectAttributes.addFlashAttribute("saveGameUpLoaded", true);
//	    return new ModelAndView("redirect:/savegame", model);
	    return "redirect:/savegame";
	}
	
	@PostMapping("/uploadParsed")
	public String uploadParsedJson(RedirectAttributes redirectAttributes, SaveGameJson saveGameJson, Model model, HttpSession session) {
		
		System.out.println("received is " + saveGameJson);
		
		sgService.processParsedJson(saveGameJson, session);
		session.setAttribute("saveGameUpLoaded", true);
		
	    redirectAttributes.addFlashAttribute("message",
	        "Your save game was successfully uploaded. Now it's being processed. Please refresh after a minute.");
	    redirectAttributes.addFlashAttribute("saveGameUpLoaded", true);
//	    return new ModelAndView("redirect:/savegame", model);
	    return "redirect:/savegame";
	}
	
}
