package game_elements;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import game_UI.Game;
import game_control.Position;
import game_control.Util;


public class Door extends GameElements{
    ImageIcon doorImage;
    Position position;
    int HEIGHT = 1, LENGTH = 1;
    
    public Door(Position position, Category cat){
        this.cat = cat;
        this.position = position;
        doorImage = Util.createImageIcon("door image", "../image/door.gif");
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(doorImage.getImage(), position.getX()*Game.GRID_SIZE+Game.GB_X, position.getY()*Game.GRID_SIZE+Game.GB_Y, 
                Game.GRID_SIZE, Game.GRID_SIZE, null);
    }
    
    public Rectangle getBound(){
        return new Rectangle(position.getX(), position.getY(), LENGTH, HEIGHT);
    }

    @Override
    public List<Position> getPositions() {
        List<Position> returnList = new ArrayList<Position>();
        returnList.add(position);
        return returnList;
    }
}