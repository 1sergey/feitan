package xo;


public class Rectangle {
    public Point a;
    public Point b;
    public Point c;
    public Point d;

    public Rectangle(Point p1, Point p2, Point p3) {
        this.a = p1;
        this.b = p2;
        Line line = new Line(p1, p2);
        double dist = line.Distance(p3);
        Point AB = new Point(p2.x-p1.x, p2.y-p1.y);
        Point AP = new Point(p3.x-p1.x, p3.y-p1.y);
        double direction = Math.signum(AB.cross(AP));
        Point offset = AB.rotated(Math.PI / 2 * direction).norm().mult(dist);
        this.c = new Point(p2.x+offset.x, p2.y+offset.y);
        this.d = new Point(p1.x+offset.x, p1.y+offset.y);
    }

    public Rectangle(AcuteAngle angle) {
        this.a = angle.top;
        this.b = angle.v1;
        this.c = angle.v2;
    }
}
