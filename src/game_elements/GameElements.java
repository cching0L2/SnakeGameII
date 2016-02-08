package game_elements;

import java.awt.Graphics;
import java.util.List;

import game_control.Position;

public abstract class GameElements{
    
    protected Category cat;

    public Category getCategory(){
        return cat;
    }
    
    public abstract void tick();
    
    public abstract void render(Graphics g);
    
    public abstract List<Position> getPositions();
    
}