package game_control;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import game_UI.IntroSnake;
import game_elements.GameElements;

public class Handler{
    public LinkedList<GameElements> objects = new LinkedList<GameElements>();
    
    public void tick(){
        for(int i=0; i<objects.size(); i++){
            GameElements tempElement = objects.get(i);
            
            if(tempElement instanceof IntroSnake){ //remove dead snakes
                IntroSnake tempSnake = (IntroSnake)tempElement;
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
    
    public void addAllObject(List<GameElements> list_objects){
        for(GameElements ge:list_objects){
            objects.add(ge);
        }
    }
    
    public void removeObject(GameElements ge){
        objects.remove(ge);
    }
    
    public void removeAllObject(List<GameElements> list_objects){
        for(GameElements ge:list_objects){
            objects.remove(ge);
        }
    }
}