import java.awt.Graphics;

public class VisualLine extends VisualCurve {
    public VisualLine(IPoint a, IPoint b, IVisualizationScheme scheme) {
        super(new Line(a, b), scheme);
    }
}