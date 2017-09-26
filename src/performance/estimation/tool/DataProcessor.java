package performance.estimation.tool;

import dataBean.Rectangle;
import dataBean.Point;
import dataBean.FileReader;
import dataBean.Pair;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;

public class DataProcessor {
    
    private File gtFile, doFile, famDir;
    
    private ArrayList<String> keys;
    
    private Hashtable<String, ArrayList<Pair>> result;
    private Hashtable<String, ArrayList<Rectangle>> gtTable, doTable;
    
    
    public DataProcessor(File gtFile, File doFile, File famDir) {
        
        this.gtFile = gtFile;
        this.doFile = doFile;
        this.famDir = famDir;
        
        this.keys = new ArrayList<String>();
        
        this.result = new Hashtable<String, ArrayList<Pair>>();
        this.gtTable = new Hashtable<String, ArrayList<Rectangle>>();
        this.doTable = new Hashtable<String, ArrayList<Rectangle>>();
        
        this.readGTFile();
        this.readDOFile();
    }
    
    private void readGTFile() {
        
        try {
            FileReader fr = new FileReader(this.gtFile);
            
            String line;
            while((line = fr.readLine()) != null) {
                String [] elements = line.split(" ");
                String key = elements[0];
                
                if(!this.keys.contains(key)) this.keys.add(key);
                
                Point A = new Point(Double.parseDouble(elements[1]), Double.parseDouble(elements[2]));
                Point B = new Point(Double.parseDouble(elements[3]), Double.parseDouble(elements[4]));
                
                if(!this.gtTable.containsKey(key)) {
                    ArrayList<Rectangle> rs = new ArrayList<Rectangle>();
                    rs.add(new Rectangle(A, B, Rectangle.GROUND_TRUTH));
                    this.gtTable.put(key, rs);
                }
                else this.gtTable.get(key).add(new Rectangle(A, B, Rectangle.GROUND_TRUTH));
            }
            
            fr.close();
        }
        catch(IOException e) {
            System.out.println(e.toString());
        }
    }
    
    private void readDOFile() {
        
        try {
            FileReader fr = new FileReader(this.doFile);
            
            String line;
            while((line = fr.readLine()) != null) {
                String [] elements = line.split(" ");
                String key = elements[0];
                
                if(!this.keys.contains(key)) this.keys.add(key);
                
                Point A = new Point(Double.parseDouble(elements[1]), Double.parseDouble(elements[2]));
                Point B = new Point(Double.parseDouble(elements[3]), Double.parseDouble(elements[4]));
                
                if(!this.doTable.containsKey(key)) {
                    ArrayList<Rectangle> rs = new ArrayList<Rectangle>();
                    rs.add(new Rectangle(A, B, Rectangle.DETECTED_OBJECT));
                    this.doTable.put(key, rs);
                }
                else this.doTable.get(key).add(new Rectangle(A, B, Rectangle.DETECTED_OBJECT));
            }
            
            fr.close();
        }
        catch(IOException e) {
            System.out.println(e.toString());
        }
    }
    
    public void execute() {
        
        this.readGTFile();
        this.readDOFile();
        
        this.process();
    }
    
    private void process() {
        
        for(String key : keys) {
            ArrayList<Rectangle> gtrs = gtTable.get(key);
            ArrayList<Rectangle> dors = doTable.get(key);
            
            for(Rectangle gtr : gtrs) {
                for(Rectangle dor : dors) {
                    double ratio = -1;
                    double gt_lt_x = gtr.getLT().getX();
                    double gt_lt_y = gtr.getLT().getY();
                    double gt_rb_x = gtr.getRB().getX();
                    double gt_rb_y = gtr.getRB().getY();
                    double do_lt_x = dor.getLT().getX();
                    double do_lt_y = dor.getLT().getY();
                    double do_rb_x = dor.getRB().getX();
                    double do_rb_y = dor.getRB().getY();
                    
                    if(!((do_lt_y > gt_rb_y) || (gt_lt_y > do_rb_y) || (gt_lt_x > do_rb_x) || (do_lt_x > gt_rb_x))) {
                        
                        double [] xArr = {gt_lt_x, gt_rb_x, do_lt_x, do_rb_x};
                        double [] yArr = {gt_lt_y, gt_rb_y, do_lt_y, do_rb_y};
                        
                        Arrays.sort(xArr);
                        Arrays.sort(yArr);
                        
                        double intersection = (xArr[2] - xArr[1]) * (yArr[2] - yArr[1]);
                        double union = (gt_rb_x - gt_lt_x) * (gt_rb_y - gt_lt_y) + (do_rb_x - do_lt_x) * (do_rb_y - do_lt_y) - intersection;
                        ratio = intersection / union;
                    }
                    
                    if(!this.result.containsKey(key)){
                        ArrayList<Pair> ps = new ArrayList<Pair>();
                        ps.add(new Pair(gtr, dor, ratio));
                        this.result.put(key, ps);
                    }
                    else this.result.get(key).add(new Pair(gtr, dor, ratio));
                    
                    Collections.sort(this.result.get(key), new Comparator<Pair>(){
                        public int compare(Pair p1, Pair p2) {
                           return p1.getRatio() < p1.getRatio() ? -1 : p1.getRatio() > p1.getRatio() ? 1 : 0;
                        }
                    });
                    
                    for(Pair p : this.result.get(key)) System.out.print(p.getRatio() + " ");
                }
            }
        }
    }
}
