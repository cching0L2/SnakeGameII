package game_control;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import game_UI.Game;
import game_elements.GameElements;

public class Util {
    
    private Util(){} //cannot be instantiated 
    
    public static int clamp(int val, int min, int max) {
        if (val < min)
            return min;
        else if (val > max)
            return max;
        else
            return val;
    }
    
    public static double clampDouble(double val, double min, double max){
        if (val < min)
            return min;
        else if (val > max)
            return max;
        else
            return val;
    }
    
    public static int pixToGrid(int pix){
        return pix / Game.GRID_SIZE;
    }
    
    public static int gridToPix(int grid){
        return grid * Game.GRID_SIZE;
    }
    
    public static ImageIcon createImageIcon(String name, String path){
        java.net.URL imageURL = Util.class.getResource(path);
        if(imageURL != null){
            return new ImageIcon(imageURL, name);
        }else{
            System.err.println("cannot find file "+path);
            return null;
        }
    }
    
    public static Rectangle getGameBound(){
        return new Rectangle(Game.GB_X, Game.GB_Y, Game.GB_WIDTH, Game.GB_HEIGHT);
    }
    
    public static Direction getRandomDirection(){
        Random random = new Random();
        int choice = random.nextInt(4);
        
        switch(choice){
        case 0: return Direction.Up;
        case 1: return Direction.Down;
        case 2: return Direction.Left;
        case 3: return Direction.Right;
        default: return Direction.Up;
        }
    }
    
    /**
     * 
     * get a random position on the game board that is not taken by any other game elements
     * 
     * @param none 
     * @return a position on the game board that is unoccupied 
     */
    public static Position getRandomPosition(Handler handler){
        Random random = new Random();
        int GRID_PER_SIZE = Game.GB_WIDTH/Game.GRID_SIZE;
        List<Position> takenPosition = new ArrayList<Position>();
        
        for(GameElements ge : handler.objects){
            takenPosition.addAll(ge.getPositions());
        }
        
        Position randPosition;
        do{
            randPosition = new Position(random.nextInt(GRID_PER_SIZE), random.nextInt(GRID_PER_SIZE));
        }while(takenPosition.contains(randPosition));
        
        return randPosition;
    }
    
}