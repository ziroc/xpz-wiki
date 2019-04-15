package net.avtolik.xpz_wiki.service;

import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.avtolik.xpz_wiki.model.Item;

@Controller
public class IndexController {

	@Autowired
	WikiDao wd;


	@GetMapping("/")
	public String greeting(@RequestParam(name="search", required=false) String search, Model model) {
		if(search!= null && !search.equals("")) {
			System.out.println("searchig for "+search);
			List<Item> result = wd.getNames().entrySet().stream().filter(i -> i.getKey().toUpperCase().contains(search.toUpperCase()))
					.map(i -> wd.getItems().get(i.getValue())).collect(Collectors.toList());
			System.out.println("found "+result.size());
			if(result.size()!= 0)
				model.addAttribute("result", result);
		}
		
		Item [] items = new Item [5];

		Random r = new Random();
		String [] itemIds = wd.getItems().keySet().toArray(new String[0]);

		for (int i = 0; i < items.length; i++) {
			int random = r.nextInt(wd.getItems().size());
			items[i] = wd.getItems().get(itemIds[random]);
		}

		model.addAttribute("test", "testtt");
		model.addAttribute("items", items);
		return "index";
	}
}
