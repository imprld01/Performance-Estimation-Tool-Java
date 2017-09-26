package dataBean;

public class Rectangle {
    
    private Point left_top;
    private Point right_btm;
    private boolean type;
    
    public final static boolean GROUND_TRUTH = true;
    public final static boolean DETECTED_OBJECT = false;
    
    public Rectangle(Point left_top, Point right_btm, boolean type) {
        
        this.type = type;
        this.left_top = left_top;
        this.right_btm = right_btm;
    }
    
    public Point getLT() {
        return this.left_top;
    }
    
    public Point getRB() {
        return this.right_btm;
    }
    
    public boolean getType() {
        return this.type;
    }
}
