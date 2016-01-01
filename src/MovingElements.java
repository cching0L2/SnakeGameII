public abstract class MovingElements extends GameElements{
    
    public MovingElements(Category cat){
        this.cat = cat;
    }
    
    public abstract void move(Position pos, Direction dir);
    
}