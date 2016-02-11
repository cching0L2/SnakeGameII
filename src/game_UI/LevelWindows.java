package game_UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import game_elements.*;

public class LevelWindows{
    private static Map<String, Color> COLOR_CHART = Game.getColorChart();
    private static Map<String, Font> FONT_CHART = Game.getFontChart();
    
    public static void renderLevelInstruction(int level, Graphics g){
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        g.setColor(COLOR_CHART.get("NoticeColor"));
        g.fillRect(90, 140, 350, 300);
        g.setColor(COLOR_CHART.get("GBBorderColor"));
        g.drawRect(90, 140, 350, 300);
        g.setFont(FONT_CHART.get("UIFont24"));
        g.drawString("Level "+level, 225, 180);
        g.setFont(FONT_CHART.get("UIFont12B"));
        g.drawString("Eat food to earn points.  When enough points are earned,", 120, 210);
        g.drawString("Go through door to enter the next level.", 120, 230);
        g.setFont(FONT_CHART.get("UIFont12B"));
        g.drawString("New Obstacles: ", 120, 290);
        g.drawImage(Fountain.getImage().getImage(), 210, 320, Fountain.getImage().getIconWidth(), 
                Fountain.getImage().getIconHeight(), null);
        g.drawImage(Bush.getImage().getImage(), 310, 330, Bush.getImage().getIconWidth(), 
                Bush.getImage().getIconHeight(), null);
        g.drawString("Press SPACE to enter the next level.", 175, 425);
        
        g.drawImage(Door.getImage().getImage(), 340, 250, Game.GRID_SIZE, Game.GRID_SIZE, null); 
        
        g.setColor(COLOR_CHART.get("noticeColor"));
        g.drawString("FOUNTAIN", 200, 372);
        g.drawString("BUSH", 307, 372);
        
        g.setColor(Color.gray); 
        for(int i = 0; i<9*Game.GRID_SIZE; i+=Game.GRID_SIZE)
            g.fillRect(270-i, 250, Game.GRID_SIZE, Game.GRID_SIZE);
        
    }
}