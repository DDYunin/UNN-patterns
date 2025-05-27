import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class MainUI extends JFrame {
    private final DrawingPanel drawingPanel = new DrawingPanel();
    private final CommandHistory history = new CommandHistory();

    public MainUI() {
        setTitle("Circle Drag App with Undo (Ctrl+Z)");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(drawingPanel);

        setupUndoHandler();
        createCircles();
    }

    private void setupUndoHandler() {
        drawingPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK), "undo");
        drawingPanel.getActionMap().put("undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("\nПопытка отмены действия...");
                if (!history.isEmpty()) {
                    history.pop().undo();
                    drawingPanel.repaint();
                } else {
                System.out.println("Нет действий для отмены");
                }
            }
        });
    }

    private void createCircles() {
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            drawingPanel.addCircle(new Circle(
                    rand.nextInt(400) + 200,
                    rand.nextInt(500),
                    rand.nextInt(31) + 20
                    )
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainUI().setVisible(true));
    }

    // Внутренний класс панели рисования
    class DrawingPanel extends JPanel {
        private final java.util.List<Circle> circles = new ArrayList<>();
        private Circle draggedCircle = null;
        private Point dragOffset = null;

        public DrawingPanel() {
            setBackground(new Color(240, 240, 240));
            addMouseListener(new CircleMouseListener());
            addMouseMotionListener(new CircleMouseMotionListener());
        }

        public void addCircle(Circle circle) {
            circles.add(circle);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Circle circle : circles) {
                circle.draw((Graphics2D) g);
            }
        }

        private class CircleMouseListener extends MouseAdapter {
            private Point originalPosition;
            @Override
            public void mousePressed(MouseEvent e) {
                for (Circle circle : circles) {
                    if (circle.contains(e.getPoint())) {
                        draggedCircle = circle;
                        originalPosition = new Point(circle.getX(), circle.getY());
                        System.out.printf("Мышь нажата тут: (%d,%d)%n",
                                e.getX(), e.getY());
                        dragOffset = new Point(
                                (int) ((int) e.getX() - circle.getX()),
                                (int) ((int) e.getY() - circle.getY())
                        );
                        System.out.printf("Смещение: (%d,%d)%n",
                                dragOffset.x, dragOffset.y);
                        circle.setColor(Color.RED);
                        repaint();
                        break;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (draggedCircle != null) {
                    System.out.printf("Мышь опущена тут: (%d,%d)%n",
                            e.getX(), e.getY());
                    Point newPos = new Point(e.getX() - dragOffset.x, e.getY() - dragOffset.y);
                    System.out.printf("Создание команды перемещения: (%d,%d) -> (%d,%d)%n",
                            originalPosition.x, originalPosition.y, newPos.x, newPos.y);
                    ICommand moveCommand = new MoveCircleCommand(
                            draggedCircle,
                            originalPosition,
                            newPos);

                    moveCommand.execute();
                    history.push(moveCommand);

                    draggedCircle.setColor(Color.WHITE);
                    draggedCircle = null;
                    repaint();
                }
            }
        }

        private class CircleMouseMotionListener extends MouseMotionAdapter {
            @Override
            public void mouseMoved(MouseEvent e) {
                boolean needRepaint = false;
                for (Circle circle : circles) {
                    boolean isHovered = circle.contains(e.getPoint());

                    if (isHovered && circle.getColor() != Color.RED) {
                        circle.setColor(Color.GREEN);
                        needRepaint = true;
                    }
                    else if (!isHovered && circle.getColor() == Color.GREEN) {
                        circle.setColor(Color.WHITE);
                        needRepaint = true;
                    }
                }
                if (needRepaint) repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {

                if (draggedCircle != null) {
                    draggedCircle.setPosition(
                            e.getX() - dragOffset.x,
                            e.getY() - dragOffset.y
                    );
/*                    System.out.printf("Мышь опущена тут: (%d,%d)%n",
                            draggedCircle.getX(), draggedCircle.getY());*/
                    repaint();
                }
            }
        }
    }
}