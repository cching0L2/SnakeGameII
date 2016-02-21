package game_UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;

import game_control.Handler;
import game_control.LevelController;
import game_control.Menu;
import game_control.Position;
import game_control.Util;
import game_elements.Category;
import game_elements.Cookie;
import game_elements.SmartSnake;

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
        
//        g.drawRect(220, 261, 93, 15);
//        g.drawRect(195, 296, 150, 15);
//        g.drawRect(208, 331, 122, 15);
//        g.drawRect(250, 366, 36, 15);
//        g.drawRect(218, 401, 105, 15);
        
        g.drawString("New Game", 220, 275);
        g.drawString("Game Instruction", 195, 310);
        g.drawString("Achievements", 208, 345);
        g.drawString("Quit", 250, 380);
        g.drawString("Reset Game", 218, 415);
        
        if(!menu.getCurrentSelect().equals("")){
            g.setColor(COLOR_CHART.get("SelectColor"));
            switch(menu.getCurrentSelect()){
            case "New Game":  g.drawString("New Game", 220, 275); break;
            case "Game Instruction": g.drawString("Game Instruction", 195, 310); break;
            case "Achievements": g.drawString("Achievements", 208, 345); break;
            case "Quit": g.drawString("Quit", 250, 380);break;
            case "Reset Game": g.drawString("Reset Game", 218, 415); break;
            }
        }
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
    
    public static class GameInstruction{
        private GameInstruction(){}; //cannot be instantiated
        private static Handler handler;
        private static int WIDTH = Game.WIDTH - 48, HEIGHT = 200;
        
        public static void tick(){
            if(handler==null){
                handler = new Handler();
                handler.addObject(new SmartSnake(Category.Snake, null, handler));
            }
            handler.tick();
        }
        
        public static void render(Graphics g){
            g.setColor(Game.COLOR_CHART.get("GBColor"));
            g.fillRect(24, 24, WIDTH, HEIGHT);
            g.setColor(Color.black);
            g.drawRect(24, 24, WIDTH, HEIGHT);
            handler.render(g);
            
            g.setColor(new Color(200,200,200));
            g.fillRect(213, 522, 110, 25);
            g.setColor(Game.COLOR_CHART.get("GBBorderColor"));
            g.drawRect(213, 522, 110, 25);
            g.setFont(Game.FONT_CHART.get("UIFont20B"));
            g.drawString("Main Menu", 220, 540);
        }
        
        public static Rectangle getBound(){
            return new Rectangle(24, 24, WIDTH, HEIGHT);
        }
    }
    
    public class AchievementWindow{
        
        public List<AchievementIcon> iconList = new ArrayList<AchievementIcon>();
        private int NUM_ROW, NUM_COL;
        
        final int CELL_WIDTH = 60;
        final int CELL_HGAP = 40;
        final int H_MARGIN = 20;
        
        final int CELL_HEIGHT = 60;
        final int CELL_VGAP = 30;
        final int V_MARGIN = 30;
        
        final int PROG_BAR_HEIGHT = 5;
        final int PROG_BAR_WIDTH = CELL_WIDTH;
        final int TOP_MARGIN = 40;
        
        Menu menu;
        
        public AchievementWindow(int num_row, int num_col, Menu menu){
            NUM_ROW = num_row;
            NUM_COL = num_col;
            this.menu = menu;
            String[] name_list = {"First Step","Dead on the Spot","Cookie Lover","Cookie Collector","So-So Snake",
                    "Pretty Good","Plant Destroyer","Water Contaminant","Fence Finder","Ambitious Dreamer","Donut Taster",
                    "Donut Dealer","Master Snake","Cookie Lunatic","Living Legend"};
            ImageIcon[] image_list = {Util.createImageIcon("first_step", "../AchievementIcon/first-step.gif"), 
                    Util.createImageIcon("dead_on_the_spot", "../AchievementIcon/dead-spot.gif"), 
                    Util.createImageIcon("cookie_lover", "../AchievementIcon/10-cookie.gif"), 
                    Util.createImageIcon("cookie_collector", "../AchievementIcon/25-cookie.gif"), 
                    Util.createImageIcon("so-so", "../AchievementIcon/10-point.gif"), 
                    Util.createImageIcon("pretty_good", "../AchievementIcon/30-point.gif"), 
                    Util.createImageIcon("plant_destroyer", "../AchievementIcon/plant-destroyer.gif"), 
                    Util.createImageIcon("water_contaminant", "../AchievementIcon/water-contaminant.gif"), 
                    Util.createImageIcon("fence_finder", "../AchievementIcon/fence-finder.gif"), 
                    Util.createImageIcon("ambitious_dreamer", "../AchievementIcon/ambitious.gif"), 
                    Util.createImageIcon("donut_taster", "../AchievementIcon/donut-taster.gif"), 
                    Util.createImageIcon("donut_dealer", "../AchievementIcon/donut-dealer.gif"),
                    Util.createImageIcon("master_snake", "../AchievementIcon/60-point.gif"), 
                    Util.createImageIcon("cookie_lunatic", "../AchievementIcon/80-cookie.gif"), 
                    Util.createImageIcon("living_legend", "../AchievementIcon/90-point.gif"), };
            String[] condition_list = {"Start a new game for the first time to unlock this achievement",
                    "Die before eating any food to unlock this achievement",
                    "Eat 10 cookies to unlock this achievement",
                    "Eat 25 cookies to unlock this achievement",
                    "Achieve a score of 10 or better in a game to unlock this achievement",
                    "Achieve a score of 30 or better in a game to unlock this achievement",
                    "Crash into bushes or flower patches 15 times to unlock this achievement",
                    "Crash into fountains 15 times to unlock this achievement",
                    "Crash into fences 15 times to unlock this achievement",
                    "View the achievement page 10 times to unlock this achievement",
                    "Eat 15 donuts to unlock this achievement",
                    "Eat 45 donuts to unlock this achievement",
                    "Achieve a score of 60 or better in a game to unlock this achievement",
                    "Eat 80 cookies to unlock this achievement",
                    "Achieve a score of 90 or better in a game to unlock this achievement"};
            String[] description_list = {"There's a saying that goes \"a journey of a thousand miles begins with a single step\".  "
                    + "As a snake, despite not having feet, I can still start my journey.",
                    "How tragic it is, to die, before eating, amazing food..",
                    "Yum..crispy on the outside, gooey on the inside.",
                    "**diabetes alert**",
                    "I am on my way of becoming a great snake.",
                    "I would like to thank food for being my constant source of motivation...",
                    "It doesn't matter how much I love them, I don't think they appreciate my presence.",
                    "Didn't jump into the fountain properly......",
                    "Embrace the obstacles!  Literately.",
                    "Keep dreaming, keep faking.  I'll fake it until I make it!",
                    "I have proven that I even though I eat a lot of donuts, I can still move fast!",
                    "One donut a day, keeps sadness away!",
                    "I think I am qualified to call myself a pretty capable snake.",
                    "I used to own a cookie store, then I ate all the cookies in my store.",
                    "Thy new snake king has risen to the throne.  Bow to me, I have accomplished the impossible."};
            
            for(int i = 0; i < NUM_ROW; i++)
                for(int j = 0; j < NUM_COL; j++)
                    addIcon(new Position(H_MARGIN + i*(CELL_WIDTH+CELL_HGAP),
                            TOP_MARGIN + j*(CELL_HEIGHT+CELL_VGAP)), name_list[j*NUM_ROW+i], image_list[j*NUM_ROW+i],
                            condition_list[j*NUM_ROW+i], description_list[j*NUM_ROW+i]);
        }
        
        public void addIcon(Position position, String name, ImageIcon image, String condition, String description){
            iconList.add(new AchievementIcon(position, name, image, condition, description));
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
            g.setColor(new Color(200,200,200));
            g.fillRect(75, 505, 150, 25);
            g.fillRect(300, 0, Game.WIDTH-200, Game.HEIGHT);
            for (AchievementIcon ai : iconList){
                ai.render(g);
                if(ai.isHoverOver(menu.getXCursor(), menu.getYCursor())){
                    renderInfoBox(g);
                    ai.renderDetailInfo(g);
                }
            }
            
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setFont(FONT_CHART.get("UIFont16"));
            g.setColor(COLOR_CHART.get("UIColor"));
            
            g.setColor(COLOR_CHART.get("GBBorderColor"));
            g.drawRect(75, 505, 150, 25);
            g.drawString("Back to Main Menu", 85, 523);
        }
        
        public void renderInfoBox(Graphics g){
            g.setColor(COLOR_CHART.get("GBBorderColor"));
            g.drawRect(314, 40, 200, 270);
            g.setColor(COLOR_CHART.get("UIColor"));
            g.fillRect(314, 40, 200, 270);
        }
    }
}