import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Util {
    
    public static int clamp(int val, int min, int max) {
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
}