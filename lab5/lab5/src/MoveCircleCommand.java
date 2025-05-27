import java.awt.*;

public class MoveCircleCommand implements ICommand {
    private Circle circle;
    private Point from;
    private Point to;

    public MoveCircleCommand(Circle circle, Point from, Point to) {
        this.circle = circle;
        this.from = new Point(from);
        this.to = new Point(to);;
    }

    @Override
    public void execute() {
        circle.setPosition(to.x, to.y);
    }

    @Override
    public void undo() {
        circle.setPosition(from.x, from.y);
    }

    @Override
    public String toString() {
        return String.format("MoveCircleCommand: (%d,%d) -> (%d,%d)",
                from.x, from.y, to.x, to.y);
    }
}
