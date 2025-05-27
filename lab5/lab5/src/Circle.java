import java.awt.*;

public class Circle {
    private int x;
    private int y;
    private int radius;

    private Color color;

    public Circle(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = Color.WHITE;
    }

    public void  setColor(Color color) { this.color = color; }
    public Color getColor() { return color; }
    public int getX() { return this.x; }
    public void setPosition(int x, int y) { this.x = x; this.y = y; }
    public int getY() { return this.y; }

    public boolean contains(Point p) {
        return Math.pow(p.x - this.x, 2) + Math.pow(p.y - this.y, 2) <= Math.pow(radius, 2);
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(
                this.x - radius,
                this.y - radius,
                radius * 2,
                radius * 2
        );
    }


}
