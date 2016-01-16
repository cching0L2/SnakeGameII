package game_UI;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class GameFrame extends Canvas{

    private static final long serialVersionUID = -8519965885188987594L;
    
    public GameFrame(int width, int height, String title, Game game){
        JFrame frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        game.start();
    }
}