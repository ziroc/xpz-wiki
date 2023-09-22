package net.avtolik.xpz_wiki.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.avtolik.xpz_wiki.model.Item;
import net.avtolik.xpz_wiki.model.Manufacture;
import net.avtolik.xpz_wiki.model.UsedForObject;

@Controller
public class ItemController {

	private static final String PREFIX1 = "_UFOPEDIA";
	private static final String PREFIX2 = "_UC_UFOPEDIA";
	Logger logger = LoggerFactory.getLogger(ItemController.class);

	@Autowired
	WikiDao wd;

	@GetMapping("/item")
	public String getItem(@RequestParam(name = "id", required = false, defaultValue = "World") String id, Model model) {

		Item item = wd.getItems().get(id);

		if (item == null) {
			Item ret = new Item();
			ret.setRealName("Not found.");
			model.addAttribute("item", ret);
			model.addAttribute("notFound", true);
			return "item";
		}
		model.addAttribute("notFound", false);
		logger.debug("Item found + " + item.getRealName());

		List<Item> ammoList = new ArrayList<>();
		if (item.getCompatibleAmmo() != null && !item.getCompatibleAmmo().isEmpty()) {
			for (String ammo : item.getCompatibleAmmo()) {
				ammoList.add(wd.getItems().get(ammo));
			}
			model.addAttribute("ammoList", ammoList);
		}

		String desc = getDescription(item);

		desc = desc.replaceAll("\\{NEWLINE\\}", "\n");

		item.setDescription(desc);

		logger.debug("flat: " + item.isFlatRate());
		if (item.isFlatRate()) {
			if (item.getCostSnap() != null) {
				model.addAttribute("flatCost", item.getCostSnap());
			} else if (item.getCostAimed() != null) {
				model.addAttribute("flatCost", item.getCostAimed());
			} else if (item.getCostMelee() != null) {
				model.addAttribute("flatCost", item.getCostMelee());
			}
		}
		HashSet<String> usedFor = wd.getUsedForManifacture().get(item.getName());
		UsedForObject u = new UsedForObject();
		if (usedFor != null && usedFor.size() > 0) {
			System.out.println(usedFor);
			ArrayList<String> nameList = new ArrayList<>(usedFor.size());
			usedFor.forEach(foritem -> {
				Object i = wd.getDict().get(foritem);
				if (i == null)
					nameList.add("unkown");
				else
					nameList.add(i.toString());
			});
			System.out.println(nameList);

			u.setNotEmpty(true);
			u.setIdList(usedFor);
			u.setNameList(nameList);
			model.addAttribute("usedFor", u);
		} else {
			model.addAttribute("usedFor", u);
		}

		Manufacture manItem = wd.getManifactureItems().get(item.getName());
		StringBuilder sb = new StringBuilder();

		if (manItem != null) {
			System.out.println("man item found + " + manItem);
			sb.append("Required items: ");
			if (manItem.getRequiredItems() != null && manItem.getRequiredItems().size() != 0) {
				manItem.getRequiredItems().forEach((reqId, numOfItems) -> {
					sb.append(wd.getDict().get(reqId) + " : ");
					sb.append(numOfItems + ", ");
				});
				sb.delete(sb.length() - 2, sb.length() - 1);
			} else {
				sb.append("none");
			}
			model.addAttribute("requiredItems", sb.toString());
			model.addAttribute("manItem", manItem);
		}

		model.addAttribute("item", item);
		model.addAttribute("newLineChar", '\n');
		return "item";
	}

	private String getDescription(Item item) {
		String desc;
		desc = (String) wd.getDict().get(item.getName() + PREFIX1);

		if (desc == null)
			desc = (String) wd.getDict().get(item.getName() + PREFIX2);

		if (desc == null || item.getArmorName() != null)
			desc = "Description not found";
		return desc;
	}

}
