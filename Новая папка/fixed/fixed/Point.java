package fixed;
// поля
public class Point {
    public int x;
    public int y;
    // метод задающий точку
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Точка{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
