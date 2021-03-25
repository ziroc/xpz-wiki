package net.avtolik.xpz_wiki.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import net.avtolik.xpz_wiki.model.Armor;
import net.avtolik.xpz_wiki.model.Article;
import net.avtolik.xpz_wiki.model.Dictionary;
import net.avtolik.xpz_wiki.model.Item;
import net.avtolik.xpz_wiki.model.PiratezRules;
import net.avtolik.xpz_wiki.model.Research;
import net.avtolik.xpz_wiki.model.saveFile.Base;
import net.avtolik.xpz_wiki.model.saveFile.CurrentResearch;
import net.avtolik.xpz_wiki.model.saveFile.SaveGame;
import net.avtolik.xpz_wiki.model.saveFile.SaveGameMetaData;


@Controller
public class WikiDao {

	private boolean loaded = false;

	private Map<String, Object> dict;
	private HashMap<String, Research> researchItems;
	private HashMap<String, String> researchNames;
	private HashMap<String, Item> items;
	private HashMap<String, String> itemNames;
	private HashMap<String, Armor> armors;
	private HashMap<String, String> armorNames;
	private HashMap<String, Article> articles;

	private SaveGameMetaData metaData = null;
	private List<Research> saveGameResearchList;


	@Autowired
	private ApplicationArguments applicationArguments;


	@EventListener(ApplicationReadyEvent.class)
	public void loadData() {
		System.out.println("Context loaded");



		loaded = true;
	}

	private void processItems() {
		// Load real names for  items
		for (Map.Entry<String, Item> entry : items.entrySet()) {
			String name = entry.getValue().getName();
			Object realName = dict.get(name) ;
			if(realName != null) {
				entry.getValue().setRealName(realName.toString()); 
				itemNames.put(realName.toString(), entry.getKey());
			}
		}
	}

	public SaveGame loadAndProcessSaveGames(InputStream inputStream) {
		SaveGame result;
		result = loadSave(inputStream);

		if(result == null)
			return null;

		List<Base> bases = result.getBases();
		for (Base base : bases) {
			System.out.println("base: "+base.getName());
			List<CurrentResearch> research = base.getResearch();

			if(research == null) // no research in this base
				continue;
			for (CurrentResearch res : research) {
				result.getCurrentResearch().add(researchItems.get(res.getProject()));
				System.out.print("" + res.getProject());
				System.out.println(" , realname: "+ dict.get(res.getProject()));
			}
		}

		return result;
	}

	private void processArmors() {
		// Load real names for  armors
		for (Map.Entry<String, Armor> entry : armors.entrySet()) {
			Armor armor = entry.getValue();
			Object realName = dict.get(armor.getName()) ;
			if(realName != null) {
				//				System.out.println("real name  found for :" + armor.getName() + "  "+ realName);
				entry.getValue().setRealName(realName.toString()); 
				armorNames.put(realName.toString(), entry.getKey());
			}
			else 
				System.out.println("real name not found for :" + armor.getName());

			if(armor.getStoreItem() != null) {
				//				System.out.println("Armor " + armor.getName() +" has store name " +armor.getStoreItem());
				Item item = items.get(armor.getStoreItem());

				if(item != null) {
					//					System.out.println("found item for it: " + item.getName());
					item.setArmorName(armor.getName());
				}
				else
					System.out.println("item not found for this armor: "+armor.getName());
			}
		}
	}

	private void processResearchItems(Map<String,Research> map) {
		System.out.println("Entering processResearchItems, count how many times");

		Map<String,Research> tempMap = new HashMap<>();

		// Load real names for research items,
		for (Map.Entry<String, Research> entry : map.entrySet()) {
			String thisName = entry.getValue().getName();
			Object realName = dict.get(thisName) ;
			if(realName != null) {
				entry.getValue().setRealName(realName.toString()); 
				researchNames.put(realName.toString(), entry.getKey());
			}
			// add "Leads-To" items
			if( entry.getValue().getDependencies() != null)
				for (String dep: entry.getValue().getDependencies()) {
					if(researchItems.get(dep ) != null) {
						researchItems.get(dep).getLeadsTo().add(thisName);
					}
					else if (tempMap.get(dep) != null) {
						tempMap.get(dep).getLeadsTo().add(thisName);
					}
					else if (articles.get(dep) != null) {
						System.out.println("found dep for: " + thisName + " with no research item: " + dep);
						Research r = new Research();
						r.setName(dep);
						r.getLeadsTo().add(thisName);						
						tempMap.put(dep, r);
					} 
					else {
						System.out.println("research is missing: " + dep);
						continue;
					}
				}
		}
		if(!tempMap.isEmpty())
			processResearchItems(tempMap);
		map.putAll(tempMap);
	}

