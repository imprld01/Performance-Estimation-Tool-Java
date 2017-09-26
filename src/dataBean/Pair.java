package dataBean;

public class Pair {
    
    private double ratio;
    private Rectangle groundTruth;
    private Rectangle detectedObject;
    
    public Pair(Rectangle groundTruth, Rectangle detectedObject, double ratio) {
        
        this.ratio = ratio;
        this.groundTruth = groundTruth;
        this.detectedObject = detectedObject;
    }
    
    public Rectangle getGT() {
        return this.groundTruth;
    }
    
    public Rectangle getDO() {
        return this.detectedObject;
    }
    
    public double getRatio() {
        return this.ratio;
    }
    
    public double compareTo(Pair pair) {
        
        return this.ratio - pair.getRatio();
    }
}