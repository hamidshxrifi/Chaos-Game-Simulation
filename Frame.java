import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class Frame extends JFrame implements ActionListener{

    Shape shape;
    Point finalPoint;
    Timer timer;
    JTextField rValueField;
    JLabel iterText;
    JLabel iterLimitText;
    JLabel pointSizeText;
    JLabel algoText;
    boolean selectedFinal;
    boolean isAnimating;
    boolean newVertex;
    double rValue;
    int iterIndex;
    int moveTo;
    int count;
    int pointIndex;
    int[] pointSizeArr = {1,2,3,4,5};
    int[] numOfIterations = {1000,2000,3000,4000,5000,6000,7000,8000,9000,10000};

    Frame(){
        isAnimating = false;
        newVertex = false;
        rValue = 0.50;
        iterIndex = 0;
        moveTo = 0;
        count = 0;
        pointIndex = 1;
        selectedFinal = false;

        iterText = new JLabel();
        iterText.setBounds(680,740,200,25);
        iterText.setVisible(false);

        iterLimitText = new JLabel("Number of iterations = " + numOfIterations[iterIndex]);
        iterLimitText.setBounds(5,740,200,25);

        pointSizeText = new JLabel("Size of Points = " + pointSizeArr[pointIndex] + "px");
        pointSizeText.setBounds(207,740,200,25);

        algoText = new JLabel("New Vertex Algorithm = " + newVertex);
        algoText.setBounds(340,740,200,25);

        rValueField = createTextField(575,25,35,30, "0.50");
        JButton algoButton = createButton(85,75,152,30,"Set Vertex Algorithm");
        algoButton.setToolTipText("If set to true, each iteration chooses a different vertex from the last iteration");
        algoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAnimating) {
                    newVertex = !newVertex;
                    // default rValue when clicked
                    rValueField.setText("0.50");
                    rValue = 0.50;
                    algoText.setText("New Vertex Algorithm = " + newVertex);
                }
            }
        });

        JButton paintSizeButton = createButton(540,75,150,30, "Set Paint Size");
        paintSizeButton.setToolTipText("Set the size of each point added to the plane");
        paintSizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pointIndex = (pointIndex+1) % 5;
                pointSizeText.setText("Size of Points = " + pointSizeArr[pointIndex] +"px");
                repaint();
            }
        });

        JButton iterLimitButton = createButton(312,75,150,30,"Set Iteration Limit");
        iterLimitButton.setToolTipText("The amount of times a point is added; Bigger value takes longer but has more detail");
        iterLimitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isAnimating) {
                    iterIndex = (iterIndex+1) % 10;
                    iterLimitText.setText("Number of iterations = " + numOfIterations[iterIndex]);
                }
            }
        });

        JButton resetButton = createButton(25,25,125,30, "Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shape.removeVerticesAndPoints();
                iterText.setVisible(false);
                selectedFinal=false;
                isAnimating = false;
                count=0;
                repaint();
            }
        });

        // clears shape then adds triangle
        JButton triangleButton = createButton(175,25,125,30, "Create Triangle");
        triangleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAnimating) {
                    shape.removeVerticesAndPoints();
                    iterText.setVisible(false);
                    selectedFinal = false;
                    shape.addVertex(new Point(400,175));
                    shape.addVertex(new Point(100,725));
                    shape.addVertex(new Point(700,725));
                    repaint();
                }
            }
        });
        // clears shape then adds square
        JButton squareButton = createButton(325,25,125,30, "Create Square");
        squareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAnimating) {
                    shape.removeVerticesAndPoints();
                    iterText.setVisible(false);
                    selectedFinal = false;
                    shape.addVertex(new Point(50,150));
                    shape.addVertex(new Point(750,150));
                    shape.addVertex(new Point(50,750));
                    shape.addVertex(new Point(750,750));
                    repaint();
                }
            }
        });

        // will set the r value to be the value entered in the JTextField
        // must be between 0.01 and 0.99
        JButton setRButton = createButton(475,25,100,29, "Set R value");
        setRButton.setToolTipText("Set the jump distance from point A to point B; Default = 0.5");
        setRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==setRButton && !isAnimating) {
                    double oldR = rValue;
                    // formatting error will set rValue back to its original value
                    try {
                        rValue = Double.parseDouble(rValueField.getText());
                    }
                    catch (NumberFormatException err) {
                        rValue = oldR;
                    }
                    if (rValue < 0.01 || rValue > 0.99) {
                        rValue = oldR;
                    }
                }
                rValueField.setText(String.valueOf(rValue));
            }
        });

        // rValue will produce optimal packing at n/n+3, n being the number of vertices
        JButton optimizeButton = createButton(635,25,125,30, "Optimize");
        optimizeButton.setToolTipText("Set R value to produce fractal with optimal packing");
        optimizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int v = shape.getNumberOfVertices();
                if (v>2 && !isAnimating) {
                    double optR = v / (v+3.0);
                    rValueField.setText(String.valueOf(optR));
                    rValue = optR;
                }
            }
        });

        this.setTitle("Chaos Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(800,800);
        this.setVisible(true);
        this.add(iterText);
        this.add(iterLimitText);
        this.add(pointSizeText);
        this.add(algoText);
        this.add(rValueField);
        this.add(resetButton);
        this.add(triangleButton);
        this.add(squareButton);
        this.add(setRButton);
        this.add(optimizeButton);
        this.add(algoButton);
        this.add(paintSizeButton);
        this.add(iterLimitButton);

        // 1ms delay between frames of animation
        timer = new Timer(1,this);
        timer.start();

        // processes left/right mouse click buttons
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // places shape vertex on screen with left click
                if(e.getY()>125 && e.getButton() == MouseEvent.BUTTON1 && !selectedFinal) {
                    shape.addVertex(new Point(e.getX(), e.getY()));
                    repaint();
                }
                // places chaos point on screen with right click
                else if(e.getY()>125 && e.getButton() == MouseEvent.BUTTON3 && !selectedFinal) {
                    int v = shape.getNumberOfVertices();
                    // shape must have 3 vertices, otherwise it will result in point (1 vertex) or line (2 vertex)
                    if (v>2) {
                        selectedFinal = true;
                        isAnimating = true;
                        finalPoint = new Point(e.getX(),e.getY());
                        shape.addPoint(finalPoint);
                        iterText.setVisible(true);
                        repaint();
                    }
                }
                // skip to end result of chaos game
                else if(e.getButton()== MouseEvent.BUTTON3 && isAnimating) {
                    isAnimating = false;
                    finishAnimation();
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {

            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {

            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }


    private JTextField createTextField(int x, int y, int width, int height, String label) {
        JTextField textField = new JTextField();
        textField.setBounds(x,y,width,height);
        Font font = new Font("Arial", Font.PLAIN, 15);
        textField.setFont(font);
        textField.setText(label);
        return textField;
    }

    private JButton createButton(int x, int y, int width, int height, String label) {
        JButton button = new JButton(label);
        button.setBounds(x,y,width,height);
        return button;
    }

    // will allow user to pass in either an empty shape, or a predefined custom shape
    public void setShape(Shape x) {
        shape = x;
    }

    // allows for double buffering
    // prevents flickering between repaint()
    public void paint(Graphics g) {
        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();
        paintComponent(dbg);
        g.drawImage(dbImage,0,0,this);
    }

    public void paintComponent(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        Color darkGreen = new Color(0,153,0);
        // enables antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ArrayList<Point> vertices = shape.getVertices();
        ArrayList<Point> chaosPoints = shape.getPoints();
        // paints to screen the points in the two arrays
        // paint vertices of the shape
        for (Point vertex : vertices) {
            g2d.setPaint(darkGreen);
            g2d.fillOval(vertex.x, vertex.y, 5, 5);
        }
        // paint points from chaos game
        for (int point = 0; point < chaosPoints.size(); point++) {
            // most recent point is painted red
            if (point == chaosPoints.size()-1) {
                g2d.setPaint(Color.red);
                g2d.fillOval(chaosPoints.get(point).x, chaosPoints.get(point).y, 10, 10);
            } else {
                g2d.setPaint(Color.black);
                g2d.fillOval(chaosPoints.get(point).x, chaosPoints.get(point).y, pointSizeArr[pointIndex], pointSizeArr[pointIndex]);
            }
        }
    }

    // completes simulation to produce final shape
    public void finishAnimation() {
        Random rand = new Random();
        Point vertex;
        Point lastPoint;
        Point nextPoint;
        int numOfVert = shape.getNumberOfVertices();
        int oldMoveTo;
        int newX;
        int newY;
        // calculates the next point using the last point and the vertex we want to move to
        do {
            // if true, sets moveTo to a different vertex from the last iteration
            if (newVertex) {
                oldMoveTo = moveTo;
                do {
                    moveTo = rand.nextInt(numOfVert);

                } while (moveTo == oldMoveTo);
            } else {
                moveTo = rand.nextInt(numOfVert);
            }
            lastPoint = shape.getLastPoint();
            vertex = shape.getVertex(moveTo);
            newX = (int) (lastPoint.x + rValue * (vertex.x - lastPoint.x));
            newY = (int) (lastPoint.y + rValue * (vertex.y - lastPoint.y));
            nextPoint = new Point(newX, newY);
            // append new point to point array, and will be used in next iteration
            shape.addPoint(nextPoint);
            count++;
            iterText.setText("Iteration " + count);
            repaint();
        }
        while(count<numOfIterations[iterIndex]);

        isAnimating=false;
        count=0;
    }

    // simulates the chaos game
    // achieves same outcome as finishAnimation(), but is instead called every 1ms to show the game being played.
    @Override
    public void actionPerformed(ActionEvent e) {

        // calculates the next point
        if(isAnimating) {
            Random rand = new Random();
            Point vertex;
            Point lastPoint;
            Point nextPoint;
            int numOfVert = shape.getNumberOfVertices();
            int oldMoveTo;
            int newX;
            int newY;
            if(count<numOfIterations[iterIndex]) {
                if (newVertex) {
                    oldMoveTo = moveTo;
                    do {
                        moveTo = rand.nextInt(numOfVert);

                    } while (moveTo == oldMoveTo);
                } else {
                    moveTo = rand.nextInt(numOfVert);
                }
                lastPoint = shape.getLastPoint();
                vertex = shape.getVertex(moveTo);
                newX = (int) (lastPoint.x + rValue * (vertex.x - lastPoint.x));
                newY = (int) (lastPoint.y + rValue * (vertex.y - lastPoint.y));
                nextPoint = new Point(newX, newY);
                shape.addPoint(nextPoint);
                count++;
                iterText.setText("Iteration " + count);
                repaint();
            }
            if (count >= numOfIterations[iterIndex]) {
                isAnimating = false;
                count=0;
            }
        }
    }
}
