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
import net.avtolik.xpz_wiki.model.Item;

@Controller
public class ItemController {

	private static final String PREFIX1 = "_UFOPEDIA";
	private static final String PREFIX2 = "_UC_UFOPEDIA";


	@Autowired
	WikiDao wd;

	@GetMapping("/item")
	public String greeting(@RequestParam(name="id", required=false, defaultValue="World") String id, Model model) {

		Item item = wd.getItems().get(id);

		if(item == null) {
			model.addAttribute("error", "Item not found!");
			return "item";
		}

		System.out.println("Item found + "+item);
		String desc = getDescription(item);

		desc = desc.replaceAll("\\{NEWLINE\\}", "\n");

		List<Item> deps = new ArrayList<>();
		if (item.getDependencies() != null) {
			for (String dep : item.getDependencies()) {
				Item i = new Item();
				i.setName(dep);
				i.setRealName(wd.getItems().get(dep).getRealName());
				deps.add(i);
			}
		}
		
		List<Item> unlocks = new ArrayList<>();
		if (item.getUnlocks() != null) {
			for (String unlock : item.getUnlocks()) {
				Item i = new Item();
				i.setName(unlock);
				i.setRealName(wd.getItems().get(unlock).getRealName());
				unlocks.add(i);
			}
		}

		
		item.setDescription(desc.toString());

		model.addAttribute("item", item);
		model.addAttribute("deps", deps);
		model.addAttribute("unlocks", unlocks);
		model.addAttribute("newLineChar", '\n');
		return "item";
	}

	private String getDescription(Item item) {
		String desc ;
		desc = (String) wd.getDict().get(item.getName()+ PREFIX1);

		if(desc == null)
			desc = (String) wd.getDict().get(item.getName()+ PREFIX2);

		if(desc == null && item.getLookup() != null) {
			Item lookup = wd.getItems().get(item.getLookup());
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