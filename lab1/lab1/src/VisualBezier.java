import java.awt.Graphics;

public class VisualBezier extends VisualCurve {
    public VisualBezier(IPoint a, IPoint b, IPoint c, IPoint d) {
        super(new Bezier(a, b, c, d));
    }

    @Override
    public void draw(Graphics g) {
        IPoint p1 = new Point(0, 0);
        IPoint p2 = new Point(0, 0);

        // Получаем первую точку кривой
        curve.getPoint(0, p1);

        // Рисуем кривую как последовательность коротких отрезков
        for (double t = 0.01; t <= 1; t += 0.01) {
            curve.getPoint(t, p2);
            g.drawLine((int)p1.getX(), (int)p1.getY(),
                    (int)p2.getX(), (int)p2.getY());
            p1.setX(p2.getX());
            p1.setY(p2.getY());
        }
    }
}