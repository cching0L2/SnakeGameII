package game_elements;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import game_UI.Game;
import game_control.Position;
import game_control.Util;

public class Fence extends ObstacleElements{

    Position position;
    ImageIcon fenceImageHorizontal = Util.createImageIcon("fence", "../image/fenceh.gif");
    ImageIcon fenceImageVertical = Util.createImageIcon("fence", "../image/fencev.gif");
    
    final int HEIGHT;
    final int LENGTH;
    
    Orientation orientation;
    
    public Fence(Position position, Orientation orientation){
        this.position = position;
        this.orientation = orientation;
        HEIGHT = (orientation==Orientation.Horizontal)? 1 : 12;
        LENGTH = (orientation==Orientation.Horizontal)? 12 : 1;
    }
    
    @Override
    public Rectangle getBound() {
        //a fence object occupies one square on the board 
            return new Rectangle(position.getX(), position.getY(), 
                    LENGTH, HEIGHT);
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        if(orientation==Orientation.Horizontal)
            g.drawImage(fenceImageHorizontal.getImage(), position.getX()*Game.GRID_SIZE+Game.GB_X, position.getY()*Game.GRID_SIZE+Game.GB_Y, 
                    Game.GRID_SIZE*LENGTH, Game.GRID_SIZE*HEIGHT, null);
        else
            g.drawImage(fenceImageVertical.getImage(), position.getX()*Game.GRID_SIZE+Game.GB_X, position.getY()*Game.GRID_SIZE+Game.GB_Y, 
                    Game.GRID_SIZE*LENGTH, Game.GRID_SIZE*HEIGHT, null);
  }

    @Override
    public List<Position> getPositions() {
        List<Position> returnList = new ArrayList<Position>();
            for(int x=0; x<LENGTH; x++){
                for(int y=0; y<HEIGHT; y++)
                    returnList.add(new Position(position.getX()+x, position.getY()+y));
            }
        return returnList;
    }
    
}