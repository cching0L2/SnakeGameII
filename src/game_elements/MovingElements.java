package game_elements;

import game_control.*;

public abstract class MovingElements extends GameElements{
    
    public MovingElements(Category cat){
        this.cat = cat;
    }
    
    public abstract void move(Direction dir);
    
}