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
    private int[] levelScheme = { 0, 3, 6, 9, 11, 16, 21, 26, 34, 42, Integer.MAX_VALUE };
    
    private static int prevSnakeLength;
    private Door randDoor;

    public LevelController(Handler handler, HUD hud) {
        this.handler = handler;
        this.hud = hud;
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

        if (score == nextLevelScore - 1) { // one score until next level
            //handler.addObject(randDoor);
            if (Collision.snakeOpenDoor(tempSnake, randDoor)) {
                score++;
                level++;
                prevSnakeLength = tempSnake.getSize();
                handler.removeObject(randDoor);
                
                randDoor = new Door(new Position(random.nextInt(Game.NUM_GRID_PER_SIDE)*Game.GRID_SIZE, 
                        random.nextInt(Game.NUM_GRID_PER_SIDE)*Game.GRID_SIZE), Category.Door);

            }
        } else {
            if (tempSnake.getSize() > prevSnakeLength){
                score++;
                if(score == nextLevelScore -1)
                    handler.addObject(randDoor);
                prevSnakeLength = tempSnake.getSize();
            }
        }
    }
}
