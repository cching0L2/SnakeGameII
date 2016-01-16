package game_elements;

import java.awt.Rectangle;
import game_control.*;

public abstract class Food extends GameElements{
    
    protected Position position;
    protected boolean eaten;
    
    public Food(Position position, Category cat){
        this.cat = cat;
        this.position = position;
    }
    
    protected Position getPosition(){
        return position;
    }
    
    protected void setPosition(Position position){
        this.position = position;
    }
    
    public abstract Rectangle getBound();
    
    public void setEaten(boolean eaten){
        this.eaten = eaten;
    }
    
    public boolean getEaten(){
        return eaten;
    }
}