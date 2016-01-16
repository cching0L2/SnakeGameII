package game_control;

import java.util.Random;
import game_UI.*;
import game_elements.*;

public class LevelController {
    Handler handler;
    HUD hud;
    Random random;
    
    public static int score = 0;
    public static int level = 0;
    
    private int[] levelScheme = {0, 3, 6, 9, 11, 16, 21, 26, 34, 42, Integer.MAX_VALUE};
    

    public LevelController(Handler handler, HUD hud) {
        this.handler = handler;
        this.hud = hud;
        random = new Random();
    }
    
    public static int getScore(){
        return score;
    }
    
    public static void setScore(int score){
        LevelController.score = score;
    }
    
    public static int getLevel(){
        return level;
    }
    
    public static void setLevel(int level){
        LevelController.level = level;
    }

    public int findLevel(int score) {
        int levelAt = 0;
        
        while(levelScheme[levelAt]<=score){
            levelAt++;
        }
        
        return levelAt;
    }

    public int getScoreToLevelUp(int level) {
        return levelScheme[level]-levelScheme[level-1];
    }

    public int getLevelMin(int level) {
        return levelScheme[level-1];
    }

    public boolean isLevelUp(int score) {
        //is level up if score equals a level threshold
        for(int i = 1; i<levelScheme.length; i++){
            if (levelScheme[i] == score) return true;
        }
        return false;
    }

    public void tick() {
        //determines score + level by finding the snake object in handler and evaluating its size
        for (int i = 0; i < handler.objects.size(); i++) {
            GameElements tempElement = handler.objects.get(i);
            if (tempElement.getCategory() == Category.Snake) {
                Snake tempSnake = (Snake) tempElement;
                score = tempSnake.getSize() - tempSnake.getInitialSize();
                level = findLevel(score);
            }
        }
    }
}