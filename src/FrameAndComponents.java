
import java.awt.*;              //for BorderLayout/Container/Graphics/Graphics2D/Color
import java.awt.event.*;        //for ActionEvent/ActionListener/MouseEvent/MouseListener
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import javax.swing.*;           //for JButton/JFrame/JPanel/JRadioButton
import javax.swing.border.*;    // for EtchedBorder/TitledBorder
import java.util.*;             // for Random/Arraylist

public class FrameAndComponents extends JFrame implements SupportInterface {

    public FrameAndComponents(String title, int height, int width) {

        super(title);
        frameSetup(height, width);
        addContentsToWindow();
        addListenerToJButtons();    // this method will add listener
        setVisible(true); // call after adding contents

    } // end constructor
    //**************************************************************************

    private void frameSetup(int height, int width) {

        setSize(width * SCALE, height * SCALE);
        setLocationRelativeTo(null);  // centers the GUI window on the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // terminates pgm upon exit

    } // end private void frameSetup(String title, int height, int width)

    //**************************************************************************
    private void addContentsToWindow() {

        Container mainJFrame = getContentPane();

        JPanel southPanel = new JPanel();
        mainJFrame.add(southPanel, BorderLayout.SOUTH);
        southPanel.add(btnAddTen);
        southPanel.add(btnClearObstacles);
        southPanel.add(btnFixCar);

        mainDrawingArea = new Canvas();

        mainJFrame.add(mainDrawingArea, BorderLayout.CENTER);
        mainDrawingArea.setBackground(CANVAS_COLOR);

        myTaxi = new MyCar();
    } // end addContentsToWindow()

    //**************************************************************************
    private void addListenerToJButtons() {
        ButtonObserver listeningToButtons = new ButtonObserver();
        btnAddTen.addActionListener(listeningToButtons);
        btnClearObstacles.addActionListener(listeningToButtons);
        btnFixCar.addActionListener(listeningToButtons);

        KeyObserver keyListener = new FrameAndComponents.KeyObserver();
        mainDrawingArea.setFocusable(true);
        mainDrawingArea.addKeyListener(keyListener);

    } // end private void addListenerToJButtons

