public class Bezier extends ACurve {
    private IPoint c;
    private IPoint d;

    public Bezier(IPoint a, IPoint b, IPoint c, IPoint d) {
        super(a, b);
        this.c = c;
        this.d = d;
    }

    @Override
    public void getPoint(double t, IPoint p) {
        double x = Math.pow(1 - t, 3) * a.getX()
                + 3 * Math.pow(1 - t, 2) * t * c.getX()
                + 3 * (1 - t) * Math.pow(t, 2) * d.getX()
                + Math.pow(t, 3) * b.getX();

        double y = Math.pow(1 - t, 3) * a.getY()
                + 3 * Math.pow(1 - t, 2) * t * c.getY()
                + 3 * (1 - t) * Math.pow(t, 2) * d.getY()
                + Math.pow(t, 3) * b.getY();

        p.setX(x);
        p.setY(y);
    }
}