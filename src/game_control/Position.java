package game_control;

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
            break;
        }
        case Down: {
            x = pos.getX();
            y = pos.getY()+1;
            break;
        }
        case Right: {
            x = pos.getX()+1;
            y = pos.getY();
            break;
        }
        case Left: {
            x = pos.getX()-1;
            y = pos.getY();
            break;
        }
        default:{
            x = pos.getX();
            y = pos.getY();
            break;
        }
        }
        
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public boolean equals(Object o){
        if(!(o instanceof Position))
            return false;
        Position temp = (Position)o;
        if(temp.getX()==x && temp.getY()==y) return true;
        else return false;
    }
    
    @Override 
    public int hashCode(){
        return x*y;
    }
    
    public String toString(){
        return "Position("+x+", "+y+")";
    }
}