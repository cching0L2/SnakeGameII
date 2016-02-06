package game_UI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.Timer;

import game_control.Handler;
import game_control.KeyController;
import game_control.LevelController;
import game_control.Menu;
import game_control.State;
import game_elements.Category;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = -8519965885188987594L;
    public static final int GRID_SIZE = 12;
    public static final int GB_X = GRID_SIZE * 2, GB_Y = GRID_SIZE * 5;
    public static final int GB_WIDTH = 480, GB_HEIGHT = 480;
    public static final int NUM_GRID_PER_SIDE = GB_WIDTH/GRID_SIZE;
    public static final int WIDTH = GB_WIDTH + GB_X * 2, HEIGHT = GB_HEIGHT + GB_Y * 2;
    private static final int DELAY = 50;
    private Thread thread;
    private boolean running = false;
    private Timer timer;

    public static final Map<String, Color> COLOR_CHART = new HashMap<String, Color>();
    public static final Map<String, Font> FONT_CHART = new HashMap<String, Font>();
    
    public static final Color UIColor = new Color(224, 224, 224);
    public static final Color GBColor = new Color(204, 229, 255);
    public static final Color NoticeColor = new Color(153, 204, 255);
    public static final Color GBBorderColor = new Color(106, 90, 205);
    public static final Color GBButtonColor = UIColor;
    public static final Color StatementColor = new Color(200, 200, 200);
    public static final Color PBColor = new Color(140, 140, 140);
    public static final Color SelectColor = new Color(102, 102, 255);
    
    public static final Font UIFont = new Font("Eurostile", Font.PLAIN, 16);
    public static final Font UIFont12B = new Font("Eurostile", Font.BOLD, 12);
    public static final Font UIFont20B = new Font("Eurostile", Font.BOLD, 20);
    public static final Font UIFont24 = new Font("Eurostile", Font.PLAIN, 28);
    public static final Font UIFont40 = new Font("Eurostile", Font.PLAIN, 40);
    public static final Font TitleFont = new Font("Noteworthy", Font.PLAIN, 80);
    
    private KeyController keyController;
    private Handler handler;
    private Handler introAnimationHandler;
    private HUD hud;
    private Menu menu;
    private ProgressBar progressBar;
    private LevelController levelController;
    private Animation animation;
    
    private GameWindows gameWindows;

    public static State gameState = State.Menu;

    public Game() throws InterruptedException {

        handler = new Handler();
        introAnimationHandler = new Handler();
        keyController = new KeyController();
        hud = new HUD();
        levelController = new LevelController(handler, hud, keyController);
        progressBar = new ProgressBar(hud, levelController);
        TimeUnit.SECONDS.sleep(1);
        animation = new Animation(introAnimationHandler, keyController);
        
        for(int i = 0; i<20; i++){
            introAnimationHandler.addObject(new IntroSnake(Category.Snake, keyController, handler));
        }
        
        menu = new Menu(levelController, hud, handler);

        this.addKeyListener(keyController);
        this.addMouseListener(menu);
        this.addMouseMotionListener(menu);
        
        initializeColorsFonts();
        gameWindows = new GameWindows();
        
        new GameFrame(WIDTH, HEIGHT, "Snake Game Version 2.0", this);
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

    }

    @Override
    public void run() {
        this.requestFocus();
        timer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (running) {
                    tick();
                    render();
                }
            }
        });
        timer.start();
    }

    private void tick() {
        if (gameState == State.Menu) {
            animation.tick();
        }

        else if (gameState == State.Game) {
            handler.tick();
            levelController.tick();
            progressBar.tick();
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setFont(UIFont);

        // background
        g.setColor(UIColor);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        if (gameState != State.Menu) {
            // draw game board
            g.setColor(GBColor);
            g.fillRect(GB_X, GB_Y, GB_WIDTH, GB_HEIGHT);

            // draw game board border
            g.setColor(GBBorderColor);
            g.drawRect(GB_X, GB_Y, GB_WIDTH, GB_HEIGHT);

            // draw grid
            g.setColor(Color.gray);
            // for (int a = GB_X; a < (GB_WIDTH + GB_X); a += GRID_SIZE) {
            // for (int b = GB_Y; b < (GB_HEIGHT + GB_Y); b += GRID_SIZE) {
            // g.drawRect(a, b, GRID_SIZE, GRID_SIZE);
            // }
            // }
        }

        // draw snake and game objects
        if (gameState == State.Game) {
            handler.render(g);
            hud.render(g);
            progressBar.render(g);

        } else if (gameState == State.Menu) {
            animation.render(g);
            
            g.setColor(GBBorderColor);
            g.setFont(TitleFont);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.drawString("SnakeGame II", 60, 160);
            g.setFont(UIFont12B);
            g.setColor(StatementColor);
            g.drawString("Designed and made by Camellia Peng.  All rights reserved.", 120, 565);
            
            g.setColor(PBColor);
            g.setFont(UIFont20B);
            if(menu.getCurrentSelect().equals("New Game")){
                g.setColor(SelectColor);
            }
            else{
                g.setColor(PBColor);
            }
            g.drawString("New Game", 220, 270);
            
            if(menu.getCurrentSelect().equals("Achievements")){
                g.setColor(SelectColor);
            }
            else{
                g.setColor(PBColor);
            }
            g.drawString("Achievements", 208, 310);
            
            if(menu.getCurrentSelect().equals("Quit")){
                g.setColor(SelectColor);
            }
            else{
                g.setColor(PBColor);
            }
            g.drawString("Quit", 250, 350);
            
            if(menu.getCurrentSelect().equals("Reset Game")){
                g.setColor(SelectColor);
            }
            else{
                g.setColor(PBColor);
            }
            g.drawString("Reset Game", 218, 390);
        }

        else if (gameState == State.Over) {
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gameWindows.renderOverWindow(g, levelController);
        }

        else if (gameState == State.Pause) {
            gameWindows.renderPauseWindow(g);
        }

        g.dispose();
        bs.show();
    }
    
    private static void initializeColorsFonts(){
      COLOR_CHART.put("UIColor", new Color(224, 224, 224));
      COLOR_CHART.put("GBColor", new Color(204, 229, 255));
      COLOR_CHART.put("NoticeColor", new Color(153, 204, 255));
      COLOR_CHART.put("GBBorderColor", new Color(106, 90, 205));
      COLOR_CHART.put("GBButtonColor", new Color(224, 224, 224));
      COLOR_CHART.put("StatementColor", new Color(200, 200, 200));
      COLOR_CHART.put("PBColor", new Color(140, 140, 140));
      COLOR_CHART.put("SelectColor", new Color(102, 102, 255));
      
      FONT_CHART.put("UIFont", new Font("Eurostile", Font.PLAIN, 16));
      FONT_CHART.put("UIFont12B", new Font("Eurostile", Font.BOLD, 12));
      FONT_CHART.put("UIFont20B", new Font("Eurostile", Font.BOLD, 20));
      FONT_CHART.put("UIFont24", new Font("Eurostile", Font.PLAIN, 28));
      FONT_CHART.put("UIFont20", new Font("Eurostile", Font.PLAIN, 40));
      FONT_CHART.put("TitleFont", new Font("Noteworthy", Font.PLAIN, 80));
    }
    
    public static Map<String, Color> getColorChart(){
        return COLOR_CHART;
    }
    
    public static Map<String, Font> getFontChart(){
        return FONT_CHART;
    }

    public static void main(String[] args) throws InterruptedException {
        new Game();
    }
}