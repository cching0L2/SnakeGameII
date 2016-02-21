package game_elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import game_UI.*;
import game_control.*;

public class Snake extends MovingElements {

    protected int INITIAL_X = 13;
    protected int INITIAL_Y = 3;
    protected final int INITIAL_LENGTH = 15;
    protected Direction INITIAL_DIRECTION = Direction.Right;

    protected List<Position> snake = new LinkedList<Position>();
    private Position lastTailPos = null;
    private Boolean isDead = false;
    private Direction prevDir = INITIAL_DIRECTION;

    private KeyController controller;
    private Handler handler;

    public Snake(Category cat, KeyController controller, Handler handler) {
        super(Category.Snake);
        this.handler = handler;
        this.controller = controller;
        initializeSnake();
    }

    protected void initializeSnake() {
        for (int i = 0; i < INITIAL_LENGTH; i++) {
            snake.add(new Position(INITIAL_X - i, INITIAL_Y));
        }
    }

    @Override
    public void tick() {
        // motion control
        if (controller.getDirection() == null)
            move(INITIAL_DIRECTION);
        else
            move(controller.getDirection());

        // control eating
        if (Collision.snakeEatFood(this, handler)) {
            grow();
        }

        // control liveliness
        if (Collision.snakeHitSelf(this) || Collision.snakeHitWall(this) || Collision.snakeHitObstacle(this, handler)) {
            Game.gameState = State.Over;
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.gray);
        for (Position p : snake) {
            g.fillRect(p.getX()*Game.GRID_SIZE + Game.GB_X, p.getY()*Game.GRID_SIZE + Game.GB_Y, Game.GRID_SIZE, Game.GRID_SIZE);
        }
    }

    @Override
    public void move(Direction dir) {
        Position head = getHeadPosition();
        Position firstSeg = null;
        Direction backDir = null;

        firstSeg = snake.get(1);

        if (firstSeg.getX() == head.getX() && (firstSeg.getY() - head.getY()) == -1)
            backDir = Direction.Up;

        else if (firstSeg.getX() == head.getX() && (firstSeg.getY() - head.getY()) == 1)
            backDir = Direction.Down;

        else if (firstSeg.getY() == head.getY() && (firstSeg.getX() - head.getX()) == 1)
            backDir = Direction.Right;

        else if (firstSeg.getY() == head.getY() && (firstSeg.getX() - head.getX()) == -1)
            backDir = Direction.Left;
            
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

    public void grow() {
        snake.add(lastTailPos);
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }

    public boolean getDead() {
        return isDead;
    }

    public Rectangle getHeadBound() {
        Position head = getHeadPosition();
        return new Rectangle(head.getX(), head.getY(), 1, 1);
    }

    public ArrayList<Rectangle> getBodyBound() {
        ArrayList<Rectangle> returnList = new ArrayList<Rectangle>();
        if (snake.size() <= 1)
            return returnList;
        else {
            for (int i = 1; i < snake.size(); i++) {
                Position p = snake.get(i);
                returnList.add(new Rectangle(p.getX(), p.getY(), 1,1));
            }
            return returnList;
        }
    }

    public void repositionSnake() {
        snake.clear();
        for (int i = 0; i < INITIAL_LENGTH; i++) {
            snake.add(new Position(INITIAL_X - i, INITIAL_Y));
        }
    }

    private Position getHeadPosition() {
        return snake.get(0);
    }

    public int getSize() {
        return snake.size();
    }

    public int getInitialSize() {
        return this.INITIAL_LENGTH;
    }
    
    public List<Position> getPositions(){
        List<Position> returnList = new ArrayList<Position>();
        returnList.addAll(snake);
        return returnList;
    }

    public void clearSnake() {
        snake.clear();
    }

    @Override
    public String getName() {
        return "snake";
    }
}