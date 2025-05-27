public class MoveTo extends CurveDecorator {
    private IPoint newStartPoint;

    public MoveTo(ICurve curve, IPoint newStartPoint) {
        super(curve);
        this.newStartPoint = newStartPoint;
    }

    @Override
    public void getPoint(double t, IPoint p) {
        // Получаем точку на исходной кривой
        decoratedCurve.getPoint(t, p);

        // Получаем начальную точку исходной кривой
        IPoint originalStart = new Point(0, 0);
        decoratedCurve.getPoint(0, originalStart);

        // Вычисляем смещение
        double dx = newStartPoint.getX() - originalStart.getX();
        double dy = newStartPoint.getY() - originalStart.getY();

        // Применяем смещение
        p.setX(p.getX() + dx);
        p.setY(p.getY() + dy);
    }
}