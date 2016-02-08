package game_elements;

import java.awt.Graphics;
import java.awt.Rectangle;

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
            return new Rectangle(position.getX()*Game.GRID_SIZE, position.getY()*Game.GRID_SIZE, 
                    Game.GRID_SIZE*LENGTH, Game.GRID_SIZE*HEIGHT);
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
    
}