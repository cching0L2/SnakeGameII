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
            y = pos.getY()-Game.GRID_SIZE;
            break;
        }
        case Down: {
            x = pos.getX();
            y = pos.getY()+Game.GRID_SIZE;
            break;
        }
        case Right: {
            x = pos.getX()+Game.GRID_SIZE;
            y = pos.getY();
            break;
        }
        case Left: {
            x = pos.getX()-Game.GRID_SIZE;
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
    
    public String toString(){
        return "Position("+Util.pixToGrid(x)+", "+Util.pixToGrid(y)+")";
    }
}