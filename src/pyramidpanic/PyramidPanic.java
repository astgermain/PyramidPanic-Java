package pyramidpanic;


public class PyramidPanic{
    private boolean newGame = false;
    private boolean gameOver = false;
    public static boolean gameWin = false;
    private boolean titleScreen = true;
    public static void main(String atgs[]){
        
        new GameWorld().start();
      
    }
}
