package game_control;

import java.awt.Rectangle;
import java.util.ArrayList;

import game_elements.*;
import game_UI.*;

public class Collision{
    
    public static boolean snakeEatFood(Snake snake, Food food){
        if(food.getCategory()==Category.Food){
            if(food.getBound().intersects(snake.getHeadBound())){
                return true;
            }
            else return false;
        }
        return false;
    }
    
    public static boolean snakeHitSelf(Snake snake){
        ArrayList<Rectangle> body = snake.getBodyBound();
        Rectangle head = snake.getHeadBound();
        if(body.isEmpty()) return false;
        for(int i = 0; i<body.size(); i++){
            if(body.get(i).intersects(head)){
                return true;
            }
        }
        return false;
    }
    
    public static boolean snakeHitWall(Snake snake){
        Rectangle head = snake.getHeadBound();
        if((int)head.getX()>=0 && (int)head.getX()<Game.GB_WIDTH){
            if((int)head.getY()>=0 && (int)head.getY()<Game.GB_HEIGHT)
                return false;
            return true;
        }
        return true;
    }
    
    public static boolean introSnakeHitWall(Snake snake){
        Rectangle head = snake.getHeadBound();
        if((int)head.getX()>=-120 && (int)head.getX()<Game.WIDTH+120){
            if((int)head.getY()>=-120 && (int)head.getY()<Game.HEIGHT+120)
                return false;
            return true;
        }
        return true;
    }
}