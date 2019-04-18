package net.avtolik.xpz_wiki.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import net.avtolik.xpz_wiki.model.Article;
import net.avtolik.xpz_wiki.model.Dictionary;
import net.avtolik.xpz_wiki.model.Item;
import net.avtolik.xpz_wiki.model.Research;
import net.avtolik.xpz_wiki.model.PiratezRules;


@Controller
public class WikiDao {
	
	private boolean loaded = false;

	private Map<String, Object> dict;
	private HashMap<String, Research> researchItems;
	private HashMap<String, Item> items;
	private HashMap<String, String> researchNames;
	private HashMap<String, String> itemNames;
	private HashMap<String, Article> articles;

	@EventListener(ApplicationReadyEvent.class)
	public void loadData() {
		System.out.println("Context loaded, trying to read items from file");
		loadResearchAndArticles();
		loadDictionary();
		researchNames = new HashMap<>(researchItems.size());
		itemNames = new HashMap<>(items.size());

		// Load real names for research items
		for (Map.Entry<String, Research> entry : researchItems.entrySet()) {
			String name = entry.getValue().getName();
			Object realName = dict.get(name) ;
			if(realName!=null) {
				entry.getValue().setRealName(realName.toString()); 
				researchNames.put(realName.toString(), entry.getKey());
			}
		}

		// Load real names for research items
		for (Map.Entry<String, Item> entry : items.entrySet()) {
			String name = entry.getValue().getName();
			if(name.toUpperCase().contains("BONE"))
				System.out.println(entry.getValue().getName());
			Object realName = dict.get(name) ;
			if(realName != null) {
				entry.getValue().setRealName(realName.toString()); 
				itemNames.put(realName.toString(), entry.getKey());
			}
		}

		loaded = true;
	}

	private void loadResearchAndArticles() {

		try {

			Constructor c = new Constructor(PiratezRules.class);
			c.setPropertyUtils(new PropertyUtils() {
				@Override
				public Property getProperty(Class<? extends Object> type, String name)  {
					if ( name.equals("type")) {
						name = "name";
					}
					return super.getProperty(type, name);
				}
			});
			c.getPropertyUtils().setSkipMissingProperties(true);

			Yaml yaml = new Yaml(c);
			InputStream inputStream = this.getClass()
					.getClassLoader()
					.getResourceAsStream("Piratez.rul");

			PiratezRules rules = yaml.load(inputStream);

			List<Research> researchItemsTemp = rules.getResearch() ;
			List<Article> articleList = rules.getUfopaedia();
			List<Item> itemList = rules.getItems();
			List<Research> filteredResearchItems = researchItemsTemp.stream().filter(i -> i.getDelete() == null).collect(Collectors.toList());
			List<Article> filteredArticles = articleList.stream().filter(i -> i.getDelete() == null).collect(Collectors.toList());
			List<Item> filteredItems = itemList.stream().filter(i -> i.getDelete() == null).collect(Collectors.toList());

			researchItems = new HashMap<>();
			articles = new HashMap<>();
			items = new HashMap<>();
			filteredResearchItems.stream().forEach(i -> researchItems.put(i.getName(), i));
			filteredArticles.stream().forEach(a -> articles.put(a.getId(), a));
			filteredItems.stream().forEach(a -> items.put(a.getName(), a));

			System.out.println("Reseach items and articles loaded");
			//		} catch (IOException e) {
			//			System.out.println("Cannot load the items. Probably cannot find the file.");		
		} catch (Exception e) {
			System.out.println("Cannot load the items: "+e.getMessage());
		}

	}

	private void loadDictionary() {
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

			InputStream inputStream = this.getClass()
					.getClassLoader()
					.getResourceAsStream("en-US.yml");

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



}
