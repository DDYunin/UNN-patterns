import java.awt.*;
import java.io.OutputStream;
import java.io.PrintWriter;

public class BlackVisualScheme  implements  IVisualizationScheme {

        private static final Stroke DASHED_STROKE = new BasicStroke(1, BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);
        @Override
        public void drawSegment(Graphics g, IPoint p1, IPoint p2) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.BLACK);
            g2d.setStroke(DASHED_STROKE);
            g2d.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
        }
        @Override
        public void drawStartPoint(Graphics g, IPoint p) {
            g.setColor(Color.BLACK);
            g.fillRect((int)p.getX()-3, (int)p.getY()-3, 6, 6);
        }
        @Override
        public void drawEndPoint(Graphics g, IPoint p) {
            g.setColor(Color.BLACK);
            g.fillRect((int)p.getX()-3, (int)p.getY()-3, 6, 6);
        }

    @Override
    public void saveToSVG(OutputStream outputStream, VisualCurve curve) {
        // Реализация сохранения в SVG для черной схемы
        try (PrintWriter writer = new PrintWriter(outputStream)) {
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">");
            writer.println("<path stroke=\"black\" stroke-dasharray=\"5\" fill=\"none\" d=\"");
            // Здесь должна быть логика преобразования кривой в SVG
            writer.println("\"/>");
            writer.println("</svg>");
        }
    }
}
