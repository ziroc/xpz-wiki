package net.avtolik.xpz_wiki.service;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.avtolik.xpz_wiki.model.Armor;
import net.avtolik.xpz_wiki.model.Item;
import net.avtolik.xpz_wiki.model.Research;

@Controller
public class IndexController {

	@Autowired
	WikiDao wd;


	@GetMapping("/")
	public String greeting(@RequestParam(name="search", required=false) String search,
			@RequestParam(name="reload", required=false) boolean reload, Model model, HttpSession session) {
		
		if(wd == null || !wd.isLoaded()) {
			model.addAttribute("loaded", false);
			return "index";
		}
		
//		if(reload) {
//			System.out.println("reloading");
//			wd.loadAndProcessSaveGames();
//		}
		
		session.setAttribute("test1", new Date());
//		session.seta
		
		if(search!= null && !search.equals("")) {
			System.out.println("searching for "+search);
			
			List<Research> researchResult = wd.getResearchNames().entrySet().stream().
					filter(i -> i.getKey().toUpperCase().contains(search.toUpperCase()))
					.map(i -> wd.getResearchItems().get(i.getValue())).collect(Collectors.toList());
			System.out.println("found research: "+researchResult.size());
			if( !researchResult.isEmpty())
				model.addAttribute("researchResult", researchResult);
			
			List<Item> itemResult = wd.getItemNames().entrySet().stream().
					filter(i -> i.getKey().toUpperCase().contains(search.toUpperCase()))
					.map(i -> wd.getItems().get(i.getValue())).collect(Collectors.toList());
			System.out.println("found items: "+itemResult.size());
			if( !itemResult.isEmpty())
				model.addAttribute("itemResult", itemResult);
			
			List<Armor> armorResult = wd.getArmorNames().entrySet().stream().
					filter(i -> i.getKey().toUpperCase().contains(search.toUpperCase()))
					.map(i -> wd.getArmors().get(i.getValue())).collect(Collectors.toList());
			System.out.println("found items: "+armorResult.size());
			if( !itemResult.isEmpty())
				model.addAttribute("armorResult", armorResult);
			
		}
		
		Research [] researchList = new Research [5];

		Random r = new Random();
		String [] itemIds = wd.getResearchItems().keySet().toArray(new String[0]);

		for (int i = 0; i < researchList.length; i++) {
			int random = r.nextInt(wd.getResearchItems().size());
			researchList[i] = wd.getResearchItems().get(itemIds[random]);
		}
		
		model.addAttribute("saveGameResearch", wd.getSaveGameResearchList());

		model.addAttribute("items", researchList);
		return "index";
	}
	
	
}
