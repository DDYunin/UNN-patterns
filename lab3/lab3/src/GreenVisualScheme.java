import java.awt.*;
import java.io.OutputStream;
import java.io.PrintWriter;


public class GreenVisualScheme implements  IVisualizationScheme {

    @Override
    public void drawSegment(Graphics g, IPoint p1, IPoint p2) {
        g.setColor(Color.GREEN);
        g.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
    }
    @Override
    public void drawStartPoint(Graphics g, IPoint p) {
        g.setColor(Color.GREEN);
        g.fillOval((int)p.getX()-3, (int)p.getY()-3, 6, 6);
    }
    @Override
    public void drawEndPoint(Graphics g, IPoint p) {
        g.setColor(Color.GREEN);
        g.drawLine((int)p.getX(), (int)p.getY(), (int)p.getX()-10, (int)p.getY()-5);
        g.drawLine((int)p.getX(), (int)p.getY(), (int)p.getX()-10, (int)p.getY()+5);
    }

    @Override
    public void saveToSVG(OutputStream outputStream, VisualCurve curve) {
        // Реализация сохранения в SVG для зеленой схемы
        try (PrintWriter writer = new PrintWriter(outputStream)) {
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">");
            // Здесь должна быть логика преобразования кривой в SVG
            writer.println("</svg>");
        }
    }
}
