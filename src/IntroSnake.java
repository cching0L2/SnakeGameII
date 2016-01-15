import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class IntroSnake extends Snake {
    private Random random;
    private Direction direction = null;
    private Color randColor;

    public IntroSnake(Category cat, KeyController controller, Handler handler) {
        super(cat, controller, handler);
        randColor = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    @Override
    public void render(Graphics g) {
        g.setColor(randColor);
        for (Position p : snake) {
            g.drawRect(p.getX(), p.getY(), Game.GRID_SIZE, Game.GRID_SIZE);
        }
    }

    @Override
    public void tick() {
        // motion control
        if (direction == null) {
            direction = Util.getRandomDirection();
        } else {
            if (random.nextInt(10) == 0)
                direction = Util.getRandomDirection();
        }
        move(direction);

        // IntroSnake does not eat

        // control liveliness
        if (Collision.snakeHitSelf(this) || Collision.introSnakeHitWall(this)) {
            super.setDead(true);
        }
    }
    
    

    @Override
    protected void initializeSnake() {
        random = new Random();
        Direction appearDirection = Util.getRandomDirection();
        Direction orientation;
        int initialX, initialY;

        switch (appearDirection) {
        case Up: {
            initialY = -Game.GRID_SIZE;
            initialX = random.nextInt(Game.WIDTH);
            orientation = Direction.Up;
            direction = Direction.Up;
            break;
        }
        case Down: {
            initialY = Game.HEIGHT;
            initialX = random.nextInt(Game.WIDTH);
            orientation = Direction.Down;
            direction = Direction.Up;
            break;
        }
        case Right: {
            initialY = random.nextInt(Game.HEIGHT);
            initialX = Game.WIDTH; // appears from right
            orientation = Direction.Right;
            direction = Direction.Left;
            break;
        }
        case Left: {
            initialY = random.nextInt(Game.GB_HEIGHT);
            initialX = -Game.GRID_SIZE; // appears from left
            orientation = Direction.Left;
            direction = Direction.Right;
            break;

        }
        default: {
            initialX = 0;
            initialY = 0;
            orientation = Direction.Left;
            direction = Direction.Right;
            break;
        }
        }
        
        snake.add(new Position(initialX, initialY)); //add head
        for (int i = 1; i < INITIAL_LENGTH; i++) {
            snake.add(new Position(snake.get(i-1), orientation));
        }
    }
}