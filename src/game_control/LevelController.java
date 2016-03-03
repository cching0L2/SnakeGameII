package game_control;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONObject;

import game_UI.Game;
import game_UI.HUD;
import game_elements.Bush;
import game_elements.Category;
import game_elements.Cookie;
import game_elements.Door;
import game_elements.Fence;
import game_elements.FlowerPatch;
import game_elements.Fountain;
import game_elements.GameElements;
import game_elements.Orientation;
import game_elements.Snake;

public class LevelController {
    Handler handler;
    HUD hud;
    Random random;
    KeyController keyController;

    public static int bestScore = 0;
    public static int score = 0;
    public static int level = 0;
    //private int MAX_LEVEL = 10;
    private int[] levelScheme = { 0, 5, 10, 16, 24, 35, 48, 65, 85, 105, Integer.MAX_VALUE };
    
    private static int prevSnakeLength;
    private Door randDoor;

    public LevelController(Handler handler, HUD hud, KeyController keyController) {
        this.handler = handler;
        this.hud = hud;
        this.keyController = keyController;
        random = new Random();
        randDoor = new Door(Util.getRandomPosition(handler), Category.Door);
    }

    public int findLevel(int score) {
        int levelAt = 0;

        while (levelScheme[levelAt] <= score) {
            levelAt++;
        }

        return levelAt - 1;
    }

    public int getScoreToLevelUp(int level) {
        if (level == levelScheme.length - 1)
            return Integer.MAX_VALUE;
        return levelScheme[level + 1] - levelScheme[level];
    }

    public int getLevelMin(int level) {
        return levelScheme[level];
    }

    public boolean isLevelUp(int score) {
        // is level up if score equals a level threshold
        for (int i = 1; i < levelScheme.length; i++) {
            if (levelScheme[i] == score)
                return true;
        }
        return false;
    }
    
    public void initializeGame(Handler handler){
        handler.objects.clear();
        handler.addObject(new Snake(Category.Snake, keyController, handler));
        handler.addObject(new Cookie(Util.getRandomPosition(handler), Category.Food));
        handler.addAllObject(getLevelObstacles(4)); 
        //add obstacles that belong to the first level 
        keyController.resetInitialDirection(Direction.Right);
        LevelController.setLevel(0);
        LevelController.setScore(0);
        LevelController.clearPrevSnakeLength();
    }
    
    private List<GameElements> getLevelObstacles(int level){
        List<GameElements> obstacle = new ArrayList<GameElements>();
        switch(level){
        case 0:{
            for(int x = 18; x<23; x++){
                for(int y = 17; y<22; y++)
                obstacle.add(new Bush(new Position(x, y)));
            }
            obstacle.add(new Fountain(new Position(19,18)));
            break;
        }
        case 1:{
            obstacle.add(new Fence(new Position(8,19), Orientation.Horizontal));
            obstacle.add(new Fence(new Position(21,19), Orientation.Horizontal));
            obstacle.add(new Fence(new Position(20,7), Orientation.Vertical));
            obstacle.add(new Fence(new Position(20,20), Orientation.Vertical));
            obstacle.add(new Bush(new Position(20,19)));
            break;
        }
        case 2:{
            obstacle.add(new Fence(new Position(7,19), Orientation.Horizontal));
            obstacle.add(new Fence(new Position(22,19), Orientation.Horizontal));
            for(int x = 7; x<34; x++){
                obstacle.add(new Bush(new Position(x, 20)));
            }
            obstacle.add(new Fountain(new Position(19,18)));
            break;
        }
        case 3:{
            for(int x = 0; x<5; x++)
                obstacle.add(new Bush(new Position(27+x, 5)));
            obstacle.add(new Fountain(new Position(32, 5)));
            for(int y = 0; y<5; y++)
                obstacle.add(new Bush(new Position(34, 8+y)));
            for(int x = 0; x<5; x++)
                obstacle.add(new Bush(new Position(8+x, 34)));
            obstacle.add(new Fountain(new Position(5, 32)));
            for(int y = 0; y<5; y++)
                obstacle.add(new Bush(new Position(5, 27+y)));
            break;
        }
        case 4:{
            obstacle.add(new Fence(new Position(11,13), Orientation.Horizontal));
            obstacle.add(new Fence(new Position(11,13), Orientation.Vertical));
            obstacle.add(new Fence(new Position(16,24), Orientation.Horizontal));
            obstacle.add(new Fence(new Position(27,13), Orientation.Vertical));
            
            for(int x = 17; x<22; x++)
                for(int y = 18; y<21; y++)
                    obstacle.add(new Bush(new Position(x, y)));
            for(int x = 18; x<=20; x++)
                obstacle.add(new FlowerPatch(new Position(x, 19)));
            
            obstacle.add(new Fountain(new Position(8, 17)));
            obstacle.add(new Fountain(new Position(28, 17)));
            
            for(int y = 17; y<20; y++)
                obstacle.add(new Bush(new Position(31, y)));
            for(int y = 17; y<20; y++)
                obstacle.add(new Bush(new Position(7, y)));
            for(int x = 7; x<=10; x++)
                obstacle.add(new Bush(new Position(x,16)));
            for(int x = 28; x<=31; x++)
                obstacle.add(new Bush(new Position(x,16)));
            for(int x = 7; x<=10; x++)
                obstacle.add(new Bush(new Position(x,20)));
            for(int x = 28; x<=31; x++)
                obstacle.add(new Bush(new Position(x,20)));
        }
        default: break;
        }
        
        return obstacle;
    }
    
