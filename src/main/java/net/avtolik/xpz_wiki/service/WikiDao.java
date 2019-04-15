package net.avtolik.xpz_wiki.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.representer.Representer;

import net.avtolik.xpz_wiki.model.Article;
import net.avtolik.xpz_wiki.model.Dictionary;
import net.avtolik.xpz_wiki.model.Item;
import net.avtolik.xpz_wiki.model.PiratezRules;


@Controller
public class WikiDao {

	private Map<String, Object> dict;
	private HashMap<String, Item> items;
	private HashMap<String, String> names;
	private HashMap<String, Article> articles;

	@EventListener(ApplicationReadyEvent.class)
	public void loadData() {
		System.out.println("Context loaded, trying to read items from file");
		loadItems();
		loadDictionary();
		names = new HashMap<>(items.size());
		
		for (Map.Entry<String, Item> entry : items.entrySet()) {
			Object rName = dict.get(entry.getValue().getName()) ;
			if(rName!=null) {
				entry.getValue().setRealName(rName.toString()); 
				names.put(rName.toString(), entry.getKey());
			}
		}
		
		
	}

	private void loadItems() {

		try {
			Representer representer = new Representer();
			representer.getPropertyUtils().setSkipMissingProperties(true);
			
			Yaml yaml = new Yaml(new Constructor(PiratezRules.class), representer);
			InputStream inputStream = this.getClass()
					.getClassLoader()
					.getResourceAsStream("Piratez.rul");

			PiratezRules rules = yaml.load(inputStream);

			List<Item> researchItems = rules.getResearch() ;
			List<Article> articleList = rules.getUfopaedia();
			List<Item> filteredResearchItems = researchItems.stream().filter(i -> i.getDelete() == null).collect(Collectors.toList());
			List<Article> filteredArticles = articleList.stream().filter(i -> i.getDelete() == null).collect(Collectors.toList());

			items = new HashMap<>();
			articles = new HashMap<>();
			filteredResearchItems.stream().forEach(i -> items.put(i.getName(), i));
			filteredArticles.stream().forEach(a -> articles.put(a.getId(), a));
			
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

	public Map<String, Item> getItems() {
		return items;
	}

	public HashMap<String, String> getNames() {
		return names;
	}

	public void setNames(HashMap<String, String> names) {
		this.names = names;
	}

	public HashMap<String, Article> getArticles() {
		return articles;
	}

	public void setDict(Map<String, Object> dict) {
		this.dict = dict;
	}

	public void setItems(HashMap<String, Item> items) {
		this.items = items;
	}

	public void setArticles(HashMap<String, Article> articles) {
		this.articles = articles;
	}

}
