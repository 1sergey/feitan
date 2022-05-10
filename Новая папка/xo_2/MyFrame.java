package xo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// Класс MyFrame описывает наше будущее окно
public class MyFrame extends JFrame implements ActionListener{
    // Два листа точек - peaks, points - в первом будут храниться вершины,
    // во втором (points) будут храниться все точки
    //
    // Разница в том, что в листе peaks хранится какая-нибудь тройка точек - либо вершины треуг-ка, либо острого угла,
    // то есть peaks.size число от 0 до 2, включительно
    //
    // А в листе points мы храним абсолютно все точки

    // В листе triangles мы будем хранить все треугольники
    // В листе angles мы будем хранить все острые углы
    // В листе shapes мы будем хранить все фигуры, полученные объединением



    JButton t_BUTTON;
    JButton a_BUTTON;
    JButton solve_BUTTON;
    JButton clear_BUTTON;
    JButton save_BUTTON;

    public boolean flag = false;
    public double maxSquare = -10; // Максимальная площадь фигуры, полученный пересечением треуг-ка и острого угла
    public Shape mainShape;


    JTextField t_FIELD;
    JTextField a_FIELD;

    public static List<Shape> shapes = new ArrayList<>();
    public static List<Point> points = new ArrayList<>();
    public static List<Triangle> triangles = new ArrayList<>();
    public static List<AcuteAngle> angles = new ArrayList<>();



    public MyFrame(String title) {
        super(title);
        initializationForButtonAndFields();
        addButtonsAndFields();
        initialization();
    }


    private void initializationForButtonAndFields() {
        save_BUTTON = new JButton("save to new File");
        save_BUTTON.setBounds(10, 300, 150, 50);
        save_BUTTON.addActionListener(this);

        solve_BUTTON = new JButton("solve");
        solve_BUTTON.setBounds(10, 500, 150, 50);
        solve_BUTTON.addActionListener(this);

        clear_BUTTON = new JButton("clear Desk");
        clear_BUTTON.setBounds(10, 400, 150, 50);
        clear_BUTTON.addActionListener(this);

        t_BUTTON = new JButton("add triangle");
        t_BUTTON.setBounds(10, 10, 150, 50);
        t_BUTTON.addActionListener(this);

        t_FIELD = new JTextField("// 500, 500, 600, 100, 400, 300\n");
        t_FIELD.setBounds(200, 10, 250, 50);

        a_BUTTON = new JButton("add angle");
        a_BUTTON.setBounds(10, 100, 150, 50);
        a_BUTTON.addActionListener(this);

        a_FIELD = new JTextField("// 700, 200, 10, 10, 10, 300");
        a_FIELD.setBounds(200, 100, 250, 50);
    }


    private void initialization() {
        setLayout(null);
        setSize(1400, 900);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }


    private void addButtonsAndFields() {
        add(t_FIELD);
        add(a_FIELD);
        add(solve_BUTTON);
        add(a_BUTTON);
        add(save_BUTTON);
        add(clear_BUTTON);
        add(t_BUTTON);
    }


    private static void drawAngle(Graphics g, AcuteAngle angle) {
        g.setColor(Color.green);
        g.drawLine(angle.top.x, angle.top.y, angle.v1.x, angle.v1.y); // рисуем линию между вершиной угла и v1
        g.drawLine(angle.top.x, angle.top.y, angle.v2.x, angle.v2.y); // рисуем линию между вершиной угла и v2
    }

    private static void drawTriangle(Graphics g, Triangle triangle) {
        g.setColor(Color.red);
        g.drawLine(triangle.a.x, triangle.a.y, triangle.b.x, triangle.b.y); // рисуем линию между A и В
        g.drawLine(triangle.a.x, triangle.a.y, triangle.c.x, triangle.c.y); // рисуем линию между A и С
        g.drawLine(triangle.b.x, triangle.b.y, triangle.c.x, triangle.c.y); // рисуем линию между В и С
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (Point point : points) {
            g.drawOval(point.x, point.y, 5, 5);
        }

        for (AcuteAngle angle : angles) {
            drawAngle(g, angle);
        }

        for (Triangle triangle : triangles) {
            drawTriangle(g, triangle);
        }

        for (Triangle triangle : triangles) {
            for (AcuteAngle angle : angles) {
                Shape shape = new Shape(triangle, angle);
                shapes.add(shape);
                if (shape.getSquare() >= maxSquare) {
                    maxSquare = shape.getSquare();
                    mainShape = shape;
                }
            }
        }




        if (flag) {
            Graphics2D g1 = (Graphics2D) g;
            BasicStroke pen1 = new BasicStroke(5);
            g1.setStroke(pen1);
            g1.setColor(Color.magenta);

            for (Point allPoint : mainShape.result) {
                g.drawOval(allPoint.x - 3, allPoint.y - 3, 10, 10);
                g.fillOval(allPoint.x - 3, allPoint.y - 3, 10, 10);
            }

            for (int i = 1; i < mainShape.result.size(); i++) {
                Point p1 = mainShape.result.get(i - 1);
                Point p2 = mainShape.result.get(i);
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }

            flag = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
        Matcher matcher;

        if (e.getSource() == t_BUTTON) {
            matcher = pat.matcher(t_FIELD.getText());

            int[] coordinates = new int[6];
            int i = 0;

            while (matcher.find()) {
                coordinates[i] = Integer.parseInt(matcher.group());
                i++;
            }

            triangles.add(new Triangle(
                    new Point(coordinates[0], coordinates[1]),
                    new Point(coordinates[2], coordinates[3]),
                    new Point(coordinates[4], coordinates[5])
            ));

            points.add(new Point(coordinates[0], coordinates[1]));
            points.add(new Point(coordinates[2], coordinates[3]));
            points.add(new Point(coordinates[4], coordinates[5]));

            repaint();
        }

        if (e.getSource() == a_BUTTON) {
            matcher = pat.matcher(a_FIELD.getText());

            int[] coordinates = new int[6];
            int i = 0;

            while (matcher.find()) {
                coordinates[i] = Integer.parseInt(matcher.group());
                i++;
            }

            angles.add(new AcuteAngle(
                    new Point(coordinates[0], coordinates[1]),
                    new Point(coordinates[2], coordinates[3]),
                    new Point(coordinates[4], coordinates[5])
            ));

            points.add(new Point(coordinates[0], coordinates[1]));
            points.add(new Point(coordinates[2], coordinates[3]));
            points.add(new Point(coordinates[4], coordinates[5]));

            repaint();
        }

        if (e.getSource() == clear_BUTTON) {
            points.clear();
            shapes.clear();
            mainShape = null;
            triangles.clear();
            angles.clear();
        }

        if (e.getSource() == solve_BUTTON) {
            flag = true;
        }

        if (e.getSource() == save_BUTTON) {
            File file = new File("file.txt");
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write(mainShape.triangle + "\n" + mainShape.angle + "\n" + "Square: " + mainShape.getSquare());
                bw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        repaint();
    }




}
