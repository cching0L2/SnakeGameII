package game_elements;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import game_UI.Game;
import game_control.Position;
import game_control.Util;

public class Cookie extends Food{
    
    ImageIcon cookieImage;
    Position position;
    int HEIGHT = 1, WIDTH = 1;

    public Cookie(Position position, Category cat) {
        super(position, cat);
        this.position = position;
        cookieImage = Util.createImageIcon("cookie image", "../image/cookie.gif");
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(cookieImage.getImage(), position.getX()*Game.GRID_SIZE+Game.GB_X, 
                position.getY()*Game.GRID_SIZE+Game.GB_Y, Game.GRID_SIZE, Game.GRID_SIZE, null);
    }
    
    public String getName(){
        return "cookie";
    }
    
    public ImageIcon getImage(){
        return cookieImage;
    }
    
    public Rectangle getBound(){
        return new Rectangle(position.getX(), position.getY(), HEIGHT, WIDTH);
    }

    @Override
    public List<Position> getPositions() {
        List<Position> returnList = new ArrayList<Position>();
        returnList.add(position);
        return returnList;
    }
    
}