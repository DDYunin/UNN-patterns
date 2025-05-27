import java.awt.Graphics;

public class VisualLine extends VisualCurve {
    public VisualLine(IPoint a, IPoint b) {
        super(new Line(a, b));
    }

    @Override
    public void draw(Graphics g) {
        IPoint start = new Point(0, 0);
        IPoint end = new Point(0, 0);

        // Получаем начальную точку (t=0)
        getPoint(0, start);
        // Получаем конечную точку (t=1)
        getPoint(1, end);

        g.drawLine((int) start.getX(), (int) start.getY(),
                (int) end.getX(), (int) end.getY());
    }
}