    private void removeObstacles(Handler handler){
        List<GameElements> toRemove = new ArrayList<GameElements>();
        for (int i = 0; i<handler.objects.size(); i++){
            GameElements tempElement = handler.objects.get(i);
            if(tempElement.getCategory()==Category.Obstacle)
                toRemove.add(tempElement);
        }
        handler.objects.removeAll(toRemove);
    }

    public void tick() {
        int nextLevelScore = getLevelMin(level + 1); //score required to get to the next level 
        Snake tempSnake = null;
        
        for (int i = 0; i < handler.objects.size(); i++) {
            GameElements tempElement = handler.objects.get(i);
            if (tempElement.getCategory() == Category.Snake) {
                tempSnake = (Snake) tempElement; //get snake in the game
                if (prevSnakeLength == 0)
                    prevSnakeLength = tempSnake.getInitialSize(); //get length of snake
            }
        }

        //control the appearance and disappearance of doors, level, and score
        if (score == nextLevelScore && Collision.snakeOpenDoor(tempSnake, randDoor)) { // one score until next level and snake head touches door
                //only increment level after door has been opened
                level++;
                Game.gameState = State.LevelUp;
                tempSnake.repositionSnake();
                keyController.resetInitialDirection(Direction.Right);
                prevSnakeLength = tempSnake.getSize();
                handler.removeObject(randDoor); //remove door from the game board 
                removeObstacles(handler); //remove all obstacles in game 
                handler.addAllObject(getLevelObstacles(level)); //add obstacles that belong to the new level 
                handler.addObject(new Cookie(Util.getRandomPosition(handler), Category.Food));
                randDoor = new Door(Util.getRandomPosition(handler), Category.Door);
                //create a new random door with different location
        } 
        else {
            if (tempSnake.getSize() > prevSnakeLength){
                score++;
                if(score == nextLevelScore)
                    handler.addObject(randDoor);
                else
                    handler.addObject(new Cookie(Util.getRandomPosition(handler), Category.Food));
                
                prevSnakeLength = tempSnake.getSize(); //update prevSnakeLength 
            }
        }
        
        if(score>bestScore) bestScore = score;
    }
    
    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        LevelController.score = score;
    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        LevelController.level = level;
    }
    
    public static void clearPrevSnakeLength(){
        prevSnakeLength = 0;
    }
    
    @SuppressWarnings("unchecked")
    public static void writeToFile(Writer writer){
        JSONObject obj = new JSONObject();
        try {
            obj.put("best_score", new Integer(bestScore));
            writer.write(obj.toString()+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
