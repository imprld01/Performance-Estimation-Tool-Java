package performance.estimation.tool;

import dataBean.Pack;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Pane pane;
    @FXML
    private TextArea ratio_table;
    @FXML
    private TextField gt_path_text, do_path_text, fm_path_text, fast_go_text;
    @FXML
    private Button gt_path_btn, do_path_btn, frame_path_btn, setup_btn, fast_go_btn,
                   frame_next_btn, frame_back_btn, trouble_next_btn, trouble_back_btn;
    
    private int index;
    private ArrayList<File> list;
    private Hashtable<String, Pack> result;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.index = 0;
        this.list = null;
        this.result = null;
    }
    
    public TextField getFastGoText() {
        
        return this.fast_go_text;
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
        
        this.index = 0;
        this.list = new ArrayList<File>(Arrays.asList(famDir.listFiles()));
        this.result = new DataProcessor(gtFile, doFile, famDir).execute();
        
        this.showImage();
    }
    
    @FXML
    private void FastForwardKeyReleased(KeyEvent event) {
        
        if (event.getCode() == KeyCode.ENTER)  {
            this.index = this.list.indexOf(fast_go_text.getText());
            this.showImage();
        }
    }
    
    @FXML
    private void FastForwardButtonAction(ActionEvent event) {
        
        this.index = this.list.indexOf(fast_go_text.getText());
        this.showImage();
    }
    
    @FXML
    private void NextFrameButtonAction(ActionEvent event) {
        
        ++this.index;
        this.showImage();
    }
    
    @FXML
    private void PreviousFrameButtonAction(ActionEvent event) {
        
        --this.index;
        this.showImage();
    }
    
    @FXML
    private void NextTroubleButtonAction(ActionEvent event) {
        
    }
    
    @FXML
    private void PreviousTroubleButtonAction(ActionEvent event) {
        
    }
    
    public void showImage() {
        
        File imageFile = this.list.get(this.index);
        ImageView canvas = new ImageView(new Image(imageFile.toURI().toString()));
        canvas.setFitHeight(328);
        canvas.setFitWidth(562);
        
        Rectangle rect = new Rectangle(0, 0, 300.56, 100.39);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.RED);
        
        pane.getChildren().add(canvas);
        pane.getChildren().add(rect);
        
        this.fast_go_text.setText(imageFile.getName());
        
        Pack pack = this.result.get(imageFile.getName());
        if(pack == null) this.ratio_table.setText("");
        else this.ratio_table.setText(pack.toString());
    }
    
    public void showNormalGT() {
        
        
    }
    
    public void showNormalDO() {
        
        
    }
    
    public void showTroubleGT() {
        
        
    }
    
    public void showTroubleDO() {
        
        
    }
}