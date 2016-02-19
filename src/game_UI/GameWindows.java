package game_UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;

import game_control.LevelController;
import game_control.Menu;
import game_control.Position;

public class GameWindows{
    Map<String, Color> COLOR_CHART = Game.getColorChart();
    Map<String, Font> FONT_CHART = Game.getFontChart();
    
    public void renderBackground(Graphics g){
        // draw game board
        g.setColor(COLOR_CHART.get("GBColor"));
        g.fillRect(Game.GB_X, Game.GB_Y, Game.GB_WIDTH, Game.GB_HEIGHT);

        // draw game board border
        g.setColor(COLOR_CHART.get("GBBorderColor"));
        g.drawRect(Game.GB_X, Game.GB_Y, Game.GB_WIDTH, Game.GB_HEIGHT);

        // draw grid
        g.setColor(Color.gray);
//         for (int a = Game.GB_X; a < (Game.GB_WIDTH + Game.GB_X); a += Game.GRID_SIZE) {
//         for (int b = Game.GB_Y; b < (Game.GB_HEIGHT + Game.GB_Y); b += Game.GRID_SIZE) {
//         g.drawRect(a, b, Game.GRID_SIZE, Game.GRID_SIZE);
//         }
//         }
    }
    
    public void renderIntroWindow(Graphics g, Menu menu){
        g.setColor(COLOR_CHART.get("GBBorderColor"));
        g.setFont(FONT_CHART.get("TitleFont"));
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawString("SnakeGame II", 60, 160);
        g.setFont(FONT_CHART.get("UIFont12B"));
        g.setColor(COLOR_CHART.get("StatementColor"));
        g.drawString("Designed and made by Camellia Peng.  All rights reserved.", 120, 565);
        
        g.setColor(COLOR_CHART.get("PBColor"));
        g.setFont(FONT_CHART.get("UIFont20B"));
        if(menu.getCurrentSelect().equals("New Game")){
            g.setColor(COLOR_CHART.get("SelectColor"));
        }
        else{
            g.setColor(COLOR_CHART.get("PBColor"));
        }
        g.drawString("New Game", 220, 270);
        
        if(menu.getCurrentSelect().equals("Achievements")){
            g.setColor(COLOR_CHART.get("SelectColor"));
        }
        else{
            g.setColor(COLOR_CHART.get("PBColor"));
        }
        g.drawString("Achievements", 208, 310);
        
        if(menu.getCurrentSelect().equals("Quit")){
            g.setColor(COLOR_CHART.get("SelectColor"));
        }
        else{
            g.setColor(COLOR_CHART.get("PBColor"));
        }
        g.drawString("Quit", 250, 350);
        
        if(menu.getCurrentSelect().equals("Reset Game")){
            g.setColor(COLOR_CHART.get("SelectColor"));
        }
        else{
            g.setColor(COLOR_CHART.get("PBColor"));
        }
        g.drawString("Reset Game", 218, 390);
    }
    
    public void renderPauseWindow(Graphics g) {
        // draw pause state graphics
        g.setColor(COLOR_CHART.get("NoticeColor"));
        g.fillRect(Game.GB_X + (Game.GB_WIDTH - 200) / 2, Game.GB_Y + (Game.GB_HEIGHT - 100) / 4, 200, 280);
        g.setColor(COLOR_CHART.get("GBBorderColor"));
        g.drawRect(Game.GB_X + (Game.GB_WIDTH - 200) / 2, Game.GB_Y + (Game.GB_HEIGHT - 100) / 4, 200, 280);
        g.setFont(FONT_CHART.get("UIFont"));
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawString("Press SPACE bar to", 185, 195);
        g.drawString("resume game", 185, 215);
        g.drawString("or select one of the", 185, 245);
        g.drawString("following options:", 185, 265);
        
        g.setColor(COLOR_CHART.get("GBButtonColor"));
        g.fillRect(185, 295, 155, 40);
        g.fillRect(185, 355, 155, 40);
        g.setColor(COLOR_CHART.get("GBBorderColor"));
        g.drawRect(185, 295, 155, 40);
        g.drawRect(185, 355, 155, 40);
        g.setFont(FONT_CHART.get("UIFont20B"));
        g.drawString("Menu", 240, 320);
        g.drawString("Quit", 247, 380);
    }
    
