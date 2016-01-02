public class Util {
    
    public static int clamp(int val, int min, int max) {
        if (val < min)
            return min;
        else if (val > max)
            return max;
        else
            return val;
    }
    
    public static int pixToGrid(int pix){
        return pix / Game.GRID_SIZE;
    }
    
    public static int gridToPix(int grid){
        return grid * Game.GRID_SIZE;
    }
}