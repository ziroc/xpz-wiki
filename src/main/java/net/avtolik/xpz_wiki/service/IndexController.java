package net.avtolik.xpz_wiki.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.avtolik.xpz_wiki.model.Item;
import net.avtolik.xpz_wiki.model.Research;

@Controller
public class IndexController {

	@Autowired
	WikiDao wd;


	@GetMapping("/")
	public String greeting(@RequestParam(name="search", required=false) String search, Model model) {
		
		if(wd == null || !wd.isLoaded()) {
			model.addAttribute("loaded", false);
			return "index";
		}
		
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
			
		}
		
		Research [] researchList = new Research [5];

		Random r = new Random();
		String [] itemIds = wd.getResearchItems().keySet().toArray(new String[0]);

		for (int i = 0; i < researchList.length; i++) {
			int random = r.nextInt(wd.getResearchItems().size());
			researchList[i] = wd.getResearchItems().get(itemIds[random]);
		}

		model.addAttribute("items", researchList);
		return "index";
	}
}
