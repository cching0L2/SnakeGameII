package game_UI;

import java.awt.Graphics;
import game_elements.*;
import game_control.*;

public class Animation{
    //animation in introduction
    
    private final int SNAKE_COUNT = 7;
    private final int MAX_SNAKE_COUNT = 10;
    
    Handler handler;
    KeyController keyController;
    
    public Animation(Handler handler, KeyController keyController){
        this.handler = handler;
        this.keyController = keyController;
        
        for(int i = 0; i<SNAKE_COUNT; i++){
            handler.addObject(new IntroSnake(Category.Snake, keyController, handler));
        }
    }
    
    public void tick(){
        while(handler.objects.size()<MAX_SNAKE_COUNT){ //make sure there are always 20 snakes
            handler.addObject(new IntroSnake(Category.Snake, keyController, handler));
        }
        handler.tick();
    }
    
    public void render(Graphics g){
        handler.render(g);
    }
}