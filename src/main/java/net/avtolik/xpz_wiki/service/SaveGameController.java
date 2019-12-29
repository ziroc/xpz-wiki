package net.avtolik.xpz_wiki.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.avtolik.xpz_wiki.model.Constants;
import net.avtolik.xpz_wiki.model.StringResponse;
import net.avtolik.xpz_wiki.model.saveFile.Discovered;
import net.avtolik.xpz_wiki.model.saveFile.SaveGame;
import net.avtolik.xpz_wiki.model.saveFile.SaveGameJson;
import net.avtolik.xpz_wiki.model.saveFile.UfopediaStatus;

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
	
	@PostMapping("/uploadDiscovered")
	public void uploadDiscovered(Discovered discovered, Model model, HttpSession session) {
		
		System.out.println("received is " + discovered);
		
		session.setAttribute("discovered", discovered);	  
//	    return "";
	}
	
	
	@RequestMapping(value = "/uploadUfopediaStatus",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
	@ResponseBody
//	@ResponseStatus(value = HttpStatus.OK)
	public StringResponse uploadUfopediaStatus(@RequestBody String payload, Model model, HttpSession session) {
		
		System.out.println(payload);
		
		
		sgService.processUfopediaRuleStatus(payload, session);
		
	    return new StringResponse("OK");
	}
	
	
	@GetMapping("/armortable")
	public String showArmorTable(Model model, HttpSession session) {
	
//		Discovered discovered = (Discovered) session.getAttribute("discovered");
		
		UfopediaStatus ufopediaStatus = (UfopediaStatus) session.getAttribute("ufopediaStatus");	  
		
		if(ufopediaStatus == null) {
			model.addAttribute("msg","Save game not uploaded!");
			model.addAttribute("saveGameLoaded", false);
			model.addAttribute("saveGameUpLoaded",  false);
			return "savegame";
		}
		
		model.addAttribute("armors", sgService.getArmorList(ufopediaStatus));
		model.addAttribute("armorModifiers", Constants.armorModifiers);
		return "knownarmor";
	}
	
}
