package game_UI;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;

import javax.swing.ImageIcon;

import game_control.Position;
import game_control.Util;

public class AchievementIcon {
    final int CELL_WIDTH = 60;
    final int CELL_HEIGHT = 60;
    final int PROG_BAR_HEIGHT = 5;
    final int PROG_BAR_WIDTH = CELL_WIDTH;
    final int TOP_MARGIN = 6;

    ImageIcon DEFAULT_IMAGE = Util.createImageIcon("default", "../AchievementIcon/default.gif"); // default

    Position position;
    String name;
    ImageIcon image;
    String condition;
    String description;

    Random random = new Random();

    double progress = 0.0; 

    public AchievementIcon(Position position, String name, ImageIcon image, String condition, String description) {
        this.position = position;
        this.name = name;
        this.image = (image == null) ? DEFAULT_IMAGE : image;
        this.condition = condition;
        this.description = description;
    }

    public void tick() {
    }

    public void render(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(position.getX(), position.getY() + CELL_HEIGHT + TOP_MARGIN, (int) (PROG_BAR_WIDTH * progress),
                PROG_BAR_HEIGHT);

        g.setColor(Color.black);

        g.drawRect(position.getX(), position.getY(), CELL_WIDTH, CELL_HEIGHT);
        g.drawRect(position.getX(), position.getY() + CELL_HEIGHT + TOP_MARGIN, PROG_BAR_WIDTH, PROG_BAR_HEIGHT);

        if (progress != 1.0) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(makeTransparent(0.4f));
            g.drawImage(image.getImage(), position.getX(), position.getY(), CELL_WIDTH, CELL_HEIGHT, null);
            g2d.setComposite(makeTransparent(1));
        } else {
            g.drawImage(image.getImage(), position.getX(), position.getY(), CELL_WIDTH, CELL_HEIGHT, null);
        }
    }

    public void renderDetailInfo(Graphics g) {
        // provide more detailed information regarding this achievement
        g.drawImage(image.getImage(), 320, 50, CELL_WIDTH, CELL_HEIGHT, null);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.setColor(Color.BLUE);
        g.setFont(Game.FONT_CHART.get("UIFont20B"));
        if (progress != 1.0)
            g.drawString("LOCKED", 390, 85);
        else
            g.drawString("UNLOCKED", 390, 85);

        g.setColor(Game.COLOR_CHART.get("GBBorderColor"));
        g.setFont(Game.FONT_CHART.get("UIFont20B"));
        g.drawString(name, 320, 140);

        g.setColor(Color.black);
        g.drawRect(320, 50, CELL_WIDTH, CELL_HEIGHT);
        g.drawLine(320, 147, 500, 147);
        g.drawLine(320, 150, 500, 150);

        g.setFont(Game.FONT_CHART.get("UIFont16"));
        this.drawStringInManyLines(g, condition, 320, 170, 25);

        g.setFont(Game.FONT_CHART.get("UIFont12B"));
        this.drawStringInManyLines(g, description, 320, 225, 32);
    }

    public boolean isHoverOver(int XCursor, int YCursor) {
        if (XCursor >= position.getX() && XCursor <= (position.getX() + CELL_WIDTH)) {
            if (YCursor >= position.getY() && YCursor <= (position.getY() + CELL_HEIGHT))
                return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }
    
    public double getProgress(){
        return progress;
    }
    
    public void setProgress(double progress){
        this.progress = progress;
    }

    private void drawStringInManyLines(Graphics g, String text, int x, int y, int maxChar) {
        int yDelta = 15;
        String[] words;
        words = text.split(" ");

        int charPrinted = 0, numLine = 0;
        String line = "";

        for (String word : words) {
            charPrinted+=word.length()+1;
            if(charPrinted>=maxChar){
                g.drawString(line, x, y+yDelta*numLine);
                numLine++;
                line = "";
                charPrinted = 0;
            }
            line+=word+" ";
        }
        g.drawString(line, x, y+yDelta*numLine);
    }

    private AlphaComposite makeTransparent(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return AlphaComposite.getInstance(type, alpha);
    }
}