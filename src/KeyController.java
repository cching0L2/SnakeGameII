import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyController extends KeyAdapter {
    
    private Direction direction;

    public KeyController() {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP: {
                direction = Direction.Up;
                break;
            }
            case KeyEvent.VK_DOWN: {
                direction = Direction.Down;
                break;
            }
            case KeyEvent.VK_RIGHT: {
                direction = Direction.Right;
                break;
            }
            case KeyEvent.VK_LEFT: {
                direction = Direction.Left;
                break;
            }
            case KeyEvent.VK_ESCAPE: {
                System.exit(0);
                break;
            }
        }
    }
    
    public Direction getDirection(){
        return direction;
    }
}