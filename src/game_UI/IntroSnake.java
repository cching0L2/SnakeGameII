package game_UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import game_control.Collision;
import game_control.Direction;
import game_control.Handler;
import game_control.KeyController;
import game_control.Position;
import game_control.State;
import game_control.Util;
import game_elements.Category;
import game_elements.Snake;

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
            g.drawRect(p.getX()*Game.GRID_SIZE, p.getY()*Game.GRID_SIZE, Game.GRID_SIZE, Game.GRID_SIZE);
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
        if(!super.getHeadBound().intersects(new Rectangle(Util.pixToGrid(Game.GB_X)-super.getInitialSize(), 
                Util.pixToGrid(Game.GB_Y)-super.getInitialSize(),
                Util.pixToGrid(Game.GB_WIDTH)+2*super.getInitialSize(), 
                Util.pixToGrid(Game.GB_HEIGHT)+2*super.getInitialSize()))){
            super.setDead(true);
        }
            
        if (Collision.snakeHitSelf(this) || Collision.introSnakeHitWall(this)) {
            super.setDead(true);
            Game.gameState = State.Menu;
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
            initialY = -3;
            initialX = random.nextInt(Game.GB_WIDTH/Game.GRID_SIZE);
            orientation = Direction.Up;
            direction = Direction.Up;
            break;
        }
        case Down: {
            initialY = Game.GB_HEIGHT/Game.GRID_SIZE+super.getInitialSize();
            initialX = random.nextInt(Game.GB_WIDTH/Game.GRID_SIZE);
            orientation = Direction.Down;
            direction = Direction.Up;
            break;
        }
        case Right: {
            initialY = random.nextInt(Game.GB_HEIGHT/Game.GRID_SIZE);
            initialX = Game.GB_WIDTH/Game.GRID_SIZE+super.getInitialSize(); // appears from right
            orientation = Direction.Right;
            direction = Direction.Left;
            break;
        }
        case Left: {
            initialY = random.nextInt(Game.GB_HEIGHT/Game.GRID_SIZE)-8;
            initialX = -1; // appears from left
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