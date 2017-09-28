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
        
        return this.pair;
    }
    
    public ArrayList<Rectangle> getUnpair() {
        
        return this.unpair;
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
