package net.avtolik.xpz_wiki.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.avtolik.xpz_wiki.model.Craft;
import net.avtolik.xpz_wiki.model.Manufacture;

@Controller
public class CraftController {
	
	private static final String PREFIX1 = "_UFOPEDIA";
	private static final String PREFIX2 = "_UC_UFOPEDIA";


	@Autowired
	WikiDao wd;

	@GetMapping("/craft")
	public String getCraft(@RequestParam(name="id", required=false, defaultValue="World") String id, Model model) {

		Craft origCraft = wd.getCrafts().get(id);

		if(origCraft == null) {
			model.addAttribute("error", "Craft not found!");
			return "craft";
		}

		Craft craft = new Craft();
		BeanUtils.copyProperties(origCraft, craft);
		
		System.out.println("Craft found + "+craft);
		String desc = getDescription(craft);

		desc = desc.replaceAll("\\{NEWLINE\\}", "\n");
		craft.setDescription(desc);
		craft.setRealName(wd.getDict().get(craft.getName()).toString());

		if (craft.getRefuelItem() == null || craft.getRefuelItem().isBlank())
			craft.setRefuelItem("Batteries");
		else {
			craft.setRefuelItem(wd.getDict().get(craft.getRefuelItem()).toString());
		}

		if (craft.getShieldCapacity() == null || craft.getShieldCapacity().isBlank())
			craft.setShieldCapacity("none");

		if (craft.getVehicles() == null || craft.getVehicles().isBlank() || craft.getVehicles().equals("-1"))
			craft.setVehicles("none");

		if (craft.getCostBuy() == null || craft.getCostBuy().isBlank() )
			craft.setCostBuy("not for sale");

		Manufacture manItem = wd.getManifactureItems().get(craft.getName());
		StringBuilder sb = new StringBuilder();

		if(manItem != null) {
			System.out.println("man item found + " + manItem);
			sb.append("Required items: ");
			if(manItem.getRequiredItems() != null && manItem.getRequiredItems().size() !=0){
				manItem.getRequiredItems().forEach((reqId, numOfItems) -> { 
					sb.append(wd.getDict().get(reqId) + ": "); 
					sb.append(numOfItems + ", ");
				});
				sb.delete(sb.length()-2, sb.length()-1);
			}
			else {
				sb.append("none");
			}
			model.addAttribute("requiredItems", sb.toString());
			model.addAttribute("manItem", manItem);
		}

		// model.addAttribute("craftModifiers", Constants.craftModifiers);
		model.addAttribute("craft", craft);
		model.addAttribute("newLineChar", '\n');
		return "craft";
	}

	private String getDescription(Craft craft) {
		String desc ;
		desc = (String) wd.getDict().get(craft.getName()+ PREFIX1);

		if(desc == null)
			desc = (String) wd.getDict().get(craft.getName()+ PREFIX2);

		if(desc == null) {
			desc = (String) wd.getDict().get(wd.getArticles().get(craft.getName()).getText());
		}
		
		if(desc == null)
			desc = "Description not found";
		return desc;
	}

}
