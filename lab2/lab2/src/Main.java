import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Создаем точки
        IPoint lineStart = new Point(100, 100);
        IPoint lineEnd = new Point(300, 100);

        IPoint bezierStart = new Point(100, 200);
        IPoint bezierControl1 = new Point(100, 300);
        IPoint bezierControl2 = new Point(400, 500);
        IPoint bezierEnd = new Point(400, 500);

        // Создаём схемы
        GreenVisualScheme greenScheme = new GreenVisualScheme();
        BlackVisualScheme blackScheme = new BlackVisualScheme();

        // Создаем визуальные объекты
        VisualLine visualLine = new VisualLine(
                lineStart,
                lineEnd,
                greenScheme
        );
        VisualBezier visualBezier = new VisualBezier(
                bezierStart, bezierControl1, bezierControl2, bezierEnd, blackScheme);

        // Создаем и настраиваем окно
        JFrame frame = new JFrame("Curves Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Очищаем панель
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());

                // Рисуем линии
                g.setColor(Color.BLACK);
                visualLine.draw(g);
                visualBezier.draw(g);
            }
        };

        frame.add(panel);
        frame.setVisible(true);
    }
}