    public void renderOverWindow(Graphics g, LevelController level_controller){
        g.setColor(COLOR_CHART.get("NoticeColor"));
        g.fillRect(Game.GB_X + (Game.GB_WIDTH - 200) / 2, Game.GB_Y + (Game.GB_HEIGHT - 100) / 4, 200, 280);
        g.setColor(COLOR_CHART.get("GBBorderColor"));
        g.drawRect(Game.GB_X + (Game.GB_WIDTH - 200) / 2, Game.GB_Y + (Game.GB_HEIGHT - 100) / 4, 200, 280);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawString("Game over...", 175, 185);
        g.drawString("You lost with a score of: ", 175, 205);
        g.setColor(COLOR_CHART.get("GBBorderColor"));
        g.setFont(FONT_CHART.get("UIFont24"));
        g.drawString(Integer.toString(LevelController.getScore()), 245, 240);

        g.setFont(FONT_CHART.get("UIFont20B"));
        g.setColor(COLOR_CHART.get("GBButtonColor"));
        g.fillRect(185, 270, 155, 40);
        g.setColor(COLOR_CHART.get("GBBorderColor"));
        g.drawRect(185, 270, 155, 40);
        g.drawString("New Game", 220, 295);

        g.setColor(COLOR_CHART.get("GBButtonColor"));
        g.fillRect(185, 325, 155, 40);
        g.setColor(COLOR_CHART.get("GBBorderColor"));
        g.drawRect(185, 325, 155, 40);
        g.drawString("Menu", 240, 350);

        g.setColor(COLOR_CHART.get("GBButtonColor"));
        g.fillRect(185, 380, 155, 40);
        g.setColor(COLOR_CHART.get("GBBorderColor"));
        g.drawRect(185, 380, 155, 40);
        g.drawString("Quit", 247, 405);
    }
    
    public void renderLevelUpWindow(Graphics g, LevelController level_controller){
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        g.setColor(COLOR_CHART.get("GBBorderColor"));
        g.drawRect(90, 220, 350, 100);
        g.setColor(COLOR_CHART.get("NoticeColor"));
        g.fillRect(90, 220, 350, 100);
        g.setColor(COLOR_CHART.get("GBBorderColor"));
        g.setFont(FONT_CHART.get("UIFont20B"));
        g.drawString("Congrats!", 220, 260);
        g.setFont(FONT_CHART.get("UIFont20"));
        g.drawString("You have cleared the level!", 155, 280);
        g.setFont(FONT_CHART.get("UIFont12B"));
        g.drawString("Press SPACE to enter the next level.", 175, 310);
    }
    
    public class AchievementWindow{
        
        List<AchievementIcon> iconList = new ArrayList<AchievementIcon>();
        private int NUM_ROW, NUM_COL;
        
        final int CELL_WIDTH = 60;
        final int CELL_HGAP = 50;
        final int H_MARGIN = 65;
        
        final int CELL_HEIGHT = 60;
        final int CELL_VGAP = 60;
        final int V_MARGIN = 40;
        
        final int PROG_BAR_HEIGHT = 5;
        final int PROG_BAR_WIDTH = CELL_WIDTH;
        final int TOP_MARGIN = 6;
        
        Menu menu;
        
        public AchievementWindow(int num_row, int num_col, Menu menu){
            NUM_ROW = num_row;
            NUM_COL = num_col;
            this.menu = menu;
            
            for(int i = 0; i < NUM_ROW; i++)
                for(int j = 0; j < NUM_COL; j++)
                    addIcon(new Position(H_MARGIN + (CELL_WIDTH + CELL_HGAP)*i,V_MARGIN 
                            + (CELL_HEIGHT + CELL_VGAP)*j), "", null);
        }
        
        public void addIcon(Position position, String name, ImageIcon image){
            iconList.add(new AchievementIcon(position, name, image));
        }
        
        public void tick(){
            for (AchievementIcon ai : iconList){
                ai.tick();
                if(ai.isHoverOver(menu.getXCursor(), menu.getYCursor())){
                    //System.out.println("Hovered over: "+ai);
                }
            }
        }
        
        public void render(Graphics g){
            for (AchievementIcon ai : iconList)
                ai.render(g);
            
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setFont(FONT_CHART.get("UIFont16"));
            g.setColor(new Color(200,200,200));
            g.fillRect(190, 505, 150, 25);
            g.setColor(COLOR_CHART.get("GBBorderColor"));
            g.drawRect(190, 505, 150, 25);
            g.drawString("Back to Main Menu", 200, 523);
        }
    }
}