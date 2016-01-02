import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.util.concurrent.TimeUnit;

import javax.swing.Timer;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = -8519965885188987594L;
    public static final int WIDTH = 720, HEIGHT = 580;
    public static final int GRID_SIZE = 12;
    private static final int DELAY = 50;
    private Thread thread;
    private boolean running = false;
    private Timer timer;

    private static final Color UIColor = new Color(224, 224, 224);

    public static final int GB_X = 20, GB_Y = 60;
    private static final int GB_WIDTH = 480, GB_HEIGHT = 480;
    private static final Color GBColor = new Color(204, 229, 255);

    private Snake snake;
    private KeyController keyController;

    public Game() {
        new GameFrame(WIDTH, HEIGHT, "Snake Game Version 2.0", this);

        keyController = new KeyController();
        snake = new Snake(Category.Snake, keyController);

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
        if (keyController.getDirection() == null)
            snake.move(Direction.Right);
        else
            snake.move(keyController.getDirection());
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

        // game board
        g.setColor(GBColor);
        g.fillRect(GB_X, GB_Y, GB_WIDTH, GB_HEIGHT);

        // draw grid
        g.setColor(Color.gray);
        for (int a = GB_X; a < (GB_WIDTH + GB_X); a += GRID_SIZE) {
            for (int b = GB_Y; b < (GB_HEIGHT + GB_Y); b += GRID_SIZE) {
                g.drawRect(a, b, GRID_SIZE, GRID_SIZE);
            }
        }

        // draw snake
        snake.render(g);

        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        new Game();
    }
}