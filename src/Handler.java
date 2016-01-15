import java.awt.Graphics;
import java.util.LinkedList;

public class Handler{
    public LinkedList<GameElements> objects = new LinkedList<GameElements>();
    
    public void tick(){
        for(int i=0; i<objects.size(); i++){
            GameElements tempElement = objects.get(i);
            
            if(tempElement instanceof Snake){ //remove dead snakes
                Snake tempSnake = (Snake)tempElement;
                if(tempSnake.getDead())
                   removeObject(tempElement);
                
            }
            tempElement.tick();
        }
    }
    
    public void render(Graphics g){
        if (!objects.isEmpty()) {
            for (int i = 0; i < objects.size(); i++) {
                GameElements tempObject = objects.get(i);
                tempObject.render(g);
            }
        }
    }
    
    public void addObject(GameElements ge){
        objects.add(ge);
    }
    
    public void removeObject(GameElements ge){
        objects.remove(ge);
    }
}