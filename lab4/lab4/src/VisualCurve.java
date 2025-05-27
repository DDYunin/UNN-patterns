import java.awt.Graphics;

public abstract class VisualCurve implements ICurve, IDrawable {
    protected ICurve curve;
    protected int pointsCount = 100;

    protected IVisualizationScheme scheme;

    public VisualCurve(ICurve curve, IVisualizationScheme scheme) {
        this.curve = curve;
        this.scheme = scheme;
    }

    public void setScheme(IVisualizationScheme scheme) {
        this.scheme = scheme;
    }

    @Override
    public void getPoint(double t, IPoint p) {
        curve.getPoint(t, p);
    }

    @Override
    public void draw(Graphics g) {
        IPoint prevPoint = new Point(0, 0);
        IPoint currentPoint = new Point(0, 0);

        // Получаем начальную точку
        getPoint(0, prevPoint);
        scheme.drawStartPoint(g, prevPoint);

        // Рисуем сегменты
        for (int i = 1; i <= pointsCount; i++) {
            double t = (double) i / pointsCount;
            getPoint(t, currentPoint);
            scheme.drawSegment(g, prevPoint, currentPoint);
            prevPoint.setX(currentPoint.getX());
            prevPoint.setY(currentPoint.getY());
        }

        // Рисуем конечную точку
        scheme.drawEndPoint(g, currentPoint);
    }
}