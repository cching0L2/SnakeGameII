import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = -8519965885188987594L;
    public static final int GRID_SIZE = 12;
    public static final int GB_X = GRID_SIZE * 2, GB_Y = GRID_SIZE * 5;
    public static final int GB_WIDTH = 480, GB_HEIGHT = 480;
    public static final int WIDTH = GB_WIDTH + GB_X * 2, HEIGHT = GB_HEIGHT + GB_Y * 2;
    private static final int DELAY = 50;
    private Thread thread;
    private boolean running = false;
    private Timer timer;
    private Random random;

    public static final Color UIColor = new Color(224, 224, 224);
    public static final Color GBColor = new Color(204, 229, 255);
    public static final Color NoticeColor = new Color(153, 204, 255);
    public static final Color GBBorderColor = new Color(106, 90, 205);
    public static final Color GBButtonColor = UIColor;
    public static final Color PBColor = new Color(140, 140, 140);
    
    public static final Font UIFont = new Font("Eurostile", Font.PLAIN, 16);
    public static final Font UIFont12B = new Font("Eurostile", Font.BOLD, 12);
    public static final Font UIFont20B = new Font("Eurostile", Font.BOLD, 20);
    public static final Font UIFont24 = new Font("Eurostile", Font.PLAIN, 28);
    public static final Font UIFont40 = new Font("Eurostile", Font.PLAIN, 40);
    public static final Font TitleFont = new Font("Noteworthy", Font.PLAIN, 80);

    private ImageIcon cursorIcon = Util.createImageIcon("cookie image", "image/cookie.gif");
    
    private KeyController keyController;
    private Handler handler;
    private HUD hud;
    private Menu menu;
    private ProgressBar progressBar;

    public static State gameState = State.Menu;

    public Game() {
        new GameFrame(WIDTH, HEIGHT, "Snake Game Version 2.0", this);

        random = new Random();
        handler = new Handler();
        keyController = new KeyController();
        hud = new HUD();
        Snake snake = new Snake(Category.Snake, keyController, handler, hud);
        progressBar = new ProgressBar(snake, hud);
        handler.addObject(snake);
        handler.addObject(new Cookie(new Position(random.nextInt(Util.pixToGrid(GB_WIDTH)) * GRID_SIZE,
                random.nextInt(Util.pixToGrid(GB_HEIGHT)) * GRID_SIZE), Category.Food));
        menu = new Menu(keyController, hud, handler);

        this.addKeyListener(keyController);
        this.addMouseListener(menu);
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

        }

        else if (gameState == State.Game) {
            handler.tick();
            for (int i = 0; i < handler.objects.size(); i++) {
                GameElements tempElement = handler.objects.get(i);
                if (tempElement.getCategory() == Category.Snake) {
                    Snake tempSnake = (Snake) tempElement;
                    hud.setScore(tempSnake.getSize() - tempSnake.getInitialSize()); // update
                                                                                    // score
                    hud.setLevel(progressBar.findLevel(hud.getScore())); // get
                                                                         // level
                }
            }
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
            g.setColor(GBBorderColor);
            g.setFont(TitleFont);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.drawString("SnakeGame II", 50, 110);
            
            g.setColor(PBColor);
            g.setFont(UIFont20B);
            g.drawString("New Game", 90, 270);
            g.drawString("Achievements", 90, 310);
            g.drawString("Quit", 90, 350);
            g.drawString("Reset Game", 90, 390);
            
            g.drawRect(80, 250, 115, 30);
            g.drawRect(80, 290, 200, 30);
            g.drawRect(80, 330, 200, 30);
            g.drawRect(80, 370, 200, 30);
        }

        else if (gameState == State.Over) {
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            g.setColor(NoticeColor);
            g.fillRect(GB_X + (GB_WIDTH - 200) / 2, GB_Y + (GB_HEIGHT - 100) / 4, 200, 280);
            g.setColor(GBBorderColor);
            g.drawRect(GB_X + (GB_WIDTH - 200) / 2, GB_Y + (GB_HEIGHT - 100) / 4, 200, 280);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.drawString("Game over...", 175, 185);
            g.drawString("You lost with a score of: ", 175, 205);
            g.setColor(GBBorderColor);
            g.setFont(UIFont24);
            g.drawString(Integer.toString(hud.getScore()), 245, 240);

            g.setFont(UIFont20B);
            g.setColor(GBButtonColor);
            g.fillRect(185, 270, 155, 40);
            g.setColor(GBBorderColor);
            g.drawRect(185, 270, 155, 40);
            g.drawString("New Game", 220, 295);

            g.setColor(GBButtonColor);
            g.fillRect(185, 325, 155, 40);
            g.setColor(GBBorderColor);
            g.drawRect(185, 325, 155, 40);
            g.drawString("Menu", 240, 350);

            g.setColor(GBButtonColor);
            g.fillRect(185, 380, 155, 40);
            g.setColor(GBBorderColor);
            g.drawRect(185, 380, 155, 40);
            g.drawString("Quit", 247, 405);
        }

        else if (gameState == State.Pause) {
            // draw pause state graphics
            g.setColor(NoticeColor);
            g.fillRect(GB_X + (GB_WIDTH - 200) / 2, GB_Y + (GB_HEIGHT - 100) / 4, 200, 280);
            g.setColor(GBBorderColor);
            g.drawRect(GB_X + (GB_WIDTH - 200) / 2, GB_Y + (GB_HEIGHT - 100) / 4, 200, 280);
            g.setFont(UIFont);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.drawString("Press SPACE bar to", 185, 195);
            g.drawString("resume game", 185, 215);
            g.drawString("or select one of the", 185, 245);
            g.drawString("following options:", 185, 265);
            g.setColor(GBButtonColor);
            g.fillRect(185, 295, 155, 40);
            g.setColor(GBBorderColor);
            g.drawRect(185, 295, 155, 40);
            g.setFont(UIFont20B);
            g.drawString("Menu", 240, 320);
            g.setColor(GBButtonColor);
            g.fillRect(185, 355, 155, 40);
            g.setColor(GBBorderColor);
            g.drawRect(185, 355, 155, 40);
            g.drawString("Quit", 247, 380);
        }

        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        new Game();
    }
}