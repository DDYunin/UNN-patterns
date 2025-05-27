public class Line extends ACurve {
    public Line(IPoint a, IPoint b) {
        super(a, b);
    }

    @Override
    public void getPoint(double t, IPoint p) {
        double x = (1 - t) * a.getX() + t * b.getX();
        double y = (1 - t) * a.getY() + t * b.getY();
        p.setX(x);
        p.setY(y);
    }
}
