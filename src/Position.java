public class Position{
    private int x;
    private int y;
    
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public Position(Position pos, Direction dir){
        switch(dir){
        case Up: {
            x = pos.getX();
            y = pos.getY()-1;
        }
        case Down: {
            x = pos.getX();
            y = pos.getY()+1;
        }
        case Right: {
            x = pos.getX()+1;
            y = pos.getY();
        }
        case Left: {
            x = pos.getX()-1;
            y = pos.getY();
        }
        default:{
            x = pos.getX();
            y = pos.getY();
        }
        }
        
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public String toString(){
        return "Position("+x+", "+y+")";
    }
}