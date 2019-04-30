package net.avtolik.xpz_wiki.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.avtolik.xpz_wiki.model.Item;

@Controller
public class ItemController {
	
	private static final String PREFIX1 = "_UFOPEDIA";
	private static final String PREFIX2 = "_UC_UFOPEDIA";


	@Autowired
	WikiDao wd;

	@GetMapping("/item")
	public String getItem(@RequestParam(name="id", required=false, defaultValue="World") String id, Model model) {

		Item item = wd.getItems().get(id);

		if(item == null) {
			model.addAttribute("error", "Item not found!");
			return "item";
		}
		
		List<Item> ammoList = new ArrayList<>();
		if(item.getCompatibleAmmo() != null && !item.getCompatibleAmmo().isEmpty()) {
			for (String ammo : item.getCompatibleAmmo()) {
				ammoList.add(wd.getItems().get(ammo));
			}
			model.addAttribute("ammoList", ammoList);
		}

		System.out.println("Item found + "+item);
		String desc = getDescription(item);

		desc = desc.replaceAll("\\{NEWLINE\\}", "\n");

		item.setDescription(desc);

		model.addAttribute("item", item);
		model.addAttribute("newLineChar", '\n');
		return "item";
	}

	private String getDescription(Item item) {
		String desc ;
		desc = (String) wd.getDict().get(item.getName()+ PREFIX1);

		if(desc == null)
			desc = (String) wd.getDict().get(item.getName()+ PREFIX2);


		if(desc == null || item.getArmorName() != null)
			desc = "Description not found";
		return desc;
	}

}
