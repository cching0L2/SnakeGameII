package game_control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import game_elements.*;
import game_UI.*;

public class Menu extends MouseAdapter{
    Handler handler;
    KeyController keyController;
    HUD hud;
    Random random;
    private String currentSelect="";
    
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
        
        if(Game.gameState==State.Menu){
            if(mouseSelect(XCursor, YCursor, 220, 255, 95, 18)){
                initializeGame(handler);
                Game.gameState = State.Game;
            }
            else if(mouseSelect(XCursor, YCursor, 250, 335, 38, 18))
                System.exit(0);
        }
        
        else if(Game.gameState==State.Over){
            handler.objects.clear();
            if(mouseSelect(XCursor, YCursor, 185, 380, 155, 40))
                System.exit(0);
            else if(mouseSelect(XCursor, YCursor, 185, 270, 155, 40)){
                initializeGame(handler);
                Game.gameState = State.Game;
            }
            else if(mouseSelect(XCursor, YCursor, 185, 325, 155, 40)){
                Game.gameState = State.Menu;
            }
        }
        else if(Game.gameState==State.Pause){
            if(mouseSelect(XCursor, YCursor, 185, 355, 155, 40))
                System.exit(0);
            else if(mouseSelect(XCursor, YCursor, 185, 295, 155, 40)){
                Game.gameState = State.Menu;
            }
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
    
    @Override 
    public void mouseMoved(MouseEvent e){
        int XCursor = e.getX();
        int YCursor = e.getY();
        if(mouseSelect(XCursor, YCursor, 220, 255, 95, 18)){
            currentSelect = "New Game";
        }
        else if(mouseSelect(XCursor, YCursor, 208, 295, 123, 18)){
            currentSelect = "Achievements";
        }
        else if(mouseSelect(XCursor, YCursor, 250, 335, 38, 18)){
            currentSelect = "Quit";
        }
        else if(mouseSelect(XCursor, YCursor, 218, 375, 108, 18)){
            currentSelect = "Reset Game";
        }
        else
            currentSelect = " ";
    }
    
    private void initializeGame(Handler handler){
        handler.objects.clear();
        handler.addObject(new Snake(Category.Snake, keyController, handler));
        handler.addObject(new Cookie(new Position(random.nextInt(Util.pixToGrid(Game.GB_WIDTH))*Game.GRID_SIZE
                , random.nextInt(Util.pixToGrid(Game.GB_HEIGHT))*Game.GRID_SIZE), Category.Food));
        keyController.resetInitialDirection(Direction.Right);
        LevelController.setLevel(0);
        LevelController.setScore(0);
        LevelController.clearPrevSnakeLength();
    }
    
    public String getCurrentSelect(){
        return currentSelect;
    }
}