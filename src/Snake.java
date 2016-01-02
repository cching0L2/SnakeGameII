import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Snake extends MovingElements{

    static private int INITIAL_X = Util.gridToPix(13);
    static private int INITIAL_Y = Util.gridToPix(3);
    private final static int INITIAL_LENGTH = 10;

    static private List<Position> snake = new LinkedList<Position>();
    static private Position lastTailPos = null;
    static private Boolean isDead = false;
    
    public Snake(Category cat, KeyController controller) {
        super(Category.Snake);
        for(int i = 0; i<INITIAL_LENGTH; i++){
            snake.add(new Position(INITIAL_X - Util.gridToPix(i), INITIAL_Y));
        }
    }

    @Override
    public void move(Direction dir) {
        Position prevHead = getHeadPosition();
        Position newLoc = new Position(prevHead, dir);
        snake.add(0, newLoc);
        lastTailPos = snake.get(snake.size()-1);
        snake.remove(lastTailPos);
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        for(Position p : snake){
            g.fillRect(p.getX()+Game.GB_X, p.getY()+Game.GB_Y, Game.GRID_SIZE, Game.GRID_SIZE);
        }
    }
    
    private Position getHeadPosition(){
        return snake.get(0);
    }
    
}