package es.euiv.main;

import es.euiv.sections.music.Music;
import es.euiv.utils.FileUtils;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author fran
 */
public class MainController implements Initializable {

    private File modDirectory;

    @FXML
    TextField directory;
    @FXML
    TitledPane dragmusic;
    @FXML
    ProgressBar musicProgress;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * Mostrar para seleccionar el directorio
     *
     * @param event
     */
    @FXML
    protected void showFileChooser(ActionEvent event) {
        //obtener la ventana
        Node source = (Node) event.getSource();
        Window stage = source.getScene().getWindow();
        
        //Obtener la última ruta 
        String lastDirectory = FileUtils.getProperty("modDirectory");

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select your mods directory ");
        //cambiar la dirección
        if(lastDirectory!=null){
            directoryChooser.setInitialDirectory(new File(lastDirectory));
        }

        //Obtener el directorio
        File fdirectory = directoryChooser.showDialog(stage);
        if (fdirectory.isDirectory()) {
            modDirectory = fdirectory;
            directory.setText(fdirectory.getAbsolutePath());
            FileUtils.storeProperty("modDirectory", fdirectory.getAbsolutePath());
        }
    }

    @FXML
    protected void onOverMusicFile(DragEvent event) {
        musicProgress.setProgress(0);
        if (event.getDragboard().hasFiles()) {
            /* allow for both copying and moving, whatever user chooses */
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    @FXML
    protected void onDropMusicFile(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            //Obtener las canciones
            Music m = new Music(modDirectory.getAbsolutePath());
            List<File> files = db.getFiles();
            for(File song: files){
                m.addMusic(song);
                //Mostrar mensaje
                dragmusic.setText(dragmusic.getText()+"\n"+song.getName()+" has been inserted.");
            }
            success = true;
        }
        musicProgress.setProgress(1);
        event.setDropCompleted(success);
        event.consume();
    }
}
