/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pyramidpanic;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;

/**
 *
 * @author A
 */
public class Player extends GameObject {

    private boolean up, down, left, right, light, hasLight;
    public boolean canMove, canShoot, drawWinBool, drawLoseBool;
    private int score, lives, tempX, tempY, lastMovement, lastInput, spawnX, spawnY, level;
   
    GameWorld game;
    GamePhysics gp;
    BufferedImage img;

    public Player(int x, int y, int objectType) {
        super(x, y, objectType);
        this.spawnX = 32;
        this.spawnY = 32;
        setX(32);
        setY(32);
        score = 0;
        up = false;
        down = false;
        left = false;
        right = false;
        lives = 3;
        lastMovement = 0;
        canMove = false;
        canShoot = false;
        level = 1;
        light = false;
        hasLight = false;
        drawWinBool = false;
        drawLoseBool = false;
                
    }

    public void update(GameWorld game) {
        
        this.game = game;
        img = game.getBimg(getObjectType());
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                canMove = true;

            }
        }, 1000, 3000);
        this.gp = new GamePhysics();
        if (game.getPlayer().canMove) {
            if (gp.noPlayerCollision(game)) {
                if (up) {
                    this.moveUp();
                    this.setObjectType(19);
                    lastInput = 0;
                    canMove = false;
                } else if (down) {
                    this.moveDown();
                    this.setObjectType(20);
                    lastInput = 1;
                    canMove = false;
                } else if (left) {
                    this.moveLeft();
                    this.setObjectType(21);
                    lastInput = 2;
                    canMove = false;
                } else if (right) {
                    this.moveRight();
                    this.setObjectType(22);
                    lastInput = 3;
                    canMove = false;
                }
            } else if (!(gp.noPlayerCollision(game))) {
                if (getObjectType() == 19) {
                    tempY = getY() + 2;
                    setY(tempY);
                } else if (getObjectType() == 20) {
                    tempY = getY() - 2;
                    setY(tempY);
                } else if (getObjectType() == 21) {
                    tempX = getX() + 2;
                    setX(tempX);
                } else if (getObjectType() == 22) {
                    tempX = getX() - 2;
                    setX(tempX);
                }
            }
            if (light == true) {
                score = score - 20;
            } else if (lastInput == 0 && (this.getY() % 32 != 0)) {
                this.setY(this.getY() - 1);
            } else if (lastInput == 1 && (this.getY() % 32 != 0)) {
                this.setY(this.getY() + 1);
            } else if (lastInput == 2 && (this.getX() % 32 != 0)) {
                this.setX(this.getX() - 1);
            } else if (lastInput == 3 && (this.getX() % 32 != 0)) {
                this.setX(this.getX() + 1);
            }
            
        }

        //System.out.println("x: " + this.getX() + " y: " + this.getY());
        //System.out.println("x: " + ox + " y: " + oy);
    }

    //May not need
    public void paint(Graphics g){
       // doesn't even work
      
        g.drawImage(img, game.cameraX, game.cameraY, null);
    }

    public void moveUp() {
        tempY = getY() - 1;
        setY(tempY);
    }
    public int getLevel(){
        return level;
    }
    public void setLevel(int level){
        this.level = level;
    }

    public void moveDown() {
        tempY = getY() + 1;
        setY(tempY);
    }

    public void moveLeft() {
        tempX = getX() - 1;
        setX(tempX);
    }

    public void moveRight() {
        tempX = getX() + 1;
        setX(tempX);
        
    }
    public void canLight(boolean bp){
        hasLight = bp;
    }
    public boolean hLight(){
        return hasLight;
    }
        
    public boolean mapLight(){
        return light;
    }
    public void setMapLight(boolean lt){
        light = lt;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
        
    }

    public void toogleUp() {
        up = true;
    }

    public void toogleDown() {
        down = true;
    }

    public void toogleRight() {
        right = true;
    }

    public void toogleLeft() {
        left = true;
    }

    public void toogleLight() {
        light = true;
    }

    public void unToogleUp() {
        up = false;
    }

    public void unToogleDown() {
        down = false;
    }

    public void unToogleRight() {
        right = false;
    }

    public void unToogleLeft() {
        left = false;
    }

    public void unToogleLight() {
        light = false;
    }

    public void died() {
        if (lives > 0) {
            lives--;
        } else {
            isDead();
        }
        
    }

    public int getLives() {
        return lives;
    }

    public void setLives(GameWorld game) {
        if(lives == 1){
            drawLoseBool = true;
            
        }
        this.lives--;
        
    }
    public void setLivesUp(GameWorld game) {
        this.lives++;
        
    }
    public void setCanShoot(){
        canShoot = true;
    }

    public void shoot(){
        if(canShoot){
            System.out.println("bang");
        }
    }
    public void setSpawn(GameWorld game) {
        game.getPlayer().setX(this.spawnX);
        game.getPlayer().setY(this.spawnY);
    }

    public void isDead() {
        //Terminate to gameover screen
    }

}
