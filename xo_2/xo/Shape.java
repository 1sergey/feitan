package xo;

import java.util.ArrayList;
import java.util.List;

import static xo.Point.sub;

public class Shape {
    public final Rectangle rectangle;
    public final AcuteAngle angle;

    public static List<Point> result = new ArrayList<>();

    public Shape(Rectangle rectangle, AcuteAngle angle) {
        this.rectangle = rectangle;
        this.angle = angle;

        findUnionShape();
    }

    public void findUnionShape() {
        result = new ArrayList<>();

        Line AB = new Line(rectangle.a, rectangle.b);
        Line BC = new Line(rectangle.b, rectangle.c);
        Line CD = new Line(rectangle.c, rectangle.d);
        Line DA = new Line(rectangle.d, rectangle.a);

        Line firstSide = new Line(angle.top, new Point(angle.v1.x, angle.v1.y));
        Line secondSide = new Line(angle.top, new Point(angle.v2.x, angle.v2.y));

        if (isInsideRect(angle.top, rectangle)) {
            result.add(angle.top);
        }
        if (isInsideTheTriangle(rectangle.a, new Rectangle(angle))) {
            result.add(rectangle.a);
        }
        if (isInsideTheTriangle(rectangle.b, new Rectangle(angle))) {
            result.add(rectangle.b);
        }
        if (isInsideTheTriangle(rectangle.c, new Rectangle(angle))) {
            result.add(rectangle.c);
        }
        if (isInsideTheTriangle(rectangle.d, new Rectangle(angle))) {
            result.add(rectangle.d);
        }

        addPoint(AB, firstSide);
        addPoint(BC, firstSide);
        addPoint(CD, firstSide);
        addPoint(DA, firstSide);
        addPoint(AB, secondSide);
        addPoint(BC, secondSide);
        addPoint(CD, secondSide);
        addPoint(DA, secondSide);
    }

    private void addPoint(Line line2, Line line1) {
        Point point = pointOfIntersection(line2, line1);

        if (point != null
                && isInsideRect(point, this.rectangle)
                && isInsideTheTriangle(point, new Rectangle(this.angle))) {
            result.add(point);
        }
    }

    private static boolean isInsideRect(Point p, Rectangle t){
        double q1 = sub(p, t.a).cross(sub(t.d, t.a));
        double q2 = sub(p, t.b).cross(sub(t.a, t.b));
        double q3 = sub(p, t.c).cross(sub(t.b, t.c));
        double q4 = sub(p, t.d).cross(sub(t.c, t.d));
        return (q1 >= -10 && q2 >= -10 && q3 >= -10 && q4 >= -10) || (q1 <= 10 && q2 <= 10 && q3 <= 10 && q4 <= 10);
    }

    private static boolean isInsideTheTriangle(Point p, Rectangle t) {
        double x1, x2, x3, x0;
        double y1, y2, y3, y0;

        x0 = p.x;
        y0 = p.y;
        x1 = t.a.x;
        x2 = t.b.x;
        x3 = t.c.x;
        y1 = t.a.y;
        y2 = t.b.y;
        y3 = t.c.y;

        double q1 = (x1 - x0) * (y2 - y1) - (x2 - x1) * (y1 - y0);
        double q2 = (x2 - x0) * (y3 - y2) - (x3 - x2) * (y2 - y0);
        double q3 = (x3 - x0) * (y1 - y3) - (x1 - x3) * (y3 - y0);
        return (q1 >= -10 && q2 >= -10 && q3 >= -10) || (q1 <= 10 && q2 <= 10 && q3 <= 10);
    }

    private static Point pointOfIntersection(Line l1, Line l2) {
        double x1 = l1.p1.x;
        double y1 = l1.p1.y;
        double x2 = l1.p2.x;
        double y2 = l1.p2.y;
        double x3 = l2.p1.x;
        double y3 = l2.p1.y;
        double x4 = l2.p2.x;
        double y4 = l2.p2.y;

        if ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4) == 0) return null;

        double x, y;
        double a = (y2 - y1) / (x2 - x1);
        double b = (y4 - y3) / (x4 - x3);
        double c = y1 - a * x1;
        double d = y3 - b * x3;
        x = (d - c) / (a - b);
        y = a * x + c;
        return new Point(x, y);
    }

    public double getSquare() {
        double s = 0;
        for (int i = 0; i < result.size() - 1; i++) {
            s += (result.get(i).x + result.get(i + 1).y);
            s -= (result.get(i + 1).x + result.get(i).y);
        }
        return Math.abs(s) * 0.5;
    }
}
