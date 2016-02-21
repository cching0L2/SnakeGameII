package game_elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import game_UI.Game;
import game_UI.GameWindows;
import game_control.Collision;
import game_control.Direction;
import game_control.Handler;
import game_control.KeyController;
import game_control.Position;

public class SmartSnake extends Snake{
    private Random random = new Random();
    private Color randColor;
    private Direction[] direction = {Direction.Right, Direction.Down, Direction.Right, Direction.Up, Direction.Right, 
            Direction.Up, Direction.Right, Direction.Down, 
            Direction.Right, Direction.Up, Direction.Right, Direction.Down, Direction.Left, Direction.Down, Direction.Right,
            Direction.Up, Direction.Right, Direction.Up, Direction.Right};
    private int[] repetition = {1, 3, 2, 3, 4, 3, 8, 2, 4, 4, 8, 10, 3, 2, 12, 9, 8, 6, 10};
    private int index = 0;
    private int repLeft = repetition[0];
    
    public SmartSnake(Category cat, KeyController controller, Handler handler) {
        super(cat, null, handler);
        randColor = new Color(random.nextInt(205)+50, random.nextInt(255), random.nextInt(155)+100);
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(randColor);
        for (Position p : snake) {
            Rectangle body = new Rectangle(p.getX()*Game.GRID_SIZE, p.getY()*Game.GRID_SIZE, Game.GRID_SIZE, Game.GRID_SIZE);
            if(body.intersects(GameWindows.GameInstruction.getBound()))
                g.fillRect(p.getX()*Game.GRID_SIZE, p.getY()*Game.GRID_SIZE, Game.GRID_SIZE, Game.GRID_SIZE);
        }
    }

    @Override
    public void tick() {
        move(direction[index]);
        repLeft--;
        
        if(repLeft==0){
            index++;
            if(index==repetition.length){
                index = 0;
                snake.clear();
                initializeSnake();
            }
            repLeft = repetition[index];
        }
        
        if(Collision.snakeEatFood(this, this.handler))
            this.grow();
    }
   
    @Override
    protected void initializeSnake() {
        int initialX = 0, initialY = 10;

        snake.add(new Position(initialX, initialY)); //add head
        for (int i = 1; i < INITIAL_LENGTH; i++) {
            snake.add(new Position(snake.get(i-1), Direction.Left));
        }
    }
}