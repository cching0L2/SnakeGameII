package game_UI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import game_control.LevelController;

public class HUD{
    
    public void render(Graphics g){
        g.setColor(Game.NoticeColor);
        g.fillRect(Game.GB_X, 10, 150, 40);
        g.setColor(Game.GBBorderColor);
        g.drawRect(Game.GB_X, 10, 150, 40);
        g.setFont(Game.UIFont);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawString("Score: " + LevelController.getScore(), Game.GB_X + 15, 27);
        g.drawString("Level: " + LevelController.getLevel(), Game.GB_X + 15, 44);
    }
}