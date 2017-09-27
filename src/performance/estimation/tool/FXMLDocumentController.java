package performance.estimation.tool;

import dataBean.Pack;
import dataBean.Pair;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
        
        if(this.index == this.list.size() - 1) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("!! Warning !!");
            alert.setHeaderText("No Next Frame Available");
            alert.setContentText("There is no next frame available!");
            alert.showAndWait();
        }
        else {
            ++this.index;
            this.showImage();
        }
    }
    
    @FXML
    private void PreviousFrameButtonAction(ActionEvent event) {
        
        if(this.index == 0) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("!! Warning !!");
            alert.setHeaderText("No Previous Frame Available");
            alert.setContentText("There is no previous frame available!");
            alert.showAndWait();
        }
        else {
            --this.index;
            this.showImage();
        }
    }
    
    @FXML
    private void NextTroubleButtonAction(ActionEvent event) {
        
    }
    
    @FXML
    private void PreviousTroubleButtonAction(ActionEvent event) {
        
    }
    
    public void showImage() {
        
        File imageFile = this.list.get(this.index);
        
        Pack pack = this.result.get(imageFile.getName());
        Image image = new Image(imageFile.toURI().toString());
        
        ImageView canvas = new ImageView(image);
        canvas.setFitWidth(this.pane.getWidth());
        canvas.setFitHeight(this.pane.getHeight());
        
        this.pane.getChildren().add(canvas);
        this.drawPackOnImage(pack, image);
        
        this.fast_go_text.setText(imageFile.getName());
        if(pack == null) this.ratio_table.setText("");
        else this.ratio_table.setText(pack.toString());
    }
    
    public void drawPackOnImage(Pack pack, Image image) {
        
        double resizeW = this.pane.getWidth() / image.getWidth();
        double resizeH = this.pane.getHeight() / image.getHeight();
        
        ArrayList<Pair> pairs = pack.getPair();
        ArrayList<dataBean.Rectangle> unpairs = pack.getUnpair();
        
        for(Pair pair : pairs){
            this.showNormalGT(pair.getGT(), resizeW, resizeH);
            this.showNormalDO(pair.getDO(), resizeW, resizeH);
        }
        
        for(dataBean.Rectangle unpair : unpairs) {
            
            if(unpair.getType() == dataBean.Rectangle.GROUND_TRUTH) showTroubleGT(unpair, resizeW, resizeH);
            else showTroubleDO(unpair, resizeW, resizeH);
        }
    }
    
    public void draw(dataBean.Rectangle pos, double resizeW, double resizeH, Color color) {
        
        double x1 = pos.getLT().getX();
        double y1 = pos.getLT().getY();
        double x2 = pos.getRB().getX();
        double y2 = pos.getRB().getY();
        
        Rectangle rect = new Rectangle(x1 * resizeW, y1 * resizeH, (x2 - x1) * resizeW, (y2 - y1) * resizeH);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(color);
        
        this.pane.getChildren().add(rect);
    }
    
    public void showNormalGT(dataBean.Rectangle pos, double resizeW, double resizeH) {
        
        this.draw(pos, resizeW, resizeH, Color.RED);
    }
    
    public void showNormalDO(dataBean.Rectangle pos, double resizeW, double resizeH) {
        
        this.draw(pos, resizeW, resizeH, Color.GREEN);
    }
    
    public void showTroubleGT(dataBean.Rectangle pos, double resizeW, double resizeH) {
        
        this.draw(pos, resizeW, resizeH, new Color(1.0, 0, 0, 0.2));
    }
    
    public void showTroubleDO(dataBean.Rectangle pos, double resizeW, double resizeH) {
        
        this.draw(pos, resizeW, resizeH, new Color(0, 1.0, 0, 0.2));
    }
}