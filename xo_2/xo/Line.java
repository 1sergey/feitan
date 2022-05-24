package xo;

public class Line {
    public Point p1;
    private final double a;
    private final double b;
    private final double c;
    public Point p2;

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
        a = p1.y - p2.y;
        b = p2.x - p1.x;
        c = p1.x * p2.y - p2.x * p1.y;
    }
    public double Distance(Point pos) {
        return Math.abs(a * pos.x + b * pos.y + c) / Math.sqrt(a * a + b * b);
    }

}
