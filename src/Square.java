
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Square extends MyShape {

    public Square(int xAxis, int yAxis, Color color, int size) {

        super(new Point2D.Double(xAxis,yAxis), color, size);   // uses super class's implementation  
    } // end constructor

    
    /***************************************************************************
    * The getCollisionBox() returns an imaginary box around our shapes (both the
    * circles and squares but also around the car's body parts. The collision-box
    * is a square so collision detection becomes easier by using the intersects()
    * method. Now even though I LOVE to use instance variable whenever I can,
    * over here I MUST use the getSize() method to get the object's size simply
    * because the car body parts have different sizes from each other and from the
    * other objects. So unfortunately I cannot use an instance variable here since
    * it would not account for all objects properly. But the getSize() method will.
    * **************************************************************************
    */
    @Override
    public Rectangle2D.Double getCollisionBox() {
        return new Rectangle2D.Double(getLocation().x - getSize() / 2,
                getLocation().y - getSize() / 2, getSize(), getSize());
    } // end Rectangle2D.Double getCollisionBox()
    
    
    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(getColor());

        Rectangle2D rectangle = new Rectangle2D.Double(getLocation().getX(),
                getLocation().getY(), getSize(), getSize());
        g2.fill(rectangle);
    }
    
} // end public class Square extends MyShape
