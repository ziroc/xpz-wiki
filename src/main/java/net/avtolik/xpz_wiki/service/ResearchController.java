package net.avtolik.xpz_wiki.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.avtolik.xpz_wiki.model.Article;
import net.avtolik.xpz_wiki.model.Research;

@Controller
public class ResearchController {

	private static final String PREFIX1 = "_UFOPEDIA";
	private static final String PREFIX2 = "_UC_UFOPEDIA";


	@Autowired
	WikiDao wd;

	@GetMapping("/research")
	public String getResearch(@RequestParam(name="id", required=false, defaultValue="World") String id, Model model) {

		Research item = wd.getResearchItems().get(id);

		if(item == null) {
			model.addAttribute("error", "Item not found!");
			return "item";
		}

		System.out.println("Item found + "+item);
		String desc = getDescription(item);

		desc = desc.replaceAll("\\{NEWLINE\\}", "\n");

		List<Research> deps = new ArrayList<>();
		if (item.getDependencies() != null) {
			for (String dep : item.getDependencies()) {
				Research i = new Research();
				i.setName(dep);
				i.setRealName(wd.getResearchItems().get(dep).getRealName());
				deps.add(i);
			}
		}
		
		List<Research> unlocks = new ArrayList<>();
		if (item.getUnlocks() != null) {
			for (String unlock : item.getUnlocks()) {
				Research i = new Research();
				i.setName(unlock);
				i.setRealName(wd.getResearchItems().get(unlock).getRealName());
				unlocks.add(i);
			}
		}

		
		item.setDescription(desc);

		model.addAttribute("item", item);
		model.addAttribute("deps", deps);
		model.addAttribute("unlocks", unlocks);
		model.addAttribute("newLineChar", '\n');
		return "research";
	}

	private String getDescription(Research item) {
		String desc ;
		desc = (String) wd.getDict().get(item.getName()+ PREFIX1);

		if(desc == null)
			desc = (String) wd.getDict().get(item.getName()+ PREFIX2);

		if(desc == null && item.getLookup() != null) {
			Research lookup = wd.getResearchItems().get(item.getLookup());
			if(lookup != null) {
				System.out.println("lookup found");
				return getDescription(lookup);
			}
		}

		if(desc == null && item.getCost() > 0) {
			System.out.println("trying slow search - for required");

			for (Entry<String, Article> entry : wd.getArticles().entrySet()) {
				if(entry.getValue().getRequires() != null) {
					for (Object req : entry.getValue().getRequires()) {
						if(((String)req).equals(item.getName()))
							desc = (String) wd.getDict().get(entry.getValue().getText());
					}
				}
			}
		}
		if(desc == null && item.getCost() == 0) 
			desc = "The tech has zero cost, probably it is a special tech for technical reasons.";
		
		if(desc == null)
			desc = "Description not found";
		return desc;
	}

}