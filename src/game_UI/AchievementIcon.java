package game_UI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import game_control.Position;
import game_control.Util;

public class AchievementIcon {
    final int CELL_WIDTH = 60;
    final int CELL_HGAP = 50;
    final int H_MARGIN = 65;

    final int CELL_HEIGHT = 60;
    final int CELL_VGAP = 60;
    final int V_MARGIN = 50;

    final int PROG_BAR_HEIGHT = 5;
    final int PROG_BAR_WIDTH = CELL_WIDTH;
    final int TOP_MARGIN = 6;

    Position position;
    String name;
    //ImageIcon image;
    
    ImageIcon image = Util.createImageIcon("default", "../AchievementIcon/default.gif");

    public AchievementIcon(Position position, String name, ImageIcon image) {
        this.position = position;
        this.name = name;
        //this.image = image;
    }

    public void tick() {
    }

    public void render(Graphics g) {
        g.setColor(Color.black);

        g.drawRect(position.getX(), position.getY(), CELL_WIDTH, CELL_HEIGHT);
        g.drawRect(position.getX(), position.getY() + CELL_HEIGHT + TOP_MARGIN, PROG_BAR_WIDTH, PROG_BAR_HEIGHT);
        g.drawImage(image.getImage(), position.getX(), position.getY(), CELL_WIDTH, CELL_HEIGHT, null);
        
    }
    
    public boolean isHoverOver(int XCursor, int YCursor){
        if(XCursor>=position.getX() && XCursor<=(position.getX()+CELL_WIDTH)){
            if(YCursor>=position.getY() && YCursor<=(position.getY()+CELL_HEIGHT))
                return true;
        }
        return false;
    }
}