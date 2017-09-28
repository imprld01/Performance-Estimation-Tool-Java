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
        
        // 5 digits precision
        return (double)Math.round(this.ratio * 100000d) / 100000d;
    }
    
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(this.groundTruth);
        sb.append(" ");
        sb.append(this.detectedObject);
        sb.append(" Ratio:");
        sb.append(this.ratio);
        
        return sb.toString();
    }
}