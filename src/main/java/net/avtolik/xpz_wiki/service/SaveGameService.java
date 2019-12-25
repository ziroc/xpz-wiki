package net.avtolik.xpz_wiki.service;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import net.avtolik.xpz_wiki.model.saveFile.SaveGame;
import net.avtolik.xpz_wiki.model.saveFile.SaveGameJson;
import net.avtolik.xpz_wiki.model.saveFile.StorageException;

@Service
public class SaveGameService {

	@Autowired
	WikiDao wd;
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

	public void processParsedJson(SaveGameJson saveGameJson, HttpSession session) {
		// TODO Auto-generated method stub
		
	}


}
