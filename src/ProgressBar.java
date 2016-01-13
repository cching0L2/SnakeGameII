import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class ProgressBar {
    HUD hud;
    LevelController levelController;

    private double percentage;
    private int difference;
    private boolean levelUp = false;
    private float alpha = 1;
    private float life = 0.1f;

    public ProgressBar(HUD hud, LevelController levelController) {
        this.hud = hud;
        this.levelController = levelController;
    }

    public void tick() {
        percentage = (LevelController.getScore() - levelController.getLevelMin(LevelController.getLevel())) / 
                (double) levelController.getScoreToLevelUp(LevelController.getLevel());
        difference = levelController.getLevelMin(LevelController.getLevel() + 1) - LevelController.getScore();
        levelUp = levelController.isLevelUp(LevelController.getScore());
    }

    public void render(Graphics g) {
        g.setColor(Game.NoticeColor);
        g.fillRect(Game.GB_X + 165, 10, 315, 40);
        g.setColor(Game.GBBorderColor);
        g.drawRect(Game.GB_X + 165, 10, 315, 40);
        g.setFont(Game.UIFont12B);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // grammar issue
        if (difference == 1)
            g.drawString(difference + " point until the next level", Game.GB_X + 250, 24);
        else
            g.drawString(difference + " points until the next level", Game.GB_X + 250, 24);

        g.setColor(Game.PBColor);
        g.drawRect(Game.GB_X + 180, 30, 283, 12);
        g.fillRect(Game.GB_X + 180, 30, (int) (283 * percentage), 12);

        if (levelUp) {
            g2d.setComposite(makeTransparent(alpha));
            g.setColor(Game.GBBorderColor);
            g.setFont(Game.UIFont40);
            g.drawString("Level Up!", 190, 280);
            g2d.setComposite(makeTransparent(1));
            if (alpha >= life)
                alpha -= (life - 0.01);
        } else {
            alpha = 1;
        }
    }

    private AlphaComposite makeTransparent(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return AlphaComposite.getInstance(type, alpha);
    }
}