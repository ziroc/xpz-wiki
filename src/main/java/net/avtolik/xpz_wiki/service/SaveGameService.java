package net.avtolik.xpz_wiki.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import net.avtolik.xpz_wiki.model.Armor;
import net.avtolik.xpz_wiki.model.saveFile.SaveGame;
import net.avtolik.xpz_wiki.model.saveFile.SaveGameJson;
import net.avtolik.xpz_wiki.model.saveFile.StorageException;
import net.avtolik.xpz_wiki.model.saveFile.UfopediaStatus;

@Service
public class SaveGameService {

	@Autowired
	WikiDao wd;
	private String[] split;
	
	private static String [] girls = {"STR_SOLDIER","STR_SOLDIER_S", "STR_SOLDIER_M", 
			"STR_SOLDIER_V 	", "STR_SOLDIER_X", "STR_SOLDIER_R", "STR_SOLDIER_W"};
//	
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
				
				if(a.getUnits()!=null) {
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



}
