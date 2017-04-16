package es.euiv.sections.music;

import es.euiv.utils.FileUtils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author fran
 */
public class Music {
    private final String MUSIC = "/music/";
    private final String ASSET = "/music/music.asset";
    private final String SONGS = "/music/songs.txt";
    private final String LOCAL = "/localisation/music_l_english.yml";
    
    private String directory;
    
    
    public Music(String directory){
        this.directory=directory;
    }
    
    public void addMusic(File song){
        //Quitar tildes, espacios en blanco y caracteres extraños
        String formatedName = song.getName().trim().toLowerCase().replaceAll(" ", "_").replaceAll(",", "");
        formatedName = formatedName.replaceAll("á", "a").replaceAll("é", "e").replaceAll("í", "i").replaceAll("ó", "o").replaceAll("ú", "u");
        String noExtension = FileUtils.removeExtension(formatedName);
         
        //Modificar los ficheros
        generateAsset(noExtension, formatedName);
        generateSongs(noExtension);
        generateLocal(noExtension, FileUtils.removeExtension(song.getName()));
        
        //Copiar el archivo
        try{
            Files.copy(song.toPath(), new File(directory+MUSIC+formatedName).toPath(), StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException ex){
            System.err.println(ex.getMessage());
        }
    }
    
    private void generateAsset(String format, String ext){
        String output = "music = { name =\""+format+"\"  file =\""+ext+"\"}";
        FileUtils.appendText(new File(directory+ASSET), output);
    }
    
    private void generateSongs(String format){
        String output = "song = { name =\""+format+"\" chance = { modifier = { factor = 1.25 } } }";
        FileUtils.appendText(new File(directory+SONGS), output);
    }
    
    private void generateLocal(String format, String original){
        String output = " "+format+": \""+original+"\"";
        FileUtils.appendText(new File(directory+LOCAL), output);
    }
}
