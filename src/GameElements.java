import java.awt.Graphics;

public abstract class GameElements{
    
    protected Category cat;

    public Category getCategory(){
        return cat;
    }
    
    public abstract void tick();
    
    public abstract void render(Graphics g);
    
}