import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Cookie extends Food{
    
    ImageIcon cookieImage;
    boolean eaten = false;

    public Cookie(Position position, Category cat) {
        super(position, cat);
        cookieImage = Util.createImageIcon("cookie image", "image/cookie.gif");
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(cookieImage.getImage(), position.getX()+Game.GB_X, position.getY()+Game.GB_Y, Game.GRID_SIZE, Game.GRID_SIZE, null);
    }
    
    public ImageIcon getImage(){
        return cookieImage;
    }
    
    public Rectangle getBound(){
        return new Rectangle(position.getX(), position.getY(), Game.GRID_SIZE, Game.GRID_SIZE);
    }
    
    public void setEaten(boolean eaten){
        this.eaten = eaten;
    }
    
    public boolean getEaten(){
        return eaten;
    }
    
}