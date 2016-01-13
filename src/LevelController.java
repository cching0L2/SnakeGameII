import java.util.Random;

public class LevelController{
    Handler handler;
    HUD hud;
    Random random;
    
    public LevelController(Handler handler, HUD hud){
        this.handler = handler;
        this.hud = hud;
        random = new Random();
    }
    
    public int findLevel(int score) {
        // level scheme: 3, 3, 5, 5, 5, 5, 8, 8...
        // get the level corresponding to score
        if (score >= 0 && score < 3) // between 0 and 10
            return 1;
        else if (score >= 3 && score < 6)
            return 2;
        else if (score >= 6 && score < 11)
            return 3;
        else if (score >= 11 && score < 16)
            return 4;
        else if (score >= 16 && score < 21)
            return 5;
        else if (score >= 21 && score < 26)
            return 6;
        else if (score >= 26 && score < 34)
            return 7;
        else if (score >= 34 && score < 42)
            return 8;
        else
            return -1;
    }

    public int getScoreToLevelUp(int level) {
        switch (level) {
        case 1:
            return 3;
        case 2:
            return 5;
        case 3:
            return 5;
        case 4:
            return 5;
        case 5:
            return 5;
        case 6:
            return 8;
        case 7:
            return 8;
        default:
            return Integer.MAX_VALUE;
        }
    }

    public int getLevelMin(int level) {
        switch (level) {
        case 1:
            return 0;
        case 2:
            return 3;
        case 3:
            return 6;
        case 4:
            return 11;
        case 5:
            return 16;
        case 6:
            return 21;
        case 7:
            return 26;
        case 8:
            return 34;
        default:
            return Integer.MAX_VALUE;
        }
    }

    public boolean isLevelUp(int score) {
        if (score == 3 || score == 6 || score == 11 || score == 16 || score == 21 || score == 26 || score == 34
                || score == 42)
            return true;
        else
            return false;
    }
    
    public void tick(){
      for (int i = 0; i < handler.objects.size(); i++) {
      GameElements tempElement = handler.objects.get(i);
      if (tempElement.getCategory() == Category.Snake) {
          Snake tempSnake = (Snake) tempElement;
          hud.setScore(tempSnake.getSize() - tempSnake.getInitialSize()); 
          hud.setLevel(findLevel(hud.getScore())); 
      }
  }
    }
}