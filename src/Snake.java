import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Snake extends MovingElements {

    protected int INITIAL_X = Util.gridToPix(13);
    protected int INITIAL_Y = Util.gridToPix(3);
    protected final int INITIAL_LENGTH = 15;
    protected Direction INITIAL_DIRECTION = Direction.Right;

    protected List<Position> snake = new LinkedList<Position>();
    private Position lastTailPos = null;
    private Boolean isDead = false;
    private Direction prevDir = INITIAL_DIRECTION;

    private Random random;
    private KeyController controller;
    private Handler handler;

    public Snake(Category cat, KeyController controller, Handler handler) {
        super(Category.Snake);
        random = new Random();
        this.handler = handler;
        this.controller = controller;
        initializeSnake();
    }

    protected void initializeSnake(){
        for (int i = 0; i < INITIAL_LENGTH; i++) {
            snake.add(new Position(INITIAL_X - Util.gridToPix(i), INITIAL_Y));
        }
    }
    
    @Override
    public void tick() {
        //motion control 
        if (controller.getDirection() == null) {
            move(INITIAL_DIRECTION);
        } else {
            move(controller.getDirection());
        }
        
        //control eating
        for(int i=0; i<handler.objects.size(); i++){
            GameElements tempObject = handler.objects.get(i);
            if(tempObject.getCategory()==Category.Food){
                Food food = (Food)tempObject;
                if(Collision.snakeEatFood(this, food)){
                    grow();
                    food.setEaten(true);
                    handler.removeObject(tempObject);
                    handler.addObject(new Cookie(new Position(random.nextInt(Util.pixToGrid(Game.GB_WIDTH))*Game.GRID_SIZE
                            , random.nextInt(Util.pixToGrid(Game.GB_HEIGHT))*Game.GRID_SIZE), Category.Food));
                }
            }
        }
        
        //control liveliness
        if(Collision.snakeHitSelf(this)||Collision.snakeHitWall(this)){
            Game.gameState = State.Over;
        }
    }

    @Override
    public void render(Graphics g) {
        for (Position p : snake) {
            g.fillRect(p.getX() + Game.GB_X, p.getY() + Game.GB_Y, Game.GRID_SIZE, Game.GRID_SIZE);
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

    public void grow() {
        snake.add(lastTailPos);
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }

    public boolean getDead() {
        return isDead;
    }
    
    public Rectangle getHeadBound(){
        Position head = getHeadPosition();
        return new Rectangle(head.getX(), head.getY(), Game.GRID_SIZE, Game.GRID_SIZE);
    }
    
    public ArrayList<Rectangle> getBodyBound(){
        ArrayList<Rectangle> returnList = new ArrayList<Rectangle>();
        if(snake.size()<=1) return returnList;
        else{
            for(int i=1; i<snake.size(); i++){
                Position p = snake.get(i);
                returnList.add(new Rectangle(p.getX(), p.getY(), Game.GRID_SIZE, Game.GRID_SIZE));
            }
            return returnList;
        }
    }

    private Position getHeadPosition() {
        return snake.get(0);
    }
    
    public int getSize(){
        return snake.size();
    }
    
    public int getInitialSize(){
        return this.INITIAL_LENGTH;
    }
    
    public void clearSnake(){
        snake.clear();
    }
}