public class HUD{
    public static int score = 0;
    public static int level = 0;
    
    public int getScore(){
        return score;
    }
    
    public void setScore(int score){
        HUD.score = score;
    }
    
    public int getLevel(){
        return level;
    }
    
    public void setLevel(int level){
        HUD.level = level;
    }
}