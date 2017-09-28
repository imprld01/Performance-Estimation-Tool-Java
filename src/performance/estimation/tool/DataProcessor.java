package performance.estimation.tool;

import dataBean.Rectangle;
import dataBean.Point;
import dataBean.FileReader;
import dataBean.Pack;
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
    
    private ArrayList<String> keys, troubleKey;
    
    private Hashtable<String, ArrayList<Rectangle>> gtTable, doTable;
    
    public DataProcessor(File gtFile, File doFile, File famDir) {
        
        this.gtFile = gtFile;
        this.doFile = doFile;
        this.famDir = famDir;
        
        this.keys = new ArrayList<String>();
        this.troubleKey = new ArrayList<String>();
        
        this.gtTable = new Hashtable<String, ArrayList<Rectangle>>();
        this.doTable = new Hashtable<String, ArrayList<Rectangle>>();
    }
    
    public ArrayList<String> getTroubleKey() {
        
        return this.troubleKey;
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
    
    public Hashtable<String, Pack> execute(double input_threshold) {
        
        this.readGTFile();
        this.readDOFile();
        
        return this.process(input_threshold);
    }
    
    private Hashtable<String, Pack> process(double input_threshold) {
        
        Hashtable<String, Pack> result = new Hashtable<String, Pack>();
        
        for(String key : this.keys) {
            
            ArrayList<Pair> temp = new ArrayList<Pair>();
            ArrayList<Rectangle> list = new ArrayList<Rectangle>();
            ArrayList<Rectangle> gtrs = this.gtTable.get(key);
            ArrayList<Rectangle> dors = this.doTable.get(key);
            
            if(gtrs != null && dors != null) {
                
                for(Rectangle gtr : gtrs) if(!list.contains(gtr)) list.add(gtr);
                for(Rectangle dor : dors) if(!list.contains(dor)) list.add(dor);
                
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

                        temp.add(new Pair(gtr, dor, ratio));
                    }
                }
            }
            
            Collections.sort(temp, new Comparator<Pair>(){
                // from big to small
                public int compare(Pair p1, Pair p2) {
                   return p1.getRatio() > p2.getRatio() ? -1 : p1.getRatio() < p2.getRatio() ? 1 : 0;
                }
            });
            
            ArrayList<Pair> pair = new ArrayList<Pair>();
            ArrayList<Rectangle> unpair = new ArrayList<Rectangle>();
            
            for(Pair p : temp) {
                if(p.getRatio() == -1) break;
                else if(p.getRatio() < input_threshold){
                    if(!this.troubleKey.contains(key)) this.troubleKey.add(key);
                }
                
                Rectangle r1 = p.getGT();
                Rectangle r2 = p.getDO();
                
                if(list.contains(r1) && list.contains(r2)){
                    pair.add(p);
                    list.remove(r1);
                    list.remove(r2);
                }
            }
            
            for(Rectangle r : list){
                if(!this.troubleKey.contains(key)) this.troubleKey.add(key);
                unpair.add(r);
            }
            
            result.put(key, new Pack(pair, unpair));
        }
        
        return result;
    }
    
    public void release() {
        
        this.keys.clear();
        this.gtTable.clear();
        this.doTable.clear();
    }
}