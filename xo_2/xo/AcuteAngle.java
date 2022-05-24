package xo;

import static xo.Point.sub;
import static xo.Point.sum;

public class AcuteAngle {
    public Point top;
    public Point v1;
    public Point v2;

    public AcuteAngle(Point p1, Point p2, Point p3) {
        this.top = p1;
        Point d = sub(p2, p1);
        this.v1 = sum(p2, d.mult(900));
        d = sub(p3, p1);
        this.v2 = sum(p3, d.mult(900));
    }
}