    //**************************************************************************
    class ButtonObserver implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == btnAddTen) {
                Random rand = new Random();
                for (int i = 0; i < NUMBER_OF_SHAPES; i++) {

                    /*
                     Due to the South Panel occupying some of the space allocated
                     to the JFrame, the yAxis (height) of the Center region is less
                     than FRAME_HEIGHT. Plus, to avoid any object being drawn out
                     of bounds, drawing board's x- and y-Axes less than the JFrame
                     size.
                     */
                    xAxis = rand.nextInt(FRAME_WIDTH * SCALE - 25); //Unaffected by JPanel
                    yAxis = rand.nextInt(FRAME_HEIGHT * SCALE - 85);//compensate for JPanel

                    // alternately, a cicle and a square added to arraylist.
                    if (addACircle) {                        
                        shapesArray.add(new Circle(xAxis, yAxis,
                                COLOR_OF_SHAPES, SHAPE_SIZE));

                        addACircle = !addACircle;       //makes it false
                    } // end inner if
                    else {      // when addACircle is false
                        
                        shapesArray.add(new Square(xAxis, yAxis, COLOR_OF_SHAPES,
                                SHAPE_SIZE));

                        addACircle = !addACircle;       // makes it true again
                        
                    } // end else
                } // end for
            } // end first if statement
            else if (source == btnClearObstacles) {
                shapesArray.clear();
                
            } else if (source == btnFixCar) {
                frontEndIsDamaged = false;
                tailEndIsDamaged = false;
                frontTireIsDamaged = false;
                rearTireIsDamaged = false;
                hoodIsDamaged = false;
                myTaxi.topBody.changeColor(myTaxi.normalColor);
                myTaxi.bottomBodyFront.changeColor(myTaxi.normalColor);
                myTaxi.bottomBodyRear.changeColor(myTaxi.normalColor);
                myTaxi.frontTire.changeColor(myTaxi.normalColor);
                myTaxi.rearTire.changeColor(myTaxi.normalColor);
            } // end last else-if
            /*
             The problem I faced was that myCar was moving OK till I clicked a
             button to add 10 objects. Then it would not move. I put some println
             statements in the keyboard input and quickly realised that after I
             pressed a button on the GUI screen, the keyboard method was not
             executing so i knew the JButtons were not letting go, once they were
             pressed. As per Dr Sung, setFocusable(false) took care of the problem.
             Now even though I should write each setFocusable(false) statement in
             its block so all 3 are not being executed unnecessarily, I decided to
             group them together to be able to write this explanation.
             */
            btnAddTen.setFocusable(false);
            btnClearObstacles.setFocusable(false);
            btnFixCar.setFocusable(false);

            mainDrawingArea.repaint();

        } // end actionPerformed method
    } // end class ButtonObserver

    //**************************************************************************
    // These next four methods control the Up, Down, Left, Right, movements of
    // the car.
    //**************************************************************************
    private class KeyObserver extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    myTaxi.moveAlongYAxis(-1);
                    break;
                case KeyEvent.VK_DOWN:
                    myTaxi.moveAlongYAxis(1);
                    break;
                case KeyEvent.VK_LEFT:
                    myTaxi.moveAlongXAxis(-1);
                    break;
                case KeyEvent.VK_RIGHT:
                    myTaxi.moveAlongXAxis(1);
                    break;
                default: {
                }    // no action; for readibility, added empty block
            } // end switch statement
            checkForCollision();
            mainDrawingArea.repaint();
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }
    }

    //**************************************************************************
    //******************* class Canvas *****************************************
    private class Canvas extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);    // ensures image uniformity on different platforms
            Graphics2D g2 = (Graphics2D) g;
            
            // Draw the shapes.
            for (int i = 0; i < shapesArray.size(); i++) {
                shapesArray.get(i).draw(g2);
            } // end for statement

            // The car is made up of 5 parts -- 2 tires and 3 panels; each
            // has to been drawn seperately but together as a group so I am
            // enclosing them in a {...} block for readability sake. Notice that
            // the program draws the circle / square polymorphically by calling
            // the Circle or Square class's draw method. In addition, if the
            // body part has collided with a shape obstacle, it calls a modified
            // draw method that paints the panel red, otherwise the regular draw
            // method is called.
            { // start myTaxi draw block
                if (frontTireIsDamaged) {
                    myTaxi.getFrontTire2().draw(g2);
                } else {
                    myTaxi.frontTire.draw(g2);
                }
                if (rearTireIsDamaged) {
                    myTaxi.getRearTire2().draw(g2);
                } else {
                    myTaxi.rearTire.draw(g2);
                }
                if (frontEndIsDamaged) {
                    myTaxi.getBodyFront2().draw(g2);
                } else {
                    myTaxi.bottomBodyFront.draw(g2);
                }
                if (tailEndIsDamaged) {
                    myTaxi.getBodyRear2().draw(g2);
                } else {
                    myTaxi.bottomBodyRear.draw(g2);
                }
                if (hoodIsDamaged) {
                    myTaxi.getBodyTop2().draw(g2);
                } else {
                    myTaxi.topBody.draw(g2);
                }
            } // end myTaxi draw block

        } // end public void paintComponent(Graphics g) method
    } // end private class Canvas extends JPanel

    private void checkForCollision() {

        for (int i = 0; i < shapesArray.size(); i++) {
            if (myTaxi.bottomBodyFront.getCollisionBox().intersects(shapesArray.get(i).getCollisionBox())) {
                frontEndIsDamaged = true;
                shapesArray.remove(i);      // collided shape at (i) deleted from the
                                            // arraylist, (and thus from the screen)
            } // end if                     
            else if (myTaxi.bottomBodyRear.getCollisionBox().intersects(shapesArray.get(i).getCollisionBox())) {
                tailEndIsDamaged = true;
                shapesArray.remove(i);
            } // end last if
            else if (myTaxi.topBody.getCollisionBox().intersects(shapesArray.get(i).getCollisionBox())) {
                hoodIsDamaged = true;
                shapesArray.remove(i);
            } // end last if
            else if (myTaxi.frontTire.getCollisionBox().intersects(shapesArray.get(i).getCollisionBox())) {
                frontTireIsDamaged = true;
                shapesArray.remove(i);
            } // end last if
            else if (myTaxi.rearTire.getCollisionBox().intersects(shapesArray.get(i).getCollisionBox())) {
                rearTireIsDamaged = true;
                shapesArray.remove(i);
            } // end last if
        } // end for
    }
    //**************************************************************************
    private boolean addACircle = true;
    private boolean hoodIsDamaged = false, frontEndIsDamaged = false,
            tailEndIsDamaged = false, frontTireIsDamaged = false,
            rearTireIsDamaged = false;

    private Canvas mainDrawingArea;
    private int xAxis = 0, yAxis = 0;

    MyCar myTaxi;
    ArrayList<MyShape> shapesArray = new ArrayList<>();

    JButton btnAddTen = new JButton("Add 10"),
            btnClearObstacles = new JButton("Clear All"),
            btnFixCar = new JButton("Fix Car");

} // end class FrameAndComponents
