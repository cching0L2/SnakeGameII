import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Snake extends MovingElements {

    private int INITIAL_X = Util.gridToPix(13);
    private int INITIAL_Y = Util.gridToPix(3);
    private final int INITIAL_LENGTH = 10;
    private Direction INITIAL_DIRECTION = Direction.Right;

    static private List<Position> snake = new LinkedList<Position>();
    static private Position lastTailPos = null;
    static private Boolean isDead = false;
    private Direction prevDir = INITIAL_DIRECTION;

    private KeyController controller;

    public Snake(Category cat, KeyController controller) {
        super(Category.Snake);
        this.controller = controller;
        for (int i = 0; i < INITIAL_LENGTH; i++) {
            snake.add(new Position(INITIAL_X - Util.gridToPix(i), INITIAL_Y));
        }
    }

    @Override
    public void move(Direction dir) {
        Position head = getHeadPosition();
        Position firstSeg = null;
        Direction backDir = null;

        if (snake.size() > 1) {
            firstSeg = snake.get(1);

            if (firstSeg.getX() == head.getX() && (firstSeg.getY() - head.getY()) == Util.gridToPix(-1))
                backDir = Direction.Up;

            else if (firstSeg.getX() == head.getX() && (firstSeg.getY() - head.getY()) == Util.gridToPix(1))
                backDir = Direction.Down;

            else if (firstSeg.getY() == head.getY() && (firstSeg.getX() - head.getX()) == Util.gridToPix(1))
                backDir = Direction.Right;

            else if (firstSeg.getY() == head.getY() && (firstSeg.getX() - head.getX()) == Util.gridToPix(-1))
                backDir = Direction.Left;
        }
        Position newLoc = null;
        Position prevHead = getHeadPosition();
        if (dir != backDir) {
            newLoc = new Position(prevHead, dir);
            prevDir = dir;
        } else {
            newLoc = new Position(prevHead, prevDir);
        }
        snake.add(0, newLoc);
        lastTailPos = snake.get(snake.size() - 1);
        snake.remove(lastTailPos);
    }

    @Override
    public void tick() {
        if (controller.getDirection() == null)
            move(INITIAL_DIRECTION);
        else
            move(controller.getDirection());
    }

    @Override
    public void render(Graphics g) {
        for (Position p : snake) {
            g.fillRect(p.getX() + Game.GB_X, p.getY() + Game.GB_Y, Game.GRID_SIZE, Game.GRID_SIZE);
        }
    }

    private Position getHeadPosition() {
        return snake.get(0);
    }

}