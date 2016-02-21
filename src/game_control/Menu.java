package game_control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import game_UI.*;

public class Menu extends MouseAdapter{
    Handler handler;
    LevelController levelController;
    HUD hud;
    Random random;
    private String currentSelect="";
    
    int XCursor, YCursor;
    
    public Menu(LevelController levelController, HUD hud, Handler handler){
        this.handler = handler;
        this.levelController = levelController;
        this.hud = hud;
        random = new Random();
    }
    
    @Override 
    public void mousePressed(MouseEvent e) {
        XCursor = e.getX();
        YCursor = e.getY();
        
        if(Game.gameState==State.Menu){
            if(currentSelect == "New Game"){
                levelController.initializeGame(handler);
                Game.gameState = State.Game;
            }
            else if(currentSelect == "Quit")
                System.exit(0);
            else if(currentSelect == "Achievements")
                Game.gameState = State.Achievement;
            else if(currentSelect == "Game Instruction")
                Game.gameState = State.Instruction;
        }
        
        else if(Game.gameState==State.Over){
            handler.objects.clear();
            if(mouseSelect(XCursor, YCursor, 185, 380, 155, 40))
                System.exit(0);
            else if(mouseSelect(XCursor, YCursor, 185, 270, 155, 40)){
                levelController.initializeGame(handler);
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
        else if(Game.gameState==State.Achievement){
            if(mouseSelect(XCursor, YCursor, 75, 505, 150, 25))
                Game.gameState = State.Menu;
        }
        else if(Game.gameState==State.Instruction){
            if(mouseSelect(XCursor, YCursor, 213, 522, 110, 25))
                Game.gameState = State.Menu;
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
        XCursor = e.getX();
        YCursor = e.getY();
        
        if(mouseSelect(XCursor, YCursor, 220, 261, 93, 15))
            currentSelect = "New Game";
        else if(mouseSelect(XCursor, YCursor, 208, 331, 122, 15))
            currentSelect = "Achievements";
        else if(mouseSelect(XCursor, YCursor, 250, 366, 36, 15))
            currentSelect = "Quit";
        else if(mouseSelect(XCursor, YCursor, 218, 401, 105, 15))
            currentSelect = "Reset Game";
        else if(mouseSelect(XCursor, YCursor, 195, 296, 150, 15))
            currentSelect = "Game Instruction";
        else
            currentSelect = "";
    }
    
    public String getCurrentSelect(){
        return currentSelect;
    }
    
    public int getXCursor(){
        return XCursor;
    }
    
    public int getYCursor(){
        return YCursor;
    }
}