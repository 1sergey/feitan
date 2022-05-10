package xo;

// Класс "Острый угол"
// Как и треугольник, имеет 3 точки - вершина и по одной на каждой стороне
public class AcuteAngle {
    public Point top;
    public Point v1;
    public Point v2;

    // Конструктор, создающий острый угол по 3-м точкам, указанным в параметрах
    public AcuteAngle(Point p1, Point p2, Point p3) {
        Point b = new Point(900*(p2.x-p1.x)+p1.x,(p2.y-p1.y)*900+p1.y);
        Point c = new Point(900*(p3.x-p1.x)+p1.x,(p3.y-p1.y)*900+p1.y);
        this.top = p1;
        this.v1 = b;
        this.v2 = c;
    }
    @Override
    public String toString() {
        return "Угол(" +
                "вершина=" + top +
                ", v1=" + v1 +
                ", v2=" + v2 +
                '}';
    }
}
