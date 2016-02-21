package game_control;

import game_UI.Game;
import game_UI.GameWindows.AchievementWindow;

public class AchievementManager {
    static AchievementManager instance;
    LevelController levelController;
    Handler handler;
    AchievementWindow achievementWindow;
    
    int FIRST_STEP = 0, DEAD_ON_THE_SPOT = 5, COOKIE_LOVER = 10, COOKIE_COLLECTOR = 1, SO_SO_SNAKE = 6, PRETTY_GOOD = 11, 
            PLANT_DESTROYER = 2, WATER_CONTAMINANT = 7, FENCE_FINDER = 12, AMBITIOUS_DREAMER = 3, DONUT_TASTER = 8, 
            DONUT_DEALER = 13, MASTER_SNAKE = 4, COOKIE_LUNATIC = 9, LIVING_LEGEND = 14;
    
    int achievementClicks = 0;
    State prevState = null;

    private AchievementManager(LevelController level_controller, Handler handler, AchievementWindow achievementWindow) {
        this.levelController = level_controller;
        this.handler = handler;
        this.achievementWindow = achievementWindow;
    }

    public static AchievementManager getInstace(LevelController level_controller, Handler handler, AchievementWindow achievementWindow) {
        if (instance == null)
            instance = new AchievementManager(level_controller, handler, achievementWindow);

        return instance;
    }
    
    public void tick(){
        State currState = Game.gameState;
        
        if(currState==State.Game){
            achievementWindow.iconList.get(FIRST_STEP).setProgress(1.0);
        }
        achievementWindow.iconList.get(SO_SO_SNAKE).setProgress(Util.clampDouble((double)LevelController.bestScore/10, 0.0, 1.0));
        achievementWindow.iconList.get(PRETTY_GOOD).setProgress(Util.clampDouble((double)LevelController.bestScore/30, 0.0, 1.0));
        achievementWindow.iconList.get(MASTER_SNAKE).setProgress(Util.clampDouble((double)LevelController.bestScore/60, 0.0, 1.0));
        achievementWindow.iconList.get(LIVING_LEGEND).setProgress(Util.clampDouble((double)LevelController.bestScore/90, 0.0, 1.0));
    
        if(Collision.getCollisionObject()!=""){
            int iconIndex = 0;
            
            switch(Collision.getCollisionObject()){
            case "fence": iconIndex = FENCE_FINDER; break;
            case "fountain": iconIndex = WATER_CONTAMINANT; break;
            case "flower patch":
            case "bush": iconIndex = PLANT_DESTROYER; break;
            default: System.err.println("unidentifiable collision object name.");
            }
            
            Collision.resetCollisionObject();
            
            achievementWindow.iconList.get(iconIndex).incrementProgress(1/15.0);;
            
        }
        
        if(currState == State.Achievement && prevState!=State.Achievement){
            achievementClicks++;
            achievementWindow.iconList.get(AMBITIOUS_DREAMER).incrementProgress(0.1);;
        }
        prevState = currState;
        
        if(Collision.getfoodEaten()!=""){
            switch(Collision.getfoodEaten()){
            case "cookie": {
                achievementWindow.iconList.get(COOKIE_LOVER).incrementProgress(0.1);
                achievementWindow.iconList.get(COOKIE_COLLECTOR).incrementProgress(1/25.0);
                achievementWindow.iconList.get(COOKIE_LUNATIC).incrementProgress(1/80.0);
                break;
            }
            case "donut": {
                achievementWindow.iconList.get(DONUT_TASTER).incrementProgress(1/15.0);
                achievementWindow.iconList.get(DONUT_DEALER).incrementProgress(1/45.0);
                break;
            }
            default: System.err.println("unidentifiable food name.");
            }
            
            Collision.resetFoodEaten();
        }
        
        if(currState==State.Over && LevelController.getScore()==0)
            achievementWindow.iconList.get(DEAD_ON_THE_SPOT).setProgress(1.0);
    }
}