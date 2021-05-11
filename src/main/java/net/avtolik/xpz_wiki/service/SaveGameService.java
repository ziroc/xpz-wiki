package net.avtolik.xpz_wiki.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import net.avtolik.xpz_wiki.model.Armor;
import net.avtolik.xpz_wiki.model.saveFile.Base;
import net.avtolik.xpz_wiki.model.saveFile.SaveGame;
import net.avtolik.xpz_wiki.model.saveFile.SaveGameJson;
import net.avtolik.xpz_wiki.model.saveFile.Soldier;
import net.avtolik.xpz_wiki.model.saveFile.StorageException;
import net.avtolik.xpz_wiki.model.saveFile.UfopediaStatus;

@Service
public class SaveGameService {

	@Autowired
	WikiDao wd;
	private String[] split;
	
	private static String [] girls = {"STR_SOLDIER","STR_SOLDIER_S", "STR_SOLDIER_M", 
			"STR_SOLDIER_V 	", "STR_SOLDIER_X", "STR_SOLDIER_R", "STR_SOLDIER_W"};
	
	private static List<String> girlsList = Arrays.asList(girls);
	private static final String LOKNAR = "STR_SOLDIER_LOKNAR";
	private static final String SLAVE = "STR_SOLDIER_SLAVE";
	private static final String PEASANT = "STR_SOLDIER_PEASANT";
	private static final String HERO = "STR_SOLDIER_HERO";

	
//	private final Path rootLocation = Paths.get("upload-dir");

	@Async
	public void processSaveGame(MultipartFile file, HttpSession session) {

		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		
		try {
			if (file.isEmpty()) {
				session.setAttribute("saveGameUpLoaded", false);
				throw new StorageException("Failed to store empty file " + filename);
			}
			if (filename.contains("..")) {
				session.setAttribute("saveGameUpLoaded", false);
				// This is a security check
				throw new StorageException(
						"Cannot store file with relative path outside current directory "
								+ filename);
			}
			
			SaveGame savedGame = wd.loadAndProcessSaveGames(file.getInputStream());
			if(savedGame != null)
				session.setAttribute("savegame", savedGame);
			else
				session.setAttribute("saveGameUpLoaded", false);
			
//			try (InputStream inputStream = file.getInputStream()) {
//				Files.copy(inputStream, this.rootLocation.resolve(filename),
//						StandardCopyOption.REPLACE_EXISTING);
//			}
			
			
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file " + filename, e);
		}

		
	}

	public List<Armor> getArmorList(UfopediaStatus ufopediaStatus) {
		List<Armor> result = new ArrayList<>();
		
		Set<String> armors = wd.getArmors().keySet();
		for (String armor : armors) {
			if(ufopediaStatus.getMap().containsKey(armor)) {
				//check if its a girl armor
				Armor a = wd.getArmors().get(armor);
				
				if(a.getUnits() != null) {
					if(a.getUnits().contains(PEASANT) || a.getUnits().contains(LOKNAR)) {
						result.add(a);
						continue;
					}
						
					for (String girl : girls) {
						if(a.getUnits().contains(girl)) {
							result.add(a);
							break;
						}
					}					
				}
			}
		}

		return result;
	}
	
	
	public void processParsedJson(SaveGameJson saveGameJson, HttpSession session) {
		// TODO Auto-generated method stub
		
	}

	public void processUfopediaRuleStatus(String payload, HttpSession session) {
		UfopediaStatus u = new UfopediaStatus();

		String[] splitted = payload.split("&");
		
		for (String entry : splitted) {
			split = entry.split("=");
			u.getMap().put(split[0], Integer.parseInt(split[1]));
		}
		session.setAttribute("ufopediaStatus", u);
		session.setAttribute("saveGameUpLoaded", true);
	}

    public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
    	
    	 ObjectMapper objectMapper = new ObjectMapper();
    	 Base b = new Base();
    	 b.setName("Name1");
    	 
    	 ArrayList<Base> ar =  new ArrayList<>();
    	 ar.add(b);
    	 SaveGame s = new SaveGame();
    	 s.setBases(ar);
    	 
    	  //configure objectMapper for pretty input
         objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

         //write customerObj object to customer2.json file
         objectMapper.writeValue(new File("customer2.json"), s);
    }

	public void processBases(List<Base> bases, HttpSession session) {
		int gals = 0;
		int slaves = 0;
		int loknars = 0;
		int peasants = 0;
		int heroes = 0;
		int other = 0;
		for (Base base : bases) {
			if(base.getSoldiers() == null)
				continue;
			for (Soldier soldier : base.getSoldiers()) {
				if(girlsList.contains(soldier.getType()))
					gals++;
				else if(soldier.getType().equals(LOKNAR))
					loknars++;
				else if(soldier.getType().equals(SLAVE))
					slaves++;
				else if(soldier.getType().equals(PEASANT))
					peasants++;
				else if(soldier.getType().equals(HERO))
					heroes++;
				else 
					other++;
			}
		}
		
		System.out.println("total number of soldiers: " + gals + slaves + loknars + peasants + heroes + other);
		System.out.println("gals: " + gals);
		System.out.println("slaves: " +slaves);
		System.out.println("loknars: " + loknars);
		System.out.println("peasants: " + peasants);
		System.out.println("heroes: " + heroes);
		System.out.println("other: " + other);
	}


}
