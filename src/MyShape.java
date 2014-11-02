
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class MyShape implements CarCollision, SupportInterface {
    
        public MyShape (Point2D.Double location, Color color, int size) {
        this.location = location;
        this.color = color;
        this.size = size;
    }

    
    //**************************************************************************
    // location = center of Circle or Square
    // size = diameter; (2 x radius) if Circle
    // size = a side, if Square
    //**************************************************************************
    


    public Point2D.Double getLocation() {
        return location;
    }

    public Color getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }
    
    public void changeColor(Color colour) {
        this.color = colour;
    }

    public abstract void draw(Graphics2D g2);
    /*
    The method below is functionally similar to Interface class method supplied 
    in this program so commenting  it out. No need to duplicate code.
    */
    //public abstract void collisionRadius();   
    

    //**************************************************************************
    //******************* Instance Variables ***********************************   
    
    private final Point2D.Double location;
    private Color color;
    private final int size;
} // end class MyShape
