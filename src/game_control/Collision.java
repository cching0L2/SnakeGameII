package game_control;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import game_UI.Game;
import game_elements.Category;
import game_elements.Cookie;
import game_elements.Door;
import game_elements.Food;
import game_elements.GameElements;
import game_elements.ObstacleElements;
import game_elements.Snake;

public class Collision {

    static Random random = new Random();

    public static boolean snakeEatFood(Snake snake, Handler handler) {
        for (int i = 0; i < handler.objects.size(); i++) {
            GameElements tempObject = handler.objects.get(i);
            if (tempObject.getCategory() == Category.Food) {
                Food food = (Food) tempObject;
                if (food.getBound().intersects(snake.getHeadBound())) {
                    handler.removeObject(tempObject);
                    handler.addObject(new Cookie(new Position(random.nextInt(Game.GB_WIDTH / Game.GRID_SIZE),
                            random.nextInt(Game.GB_HEIGHT / Game.GRID_SIZE)), Category.Food));
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean snakeOpenDoor(Snake snake, Door door) {
        if (door.getBound().intersects(snake.getHeadBound()))
            return true;
        return false;
    }

    public static boolean snakeHitSelf(Snake snake) {
        ArrayList<Rectangle> body = snake.getBodyBound();
        Rectangle head = snake.getHeadBound();
        if (body.isEmpty())
            return false;
        for (int i = 0; i < body.size(); i++) {
            if (body.get(i).intersects(head)) {
                return true;
            }
        }
        return false;
    }

    public static boolean snakeHitWall(Snake snake) {
        Rectangle head = snake.getHeadBound();
        if ((int) head.getX() >= 0 && (int) head.getX() < Game.GB_WIDTH / Game.GRID_SIZE) {
            if ((int) head.getY() >= 0 && (int) head.getY() < Game.GB_HEIGHT / Game.GRID_SIZE)
                return false;
            return true;
        }
        return true;
    }

    public static boolean snakeHitObstacle(Snake snake, Handler handler) {
        Rectangle head = snake.getHeadBound();

        for (Object o : handler.objects) {
            if (o instanceof ObstacleElements) {
                ObstacleElements tempObstacle = (ObstacleElements) o;
                if (tempObstacle.getBound().intersects(head)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean introSnakeHitWall(Snake snake) {
        Rectangle head = snake.getHeadBound();
        if ((int) head.getX() >= -120 && (int) head.getX() < Game.WIDTH + 120) {
            if ((int) head.getY() >= -120 && (int) head.getY() < Game.HEIGHT + 120)
                return false;
            return true;
        }
        return true;
    }
}