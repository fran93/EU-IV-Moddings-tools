package es.euiv.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

/**
 *
 * @author fran
 */
public class FileUtils {
    /**
     * Método que añade una línea al final del fichero
     * @param f
     * @param text 
     */
    public static void appendText(File f, String text){
        try(FileWriter fw = new FileWriter(f, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println();
            out.print(text);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * Quita la extensión del archivo
     * @return 
     */
    public static String removeExtension(String name){
        return name.length()>4 ? name.substring(0, name.length()-4) : name;
    }
    
    public static void storeProperty(String name, String value){
        try(FileOutputStream output = new FileOutputStream("config.properties")){
            Properties prop = new Properties();
            prop.setProperty(name, value);
            prop.store(output, null);
        }catch(IOException ex){
            System.err.println(ex.getMessage());
        }
    }
    
    public static String getProperty(String name){
        String result = null;
         try(FileInputStream input = new FileInputStream("config.properties")){
             Properties prop = new Properties();
             prop.load(input);
             result = prop.getProperty(name);        
         }catch(IOException ex){
            System.err.println(ex.getMessage());
        }
         return result;
    }
}
