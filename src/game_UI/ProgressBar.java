package game_UI;

import java.awt.Graphics;

import game_control.LevelController;

public class ProgressBar {
    HUD hud;
    LevelController levelController;

    private double percentage;
    private int difference;

    public ProgressBar(HUD hud, LevelController levelController) {
        this.hud = hud;
        this.levelController = levelController;
    }

    public void tick() {
        percentage = (LevelController.getScore() - levelController.getLevelMin(LevelController.getLevel())) / 
                (double) levelController.getScoreToLevelUp(LevelController.getLevel());
        difference = levelController.getLevelMin(LevelController.getLevel() + 1) - LevelController.getScore();
    }

    public void render(Graphics g) {
        g.setColor(Game.COLOR_CHART.get("NoticeColor"));
        g.fillRect(Game.GB_X + 165, 10, 315, 40);
        g.setColor(Game.COLOR_CHART.get("GBBorderColor"));
        g.drawRect(Game.GB_X + 165, 10, 315, 40);
        g.setFont(Game.FONT_CHART.get("UIFont12B"));

        // grammar issue
        if (difference == 1)
            g.drawString(difference + " point until the next level", Game.GB_X + 250, 24);
        else
            g.drawString(difference + " points until the next level", Game.GB_X + 250, 24);

        g.setColor(Game.COLOR_CHART.get("PBColor"));
        g.drawRect(Game.GB_X + 180, 30, 283, 12);
        g.fillRect(Game.GB_X + 180, 30, (int) (283 * percentage), 12);
    }
}