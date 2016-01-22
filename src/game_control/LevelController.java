package game_control;

import java.util.Random;
import game_UI.*;
import game_elements.*;

public class LevelController {
    Handler handler;
    HUD hud;
    Random random;
    KeyController keyController;

    public static int score = 0;
    public static int level = 0;
    private int[] levelScheme = { 0, 5, 10, 16, 24, 35, 48, 65, 85, 105, Integer.MAX_VALUE };
    
    private static int prevSnakeLength;
    private Door randDoor;

    public LevelController(Handler handler, HUD hud, KeyController keyController) {
        this.handler = handler;
        this.hud = hud;
        this.keyController = keyController;
        random = new Random();
        randDoor = new Door(new Position(random.nextInt(Game.NUM_GRID_PER_SIDE)*Game.GRID_SIZE, 
                random.nextInt(Game.NUM_GRID_PER_SIDE)*Game.GRID_SIZE), Category.Door);
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
        handler.addObject(new Cookie(new Position(random.nextInt(Util.pixToGrid(Game.GB_WIDTH))*Game.GRID_SIZE
                , random.nextInt(Util.pixToGrid(Game.GB_HEIGHT))*Game.GRID_SIZE), Category.Food));
        keyController.resetInitialDirection(Direction.Right);
        LevelController.setLevel(0);
        LevelController.setScore(0);
        LevelController.clearPrevSnakeLength();
    }

    public void tick() {
        int nextLevelScore = getLevelMin(level + 1);
        Snake tempSnake = null;

        for (int i = 0; i < handler.objects.size(); i++) {
            GameElements tempElement = handler.objects.get(i);
            if (tempElement.getCategory() == Category.Snake) {
                tempSnake = (Snake) tempElement;
                if (prevSnakeLength == 0)
                    prevSnakeLength = tempSnake.getInitialSize();
            }
        }

        //control the appearance and disappearance of doors, level, and score
        if (score == nextLevelScore - 1) { // one score until next level
            //handler.addObject(randDoor);
            if (Collision.snakeOpenDoor(tempSnake, randDoor)) { //if snake's head touches door 
                score++; //increment score and level 
                level++;
                prevSnakeLength = tempSnake.getSize();
                handler.removeObject(randDoor); //remove door from the game board 
                
                randDoor = new Door(new Position(random.nextInt(Game.NUM_GRID_PER_SIDE)*Game.GRID_SIZE, 
                        random.nextInt(Game.NUM_GRID_PER_SIDE)*Game.GRID_SIZE), Category.Door);
                //create a new random door with different location
             

            }
        } else {
            if (tempSnake.getSize() > prevSnakeLength){
                score++;
                if(score == nextLevelScore -1)
                    handler.addObject(randDoor);
                prevSnakeLength = tempSnake.getSize();
            }
        }
     
        if(level == 0){
            handler.addObject(new Fence(new Position(9,10), Orientation.Vertical));
            handler.addObject(new Fence(new Position(21,10), Orientation.Horizontal));
            handler.addObject(new Fence(new Position(9,20), Orientation.Vertical));
            handler.addObject(new Fence(new Position(21,30), Orientation.Horizontal));
        }
    }
}
