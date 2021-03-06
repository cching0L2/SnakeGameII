package game_control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import game_UI.*;

public class KeyController extends KeyAdapter {
    
    private Direction direction;

    public KeyController() {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP: {
                if(Game.gameState == State.Game)
                    direction = Direction.Up;
                break;
            }
            case KeyEvent.VK_DOWN: {
                if(Game.gameState == State.Game)
                    direction = Direction.Down;
                break;
            }
            case KeyEvent.VK_RIGHT: {
                if(Game.gameState == State.Game)
                    direction = Direction.Right;
                break;
            }
            case KeyEvent.VK_LEFT: {
                if(Game.gameState == State.Game)
                    direction = Direction.Left;
                break;
            }
            case KeyEvent.VK_ESCAPE: {
                try {
                    Game.exitGame();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    System.exit(0);
                }
                break;
            }
            case KeyEvent.VK_SPACE: {
                //space -> pause and restart game
                if(Game.gameState == State.Game)
                    Game.gameState = State.Pause;
                else if (Game.gameState == State.Pause || Game.gameState == State.LevelUp)
                    Game.gameState = State.Game;
            }
        }
    }
    
    public Direction getDirection(){
        return direction;
    }
    
    public void resetInitialDirection(Direction initialDirection){
        direction = initialDirection;
    }
}