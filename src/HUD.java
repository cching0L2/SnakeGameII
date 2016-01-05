import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class HUD{
    public static int score = 0;
    public static int level = 0;
    
    public int getScore(){
        return score;
    }
    
    public void setScore(int score){
        HUD.score = score;
    }
    
    public int getLevel(){
        return level;
    }
    
    public void setLevel(int level){
        HUD.level = level;
    }
    
    public void render(Graphics g){
        g.setColor(Game.NoticeColor);
        g.fillRect(Game.GB_X, 10, 150, 40);
        g.setColor(Game.GBBorderColor);
        g.drawRect(Game.GB_X, 10, 150, 40);
        g.setFont(Game.UIFont);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawString("Score: " + getScore(), Game.GB_X + 15, 27);
        g.drawString("Level: " + getLevel(), Game.GB_X + 15, 44);
    }
}