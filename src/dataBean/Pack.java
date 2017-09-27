package dataBean;

import java.util.ArrayList;

public class Pack {
    
    private ArrayList<Pair> pair;
    private ArrayList<Rectangle> unpair;
    
    public Pack(ArrayList<Pair> pair, ArrayList<Rectangle> unpair) {
        this.pair = pair;
        this.unpair = unpair;
    }
    
    public ArrayList<Pair> getPair() {
        
        ArrayList<Pair> temp = new ArrayList<Pair>();
        
        for(Pair p : this.pair) temp.add(p);
        
        return temp;
    }
    
    public ArrayList<Rectangle> getUnpair() {
        
        ArrayList<Rectangle> temp = new ArrayList<Rectangle>();
        
        for(Rectangle r : this.unpair) temp.add(r);
        
        return temp;
    }
    
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        for(Pair p : pair){
            sb.append(p);
            sb.append("\n");
        }
        
        return sb.toString();
    }
}
