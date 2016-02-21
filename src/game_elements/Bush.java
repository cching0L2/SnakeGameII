package game_elements;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import game_UI.Game;
import game_control.Position;
import game_control.Util;

public class Bush extends ObstacleElements{
    
    Position position;
    int HEIGHT = 1, LENGTH = 1;
    static ImageIcon bushImage = Util.createImageIcon("bush", "../image/bush.gif");

    public Bush(Position position){
        this.position = position;
    }
    
    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(bushImage.getImage(), position.getX()*Game.GRID_SIZE+Game.GB_X, position.getY()*Game.GRID_SIZE+Game.GB_Y, 
                Game.GRID_SIZE*LENGTH, Game.GRID_SIZE*HEIGHT, null);
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(position.getX(), position.getY(), LENGTH, HEIGHT);
    }

    public String getName(){
        return "bush";
    }
    
    @Override
    public List<Position> getPositions() {
        List<Position> returnList = new ArrayList<Position>();
        for(int x=0; x<LENGTH; x++){
            for(int y=0; y<HEIGHT; y++)
                returnList.add(new Position(position.getX()+x, position.getY()));
        }

    return returnList;
    }

    public static ImageIcon getImage() {
        return bushImage;
    }
    
}