public abstract class StillElements extends GameElements{
    
    protected Position position;
    
    public StillElements(Position position, Category cat){
        this.cat = cat;
        this.position = position;
    }
    
    protected Position getPosition(){
        return position;
    }
    
    protected void setPosition(Position position){
        this.position = position;
    }
}