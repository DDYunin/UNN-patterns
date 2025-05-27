import java.awt.Graphics;

public class VisualBezier extends VisualCurve {
    public VisualBezier(IPoint a, IPoint b, IPoint c, IPoint d, IVisualizationScheme scheme) {
        super(new Bezier(a, b, c, d), scheme);
    }
}