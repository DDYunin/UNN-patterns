import java.awt.*;
import java.io.OutputStream;

public interface IVisualizationScheme {
    void drawSegment(Graphics g, IPoint p1, IPoint p2);
    void drawStartPoint(Graphics g, IPoint p);
    void drawEndPoint(Graphics g, IPoint p);
    void saveToSVG(OutputStream writer, VisualCurve curve);
}
