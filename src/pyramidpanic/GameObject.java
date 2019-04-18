package pyramidpanic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class GameObject {
    private BufferedImage image;
    private int x, y, width, height, speed, objectType, resetTmp;
    public int ox, oy;
    private boolean exists, solid, isLeft, isRight, isUp, isDown, reset, move;
    GameWorld game;
    
    public GameObject(int x, int y, int objectType){
        this.x = x;
        this.y = y;
        this.ox = x;
        this.oy = y;
        this.width = 32;
        this.height = 32;
        this.objectType = objectType;
        this.solid = true;
        this.reset = true;
        this.move = true;
    }
    
    public void update(GameWorld game){
        this.game = game;
        
    }
    public void paint(Graphics g, GameWorld game){
        
    }
    
    public BufferedImage getImage(){
        return this.image;
    }
    public boolean isSolid(){
        return solid;
    }
    public void setSolid(boolean b){
        this.solid = b;
    }
    public void setImage(BufferedImage image){
        this.image = image;
    }
    public int getX(){
        return this.x;
    }
    public void setX(int x){
        this.x = x;
    }
    public int getY(){
        return this.y;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getSpeed(){
        return this.speed;
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }
    public int getObjectType(){
        return objectType;
    }
    public void setObjectType(int i){
        this.objectType = i;
    }
    
    public void setMove(boolean mv){
        this.move = mv;
    }
    public boolean canMove(){
        return this.move;
    }

    public void setLeft(){
        setX(getX() - 1);
        isUp = false;
        isDown = false;
        isLeft = true;
        isRight = false;
    }
    public void setRight(){
        setX(getX() + 1);
        isUp = false;
        isDown = false;
        isLeft = false;
        isRight = true;
    }
    public void setUp() {
        isUp = true;
        isDown = false;
        isLeft = false;
        isRight = false;
    }

    public void setDown() {
        isUp = false;
        isDown = true;
        isLeft = false;
        isRight = false;
    }
    public boolean getUp(){
        return isUp;
    }
    public boolean getDown(){
        return isDown;
    }
    public boolean getLeft(){
        return isLeft;
    }
    public boolean getRight(){
        return isRight;
    }
    
    
}
