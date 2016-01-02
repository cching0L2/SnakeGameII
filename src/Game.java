import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = -8519965885188987594L;
    public static final int WIDTH = 720, HEIGHT = 580;
    public static final int GRID_SIZE = 12;
    private static final int DELAY = 50;
    private Thread thread;
    private boolean running = false;
    private Timer timer;
    private Random random;

    private static final Color UIColor = new Color(224, 224, 224);

    public static final int GB_X = GRID_SIZE * 2, GB_Y = GRID_SIZE * 5;
    public static final int GB_WIDTH = 480, GB_HEIGHT = 480;
    private static final Color GBColor = new Color(204, 229, 255);
    private static final Color GBBorderColor = new Color(106, 90, 205);
    private final ImageIcon gameOverImage = Util.createImageIcon("game over image", "image/game_over.gif");

    private KeyController keyController;
    private Handler handler;

    public static State gameState = State.Game;

    public Game() {
        new GameFrame(WIDTH, HEIGHT, "Snake Game Version 2.0", this);

        random = new Random();
        handler = new Handler();
        keyController = new KeyController();
        handler.addObject(new Snake(Category.Snake, keyController, handler));
        handler.addObject(new Cookie(new Position(random.nextInt(Util.pixToGrid(GB_WIDTH)) * GRID_SIZE,
                random.nextInt(Util.pixToGrid(GB_HEIGHT)) * GRID_SIZE), Category.Food));

        this.addKeyListener(keyController);
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
        if (gameState == State.Game)
            handler.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        // background
        g.setColor(UIColor);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // draw game board
        g.setColor(GBColor);
        g.fillRect(GB_X, GB_Y, GB_WIDTH, GB_HEIGHT);

        // draw game board border
        g.setColor(GBBorderColor);
        g.drawRect(GB_X, GB_Y, GB_WIDTH, GB_HEIGHT);

        // draw grid
        g.setColor(Color.gray);
//        for (int a = GB_X; a < (GB_WIDTH + GB_X); a += GRID_SIZE) {
//            for (int b = GB_Y; b < (GB_HEIGHT + GB_Y); b += GRID_SIZE) {
//                g.drawRect(a, b, GRID_SIZE, GRID_SIZE);
//            }
//        }

        // draw snake and game objects
        if(gameState == State.Game){
            handler.render(g);
        }
        if(gameState == State.Over){
            g.setFont(new Font("Courier", Font.BOLD, 30));
            g.drawImage(gameOverImage.getImage(), 150, 250, this);
        }

        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        new Game();
    }
}