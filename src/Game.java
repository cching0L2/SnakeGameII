import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = -8519965885188987594L;
    public static final int WIDTH = 720, HEIGHT = 580;
    private Thread thread;
    private boolean running = false;
    
    private static final Color UIColor = new Color(224, 224, 224);
    
    private static final int GB_WIDTH = 480, GB_HEIGHT = 480;
    private static final Color GBColor = new Color(204, 229, 255);

    public Game() {
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
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000/amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now-lastTime)/ns;
            lastTime = now;
            while(delta>=1){
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;
            
            if(System.currentTimeMillis() - timer>1000){
                timer += 1000;
                System.out.println("FPS: "+frames);
                frames = 0;
            }
        }
        stop();
    }
    
    private void tick(){
        
    }
    
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        
        //background
        g.setColor(UIColor);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        //game board 
        g.setColor(GBColor);
        g.fillRect(20, 60, GB_WIDTH, GB_HEIGHT);
        
        g.dispose();
        bs.show();
    }
    

    public static void main(String[] args) {
        new Game();
    }
}