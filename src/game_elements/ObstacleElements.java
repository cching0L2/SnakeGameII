package game_elements;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract  class ObstacleElements extends GameElements{

    @Override 
    public Category getCategory(){
        return Category.Obstacle;
    }
    
    @Override
    public abstract void tick();

    @Override
    public abstract void render(Graphics g);
    
    public abstract Rectangle getBound();
}