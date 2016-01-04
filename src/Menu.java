import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Menu extends MouseAdapter{
    Handler handler;
    KeyController keyController;
    HUD hud;
    Random random;
    
    public Menu(KeyController keyController, HUD hud, Handler handler){
        this.handler = handler;
        this.keyController = keyController;
        this.hud = hud;
        random = new Random();
    }
    
    @Override 
    public void mousePressed(MouseEvent e) {
        int XCursor = e.getX();
        int YCursor = e.getY();
        
        if(Game.gameState==State.Over){
            if(mouseSelect(XCursor, YCursor, 185, 380, 155, 40))
                System.exit(0);
            else if(mouseSelect(XCursor, YCursor, 185, 270, 155, 40)){
                handler.objects.clear();
                handler.addObject(new Snake(Category.Snake, keyController, handler, hud));
                handler.addObject(new Cookie(new Position(random.nextInt(Util.pixToGrid(Game.GB_WIDTH))*Game.GRID_SIZE
                        , random.nextInt(Util.pixToGrid(Game.GB_HEIGHT))*Game.GRID_SIZE), Category.Food));
                keyController.resetInitialDirection(Direction.Right);
                hud.setLevel(0);
                hud.setScore(0);
                Game.gameState = State.Game;
            }
        }
        else if(Game.gameState==State.Pause){
            if(mouseSelect(XCursor, YCursor, 185, 355, 155, 40))
                System.exit(0);
        }
    }
    
    public boolean mouseSelect(int XCursor, int YCursor, int x, int y, int width, int height){
        if(XCursor>=x && XCursor<=(x+width)){
            if(YCursor>=y && YCursor<=(y+height))
                return true;
            return false;
        }
        return false;
    }
}