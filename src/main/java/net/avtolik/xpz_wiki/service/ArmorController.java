package net.avtolik.xpz_wiki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.avtolik.xpz_wiki.model.Armor;
import net.avtolik.xpz_wiki.model.Constants;
import net.avtolik.xpz_wiki.model.Manufacture;

@Controller
public class ArmorController {
	
	private static final String PREFIX1 = "_UFOPEDIA";
	private static final String PREFIX2 = "_UC_UFOPEDIA";


	@Autowired
	WikiDao wd;

	@GetMapping("/armor")
	public String getItem(@RequestParam(name="id", required=false, defaultValue="World") String id, Model model) {

		Armor armor = wd.getArmors().get(id);

		if(armor == null) {
			model.addAttribute("error", "Item not found!");
			return "item";
		}

		System.out.println("Armor found + "+armor);
		String desc = getDescription(armor);

		desc = desc.replaceAll("\\{NEWLINE\\}", "\n");
		armor.setDescription(desc);

		Manufacture manItem = wd.getManifactureItems().get(armor.getStoreItem());
		StringBuilder sb = new StringBuilder();
		if(manItem != null) {
			System.out.println("man item found + " + manItem);
			sb.append("Required items: ");
			if(manItem.getRequiredItems() != null && manItem.getRequiredItems().size() !=0){
				manItem.getRequiredItems().forEach((reqId, numOfItems) -> { 
					sb.append(wd.getDict().get(reqId) + " : "); 
					sb.append(numOfItems + ", ");
				});
				sb.delete(sb.length()-2, sb.length()-1);
			}
			else {
				sb.append("none");
			}
		}

		model.addAttribute("armorModifiers", Constants.armorModifiers);
		model.addAttribute("item", armor);
		model.addAttribute("requiredItems", sb.toString());
		model.addAttribute("newLineChar", '\n');
		return "armor";
	}

	private String getDescription(Armor armor) {
		String desc ;
		desc = (String) wd.getDict().get(armor.getName()+ PREFIX1);

		if(desc == null)
			desc = (String) wd.getDict().get(armor.getName()+ PREFIX2);

		if(desc == null) {
			desc = (String) wd.getDict().get(wd.getArticles().get(armor.getName()).getText());
		}
		
		if(desc == null)
			desc = "Description not found";
		return desc;
	}

}
