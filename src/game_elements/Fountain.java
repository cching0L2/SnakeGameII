package game_elements;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import game_UI.Game;
import game_control.Position;
import game_control.Util;

public class Fountain extends ObstacleElements{

    Position position;
    int HEIGHT = 3, LENGTH = 3;
    ImageIcon fountainImage = Util.createImageIcon("fountain", "../image/fountain.gif");
    
    public Fountain(Position position){
        this.position = position;
    }
    
    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(fountainImage.getImage(), position.getX()*Game.GRID_SIZE+Game.GB_X, position.getY()*Game.GRID_SIZE+Game.GB_Y, 
                Game.GRID_SIZE*LENGTH, Game.GRID_SIZE*HEIGHT, null);
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(position.getX()*Game.GRID_SIZE, position.getY()*Game.GRID_SIZE, 
                LENGTH*Game.GRID_SIZE, HEIGHT*Game.GRID_SIZE);
    }
    
}