public class CurveDecorator implements ICurve {
    protected ICurve decoratedCurve;

    public CurveDecorator(ICurve curve) {
        this.decoratedCurve = curve;
    }

    @Override
    public void getPoint(double t, IPoint p) {
        decoratedCurve.getPoint(t, p);
    }
}