package game_UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import game_elements.*;

public class LevelWindows {
    private static Map<String, Color> COLOR_CHART = Game.getColorChart();
    private static Map<String, Font> FONT_CHART = Game.getFontChart();

    public static void renderLevelInstruction(int level, Graphics g) {
        switch (level) {
        case 1:
        case 2:
        case 3: {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            g.setColor(COLOR_CHART.get("NoticeColor"));
            g.fillRect(90, 150, 350, 160);
            g.setColor(COLOR_CHART.get("GBBorderColor"));
            g.drawRect(90, 150, 350, 160);
            g.setFont(FONT_CHART.get("UIFont24"));
            g.drawString("Level " + level, 225, 180);
            g.setFont(FONT_CHART.get("UIFont16"));
            g.drawString("Eat food to earn points.  When enough points", 100, 210);
            g.drawString("are earned, go through door to enter the next level.", 100, 230);
            g.setFont(FONT_CHART.get("UIFont12B"));
            g.drawString("press SPACE to continue", 200, 300);
            g.drawImage(Door.getImage().getImage(), 340, 255, Game.GRID_SIZE, Game.GRID_SIZE, null);

            g.setColor(Color.gray);
            for (int i = 0; i < 9 * Game.GRID_SIZE; i += Game.GRID_SIZE)
                g.fillRect(270 - i, 255, Game.GRID_SIZE, Game.GRID_SIZE);
            break;
        }
        }
    }
}