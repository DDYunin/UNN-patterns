import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.util.Random;

public class MainUI extends JFrame {
    private VisualCurve visualLine;
    private VisualCurve visualBezier;
    private IVisualizationScheme greenScheme = new GreenVisualScheme();
    private IVisualizationScheme blackScheme = new BlackVisualScheme();

    // Панели для отображения кривых
    private JPanel panel1 = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (visualLine != null) {
                visualLine.draw(g);
            }
        }
    };

    private JPanel panel2 = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (visualBezier != null) {
                visualBezier.draw(g);
            }
        }
    };

    // Кнопки сохранения
    private JButton saveButton1 = new JButton("Сохранить в SVG-файле");
    private JButton saveButton2 = new JButton("Сохранить в SVG-файле");

    public MainUI() {
        super("Визуализация кривых");
        setLayout(new BorderLayout());

        // 1. Верхняя часть - панели визуализации
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Панель для первой схемы визуализации
        JPanel schemePanel1 = new JPanel(new BorderLayout());
        schemePanel1.setBorder(BorderFactory.createTitledBorder("Схема визуализации 1"));
        schemePanel1.add(panel1, BorderLayout.CENTER);
        schemePanel1.add(saveButton1, BorderLayout.SOUTH);
        saveButton1.setEnabled(false);

        // Панель для второй схемы визуализации
        JPanel schemePanel2 = new JPanel(new BorderLayout());
        schemePanel2.setBorder(BorderFactory.createTitledBorder("Схема визуализации 2"));
        schemePanel2.add(panel2, BorderLayout.CENTER);
        schemePanel2.add(saveButton2, BorderLayout.SOUTH);
        saveButton2.setEnabled(false);

        topPanel.add(schemePanel1);
        topPanel.add(schemePanel2);

        // 2. Нижняя часть - кнопка генерации
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton generateButton = new JButton("Генерация кривых");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateCurves();
                panel1.repaint();
                panel2.repaint();
                saveButton1.setEnabled(true);
                saveButton2.setEnabled(true);
            }
        });
        bottomPanel.add(generateButton);

        // Слушатель для кнопки сохранения первой панели
        saveButton1.addActionListener(e -> {
            saveToSVG(1); // Сохраняем первую кривую (visualLine)
        });

        // Слушатель для кнопки сохранения второй панели
        saveButton2.addActionListener(e -> {
            saveToSVG(2); // Сохраняем вторую кривую (visualBezier)
        });

        // Добавляем обе части в главное окно
        add(topPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Настройки окна
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Центрируем окно
    }

    private void generateCurves() {
        Random random = new Random();

        // Генерация линии
        IPoint lineStart = new Point(100 + random.nextInt(100), 100 + random.nextInt(100));
        IPoint lineEnd = new Point(300 + random.nextInt(100), 100 + random.nextInt(100));
        visualLine = new VisualLine(lineStart, lineEnd, greenScheme);

        // Генерация кривой Безье
        IPoint bezierStart = new Point(100 + random.nextInt(100), 300 + random.nextInt(100));
        IPoint bezierControl1 = new Point(200 + random.nextInt(100), 100 + random.nextInt(100));
        IPoint bezierControl2 = new Point(300 + random.nextInt(100), 400 + random.nextInt(100));
        IPoint bezierEnd = new Point(500 + random.nextInt(100), 300 + random.nextInt(100));
        visualBezier = new VisualBezier(bezierStart, bezierControl1, bezierControl2, bezierEnd, blackScheme);
    }

    private void saveToSVG(int schemeNumber) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (FileOutputStream fos = new FileOutputStream(fileChooser.getSelectedFile())) {
                if (schemeNumber == 1) {
                    greenScheme.saveToSVG(fos, visualLine);
                } else {
                    blackScheme.saveToSVG(fos, visualBezier);
                }
                JOptionPane.showMessageDialog(this, "Файл успешно сохранен!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ошибка при сохранении файла: " + ex.getMessage(),
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainUI ui = new MainUI();
            ui.setVisible(true);
        });
    }
}