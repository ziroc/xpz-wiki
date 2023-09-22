package net.avtolik.xpz_wiki.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.avtolik.xpz_wiki.model.Armor;
import net.avtolik.xpz_wiki.model.Craft;
import net.avtolik.xpz_wiki.model.Item;
import net.avtolik.xpz_wiki.model.Research;

@Controller
public class IndexController {

	@Autowired
	WikiDao wd;

	Logger logger = LoggerFactory.getLogger(IndexController.class);

	@GetMapping("/")
	public String showIndex(@RequestParam(name = "search", required = false) String search,
			@RequestParam(name = "reload", required = false) boolean reload, Model model, HttpSession session) {

		logger.debug("opening main page");

		if (wd == null || !wd.isLoaded()) {
			model.addAttribute("loaded", false);
			return "index";
		}

		// if(reload) {
		// wd.loadAndProcessSaveGames();
		// }

		if (search != null && !search.equals("")) {
			logger.debug("searching for " + search);

			List<Research> researchResult = wd.getResearchNames().entrySet().stream()
					.filter(i -> i.getKey().toUpperCase().contains(search.toUpperCase()))
					.map(i -> wd.getResearchItems().get(i.getValue())).collect(Collectors.toList());
			logger.debug("found research: " + researchResult.size());
			if (!researchResult.isEmpty())
				model.addAttribute("researchResult", researchResult);

			List<Item> itemResult = wd.getItemNames().entrySet().stream()
					.filter(i -> i.getKey().toUpperCase().contains(search.toUpperCase()))
					.map(i -> wd.getItems().get(i.getValue())).collect(Collectors.toList());
			logger.debug("found items: " + itemResult.size());
			if (!itemResult.isEmpty())
				model.addAttribute("itemResult", itemResult);

			List<Armor> armorResult = wd.getArmorNames().entrySet().stream()
					.filter(i -> i.getKey().toUpperCase().contains(search.toUpperCase()))
					.map(i -> wd.getArmors().get(i.getValue())).collect(Collectors.toList());
			logger.debug("found armors: " + armorResult.size());
			if (!armorResult.isEmpty())
				model.addAttribute("armorResult", armorResult);

			List<Craft> craftResult = wd.getCraftNames().entrySet().stream()
					.filter(i -> i.getKey().toUpperCase().contains(search.toUpperCase()))
					.map(i -> wd.getCrafts().get(i.getValue())).collect(Collectors.toList());
			logger.debug("found crafts: " + craftResult.size());
			if (!craftResult.isEmpty())
				model.addAttribute("craftResult", craftResult);
		}

		Research[] researchList = new Research[5];

		Random r = new Random();
		String[] itemIds = wd.getResearchItems().keySet().toArray(new String[0]);

		for (int i = 0; i < researchList.length; i++) {
			int random = r.nextInt(wd.getResearchItems().size());
			researchList[i] = wd.getResearchItems().get(itemIds[random]);
		}

		// model.addAttribute("saveGameResearch", wd.getSaveGameResearchList());

		model.addAttribute("items", researchList);
		return "index";
	}

}
