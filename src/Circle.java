import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Circle extends MyShape implements SupportInterface {

    public Circle(int xAxis, int yAxis, Color color, int size) {

        super(new Point2D.Double(xAxis,yAxis), color, size);   // uses super class's implementation 

    } // end constructor


    /***************************************************************************
    * Very important self reminder. Initially I was using the constant variable
    * SHAPE_SIZE everywhere below in place of the getSize() method calls. And it
    * worked perfectly fine. But then I thought, well it works fine because ALL
    * the circular objects in this program happen to have the same size. But if 
    * the size of even one object was different, my code would not be accurate for
    * that object. So I removed the variable and put in the getSize() method call.
    ****************************************************************************
    */
    
    // Create an imaginery square box (rectangle object) around every circle
    // object and use that box for collision detection using intersect() method.
    @Override
    public Rectangle2D.Double getCollisionBox() {
        return new Rectangle2D.Double(getLocation().x - getSize() / 2,
                getLocation().y - getSize() / 2,
                getSize() * ADJUSTMENT_FACTOR, getSize() * ADJUSTMENT_FACTOR);
    } // end Rectangle2D.Double getCollisionBox()
    

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(getColor());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fill(new Ellipse2D.Double(getLocation().getX(), getLocation().getY(),
                getSize(), getSize()));
    }
} // end public class Circle extends MyShape