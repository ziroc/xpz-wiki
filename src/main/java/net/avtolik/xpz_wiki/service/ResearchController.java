package net.avtolik.xpz_wiki.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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

	Logger logger = LoggerFactory.getLogger(ResearchController.class);

	@Autowired
	WikiDao wd;

	@GetMapping("/research")
	public String getResearch(@RequestParam(name="id", required=false, defaultValue="World") String id, Model model, HttpSession session) {

		Research itemOrig = wd.getResearchItems().get(id);

		if(itemOrig == null) {
			Article a = wd.getArticles().get(id);

			if(a == null) {
				model.addAttribute("error", "Research Item not found!");
				return "research";
			}
			logger.debug("Research item not found, only article + " + itemOrig);		
			itemOrig = new Research();
			itemOrig.setName(id);
			itemOrig.setRealName((String)wd.getDict().get(id));
			model.addAttribute("item", itemOrig);
			return "research";
		}

		logger.debug("Research Item found + " + itemOrig);

		Research item = new Research();
		BeanUtils.copyProperties(itemOrig, item);
		

		String desc = getDescription(item);
		desc = desc.replaceAll("\\{NEWLINE\\}", "\n");
		item.setDescription(desc);

		List<Research> unlockedBy = new ArrayList<>();
		if (item.getUnlockedBy() != null) {
			for (String unlock : item.getUnlockedBy()) {
				Research i = new Research();
				i.setName(unlock);
				i.setRealName(wd.getResearchItems().get(unlock).getRealName());
				unlockedBy.add(i);
			}
		}

		List<Research> deps = new ArrayList<>();
		if (item.getDependencies() != null) {
			for (String depName : item.getDependencies()) {
				if(item.getUnlockedBy() != null && item.getUnlockedBy().contains(depName)) {
					continue; // skip
				}
				Research depResearch = new Research();
				depResearch.setName(depName);
				Research research = wd.getResearchItems().get(depName);
				logger.debug("GGG" + research);
				if(research == null) {
					logger.debug("res is null, setting empty id");
					depResearch.setName("#");
					depResearch.setRealName((String) wd.getDict().get(depName));
					deps.add(depResearch);
					continue;
				}
				depResearch.setRealName(research.getRealName());
				deps.add(depResearch);
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

		List<Research> disables = new ArrayList<>();
		if (item.getDisables() != null) {
			for (String disabled : item.getDisables()) {
				Research i = new Research();
				i.setName(disabled);
				i.setRealName(wd.getResearchItems().get(disabled).getRealName());
				disables.add(i);
			}
		}

		List<Research> leadsToList = new ArrayList<>();
		if (item.getLeadsTo() != null) {
			for (String leadsToItem : item.getLeadsTo()) {
				Research i = new Research();
				i.setName(leadsToItem);
				i.setRealName(wd.getResearchItems().get(leadsToItem).getRealName());
				leadsToList.add(i);
			}
		}

		List<Research> freeList = new ArrayList<>();
		if (item.getGetOneFree() != null) {
			for (String free : item.getGetOneFree()) {
				Research i = new Research();
				i.setName(free);
				Research realResearch = wd.getResearchItems().get(free);
				if (realResearch != null) {
					i.setRealName(realResearch.getRealName());
					freeList.add(i);
				}
				else {					
					i.setRealName((String)wd.getDict().get(free));
					freeList.add(i);
				}
			}
		}


		if (item.getGetOneFreeProtected() != null && !item.getGetOneFreeProtected().isEmpty()) {
			HashMap<Research, List<Research>> m = new HashMap<>();
			item.getGetOneFreeProtected().forEach((k, v) -> {
				Research rm = new Research(k);
				rm.setRealName(wd.getDict().get(k).toString());
				List<Research> l = new ArrayList<Research>(v.size());
				v.forEach(i -> {
					Research r = new Research(i);
					r.setRealName(wd.getDict().get(i).toString());
					System.out.println(r);
					l.add(r);
				});
				m.put(rm, l);				
			});
			model.addAttribute("protectedMap", m);
		}


		if (wd.getItems().containsKey(id))
			model.addAttribute("showItemLink", true);


		model.addAttribute("item", item);
		model.addAttribute("deps", deps);
		model.addAttribute("unlocks", unlocks);
		model.addAttribute("disables", disables);
		model.addAttribute("unlockedBy", unlockedBy);
		model.addAttribute("leadsToList", leadsToList);
		model.addAttribute("newLineChar", '\n');
		model.addAttribute("freeList", freeList);
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
				logger.debug("lookup found");
				return getDescription(lookup);
			}
		}

		if(desc == null && item.getCost() > 0) {
			logger.debug("trying slow search - for required");

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