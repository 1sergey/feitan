package xo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MyFrame extends JFrame implements ActionListener{

    private static final List<Point> peaks = new ArrayList<>();
    private static final List<Point> points = new ArrayList<>();
    private static final List<Rectangle> RECTANGLES = new ArrayList<>();
    private static final List<AcuteAngle> angles = new ArrayList<>();
    private static final List<Shape> shapes = new ArrayList<>();

    public boolean s = false;
    public Shape mainShape;

    public static JButton buttonRectangle = new JButton("rectangle");
    public static JButton buttonAngle = new JButton("angle");
    public static JButton buttonRandRect = new JButton("Random rect");
    public static JButton buttonRAngle = new JButton("Rangle");
    public static JButton buttonSolve = new JButton("solve");

    static JTextField tf1;
    static JTextField tf2;

    private static double maxSquare = 0;

    @Override
    public void actionPerformed(ActionEvent e) {
        String x = tf1.getText();
        String y = tf2.getText();

        if (e.getSource() == buttonSolve) {
            s = true;

        }else if (e.getSource() == buttonRectangle) {
            peaks.add(new Point(Integer.parseInt(x), Integer.parseInt(y)));
            points.add(new Point(Integer.parseInt(x), Integer.parseInt(y)));
            if (peaks.size() == 3) {
                Rectangle rectangle = new Rectangle(peaks.get(0), peaks.get(1), peaks.get(2));
                RECTANGLES.add(rectangle);
                peaks.clear();
            }
        }

        else if (e.getSource() == buttonRandRect) {
            Point a = new Point(Math.random()*900, Math.random()*900);
            Point b = new Point(Math.random()*900, Math.random()*900);
            Point c = new Point(Math.random()*900, Math.random()*900);
            points.add(a);
            points.add(b);
            points.add(c);
            peaks.add(a);
            peaks.add(b);
            peaks.add(c);
            if (peaks.size() == 3) {
                Rectangle rectangle = new Rectangle(peaks.get(0), peaks.get(1), peaks.get(2));
                RECTANGLES.add(rectangle);
                peaks.clear();
            }
        }

        else if (e.getSource() == buttonAngle) {
            peaks.add(new Point(Integer.parseInt(x), Integer.parseInt(y)));
            points.add(new Point(Integer.parseInt(x), Integer.parseInt(y)));
            if (peaks.size() == 3) {
                AcuteAngle a = new AcuteAngle(peaks.get(0), peaks.get(1), peaks.get(2));
                angles.add(a);
                peaks.clear();
            }
        }

        else if (e.getSource() == buttonRAngle) {
            Point a = new Point(Math.random()*900, Math.random()*900);
            Point b = new Point(Math.random()*900, Math.random()*900);
            Point c = new Point(Math.random()*900, Math.random()*900);
            points.add(a);
            points.add(b);
            points.add(c);
            peaks.add(a);
            peaks.add(b);
            peaks.add(c);
            if (peaks.size() == 3) {
                AcuteAngle angle = new AcuteAngle(peaks.get(0), peaks.get(1), peaks.get(2));
                angles.add(angle);
                peaks.clear();
            }
        }

        repaint();
    }

    public MyFrame(String string) {
        super(string);

        tf1 = new JTextField();
        tf1.setBounds(400, 10, 100, 50);
        tf2 = new JTextField();
        tf2.setBounds(500, 10, 100, 50);

        buttonSolve = new JButton("solve");
        buttonSolve.setBounds(750, 110, 100, 50);
        buttonSolve.addActionListener(this);

        buttonRectangle = new JButton("rectangle");
        buttonRectangle.setBounds(700, 10, 100, 50);
        buttonRectangle.addActionListener(this);

        buttonAngle = new JButton("angle");
        buttonAngle.setBounds(800, 10, 100, 50);
        buttonAngle.addActionListener(this);

        buttonRandRect = new JButton("Rand rect");
        buttonRandRect.setBounds(700, 60, 100, 50);
        buttonRandRect.addActionListener(this);

        buttonRAngle = new JButton("Rand angle");
        buttonRAngle.setBounds(800, 60, 100, 50);
        buttonRAngle.addActionListener(this);

        add(tf1);
        add(tf2);
        add(buttonAngle);
        add(buttonRectangle);
        add(buttonRAngle);
        add(buttonRandRect);
        add(buttonSolve);

        setLayout(null);
        setSize(1000, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void drawShape(Graphics g, List<Point> points) {
        g.setColor(Color.cyan);
        int len = points.size();
        for (int i = 0; i < len; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get((i + 1) % len);
            g.drawOval((int) (p1.x - 3), (int) (p1.y - 3), 15, 15);
            g.fillOval((int) (p1.x - 3), (int) (p1.y - 3), 15, 15);
            g.drawOval((int) (p2.x - 3), (int) (p2.y - 3), 15, 15);
            g.fillOval((int) (p2.x - 3), (int) (p2.y - 3), 15, 15);
        }

        for (int i = 0; i < len; i++) {
            Point p1 = points.get(i);
            for (int j = i; j < len; j++) {
                Point p2 = points.get(j);
                g.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
            }
        }
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (Point point : points) {
            g.drawOval((int)point.x, (int)point.y, 5, 5);
        }
        for (AcuteAngle angle : angles) {
            drawAngle(g, angle);
        }
        for (Rectangle rectangle : RECTANGLES) {
            drawTriangle(g, rectangle);
        }
        for (Rectangle rectangle : RECTANGLES) {
            for (AcuteAngle angle : angles) {
                Shape shape = new Shape(rectangle, angle);
                shapes.add(shape);
                if (shape.getSquare() >= maxSquare) {
                    maxSquare = shape.getSquare();
                    mainShape = shape;
                }
            }
        }

        if (s) {
            Graphics2D g1 = (Graphics2D) g;
            BasicStroke pen1 = new BasicStroke(5);
            g1.setStroke(pen1);
            g1.setColor(Color.magenta);

            for (Point allPoint : mainShape.result) {
                g.drawOval((int)allPoint.x - 3, (int)allPoint.y - 3, 10, 10);
                g.fillOval((int)allPoint.x - 3, (int)allPoint.y - 3, 10, 10);
            }

            for (int i = 1; i < mainShape.result.size(); i++) {
                Point p1 = mainShape.result.get(i - 1);
                Point p2 = mainShape.result.get(i);
                drawT(g,mainShape.result.get(0), p1, p2);
            }
            for (int i = 1; i < mainShape.result.size(); i++) {
                Point p1 = mainShape.result.get(i - 1);
                Point p2 = mainShape.result.get(i);
                drawT(g,mainShape.result.get(mainShape.result.size()-1), p1, p2);
            }

            s = false;
        }
    }

    private void drawAngle(Graphics g, AcuteAngle angle) {
        g.drawLine((int) angle.top.x, (int) angle.top.y, (int) angle.v1.x, (int) angle.v1.y);
        g.drawLine((int) angle.top.x, (int) angle.top.y, (int) angle.v2.x, (int) angle.v2.y);
    }

    private void drawTriangle(Graphics g, Rectangle rectangle) {
        g.drawLine((int) rectangle.a.x, (int) rectangle.a.y, (int) rectangle.b.x, (int) rectangle.b.y);
        g.drawLine((int) rectangle.a.x, (int) rectangle.a.y, (int) rectangle.d.x, (int) rectangle.d.y);
        g.drawLine((int) rectangle.b.x, (int) rectangle.b.y, (int) rectangle.c.x, (int) rectangle.c.y);
        g.drawLine((int) rectangle.c.x, (int) rectangle.c.y, (int) rectangle.d.x, (int) rectangle.d.y);
    }

    private void drawT(Graphics g, Point a, Point b, Point c) {
        g.drawLine((int)a.x, (int)a.y, (int)b.x, (int)b.y);
        g.drawLine((int)a.x, (int)a.y, (int)c.x, (int)c.y);
        g.drawLine((int)b.x, (int)b.y, (int)c.x, (int)c.y);
    }

    public static void main(String[] args) {
        MyFrame frame = new MyFrame("За светом всегда следует тень");
    }
}