	public SaveGame loadSave(InputStream inputStream) {
		SaveGame result = null;
		String fileName = null;
		boolean customPath = false;

		System.out.println("Loading SaveGame");
		List<String> newSaveGamePath = applicationArguments.getOptionValues("savegame");

		if (newSaveGamePath != null && newSaveGamePath.size() >0) {
			fileName = newSaveGamePath.get(0);
			System.out.println("using custom save game location: " + inputStream);
			customPath = true;
		}

		if (inputStream ==null && !customPath ) {
			System.out.println("cannot load save game");
			return null;
		}
		Constructor metaConstr = new Constructor(SaveGameMetaData.class);
		Constructor saveGameConstr = new Constructor(SaveGame.class);
		PropertyUtils putilsMeta = new PropertyUtils();
		putilsMeta.setSkipMissingProperties(true);
		metaConstr.setPropertyUtils(putilsMeta);
		saveGameConstr.setPropertyUtils(putilsMeta);

		Yaml yamlMeta = new Yaml(metaConstr);
		Yaml yamlSaveGame = new Yaml(saveGameConstr);

		boolean endFirstDoc = false;

		try { 
			if (customPath)
				inputStream =  new FileInputStream(fileName);

			MyBufferedReader br = new MyBufferedReader(new InputStreamReader(inputStream, "UTF-8"));

			StringBuilder sb = new StringBuilder();
			while(!endFirstDoc) {
				String line = br.readLine();
				if(line.contains("---")) {
					endFirstDoc = true;
					break;
				}
				//		    	System.out.println(line);
				sb.append(line+"\n");
			}

			if(endFirstDoc) { 
				metaData = yamlMeta.load  (sb.toString());
				System.out.println("loading meta: "+metaData.getName());
				result = yamlSaveGame.load(br);
			}



		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		System.out.println("Save game name: " + metaData.getName());
		return result;

	}

	List<String> toFixCase = Arrays.asList(new String[]{"ToArmorPre", "ArmorEffectiveness", "ToArmor", "ToMorale", "ToWound"});

	private void loadResearchAndArticles() {

		InputStream inputStream;
		try {
			Constructor c = new Constructor(PiratezRules.class);
			c.setPropertyUtils(new PropertyUtils() {
				@Override
				public Property getProperty(Class<? extends Object> type, String name)  {
					if ( name.equals("type")) 
						name = "name";
					else if (toFixCase.contains(name)) 
						name = name.substring(0, 1).toLowerCase() + name.substring(1);
					return super.getProperty(type, name);
				}
			});
			c.getPropertyUtils().setSkipMissingProperties(true);

			Yaml yaml = new Yaml(c);

			inputStream = this.getClass()
					.getClassLoader()
					.getResourceAsStream("Piratez.rul");

			if(inputStream == null)
				inputStream =  new FileInputStream("Piratez.rul");

			PiratezRules rules = yaml.load(inputStream);

			List<Research> researchItemsTemp = rules.getResearch() ;
			List<Article> articleList = rules.getUfopaedia();
			List<Item> itemList = rules.getItems();
			List<Armor> armorList = rules.getArmors();
			List<Research> filteredResearchItems = researchItemsTemp.stream().filter(i -> i.getDelete() == null).collect(Collectors.toList());
			List<Article> filteredArticles = articleList.stream().filter(i -> i.getDelete() == null).collect(Collectors.toList());
			List<Item> filteredItems = itemList.stream().filter(i -> i.getDelete() == null).collect(Collectors.toList());

			researchItems = new HashMap<>();
			articles = new HashMap<>();
			items = new HashMap<>();
			armors = new HashMap<>();

			filteredResearchItems.stream().forEach(i -> researchItems.put(i.getName(), i));
			filteredArticles.stream().forEach(a -> articles.put(a.getId(), a));
			filteredItems.stream().forEach(a -> items.put(a.getName(), a));
			armorList.stream().forEach(a -> armors.put(a.getName(), a));

			System.out.println("Reseach items and articles loaded");

		} catch (Exception e) {
			System.out.println("Cannot load the items: "+e.getMessage());
			System.exit(-1);
		}

	}

	private void loadDictionary() {
		InputStream inputStream;
		try {
			Constructor c = new Constructor(Dictionary.class);
			c.setPropertyUtils(new PropertyUtils() {
				@Override
				public Property getProperty(Class<? extends Object> type, String name)  {
					if ( name.indexOf('-') > -1 ) {
						name = name.replace("-", "");
					}
					return super.getProperty(type, name);
				}
			});
			Yaml yaml = new Yaml(c);

			inputStream = this.getClass()
					.getClassLoader()
					.getResourceAsStream("en-US.yml");

			if(inputStream == null)
				inputStream =  new FileInputStream("en-US.yml");

			Dictionary d = yaml.load(inputStream);

			System.out.println("Dic loaded: " + d.getEnUS().get("STR_TECHNOCRACY").equals("THE TECHNOCRACY"));
			dict = d.getEnUS();
		} catch (Exception e) {
			System.out.println("Cannot load the Dictionary: "+e.getMessage());
		}
	}

	public Map<String, Object> getDict() {
		return dict;
	}

	public HashMap<String, Research> getResearchItems() {
		return researchItems;
	}

	public HashMap<String, Item> getItems() {
		return items;
	}

	public HashMap<String, String> getResearchNames() {
		return researchNames;
	}

	public HashMap<String, String> getItemNames() {
		return itemNames;
	}

	public HashMap<String, Article> getArticles() {
		return articles;
	}

	public void setDict(Map<String, Object> dict) {
		this.dict = dict;
	}

	public void setResearchItems(HashMap<String, Research> researchItems) {
		this.researchItems = researchItems;
	}

	public void setItems(HashMap<String, Item> items) {
		this.items = items;
	}

	public void setResearchNames(HashMap<String, String> researchNames) {
		this.researchNames = researchNames;
	}

	public void setItemNames(HashMap<String, String> itemNames) {
		this.itemNames = itemNames;
	}

	public void setArticles(HashMap<String, Article> articles) {
		this.articles = articles;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public HashMap<String, Armor> getArmors() {
		return armors;
	}

	public HashMap<String, String> getArmorNames() {
		return armorNames;
	}

	public List<Research> getSaveGameResearchList() {
		return saveGameResearchList;
	}
}
