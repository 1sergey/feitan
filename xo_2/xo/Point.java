package xo;

public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double cross(Point v) {
        return this.x * v.y - this.y * v.x;
    }
    public Point rotated(double a) {
        return new Point(
                x * Math.cos(a) - y * Math.sin(a),
                x * Math.sin(a) + y * Math.cos(a)
        );
    }
    public double length() {
        return Math.sqrt(x * x + y * y);
    }
    public Point norm() {
        double length = length();
        return new Point(x / length, y / length);
    }
    public Point mult(double s) {
        return new Point(s * x, s * y);
    }
    public static Point sub(Point a, Point b) {
        return new Point(a.x - b.x, a.y - b.y);
    }
    public static Point sum(Point a, Point b) {
        return new Point(a.x + b.x, a.y + b.y);
    }
}
