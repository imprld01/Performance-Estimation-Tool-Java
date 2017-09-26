package performance.estimation.tool;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class FXMLDocumentController implements Initializable {
    
    @FXML
    private ImageView canvas;
    @FXML
    private TextArea ratio_table;
    @FXML
    private TextField gt_path_text, do_path_text, fm_path_text, fast_go_text;
    @FXML
    private Button gt_path_btn, do_path_btn, frame_path_btn, setup_btn, fast_go_btn,
                   frame_next_btn, frame_back_btn, trouble_next_btn, trouble_back_btn;
    
    private DataProcessor dp;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    @FXML
    private void GroundTruthPathButtonAction(ActionEvent event) {
        
        String curr = PerformanceEstimationTool.FileSelector(
                "Please Select the Ground Truth File Path", System.getProperty("user.dir")).getAbsolutePath();
        this.gt_path_text.setText(curr);
    }
    
    @FXML
    private void DetectedOutputPathButtonAction(ActionEvent event) {
        
         String curr = PerformanceEstimationTool.FileSelector(
                 "Please Select the Detected Object File Path", System.getProperty("user.dir")).getAbsolutePath();
         this.do_path_text.setText(curr);
    }
    
    @FXML
    private void FrameFolderPathButtonAction(ActionEvent event) {
        
         String curr = PerformanceEstimationTool.FolderSelector(
                 "Please Select the Frame Folder Path", System.getProperty("user.dir")).getAbsolutePath();
         this.fm_path_text.setText(curr);
    }
    
    @FXML
    private void SetupButtonAction(ActionEvent event) {
        
        File gtFile = new File(this.gt_path_text.getText());
        File doFile = new File(this.do_path_text.getText());
        File famDir = new File(this.fm_path_text.getText());
        
        this.dp = new DataProcessor(gtFile, doFile, famDir);
        this.dp.execute();
    }
    
    @FXML
    private void FastForwardKeyReleased(KeyEvent event) {
        
        if (event.getCode() == KeyCode.ENTER)  {
            String wanted = fast_go_text.getText();
        }
    }
    
    @FXML
    private void FastForwardButtonAction(ActionEvent event) {
        
    }
    
    @FXML
    private void NextFrameButtonAction(ActionEvent event) {
        
    }
    
    @FXML
    private void PreviousFrameButtonAction(ActionEvent event) {
        
    }
    
    @FXML
    private void NextTroubleButtonAction(ActionEvent event) {
        
    }
    
    @FXML
    private void PreviousTroubleButtonAction(ActionEvent event) {
        
    }
}