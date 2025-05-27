import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class MainUI3 extends JFrame {
    private VisualCurve originalCurve;
    private VisualCurve modifiedCurve;
    private IVisualizationScheme scheme;

    private int curveType;

    private JPanel originalPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (originalCurve != null) {
                originalCurve.draw(g);
            }
        }
    };

    private JPanel modifiedPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (modifiedCurve != null) {
                modifiedCurve.draw(g);
            }
        }
    };

    public MainUI3() {
        super("Управление кривыми с декораторами");
        setLayout(new BorderLayout());

        // Панель управления
        JPanel controlPanel = new JPanel(new GridLayout(1, 4));

        JButton generateButton = new JButton("Создать кривую");
        generateButton.addActionListener(this::generateCurve);

        JButton reverseButton = new JButton("Поменять начало и конец");
        reverseButton.addActionListener(this::reverseCurve);

        JButton moveFragmentButton = new JButton("Сместить фрагмент");
        moveFragmentButton.addActionListener(this::moveFragment);

        JButton combineButton = new JButton("Совместить фрагменты");
        combineButton.addActionListener(this::combineFragments);

        controlPanel.add(generateButton);
        controlPanel.add(reverseButton);
        controlPanel.add(moveFragmentButton);
        controlPanel.add(combineButton);
        add(controlPanel, BorderLayout.NORTH);

        // Панели визуализации
        JPanel visualizationPanel = new JPanel(new GridLayout(1, 2));
        originalPanel.setBorder(BorderFactory.createTitledBorder("Исходная кривая"));
        modifiedPanel.setBorder(BorderFactory.createTitledBorder("Модифицированная кривая"));
        visualizationPanel.add(originalPanel);
        visualizationPanel.add(modifiedPanel);
        add(visualizationPanel, BorderLayout.CENTER);

        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void generateCurve(ActionEvent e) {
        Random random = new Random();
        curveType = random.nextInt(2);

        if (curveType == 0) {
            scheme = new GreenVisualScheme();
        } else {
            scheme = new BlackVisualScheme();
        }

        if (curveType == 0) {
            // Линия
            IPoint start = new Point(100 + random.nextInt(100), 100 + random.nextInt(100));
            IPoint end = new Point(300 + random.nextInt(100), 100 + random.nextInt(100));
            originalCurve = new VisualLine(start, end, scheme);
        } else {
            // Кривая Безье
            IPoint bezierStart = new Point(100 + random.nextInt(100), 300 + random.nextInt(100));
            IPoint bezierControl1 = new Point(200 + random.nextInt(100), 100 + random.nextInt(100));
            IPoint bezierControl2 = new Point(300 + random.nextInt(100), 400 + random.nextInt(100));
            IPoint bezierEnd = new Point(500 + random.nextInt(100), 300 + random.nextInt(100));
            originalCurve = new VisualBezier(bezierStart, bezierControl1, bezierControl2, bezierEnd, scheme);
        }

        modifiedCurve = null;
        originalPanel.repaint();
        modifiedPanel.repaint();
    }

    private void reverseCurve(ActionEvent e) {
        if (originalCurve == null) return;

        // Создаем фрагмент с обратным направлением (от 1 до 0)
        ICurve reversed = new Fragment(originalCurve, 1, 0);
        modifiedCurve = new VisualCurve(reversed, scheme) {
            @Override
            public void getPoint(double t, IPoint p) {
                reversed.getPoint(t, p);
            }
        };

        modifiedPanel.repaint();
    }

    private void moveFragment(ActionEvent e) {
        if (originalCurve == null) return;

        // Выбираем случайный фрагмент кривой
        double tStart = 0.2;
        double tEnd = 0.8;
        ICurve fragment = new Fragment(originalCurve, tStart, tEnd);

        // Создаем случайную точку для перемещения
        Random random = new Random();
        IPoint newPoint = new Point(
                100 + random.nextInt(100),
                200 + random.nextInt(300)
        );

        // Перемещаем фрагмент
        ICurve movedFragment = new MoveTo(fragment, newPoint);
        modifiedCurve = new VisualCurve(movedFragment, scheme) {
            @Override
            public void getPoint(double t, IPoint p) {
                movedFragment.getPoint(t, p);
            }
        };

        modifiedPanel.repaint();
    }

    private void combineFragments(ActionEvent e) {
        if (originalCurve == null) return;

        // Создаем вторую кривую для комбинирования
        Random random = new Random();
        ICurve secondCurve;
        if (random.nextBoolean()) {
            IPoint start = new Point(100, 300);
            IPoint end = new Point(600, 300);
            secondCurve = new Line(start, end);
        } else {
            IPoint start = new Point(100, 400);
            IPoint control1 = new Point(200, 200);
            IPoint control2 = new Point(400, 500);
            IPoint end = new Point(600, 400);
            secondCurve = new Bezier(start, control1, control2, end);
        }

        // Выбираем фрагменты из обеих кривых
        ICurve fragment1 = new Fragment(originalCurve, 0.3, 0.7);
        ICurve fragment2 = new Fragment(secondCurve, 0.1, 0.9);

        // Создаем составную кривую
        ICurve combinedCurve = new ICurve() {
            @Override
            public void getPoint(double t, IPoint p) {
                if (t < 0.5) {
                    // Первая половина - фрагмент первой кривой
                    fragment1.getPoint(t * 2, p);
                } else {
                    // Вторая половина - фрагмент второй кривой
                    fragment2.getPoint((t - 0.5) * 2, p);
                }
            }
        };

        modifiedCurve = new VisualCurve(combinedCurve, scheme) {
            @Override
            public void getPoint(double t, IPoint p) {
                combinedCurve.getPoint(t, p);
            }
        };

        modifiedPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainUI3 ui = new MainUI3();
            ui.setVisible(true);
        });
    }
}