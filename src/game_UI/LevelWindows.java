package game_UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import game_elements.Door;

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
        g.fillRect(90, 180, 350, 200);
        g.setColor(COLOR_CHART.get("GBBorderColor"));
        g.drawRect(90, 180, 350, 200);
        g.setFont(FONT_CHART.get("UIFont24"));
        g.drawString("Level "+level, 225, 220);
        g.setFont(FONT_CHART.get("UIFont12B"));
        g.drawString("Eat food to earn points.  When enough points are earned,", 120, 250);
        g.drawString("Go through door to enter the next level.", 120, 270);
        g.setFont(FONT_CHART.get("UIFont12B"));
        g.drawString("Press SPACE to enter the next level.", 175, 365);
        
        g.drawImage(Door.getImage().getImage(), 340, 300, Game.GRID_SIZE, Game.GRID_SIZE, null); 
        
        g.setColor(Color.gray);
        
        for(int i = 0; i<9*Game.GRID_SIZE; i+=Game.GRID_SIZE)
            g.fillRect(270-i, 300, Game.GRID_SIZE, Game.GRID_SIZE);
        
    }
}