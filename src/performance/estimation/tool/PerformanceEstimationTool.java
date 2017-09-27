package performance.estimation.tool;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PerformanceEstimationTool extends Application {
    
    private static Stage mainStage;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        PerformanceEstimationTool.mainStage = stage;
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public static File FolderSelector(String title, String defaultDir) {
        
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle(title);
        dc.setInitialDirectory(new File(defaultDir));
        return dc.showDialog(PerformanceEstimationTool.mainStage);
    }
    
    public static File FileSelector(String title, String defaultDir) {
        
        FileChooser fc = new FileChooser();
        fc.setTitle(title);
        fc.setInitialDirectory(new File(defaultDir));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        return fc.showOpenDialog(PerformanceEstimationTool.mainStage);
    }
}