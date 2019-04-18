package pyramidpanic;

/**
 *
 * @author A
 */
abstract public class Enemies extends GameObject{
    
    private int health;
    private boolean isAlive;
    public Enemies(int x, int y, int objectType) {
        super(x, y, objectType);
    }
    public int getHealth(){
        return this.health;
    }
    public void setHealth(int health){
        this.health = health;
    }
    
}
