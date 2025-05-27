public class Fragment extends CurveDecorator {
    private double tStart;
    private double tFinish;

    public Fragment(ICurve curve, double tStart, double tFinish) {
        super(curve);
        this.tStart = tStart;
        this.tFinish = tFinish;
    }

    @Override
    public void getPoint(double t, IPoint p) {
        // Преобразуем t из [0,1] в [tStart, tFinish]
        double transformedT = tStart + t * (tFinish - tStart);
        decoratedCurve.getPoint(transformedT, p);
    }
}