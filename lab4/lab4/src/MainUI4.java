import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class MainUI4 extends JFrame {
    private VisualCurve visualChain;
    private IVisualizationScheme scheme = new BlackVisualScheme();

    private JPanel drawingPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (visualChain != null) {
                visualChain.draw(g);
            }
        }
    };

    public MainUI4() {
        super("Композитные кривые");
        setLayout(new BorderLayout());

        // Панель управления
        JPanel controlPanel = new JPanel();
        JButton createChainButton = new JButton("Создать цепь из 3 кривых");
        createChainButton.addActionListener(e -> createChain());
        controlPanel.add(createChainButton);

        add(controlPanel, BorderLayout.NORTH);
        add(drawingPanel, BorderLayout.CENTER);

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createChain() {
        // Создаем 3 кривые
        ICurve line1 = new Line(
                new Point(100, 300),
                new Point(250, 150)
        );
        ICurve bezier = new Bezier(
                new Point(250, 150),
                new Point(500, 300),
                new Point(350, 150),
                new Point(350, 300)
        );
        ICurve line2 = new Line(
                new Point(500, 300),
                new Point(650, 100)
        );

        // Собираем цепь
        ICurve chain = new Chain(line1, bezier, line2);

        // Визуализируем
        visualChain = new VisualCurve(chain, scheme) {
            @Override
            public void getPoint(double t, IPoint p) {
                chain.getPoint(t, p);
            }
        };

        drawingPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainUI4 ui = new MainUI4();
            ui.setVisible(true);
        });
    }
}