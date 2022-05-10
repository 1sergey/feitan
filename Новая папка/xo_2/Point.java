package xo;

// Класс Точка - описывает объект типа "Точка"
// Имеет два вещественных поля - две координаты
public class Point {
    public int x;
    public int y;

    // Конструтор, принимающий на вход два вещественных числа,
    // создает по ним новую точку
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
