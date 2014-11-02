
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

public class MyCar implements SupportInterface {

    public MyCar() {

        bottomBodyFront = new Square(45, 45, normalColor, BODY_SIZE);
        bottomBodyRear = new Square(15, 45, normalColor, BODY_SIZE);
        topBody = new Square(30, 15, normalColor, BODY_SIZE);
        frontTire = new Circle(50, 70, normalColor, TIRE_SIZE);
        rearTire = new Circle(20, 70, normalColor, TIRE_SIZE);

    } // end constructor

    /***************************************************************************
    * Change in object's x-Axis is the focus of next method. direction (-1) means 
    * going left and direction (+1) means going right. direction is multiplied by 
    * distance and added to previous X-LOC for new X-Axis position. Y-Axis does
    * not change here. A new ArrayList tracks up-to-date location and since the 
    * X- and Y- variables are instance variables, the values are retained between
    * method calls so current/new position of object is properly maintained.
    ****************************************************************************
    */
    public void moveAlongXAxis(int direction) {

        bottomBodyFront = new Square(frontPanelXLoc += (direction * DISTANCE),
                frontPanelYLoc, normalColor, BODY_SIZE);
        bottomBodyRear = new Square(rearPanelXLoc += (direction * DISTANCE),
                rearPanelYLoc, normalColor, BODY_SIZE);
        topBody = new Square(topPanelXLoc += (direction * DISTANCE),
                topPanelYLoc, normalColor, BODY_SIZE);
        frontTire = new Circle(frontTireXLoc += (direction * DISTANCE),
                frontTireYLoc, normalColor, TIRE_SIZE);
        rearTire = new Circle(rearTireXLoc += (direction * DISTANCE),
                rearTireYLoc, normalColor, TIRE_SIZE);
    } // end moveAlongXAxis

    //**************************************************************************
    /***************************************************************************
    * Same logic as the method above but here the focus is movement vertically
    * along the y-Axis while the X-axis remains the same. 
    ****************************************************************************
    */
    public void moveAlongYAxis(int direction) {
        
        bottomBodyFront = new Square(frontPanelXLoc,
                frontPanelYLoc += (direction * DISTANCE),
                normalColor, BODY_SIZE);
        bottomBodyRear = new Square(rearPanelXLoc,
                rearPanelYLoc += (direction * DISTANCE),
                normalColor, BODY_SIZE);
        topBody = new Square(topPanelXLoc,
                topPanelYLoc += (direction * DISTANCE),
                normalColor, BODY_SIZE);
        frontTire = new Circle(frontTireXLoc,
                frontTireYLoc += (direction * DISTANCE),
                normalColor, TIRE_SIZE);
        rearTire = new Circle(rearTireXLoc,
                rearTireYLoc += (direction * DISTANCE),
                normalColor, TIRE_SIZE);
    } // end moveAlongYAxis

    public Color getBrokenColor() {
        return brokenColor;
    }


    public MyShape getBodyFront2() {
        return bottomBodyFront = new Square(frontPanelXLoc, frontPanelYLoc,
                brokenColor, BODY_SIZE);
    }

    public MyShape getBodyRear2() {
        return bottomBodyRear = new Square(rearPanelXLoc, rearPanelYLoc,
                brokenColor, BODY_SIZE);
    }

    public MyShape getBodyTop2() {
        return topBody = new Square(topPanelXLoc, topPanelYLoc,
                brokenColor, BODY_SIZE);
    }

    public MyShape getFrontTire2() {
        return frontTire = new Circle(frontTireXLoc, frontTireYLoc,
                brokenColor, TIRE_SIZE);
    }

    public MyShape getRearTire2() {
        return rearTire = new Circle(rearTireXLoc, rearTireYLoc,
                brokenColor, TIRE_SIZE);
    }

//******************************************************************************
    Circle frontTire;
    Circle rearTire;
    Square bottomBodyFront;    
    Square bottomBodyRear;
    Square topBody;
    final Color normalColor = Color.YELLOW;
    final Color brokenColor = Color.RED;
    private Color color;
    protected int frontPanelXLoc = 45, rearPanelXLoc = 15, topPanelXLoc = 30,
            frontTireXLoc = 50, rearTireXLoc = 20;
    protected int frontPanelYLoc = 45, rearPanelYLoc = 45, topPanelYLoc = 15,
            frontTireYLoc = 70, rearTireYLoc = 70;
}
