import java.awt.Graphics;

public abstract class VisualCurve implements ICurve, IDrawable {
    protected ICurve curve;

    public VisualCurve(ICurve curve) {
        this.curve = curve;
    }

    @Override
    public void getPoint(double t, IPoint p) {
        curve.getPoint(t, p);
    }

    @Override
    public abstract void draw(Graphics g);
}