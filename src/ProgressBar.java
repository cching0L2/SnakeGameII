import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class ProgressBar {
    Snake snake;
    HUD hud;

    private double percentage;
    private int difference;
    private boolean levelUp = false;
    private float alpha = 1;
    private float life = 0.1f;

    public ProgressBar(Snake snake, HUD hud) {
        this.snake = snake;
        this.hud = hud;
    }

    public int findLevel(int score) {
        // level scheme: 3, 3, 5, 5, 5, 5, 8, 8...
        // get the level corresponding to score
        if (score >= 0 && score < 3) // between 0 and 10
            return 1;
        else if (score >= 3 && score < 6)
            return 2;
        else if (score >= 6 && score < 11)
            return 3;
        else if (score >= 11 && score < 16)
            return 4;
        else if (score >= 16 && score < 21)
            return 5;
        else if (score >= 21 && score < 26)
            return 6;
        else if (score >= 26 && score < 34)
            return 7;
        else if (score >= 34 && score < 42)
            return 8;
        else
            return -1;
    }

    public int getScoreToLevelUp(int level) {
        switch (level) {
        case 1:
            return 3;
        case 2:
            return 5;
        case 3:
            return 5;
        case 4:
            return 5;
        case 5:
            return 5;
        case 6:
            return 8;
        case 7:
            return 8;
        default:
            return Integer.MAX_VALUE;
        }
    }

    public int getLevelMin(int level) {
        switch (level) {
        case 1:
            return 0;
        case 2:
            return 3;
        case 3:
            return 6;
        case 4:
            return 11;
        case 5:
            return 16;
        case 6:
            return 21;
        case 7:
            return 26;
        case 8:
            return 34;
        default:
            return Integer.MAX_VALUE;
        }
    }

    public boolean isLevelUp(int score) {
        if (score == 3 || score == 6 || score == 11 || score == 16 || score == 21 || score == 26 || score == 34
                || score == 42)
            return true;
        else
            return false;
    }

    public void tick() {
        percentage = (hud.getScore() - getLevelMin(hud.getLevel())) / (double) getScoreToLevelUp(hud.getLevel());
        difference = getLevelMin(hud.getLevel() + 1) - hud.getScore();
        levelUp = isLevelUp(hud.getScore());
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