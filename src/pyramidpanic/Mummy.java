package pyramidpanic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Mummy extends Enemies {

    private boolean isLeft, isRight, isUp, isDown, inMotion;
    private GameWorld game;
    private GamePhysics gp;
    private int lastInput;
    ;
    BufferedImage img;

    public Mummy(int x, int y, int objectType) {
        super(x, y, objectType);
        if (objectType == 13) {
            setUp();
            setLastInput(0);
        } else if (objectType == 14) {
            setDown();
            setLastInput(1);
        } else if (objectType == 15) {
            setLeft();
            setLastInput(2);
        } else {
            setRight();
            setLastInput(3);
        }
    }

    public void update(GameWorld game) {
        this.gp = new GamePhysics();
        img = game.getBimg(getObjectType());

        int x = new Random().nextInt(4);
        if (getX() % 32 != 0 && getY() % 32 != 0) {
            inMotion = true;
        } else {
            inMotion = false;
        }

        if (gp.noMummyCollision(game, this) && (!inMotion)) {

            if (getLeft()) {
                setX(getX() - 1);
            } else if (getRight()) {
                setX(getX() + 1);
            } else if (getUp()) {
                setY(getY() + 1);
            } else {
                setY(getY() - 1);
            }

        } 
        else if (!(gp.noMummyCollision(game, this))) {
            if(getUp()){
                setY(getY() - 1);
            }
            else if(getDown()){
                setY(getY() + 1);
            }
            else if(getLeft()){
                setX(getX() - 1);
            }
            else if(getRight()){
                setX(getX() + 1);
            }
            
            if (x == 0 && !getUp()) {
                setUp();
                setLastInput(0);
            } else if (x == 1 && lastInput != 1) {
                setDown();
                setLastInput(1);
            } else if (x == 2 && lastInput != 2) {
                setLeft();
                setLastInput(2);
            } else if(x == 3 && lastInput != 3) {
                setRight();
                setLastInput(3);
            }
        }
        else if(inMotion){
             if(lastInput == 0){
                 setY(getY() + 1);
             }
             else if(lastInput == 1){
                 setY(getY() - 1);
             }
             else if(lastInput == 2){
                  setX(getX() - 1);
             }
             else if(lastInput == 3){
                 setX(getX() + 1);
             }
         }

    }

    public void paint(Graphics g) {
        g.drawImage(img, getX() - game.getPlayer().getX(), getY() - game.getPlayer().getY(), null);

    }

    public void setLastInput(int i) {
        this.lastInput = i;
    }

    public int getLastInput() {
        return lastInput;
    }

}
